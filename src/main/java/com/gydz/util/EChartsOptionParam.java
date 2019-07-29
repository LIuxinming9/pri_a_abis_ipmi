package com.gydz.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EChartsOptionParam {

	private String title;   //标题
	private String subtext; //子标题
	private String name;    //显示项名称
	private boolean yValue; //纵轴为值轴
	private Map<String,List<String>> stackMap; //堆叠Map
	private boolean showToolbox; //是否显示工具栏
	private String yUnit;  //纵轴单位
	private String xUnit;  //横轴单位
		
	public EChartsOptionParam() {
		super();
		this.yValue = true;
		this.showToolbox = false;
		this.stackMap = new HashMap<String,List<String>>();
	}
	
	public EChartsOptionParam(String title, String name) {
		super();
		this.title = title;
		this.name = name;
		this.yValue = true;
		this.showToolbox = false;
		this.stackMap = new HashMap<String,List<String>>();
	}
	
	public EChartsOptionParam(String title, String name, boolean showToolbox) {
		super();
		this.title = title;
		this.name = name;
		this.showToolbox = showToolbox;
		this.yValue = true;
		this.stackMap = new HashMap<String,List<String>>();
	}

	public EChartsOptionParam(String title, String subtext, String name) {
		super();
		this.title = title;
		this.subtext = subtext;
		this.name = name;
		this.yValue = true;
		this.showToolbox = false;
		this.stackMap = new HashMap<String,List<String>>();
	}

	public EChartsOptionParam(String title, String subtext, String name, boolean yValue) {
		super();
		this.title = title;
		this.subtext = subtext;
		this.name = name;
		this.yValue = yValue;
		this.showToolbox = false;
		this.stackMap = new HashMap<String,List<String>>();
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getSubtext() {
		return subtext;
	}

	public void setSubtext(String subtext) {
		this.subtext = subtext;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public boolean isyValue() {
		return yValue;
	}

	public void setyValue(boolean yValue) {
		this.yValue = yValue;
	}

	public Map<String, List<String>> getStackMap() {
		return stackMap;
	}

	public void setStackMap(Map<String, List<String>> stackMap) {
		this.stackMap = stackMap;
	}

	public boolean isShowToolbox() {
		return showToolbox;
	}

	public void setShowToolbox(boolean showToolbox) {
		this.showToolbox = showToolbox;
	}

	public String getyUnit() {
		return yUnit;
	}

	public void setyUnit(String yUnit) {
		this.yUnit = yUnit;
	}

	public String getxUnit() {
		return xUnit;
	}

	public void setxUnit(String xUnit) {
		this.xUnit = xUnit;
	}

}
