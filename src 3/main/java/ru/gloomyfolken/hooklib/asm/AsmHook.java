package ru.gloomyfolken.hooklib.asm;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import ru.gloomyfolken.hooklib.asm.HookInjectorFactory.MethodEnter;
import ru.gloomyfolken.hooklib.asm.HookInjectorFactory.MethodExit;

import java.util.ArrayList;
import java.util.List;

import static org.objectweb.asm.Opcodes.*;
import static org.objectweb.asm.Type.*;

/**
 * Класс, отвечающий за установку одного хука в один метод.
 * Терминология:
 * hook (хук) - вызов вашего статического метода из стороннего кода (майнкрафта, форджа, других модов)
 * targetMethod (целевой метод) - метод, куда вставляется хук
 * targetClass (целевой класс) - класс, где находится метод, куда вставляется хук
 * hookMethod (хук-метод) - ваш статический метод, который вызывается из стороннего кода
 * hookClass (класс с хуком) - класс, в котором содержится хук-метод
 */
public class AsmHook implements Cloneable, Comparable<AsmHook> {

    private String targetClassName; // через точки
    private String targetMethodName;
    private List<Type> targetMethodParameters = new ArrayList<Type>(2);
    private Type targetMethodReturnType; //если не задано, то не проверяется

    private String hooksClassName; // через точки
    private String hookMethodName;
    // -1 - значение return
    private List<Integer> transmittableVariableIds = new ArrayList<Integer>(2);
    private List<Type> hookMethodParameters = new ArrayList<Type>(2);
    private Type hookMethodReturnType = Type.VOID_TYPE;
    private boolean hasReturnValueParameter; // если в хук-метод передается значение из return

    private ReturnCondition returnCondition = ReturnCondition.NEVER;
    private ReturnValue returnValue = ReturnValue.VOID;
    private Object primitiveConstant;

    private HookInjectorFactory injectorFactory = ON_ENTER_FACTORY;
    private HookPriority priority = HookPriority.NORMAL;

    public static final HookInjectorFactory ON_ENTER_FACTORY = MethodEnter.INSTANCE;
    public static final HookInjectorFactory ON_EXIT_FACTORY = MethodExit.INSTANCE;

    // может быть без возвращаемого типа
    private String targetMethodDescription;
    private String hookMethodDescription;
    private String returnMethodName;
    // может быть без возвращаемого типа
    private String returnMethodDescription;

    private boolean createMethod;
    private boolean isMandatory;

    protected String getTargetClassName() {
        return targetClassName;
    }

    private String getTargetClassInternalName() {
        return targetClassName.replace('.', '/');
    }

    private String getHookClassInternalName() {
        return hooksClassName.replace('.', '/');
    }

    protected boolean isTargetMethod(String name, String desc) {
        return (targetMethodReturnType == null && desc.startsWith(targetMethodDescription) ||
                desc.equals(targetMethodDescription)) && name.equals(targetMethodName);
    }

    protected boolean getCreateMethod() {
        return createMethod;
    }

    protected boolean isMandatory() {
         return isMandatory;
    }

    protected HookInjectorFactory getInjectorFactory() {
        return injectorFactory;
    }

    private boolean hasHookMethod() {
        return hookMethodName != null && hooksClassName != null;
    }

    protected void createMethod(HookInjectorClassVisitor classVisitor) {
        ClassMetadataReader.MethodReference superMethod = classVisitor.transformer.classMetadataReader
                .findVirtualMethod(getTargetClassInternalName(), targetMethodName, targetMethodDescription);
        // юзаем название суперметода, потому что findVirtualMethod может вернуть метод с другим названием
        MethodVisitor mv = classVisitor.visitMethod(Opcodes.ACC_PUBLIC,
                superMethod == null ? targetMethodName : superMethod.name, targetMethodDescription, null, null);
        if (mv instanceof HookInjectorMethodVisitor) {
            HookInjectorMethodVisitor inj = (HookInjectorMethodVisitor) mv;
            inj.visitCode();
            inj.visitLabel(new Label());
            if (superMethod == null) {
                injectDefaultValue(inj, targetMethodReturnType);
            } else {
                injectSuperCall(inj, superMethod);
            }
            injectReturn(inj, targetMethodReturnType);
            inj.visitLabel(new Label());
            inj.visitMaxs(0, 0);
            inj.visitEnd();
        } else {
            throw new IllegalArgumentException("Hook injector not created");
        }
    }

