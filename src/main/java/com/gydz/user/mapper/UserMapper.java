package com.gydz.user.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.gydz.user.model.User;

public interface UserMapper {

	public List<User> getAllUsers();

	public List<User> getUserByKeyword(String keyword);
	
	public void addUser(User user);
	
	public void updateUserName(HashMap<String,Object> map);
	
	public void updateUserPwd(HashMap<String,Object> map);
	
	public void deleteUser(String id);

	public User getByUsername(String account);
	 
    public Set<String> getRoles(String account);
 
    public Set<String> getPermissions(String account);

	public int getroleid(User user);

	public void addRole(HashMap<String, Object> map);

	public void deleteRole(String id);
    
}
