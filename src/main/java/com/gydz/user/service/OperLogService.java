package com.gydz.user.service;

import java.util.List;

import com.gydz.user.model.OperationLog;
import com.gydz.user.model.QueryParam;

public interface OperLogService {
	
	public List<OperationLog> getAllOperLogs();

	public List<OperationLog> getOperLogByKeyWord(QueryParam param);
	
	public void addOperLog(OperationLog user);

}
