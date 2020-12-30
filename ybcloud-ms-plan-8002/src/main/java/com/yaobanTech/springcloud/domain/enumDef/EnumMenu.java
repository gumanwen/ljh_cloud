package com.yaobanTech.springcloud.domain.enumDef;

public enum EnumMenu {

	DISCHARGE("0", "末梢水排放","定点巡查类型"),
	HIDDENDANGER("1", "隐患点","定点巡查类型"),
	OTHER("2", "其他","定点巡查类型"),

	CHENGNAN("3", "城南用水管理所","用水管理所"),
	CHENGBEI("4", "城北用水管理所","用水管理所"),
	SHIJIAO("5", "石角用水管理所","用水管理所"),

	DISABLED("6", "禁用","状态"),
	ENABLED("7", "启用","状态"),

	AREA("8", "区域巡查","路线类型"),
	LINE("9", "沿线巡查","路线类型"),
	POINT("10", "定点巡查","路线类型"),


	UNAPPROVAL("11", "待审核","计划类型"),
	PROGRESSING("12", "进行中","计划类型"),
	COMPLETE("13", "已完成","计划类型"),
	APPROVALED("14", "已审核","计划类型"),
	UNPASS("15", "审核不通过","计划类型"),
	DELETE("16", "已删除","计划类型"),

	INSTWO("17", "2天一巡","计划周期"),
	INSTRREE("18", "3天一巡","计划周期"),
	INSSEVEN("19", "7天一巡","计划周期"),

	USED("20", "启用","计划状态"),
	UNUSED("21", "禁用","计划状态");
	private String code;    //枚举值
	private String desc;    //枚举描述
	private String mode;    //所属模块

	private EnumMenu(String code, String desc,String mode) {
		this.code = code;
		this.desc = desc;
		this.mode = mode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public static EnumMenu getEnumByKey(String code) {
		if (null == code) {
			return null;
		}
		for (EnumMenu temp : EnumMenu.values()) {
			if (temp.getCode().equals(code)) {
				return temp;
			}
		}
		return null;
	}

}
