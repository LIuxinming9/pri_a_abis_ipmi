package com.gydz.user.service;

import java.util.List;
import java.util.Set;

import com.gydz.user.model.User;

public interface UserService {
	
	public List<User> getAllUsers();
	
	public List<User> getUserByKeyWord(String keyword);
	
	public void addUser(User user);
	
	public void updateUserName(int id, String username);
	
	public void updateUserPwd(int id, String password);
	
	public void deleteUser(String id);

	public User getByUsername(String username);
	 
    public Set<String> getRoles(String username);
 
    public Set<String> getPermissions(String username);

    public int getroleid(User user);
    
    public void addRole(int rid, int id);
}
