package com.gydz.user.mapper;

import java.util.HashMap;
import java.util.List;

import com.gydz.user.model.english;

public interface englishMapper {

	public void add(HashMap<String, String> map);
	
	public List<english> getmap();
	
	public void addid();
	
	public void del(HashMap<String, String> map);
	
	public void update(HashMap<String, String> map);
}
