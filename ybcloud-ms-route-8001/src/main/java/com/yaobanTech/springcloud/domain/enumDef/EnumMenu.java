package com.yaobanTech.springcloud.domain.enumDef;

import com.yaobanTech.springcloud.domain.RespBean;

import java.util.HashMap;
import java.util.Map;

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


	UNAPPROVAL("11", "待审核","计划状态"),
	PROGRESSING("12", "进行中","计划状态"),
	COMPLETE("13", "已完成","计划状态"),
	APPROVALED("14", "已审核","计划状态"),
	UNPASS("15", "审核不通过","计划状态"),
	DELETE("16", "已删除","计划状态"),

	INSTWO("17", "2天一巡","计划周期"),
	INSTRREE("18", "3天一巡","计划周期"),
	INSSEVEN("19", "7天一巡","计划周期"),

	DAYLY("20", "日常巡查","计划类型"),
	CORRENT("21", "临时巡查","计划类型"),

	COMMON("22", "普通","签到点类型"),
	HIGHDANGER("23", "高危","签到点类型"),

	EQUIPMENT("24", "阀门","设备类别"),
	TUBE("25", "管段","设备类别"),
	FIRE("26", "消火栓","设备类别"),

	FAMEN("27", "阀门","资产类别"),
	TUBENET("28", "管网","资产类别"),
	JINGAI("29", "井盖","资产类别"),

	DN15("30", "DN15","阀门口径"),
	DN20("31", "DN20","阀门口径"),
	DN25("32", "DN25","阀门口径"),
	DN30("33", "DN30","阀门口径"),
	DN40("34", "DN40","阀门口径"),
	DN50("35", "DN50","阀门口径"),
	DN80("36", "DN80","阀门口径"),
	DN100("37", "DN100","阀门口径"),
	DN150("38", "DN150","阀门口径"),
	DN200("39", "DN200","阀门口径"),
	DN250("40", "DN250","阀门口径"),
	DN300("41", "DN300","阀门口径"),
	DN400("42", "DN400","阀门口径"),
	DN500("43", "DN500","阀门口径"),
	DN600("44", "DN600","阀门口径"),
	DN700("45", "DN700","阀门口径"),
	DN800("46", "DN800","阀门口径"),
	DN1000("47", "DN1000","阀门口径"),
	DN1200("48", "DN1200","阀门口径"),
	DN1400("49", "DN1400","阀门口径"),
	DN1600("50", "DN1600","阀门口径"),
	DN1800("51", "DN1800","阀门口径"),
	DN2000("52", "DN2000","阀门口径");

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

	public static RespBean findEnum(String code){
		Map<String, Object> map = new HashMap<>();
		if(code != null) {
			EnumMenu[] menus = EnumMenu.values();
			for (int i = 0; i < menus.length; i++) {
				EnumMenu menu = menus[i];
				if (code.equals(menu.getCode())) {
					map.put("mode", menu.getMode());
					map.put("code", menu.getCode());
					map.put("desc", menu.getDesc());
					break;
				}
			}
		}else{
			return RespBean.error("枚举code为空！");
		}
		return RespBean.ok("查询成功！", map);
	}

}
