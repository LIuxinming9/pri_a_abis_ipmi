package com.gydz.user.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gydz.user.mapper.OperationLogMapper;
import com.gydz.user.model.OperationLog;
import com.gydz.user.model.QueryParam;

@Service("operLogService")
public class OperLogServiceImpl implements OperLogService {
	
    @Resource
    private OperationLogMapper operLogMapper;

    /**
     * 查询所有系统日志
     */
	@Override
	public List<OperationLog> getAllOperLogs() {
		return operLogMapper.getAllOperLogs();
	}

	/**
	 * 根据查询条件获取系统日志
	 */
	@Override
	public List<OperationLog> getOperLogByKeyWord(QueryParam param) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("keyword", param.getKeyword());
		map.put("datemin", param.getDatemin());
		map.put("datemax", param.getDatemax());
		return operLogMapper.getOperLogByKeyword(map);
	}

	/**
	 * 新增系统日志
	 */
	@Override
	public void addOperLog(OperationLog user) {
		operLogMapper.addOperLog(user);
	}
}