    protected void inject(HookInjectorMethodVisitor inj) {
        Type targetMethodReturnType = inj.methodType.getReturnType();

        // сохраняем значение, которое было передано return в локальную переменную
        int returnLocalId = -1;
        if (hasReturnValueParameter) {
            returnLocalId = inj.newLocal(targetMethodReturnType);
            inj.visitVarInsn(targetMethodReturnType.getOpcode(54), returnLocalId); //storeLocal
        }

        // вызываем хук-метод
        int hookResultLocalId = -1;
        if (hasHookMethod()) {
            injectInvokeStatic(inj, returnLocalId, hookMethodName, hookMethodDescription);

            if (returnValue == ReturnValue.HOOK_RETURN_VALUE || returnCondition.requiresCondition) {
                hookResultLocalId = inj.newLocal(hookMethodReturnType);
                inj.visitVarInsn(hookMethodReturnType.getOpcode(54), hookResultLocalId); //storeLocal
            }
        }

        // вызываем return
        if (returnCondition != ReturnCondition.NEVER) {
            Label label = inj.newLabel();

            // вставляем GOTO-переход к label'у после вызова return
            if (returnCondition != ReturnCondition.ALWAYS) {
                inj.visitVarInsn(hookMethodReturnType.getOpcode(21), hookResultLocalId); //loadLocal
                if (returnCondition == ReturnCondition.ON_TRUE) {
                    inj.visitJumpInsn(IFEQ, label);
                } else if (returnCondition == ReturnCondition.ON_NULL) {
                    inj.visitJumpInsn(IFNONNULL, label);
                } else if (returnCondition == ReturnCondition.ON_NOT_NULL) {
                    inj.visitJumpInsn(IFNULL, label);
                }
            }

            // вставляем в стак значение, которое необходимо вернуть
            if (returnValue == ReturnValue.NULL) {
                inj.visitInsn(Opcodes.ACONST_NULL);
            } else if (returnValue == ReturnValue.PRIMITIVE_CONSTANT) {
                inj.visitLdcInsn(primitiveConstant);
            } else if (returnValue == ReturnValue.HOOK_RETURN_VALUE) {
                inj.visitVarInsn(hookMethodReturnType.getOpcode(21), hookResultLocalId); //loadLocal
            } else if (returnValue == ReturnValue.ANOTHER_METHOD_RETURN_VALUE) {
                String returnMethodDescription = .returnMethodDescription;
                // если не был определён заранее нужный возвращаемый тип, то добавляем его к описанию
                if (returnMethodDescription.endsWith(")")) {
                    returnMethodDescription += targetMethodReturnType.getDescriptor();
                }
                injectInvokeStatic(inj, returnLocalId, returnMethodName, returnMethodDescription);
            }

            // вызываем return
            injectReturn(inj, targetMethodReturnType);

            // вставляем label, к которому идет GOTO-переход
            inj.visitLabel(label);
        }

        //кладем в стек значение, которое шло в return
        if (hasReturnValueParameter) {
            injectLoad(inj, targetMethodReturnType, returnLocalId);
        }
    }

    private void injectLoad(HookInjectorMethodVisitor inj, Type parameterType, int variableId) {
        int opcode;
        if (parameterType == INT_TYPE || parameterType == BYTE_TYPE || parameterType == CHAR_TYPE ||
                parameterType == BOOLEAN_TYPE || parameterType == SHORT_TYPE) {
            opcode = ILOAD;
        } else if (parameterType == LONG_TYPE) {
            opcode = LLOAD;
        } else if (parameterType == FLOAT_TYPE) {
            opcode = FLOAD;
        } else if (parameterType == DOUBLE_TYPE) {
            opcode = DLOAD;
        } else {
            opcode = ALOAD;
        }
        inj.visitVarInsn(opcode, variableId);
    }

    private void injectSuperCall(HookInjectorMethodVisitor inj, ClassMetadataReader.MethodReference method) {
        int variableId = 0;
        for (int i = 0; i <= targetMethodParameters.size(); i++) {
            Type parameterType = i == 0 ? TypeHelper.getType(targetClassName) : targetMethodParameters.get(i - 1);
            injectLoad(inj, parameterType, variableId);
            if (parameterType.getSort() == Type.DOUBLE || parameterType.getSort() == Type.LONG) {
                variableId += 2;
            } else {
                variableId++;
            }
        }
        inj.visitMethodInsn(INVOKESPECIAL, method.owner, method.name, method.desc, false);
    }

