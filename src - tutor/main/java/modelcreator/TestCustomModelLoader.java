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
         * �������� modelLocation'� ����� �� ����������, �.�.
         * � �������� �������� ������� ��� ��������� ��� �������������
         */
        return modelLocation.toString().contains("testitem");
    }





    @Override
    public IModel loadModel(ResourceLocation modelLocation) throws Exception {
        //��� �������� ����� ������ ���� ����� �������� ������������ ���� ���� ������, ��� ��������� ����������
        if(modelLocation.toString().contains("testitem"))
            return new TestItemModel(/* ��������� ���� �� ��������� �������� */ new ResourceLocation("items/diamond_sword"));
        return null;
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }
}

