package com.gydz.user.service;

import java.util.HashMap;
import java.util.List;

import com.gydz.user.model.english;

public interface englishService {

	public void add(String word);
	
	public HashMap<String, String> getmap();
	
	public List<String> getlist();
	
	public List<english> getinfo();
	
	public void addid();
	
	public void del(String english);
	
	public void update(english english);
}