    private void injectDefaultValue(HookInjectorMethodVisitor inj, Type targetMethodReturnType) {
        switch (targetMethodReturnType.getSort()) {
            case Type.VOID:
                break;
            case Type.BOOLEAN:
            case Type.CHAR:
            case Type.BYTE:
            case Type.SHORT:
            case Type.INT:
                inj.visitInsn(Opcodes.ICONST_0);
                break;
            case Type.FLOAT:
                inj.visitInsn(Opcodes.FCONST_0);
                break;
            case Type.LONG:
                inj.visitInsn(Opcodes.LCONST_0);
                break;
            case Type.DOUBLE:
                inj.visitInsn(Opcodes.DCONST_0);
                break;
            default:
                inj.visitInsn(Opcodes.ACONST_NULL);
                break;
        }
    }

    private void injectReturn(HookInjectorMethodVisitor inj, Type targetMethodReturnType) {
        if (targetMethodReturnType == INT_TYPE || targetMethodReturnType == SHORT_TYPE ||
                targetMethodReturnType == BOOLEAN_TYPE || targetMethodReturnType == BYTE_TYPE
                || targetMethodReturnType == CHAR_TYPE) {
            inj.visitInsn(IRETURN);
        } else if (targetMethodReturnType == LONG_TYPE) {
            inj.visitInsn(LRETURN);
        } else if (targetMethodReturnType == FLOAT_TYPE) {
            inj.visitInsn(FRETURN);
        } else if (targetMethodReturnType == DOUBLE_TYPE) {
            inj.visitInsn(DRETURN);
        } else if (targetMethodReturnType == VOID_TYPE) {
            inj.visitInsn(RETURN);
        } else {
            inj.visitInsn(ARETURN);
        }
    }

    private void injectInvokeStatic(HookInjectorMethodVisitor inj, int returnLocalId, String name, String desc) {
        for (int i = 0; i < hookMethodParameters.size(); i++) {
            Type parameterType = hookMethodParameters.get(i);
            int variableId = transmittableVariableIds.get(i);
            if (inj.isStatic) {
                // если попытка передачи  из статического метода, то передаем null
                if (variableId == 0) {
                    inj.visitInsn(Opcodes.ACONST_NULL);
                    continue;
                }
                // иначе сдвигаем номер локальной переменной
                if (variableId > 0) variableId--;
            }
            if (variableId == -1) variableId = returnLocalId;
            injectLoad(inj, parameterType, variableId);
        }

        inj.visitMethodInsn(INVOKESTATIC, getHookClassInternalName(), name, desc, false);
    }

    public String getPatchedMethodName() {
        return targetClassName + '#' + targetMethodName + targetMethodDescription;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AsmHook: ");

        sb.append(targetClassName).append('#').append(targetMethodName);
        sb.append(targetMethodDescription);
        sb.append(" -> ");
        sb.append(hooksClassName).append('#').append(hookMethodName);
        sb.append(hookMethodDescription);

        sb.append(", ReturnCondition=" + returnCondition);
        sb.append(", ReturnValue=" + returnValue);
        if (returnValue == ReturnValue.PRIMITIVE_CONSTANT) sb.append(", Constant=" + primitiveConstant);
        sb.append(", InjectorFactory: " + injectorFactory.getClass().getName());
        sb.append(", CreateMethod = " + createMethod);

        return sb.toString();
    }

    @Override
    public int compareTo(AsmHook o) {
        if (injectorFactory.isPriorityInverted && o.injectorFactory.isPriorityInverted) {
            return priority.ordinal() > o.priority.ordinal() ? -1 : 1;
        } else if (!injectorFactory.isPriorityInverted && !o.injectorFactory.isPriorityInverted) {
            return priority.ordinal() > o.priority.ordinal() ? 1 : -1;
        } else {
            return injectorFactory.isPriorityInverted ? 1 : -1;
        }
    }

    public static Builder newBuilder() {
        return new AsmHook().new Builder();
    }

    public class Builder extends AsmHook {

        private Builder() {

        }

