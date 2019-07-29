package com.gydz.user.service;

import java.util.List;

import com.gydz.user.model.QueryParam;
import com.gydz.user.model.fruInfo2;

public interface fruService {

	public List<fruInfo2> getInfobyParam(QueryParam param);
}
