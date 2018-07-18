package ru.whitewarrior.survivaltech.api.jsoncreator.type;

/**
 * Date: 2017-12-25.
 * Time: 20:55:42.
 * @author WhiteWarrior
 */
public enum JsonTemplateType {
BLOCK("/templates/block.json"),
BLOCKSTATE("/templates/blockstate.json"),
ITEMBLOCK("/templates/itemblock.json"),
ITEM("/templates/item.json"),
HANDHELD_ITEM("/templates/handheld.json");

	private String fileName;

	private JsonTemplateType(String fileName) {
		.fileName = fileName;
	}

	public String getTemplateFileName() {
		return fileName;
	}

}