        /**
         * --- ОБЯЗАТЕЛЬНО ВЫЗВАТЬ ---
         * Определяет название класса, в который необходимо установить хук.
         *
         * @param className Название класса с указанием пакета, разделенное точками.
         *                  Например: net.minecraft.world.World
         */
        public Builder setTargetClass(String className) {
            AsmHook..targetClassName = className;
            return ;
        }

        /**
         * --- ОБЯЗАТЕЛЬНО ВЫЗВАТЬ ---
         * Определяет название метода, в который необходимо вставить хук.
         * Если нужно пропатчить конструктор, то в названии метода нужно указать <init>.
         *
         * @param methodName Название метода.
         *                   Например: getBlockId
         */
        public Builder setTargetMethod(String methodName) {
            AsmHook..targetMethodName = methodName;
            return ;
        }

        /**
         * --- ОБЯЗАТЕЛЬНО ВЫЗВАТЬ, ЕСЛ�? У ЦЕЛЕВОГО МЕТОДА ЕСТЬ ПАРАМЕТРЫ ---
         * Добавляет один или несколько параметров к списку параметров целевого метода.
         * <p/>
         * Эти параметры используются, чтобы составить описание целевого метода.
         * Чтобы однозначно определить целевой метод, недостаточно только его названия - нужно ещё и описание.
         * <p/>
         * Примеры использования:
         * import static gloomyfolken.hooklib.asm.TypeHelper.*
         * //...
         * addTargetMethodParameters(Type.INT_TYPE)
         * Type worldType = getType("net.minecraft.world.World")
         * Type playerType = getType("net.minecraft.entity.player.EntityPlayer")
         * addTargetMethodParameters(worldType, playerType, playerType)
         *
         * @param parameterTypes Типы параметров целевого метода
         * @see TypeHelper
         */
        public Builder addTargetMethodParameters(Type... parameterTypes) {
            for (Type type : parameterTypes) {
                AsmHook..targetMethodParameters.add(type);
            }
            return ;
        }

        /**
         * Добавляет один или несколько параметров к списку параметров целевого метода.
         * Обёртка над addTargetMethodParameters(Type... parameterTypes), которая сама строит типы из названия.
         *
         * @param parameterTypeNames Названия классов параметров целевого метода.
         *                           Например: net.minecraft.world.World
         */

        public Builder addTargetMethodParameters(String... parameterTypeNames) {
            Type[] types = new Type[parameterTypeNames.length];
            for (int i = 0; i < parameterTypeNames.length; i++) {
                types[i] = TypeHelper.getType(parameterTypeNames[i]);
            }
            return addTargetMethodParameters(types);
        }

        /**
         * �?зменяет тип, возвращаемый целевым методом.
         * Вовращаемый тип используется, чтобы составить описание целевого метода.
         * Чтобы однозначно определить целевой метод, недостаточно только его названия - нужно ещё и описание.
         * По умолчанию хук применяется ко всем методам, подходящим по названию и списку параметров.
         *
         * @param returnType Тип, возвращаемый целевым методом
         * @see TypeHelper
         */
        public Builder setTargetMethodReturnType(Type returnType) {
            AsmHook..targetMethodReturnType = returnType;
            return ;
        }

        /**
         * �?зменяет тип, возвращаемый целевым методом.
         * Обёртка над setTargetMethodReturnType(Type returnType)
         *
         * @param returnType Название класса, экземпляр которого возвращает целевой метод
         */
        public Builder setTargetMethodReturnType(String returnType) {
            return setTargetMethodReturnType(TypeHelper.getType(returnType));
        }

        /**
         * --- ОБЯЗАТЕЛЬНО ВЫЗВАТЬ, ЕСЛ�? НУЖЕН ХУК-МЕТОД, А НЕ ПРОСТО return SOME_CONSTANT ---
         * Определяет название класса, в котором находится хук-метод.
         *
         * @param className Название класса с указанием пакета, разделенное точками.
         *                  Например: net.myname.mymod.asm.MyHooks
         */
        public Builder setHookClass(String className) {
            AsmHook..hooksClassName = className;
            return ;
        }

