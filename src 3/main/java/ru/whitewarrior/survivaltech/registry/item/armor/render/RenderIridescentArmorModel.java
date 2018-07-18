package ru.whitewarrior.survivaltech.registry.item.armor.render;

import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import ru.whitewarrior.survivaltech.api.common.energy.IItemElectricEnergyStorage;
import ru.whitewarrior.survivaltech.registry.ShaderRegister;
import ru.whitewarrior.survivaltech.util.ShaderUtil;

/**
 * Date: 2018-01-09. 
 * Time: 10:25:15.
 * @author WhiteWarrior
 */
public class RenderIridescentArmorModel extends ModelBiped {
	private ModelRenderer rightArm1;
	private ModelRenderer leftArm1;
	private ModelRenderer head;
	private ModelRenderer body;
	private ItemStack stack;

    public RenderIridescentArmorModel(int type, ItemStack stack)
    {
        super(0, 0, 128, 128);
        .stack=stack;
        rightArm1 = new ModelRenderer();
        rightArm1.addBox(-3.4F, -2.4F, -2.4F, 5, 9, 5);
        rightArm1.setRotationPoint(0F, 0F, 0F);
        rightArm1.setTextureSize(16, 16);
        rightArm1.setTextureOffset(0, 0);
        .bipedRightArm.cubeList.clear();
        .bipedRightArm.addChild(rightArm1);
        
        leftArm1 = new ModelRenderer();
        leftArm1.addBox(-1.5F, -2.4F, -2.4F, 5, 9, 5);
        leftArm1.setRotationPoint(0F, 0F, 0F);
        leftArm1.setTextureSize(16, 16);
        leftArm1.setTextureOffset(0, 0);
        .bipedLeftArm.cubeList.clear();
        .bipedLeftArm.addChild(leftArm1);
        
        head = new ModelRenderer();
        head.addBox(-4.5f, -9F, -4.5f, 9, 9, 9, 0.1f);
        head.setRotationPoint(0F, 0F, 0F);
        head.setTextureSize(16, 16);
        head.setTextureOffset(16, 0);
        .bipedHeadwear.cubeList.clear();
        .bipedHead.cubeList.clear();
        .bipedHead.addChild(head);
        
        body = new ModelRenderer();
        body.addBox(-4f, 0F, -2.5f, 8, 12, 5, 0);
        body.setRotationPoint(0F, 0F, 0F);
        body.setTextureSize(16, 16);
        body.setTextureOffset(16, 0);
        .bipedBody.addChild(body);
        
     
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    	//зарядка
        if(((IItemElectricEnergyStorage)stack.getItem()).getEnergyStored(stack) > 0) {
            ShaderRegister.iridescent_transparent_opaqueTexture.preRender();
            ShaderUtil.useTime(ShaderRegister.iridescent_transparentAll.getId());
            super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            ShaderRegister.iridescent_transparent_opaqueTexture.postRender();
        }
        else{
            GL11.glColor4f(1,1,1,0.5f);
            super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }

    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

}
