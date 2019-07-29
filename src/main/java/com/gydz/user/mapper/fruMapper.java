package com.gydz.user.mapper;

import java.util.List;
import java.util.Map;

import com.gydz.user.model.QueryParam;
import com.gydz.user.model.fruInfo2;

public interface fruMapper {

	List<fruInfo2> getInfo();

	List<fruInfo2> getInfobyParam(Map map);

}