        /**
         * --- ОБЯЗАТЕЛЬНО ВЫЗВАТЬ, ЕСЛ�? НУЖЕН ХУК-МЕТОД, А НЕ ПРОСТО return SOME_CONSTANT ---
         * Определяет название хук-метода.
         * ХУК-МЕТОД ДОЛЖЕН БЫТЬ СТАТ�?ЧЕСК�?М, А ПРОВЕРК�? НА ЭТО НЕТ. Будьте внимательны.
         *
         * @param methodName Название хук-метода.
         *                   Например: myFirstHook
         */
        public Builder setHookMethod(String methodName) {
            AsmHook..hookMethodName = methodName;
            return ;
        }

        /**
         * --- ОБЯЗАТЕЛЬНО ВЫЗВАТЬ, ЕСЛ�? У ХУК-МЕТОДА ЕСТЬ ПАРАМЕТРЫ ---
         * Добавляет параметр в список параметров хук-метода.
         * В байткоде не сохраняются названия параметров. Вместо этого приходится использовать их номера.
         * Например, в классе EntityLivingBase есть метод attackEntityFrom(DamageSource damageSource, float damage).
         * В нём будут использоваться такие номера параметров:
         * 1 - damageSource
         * 2 - damage
         * ВАЖНЫЙ МОМЕНТ: LONG �? DOUBLE "ЗАН�?МАЮТ" ДВА НОМЕРА.
         * Теоретически, кроме параметров в хук-метод можно передать и локальные переменные, но их
         * номера сложнее посчитать.
         * Например, в классе Entity есть метод setPosition(double x, double y, double z).
         * В нём будут такие номера параметров:
         * 1 - x
         * 2 - пропущено
         * 3 - y
         * 4 - пропущено
         * 5 - z
         * 6 - пропущено
         * <p/>
         * Код этого метода таков:
         * //...
         * float f = ...;
         * float f1 = ...;
         * //...
         * В таком случае у f будет номер 7, а у f1 - 8.
         * <p/>
         * Если целевой метод static, то не нужно начинать отсчет локальных переменных с нуля, номера
         * будут смещены автоматически.
         *
         * @param parameterType Тип параметра хук-метода
         * @param variableId    ID значения, передаваемого в хук-метод
         * @throws IllegalStateException если не задано название хук-метода или класса, который его содержит
         */
        public Builder addHookMethodParameter(Type parameterType, int variableId) {
            if (!AsmHook..hasHookMethod()) {
                throw new IllegalStateException("Hook method is not specified, so can not append " +
                        "parameter to its parameters list.");
            }
            AsmHook..hookMethodParameters.add(parameterType);
            AsmHook..transmittableVariableIds.add(variableId);
            return ;
        }

        /**
         * Добавляет параметр в список параметров целевого метода.
         * Обёртка над addHookMethodParameter(Type parameterType, int variableId)
         *
         * @param parameterTypeName Название типа параметра хук-метода.
         *                          Например: net.minecraft.world.World
         * @param variableId        ID значения, передаваемого в хук-метод
         */
        public Builder addHookMethodParameter(String parameterTypeName, int variableId) {
            return addHookMethodParameter(TypeHelper.getType(parameterTypeName), variableId);
        }

        /**
         * Добавляет в список параметров хук-метода целевой класс и передает хук-методу .
         * Если целевой метод static, то будет передано null.
         *
         * @throws IllegalStateException если не задан хук-метод
         */
        public Builder addToHookMethodParameters() {
            if (!AsmHook..hasHookMethod()) {
                throw new IllegalStateException("Hook method is not specified, so can not append " +
                        "parameter to its parameters list.");
            }
            AsmHook..hookMethodParameters.add(TypeHelper.getType(targetClassName));
            AsmHook..transmittableVariableIds.add(0);
            return ;
        }

        /**
         * Добавляет в список параметров хук-метода тип, возвращаемый целевым методом и
         * передает хук-методу значение, которое вернёт return.
         * Более формально, при вызове хук-метода указывает в качестве этого параметра верхнее значение в стеке.
         * На практике основное применение -
         * Например, есть такой код метода:
         * int foo = bar();
         * return foo;
         * �?ли такой:
         * return bar()
         * <p/>
         * В обоих случаях хук-методу можно передать возвращаемое значение перед вызовом return.
         *
         * @throws IllegalStateException если целевой метод возвращает void
         * @throws IllegalStateException если не задан хук-метод
         */
        public Builder addReturnValueToHookMethodParameters() {
            if (!AsmHook..hasHookMethod()) {
                throw new IllegalStateException("Hook method is not specified, so can not append " +
                        "parameter to its parameters list.");
            }
            if (AsmHook..targetMethodReturnType == Type.VOID_TYPE) {
                throw new IllegalStateException("Target method's return type is void, it does not make sense to " +
                        "transmit its return value to hook method.");
            }
            AsmHook..hookMethodParameters.add(AsmHook..targetMethodReturnType);
            AsmHook..transmittableVariableIds.add(-1);
            AsmHook..hasReturnValueParameter = true;
            return ;
        }

