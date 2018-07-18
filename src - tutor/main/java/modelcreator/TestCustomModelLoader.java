package modelcreator;


import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ItemLayerModel;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;

public class TestCustomModelLoader implements ICustomModelLoader {


    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        /*
         * напр€мую modelLocation'ы лучше не сравнивать, т.к.
         * в процессе загрузки моделей они несколько раз пересоздаютс€
         */
        return modelLocation.toString().contains("testitem");
    }





    @Override
    public IModel loadModel(ResourceLocation modelLocation) throws Exception {
        //эту проверку можно ибрать если одним лоадером прогружаетс€ лишь одна модель, или несколько одинаковых
        if(modelLocation.toString().contains("testitem"))
            return new TestItemModel(/* ”казываем путь до текстурки предмета */ new ResourceLocation("items/diamond_sword"));
        return null;
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }
}

