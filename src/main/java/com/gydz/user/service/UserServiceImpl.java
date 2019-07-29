package com.gydz.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gydz.user.mapper.UserMapper;
import com.gydz.user.model.User;
import com.gydz.util.EncryptUtil;

@Service("userService")
public class UserServiceImpl implements UserService {
	
    @Resource
    private UserMapper userMapper;

	public List<User> getAllUsers() {
		return userMapper.getAllUsers();
	}

	public List<User> getUserByKeyWord(String keyword) {
		return userMapper.getUserByKeyword(keyword);
	}

	public void addUser(User user) {
		EncryptUtil.EncryptUser(user);
		userMapper.addUser(user);
	}

	public void updateUserName(int id, String username) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("username", username);
		userMapper.updateUserName(map);
	}

	public void updateUserPwd(int id, String password) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("password", EncryptUtil.EncryptPwd(password));
		userMapper.updateUserPwd(map);
	}

	public void deleteUser(String id) {
		userMapper.deleteUser(id);
	}
    
    public User getByUsername(String username){
        return userMapper.getByUsername(username);
    }
    
    public Set<String> getRoles(String username){
        return userMapper.getRoles(username);
    }
    
    public Set<String> getPermissions(String username){
        return userMapper.getPermissions(username);
    }

	public int getroleid(User user) {
		
		return userMapper.getroleid(user);
	}

	public void addRole(int rid, int id) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("rid", rid);
		map.put("id", id);
		userMapper.addRole(map);
	}

	public void deleteRole(String id) {
		userMapper.deleteRole(id);
	}

}

