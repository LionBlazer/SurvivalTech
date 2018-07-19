package ru.whitewarrior.survivaltech.api.jsoncreator;

import ru.whitewarrior.survivaltech.api.jsoncreator.type.JsonTemplateType;

/**
 * Date: 2017-12-26.
 * Time: 12:33:05.
 * @author WhiteWarrior
 */
public class PatternLoader {
	
	@Pattern
	public static final void createBlock(String fileName, String modid, String texturepath) {
		JsonCreator.set(JsonTemplateType.BLOCK, "src/main/resources/assets/"+modid+"/models/block/"+fileName+".json");
		JsonCreator.replace("modid", modid);
		JsonCreator.replace("texturepath", texturepath);
		JsonCreator.end();
	}
	
	@Pattern
	public static final void createBlockState(String fileName, String modid, String name) {
		JsonCreator.set(JsonTemplateType.BLOCKSTATE, "src/main/resources/assets/"+modid+"/blockstates/"+fileName+".json");
		JsonCreator.replace("modid", modid);
		JsonCreator.replace("name", name);
		JsonCreator.end();
	}
	
	@Pattern
	public static final void createItemBlock(String fileName, String modid, String name) {
		JsonCreator.set(JsonTemplateType.ITEMBLOCK, "src/main/resources/assets/"+modid+"/models/item/"+fileName+".json");
		JsonCreator.replace("modid", modid);
		JsonCreator.replace("name", name);
		JsonCreator.end();
	}
	
	@Pattern
	public static final void createItem(String fileName, String modid, String texturepath) {
		JsonCreator.set(JsonTemplateType.ITEM, "src/main/resources/assets/"+modid+"/models/item/"+fileName+".json");
		JsonCreator.replace("modid", modid);
		JsonCreator.replace("texturepath", texturepath);
		JsonCreator.end();
	}
	
	@Pattern
	public static final void createItemHandheld(String fileName, String modid, String texturepath) {
		JsonCreator.set(JsonTemplateType.HANDHELD_ITEM, "src/main/resources/assets/"+modid+"/models/item/"+fileName+".json");
		JsonCreator.replace("modid", modid);
		JsonCreator.replace("texturepath", texturepath);
		JsonCreator.end();
	}
	
}