        /**
         * Задает условие, при котором после вызова хук-метода вызывается return.
         * По умолчанию return не вызывается вообще.
         * Кроме того, этот метод изменяет тип возвращаемого значения хук-метода:
         * NEVER -> void
         * ALWAYS -> void
         * ON_TRUE -> boolean
         * ON_NULL -> Object
         * ON_NOT_NULL -> Object
         *
         * @param condition Условие выхода после вызова хук-метода
         * @throws IllegalArgumentException если condition == ON_TRUE, ON_NULL или ON_NOT_NULL, но не задан хук-метод.
         * @see ReturnCondition
         */
        public Builder setReturnCondition(ReturnCondition condition) {
            if (condition.requiresCondition && AsmHook..hookMethodName == null) {
                throw new IllegalArgumentException("Hook method is not specified, so can not use return " +
                        "condition that depends on hook method.");
            }

            AsmHook..returnCondition = condition;
            Type returnType;
            switch (condition) {
                case NEVER:
                case ALWAYS:
                    returnType = VOID_TYPE;
                    break;
                case ON_TRUE:
                    returnType = BOOLEAN_TYPE;
                    break;
                default:
                    returnType = getType(Object.class);
                    break;
            }
            AsmHook..hookMethodReturnType = returnType;
            return ;
        }

        /**
         * --- ОБЯЗАТЕЛЬНО ВЫЗВАТЬ, ЕСЛ�? ЦЕЛЕВОЙ МЕТОД ВОЗВРАЩАЕТ НЕ void, �? ВЫЗВАН setReturnCondition ---
         * Задает значение, которое возвращается при вызове return после вызова хук-метода.
         * Следует вызывать после setReturnCondition.
         * По умолчанию возвращается void.
         * Кроме того, если value == ReturnValue.HOOK_RETURN_VALUE, то этот метод изменяет тип возвращаемого
         * значения хук-метода на тип, указанный в setTargetMethodReturnType()
         *
         * @param value возвращаемое значение
         * @throws IllegalStateException    если returnCondition == NEVER (т. е. если setReturnCondition() не вызывался).
         *                                  Нет смысла указывать возвращаемое значение, если return не вызывается.
         * @throws IllegalArgumentException если value == ReturnValue.HOOK_RETURN_VALUE, а тип возвращаемого значения
         *                                  целевого метода указан как void (или setTargetMethodReturnType ещё не вызывался).
         *                                  Нет смысла использовать значение, которое вернул хук-метод, если метод возвращает void.
         */
        public Builder setReturnValue(ReturnValue value) {
            if (AsmHook..returnCondition == ReturnCondition.NEVER) {
                throw new IllegalStateException("Current return condition is ReturnCondition.NEVER, so it does not " +
                        "make sense to specify the return value.");
            }
            Type returnType = AsmHook..targetMethodReturnType;
            if (value != ReturnValue.VOID && returnType == VOID_TYPE) {
                throw new IllegalArgumentException("Target method return value is void, so it does not make sense to " +
                        "return anything else.");
            }
            if (value == ReturnValue.VOID && returnType != VOID_TYPE) {
                throw new IllegalArgumentException("Target method return value is not void, so it is impossible " +
                        "to return VOID.");
            }
            if (value == ReturnValue.PRIMITIVE_CONSTANT && returnType != null && !isPrimitive(returnType)) {
                throw new IllegalArgumentException("Target method return value is not a primitive, so it is " +
                        "impossible to return PRIVITIVE_CONSTANT.");
            }
            if (value == ReturnValue.NULL && returnType != null && isPrimitive(returnType)) {
                throw new IllegalArgumentException("Target method return value is a primitive, so it is impossible " +
                        "to return NULL.");
            }
            if (value == ReturnValue.HOOK_RETURN_VALUE && !hasHookMethod()) {
                throw new IllegalArgumentException("Hook method is not specified, so can not use return " +
                        "value that depends on hook method.");
            }

            AsmHook..returnValue = value;
            if (value == ReturnValue.HOOK_RETURN_VALUE) {
                AsmHook..hookMethodReturnType = AsmHook..targetMethodReturnType;
            }
            return ;
        }

