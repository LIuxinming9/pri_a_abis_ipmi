package com.gydz.user.service;

import java.util.List;

import com.gydz.user.model.QueryParam;
import com.gydz.user.model.sel_name;
import com.gydz.user.model.sel_type;
import com.gydz.user.model.selrecord;
import com.gydz.user.model.sensorrecord;

public interface selService {

	public List<sel_type> getsel_type();
	
	public List<sel_name> getsel_name();
	
	public List<selrecord> getInfobyParam(QueryParam param);
	
	public List<String> selectANum();
	
	public void addANum(String string);
	
	public void delANum(String hostname);
}
