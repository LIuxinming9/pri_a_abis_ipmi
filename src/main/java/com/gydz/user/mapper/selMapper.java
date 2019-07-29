package com.gydz.user.mapper;

import java.util.HashMap;
import java.util.List;

import com.gydz.user.model.sel_name;
import com.gydz.user.model.sel_type;
import com.gydz.user.model.selrecord;

public interface selMapper {

	List<sel_type> getsel_type(HashMap<String, String> map);

	List<sel_name> getsel_name(HashMap<String, String> map);

	List<selrecord> getInfobyParam(HashMap<String, String> map);
	
	public List<String> selectANum();
	
	public void addANum(HashMap<String, String> map);
	
	public void delANum(HashMap<String, String> map);

}