        /**
         * Возвращает тип возвращаемого значения хук-метода, если кому-то сложно "вычислить" его самостоятельно.
         *
         * @return тип возвращаемого значения хук-метода
         */
        public Type getHookMethodReturnType() {
            return hookMethodReturnType;
        }

        /**
         * Напрямую указывает тип, возвращаемый хук-методом.
         *
         * @param type
         */
        protected void setHookMethodReturnType(Type type) {
            AsmHook..hookMethodReturnType = type;
        }

        private boolean isPrimitive(Type type) {
            return type.getSort() > 0 && type.getSort() < 9;
        }

        /**
         * --- ОБЯЗАТЕЛЬНО ВЫЗВАТЬ, ЕСЛ�? ВОЗВРАЩАЕМОЕ ЗНАЧЕН�?Е УСТАНОВЛЕНО НА PRIMITIVE_CONSTANT ---
         * Следует вызывать после setReturnValue(ReturnValue.PRIMITIVE_CONSTANT)
         * Задает константу, которая будет возвращена при вызове return.
         * Класс заданного объекта должен соответствовать примитивному типу.
         * Например, если целевой метод возвращает int, то в этот метод должен быть передан объект класса Integer.
         *
         * @param constant Объект, класс которого соответствует примитиву, который следует возвращать.
         * @throws IllegalStateException    если возвращаемое значение не установлено на PRIMITIVE_CONSTANT
         * @throws IllegalArgumentException если класс объекта constant не является обёрткой
         *                                  для примитивного типа, который возвращает целевой метод.
         */
        public Builder setPrimitiveConstant(Object constant) {
            if (AsmHook..returnValue != ReturnValue.PRIMITIVE_CONSTANT) {
                throw new IllegalStateException("Return value is not PRIMITIVE_CONSTANT, so it does not make sence" +
                        "to specify that constant.");
            }
            Type returnType = AsmHook..targetMethodReturnType;
            if (returnType == BOOLEAN_TYPE && !(constant instanceof Boolean) ||
                    returnType == CHAR_TYPE && !(constant instanceof Character) ||
                    returnType == BYTE_TYPE && !(constant instanceof Byte) ||
                    returnType == SHORT_TYPE && !(constant instanceof Short) ||
                    returnType == INT_TYPE && !(constant instanceof Integer) ||
                    returnType == LONG_TYPE && !(constant instanceof Long) ||
                    returnType == FLOAT_TYPE && !(constant instanceof Float) ||
                    returnType == DOUBLE_TYPE && !(constant instanceof Double)) {
                throw new IllegalArgumentException("Given object class does not math target method return type.");
            }

            AsmHook..primitiveConstant = constant;
            return ;
        }

        /**
         * --- ОБЯЗАТЕЛЬНО ВЫЗВАТЬ, ЕСЛ�? ВОЗВРАЩАЕМОЕ ЗНАЧЕН�?Е УСТАНОВЛЕНО НА ANOTHER_METHOD_RETURN_VALUE ---
         * Следует вызывать после setReturnValue(ReturnValue.ANOTHER_METHOD_RETURN_VALUE)
         * Задает метод, результат вызова которого будет возвращён при вызове return.
         *
         * @param methodName название метода, результат вызова которого следует возвращать
         * @throws IllegalStateException если возвращаемое значение не установлено на ANOTHER_METHOD_RETURN_VALUE
         */
        public Builder setReturnMethod(String methodName) {
            if (AsmHook..returnValue != ReturnValue.ANOTHER_METHOD_RETURN_VALUE) {
                throw new IllegalStateException("Return value is not ANOTHER_METHOD_RETURN_VALUE, " +
                        "so it does not make sence to specify that method.");
            }

            AsmHook..returnMethodName = methodName;
            return ;
        }

