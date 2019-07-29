package com.gydz.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gydz.user.mapper.englishMapper;
import com.gydz.user.model.english;

@Service("englishService")
public class englishImpl implements englishService{

	
	@Resource
	private englishMapper englishMapper;
	
	public void add(String word) {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("word", word);
		englishMapper.add(map);
	}

	public HashMap<String, String> getmap() {
		HashMap<String,String> map = new HashMap<String,String>();
		List<english> list = englishMapper.getmap();
		for (int i = 0; i < list.size(); i++) {
			map.put(list.get(i).getEnglish(), list.get(i).getChinese());
		}
		return map;
	}

	public List<String> getlist() {
		List<english> list = englishMapper.getmap();
		List<String> mlist = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			mlist.add(list.get(i).getEnglish());
		}
		return mlist;
	}

	public List<english> getinfo() {
		return  englishMapper.getmap();
	}

	public void addid() {
		englishMapper.addid();
	}

	public void del(String english) {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("english", english);
		englishMapper.del(map);
	}

	public void update(english english) {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("english", english.getEnglish());
		map.put("chinese", english.getChinese());
		englishMapper.update(map);
	}

}
