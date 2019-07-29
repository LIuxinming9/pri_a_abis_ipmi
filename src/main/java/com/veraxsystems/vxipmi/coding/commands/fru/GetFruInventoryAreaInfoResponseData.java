/*
 * GetFruInventoryAreaInfoResponseData.java 
 * Created on 2011-08-11
 *
 * Copyright (c) Verax Systems 2011.
 * All rights reserved.
 *
 * This software is furnished under a license. Use, duplication,
 * disclosure and all other uses are restricted to the rights
 * specified in the written license agreement.
 */
package com.veraxsystems.vxipmi.coding.commands.fru;

import com.veraxsystems.vxipmi.coding.commands.ResponseData;

/**
 * A wrapper for Get Fru Inventory Area Info response
 * 获取Fru库存区域信息响应的包装器
 */
public class GetFruInventoryAreaInfoResponseData implements ResponseData {
	
	@Override
	public String toString() {
		return "GetFruInventoryAreaInfoResponseData [fruInventoryAreaSize=" + fruInventoryAreaSize + ", fruUnit="
				+ fruUnit + "]";
	}

	private int fruInventoryAreaSize;//内建单元库存区域大小
	
	private BaseUnit fruUnit;//内建单元的个数

	public int getFruInventoryAreaSize() {
		return fruInventoryAreaSize;
	}

	public void setFruInventoryAreaSize(int fruInventoryAreaSize) {
		this.fruInventoryAreaSize = fruInventoryAreaSize;
	}

	public BaseUnit getFruUnit() {
		return fruUnit;
	}

	public void setFruUnit(BaseUnit fruUnit) {
		this.fruUnit = fruUnit;
	}
}