        /**
         * Задает фабрику, которая создаст инжектор для этого хука.
         * Если говорить более человеческим языком, то этот метод определяет, где будет вставлен хук:
         * в начале метода, в конце или где-то ещё.
         * Если не создавать своих инжекторов, то можно использовать две фабрики:
         * AsmHook.ON_ENTER_FACTORY (вставляет хук на входе в метод, используется по умолчанию)
         * AsmHook.ON_EXIT_FACTORY (вставляет хук на выходе из метода)
         *
         * @param factory Фабрика, создающая инжектор для этого хука
         */
        public Builder setInjectorFactory(HookInjectorFactory factory) {
            AsmHook..injectorFactory = factory;
            return ;
        }

        /**
         * Задает приоритет хука.
         * Хуки с большим приоритетом вызаваются раньше.
         */
        public Builder setPriority(HookPriority priority) {
            AsmHook..priority = priority;
            return ;
        }

        /**
         * Позволяет не только вставлять хуки в существующие методы, но и добавлять новые. Это может понадобиться,
         * когда нужно переопределить метод суперкласса. Если супер-метод найден, то тело генерируемого метода
         * представляет собой вызов супер-метода. �?наче это просто пустой метод или return false/0/null в зависимости
         * от возвращаемого типа.
         */
        public Builder setCreateMethod(boolean createMethod) {
            AsmHook..createMethod = createMethod;
            return ;
        }

        /**
         * Позволяет объявить хук "обязательным" для запуска игры. В случае неудачи во время вставки такого хука
         * будет не просто выведено сообщение в лог, а крашнется игра.
         */
        public Builder setMandatory(boolean isMandatory) {
            AsmHook..isMandatory = isMandatory;
            return ;
        }

        private String getMethodDesc(Type returnType, List<Type> paramTypes) {
            Type[] paramTypesArray = paramTypes.toArray(new Type[0]);
            if (returnType == null) {
                String voidDesc = Type.getMethodDescriptor(Type.VOID_TYPE, paramTypesArray);
                return voidDesc.substring(0, voidDesc.length() - 1);
            } else {
                return Type.getMethodDescriptor(returnType, paramTypesArray);
            }
        }

        /**
         * Создает хук по заданным параметрам.
         *
         * @return полученный хук
         * @throws IllegalStateException если не был вызван какой-либо из обязательных методов
         */
        public AsmHook build() {
            AsmHook hook = AsmHook.;

            if (hook.createMethod && hook.targetMethodReturnType == null) {
                hook.targetMethodReturnType = hook.hookMethodReturnType;
            }
            hook.targetMethodDescription = getMethodDesc(hook.targetMethodReturnType, hook.targetMethodParameters);

            if (hook.hasHookMethod()) {
                hook.hookMethodDescription = Type.getMethodDescriptor(hook.hookMethodReturnType,
                        hook.hookMethodParameters.toArray(new Type[0]));
            }
            if (hook.returnValue == ReturnValue.ANOTHER_METHOD_RETURN_VALUE) {
                hook.returnMethodDescription = getMethodDesc(hook.targetMethodReturnType, hook.hookMethodParameters);
            }

            try {
                hook = (AsmHook) AsmHook..clone();
            } catch (CloneNotSupportedException impossible) {
            }

            if (hook.targetClassName == null) {
                throw new IllegalStateException("Target class name is not specified. " +
                        "Call setTargetClassName() before build().");
            }

            if (hook.targetMethodName == null) {
                throw new IllegalStateException("Target method name is not specified. " +
                        "Call setTargetMethodName() before build().");
            }

            if (hook.returnValue == ReturnValue.PRIMITIVE_CONSTANT && hook.primitiveConstant == null) {
                throw new IllegalStateException("Return value is PRIMITIVE_CONSTANT, but the constant is not " +
                        "specified. Call setReturnValue() before build().");
            }

            if (hook.returnValue == ReturnValue.ANOTHER_METHOD_RETURN_VALUE && hook.returnMethodName == null) {
                throw new IllegalStateException("Return value is ANOTHER_METHOD_RETURN_VALUE, but the method is not " +
                        "specified. Call setReturnMethod() before build().");
            }

            if (!(hook.injectorFactory instanceof MethodExit) && hook.hasReturnValueParameter) {
                throw new IllegalStateException("Can not pass return value to hook method " +
                        "because hook location is not return insn.");
            }

            return hook;
        }

    }

}
