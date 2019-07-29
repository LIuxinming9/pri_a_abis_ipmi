package com.gydz.user.mapper;

import java.util.HashMap;
import java.util.List;

import com.gydz.user.model.OperationLog;

public interface OperationLogMapper {

	public List<OperationLog> getAllOperLogs();

	public List<OperationLog> getOperLogByKeyword(HashMap<String,Object> map);
	
	public void addOperLog(OperationLog user);

}
