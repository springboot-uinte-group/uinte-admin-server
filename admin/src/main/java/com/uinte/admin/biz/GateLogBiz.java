package com.uinte.admin.biz;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uinte.admin.mapper.GateLogMapper;
import com.uinte.common.biz.BaseBiz;
import com.uinte.common.entity.user.GateLog;


@Service
@Transactional(rollbackFor = Exception.class)
public class GateLogBiz extends BaseBiz<GateLogMapper, GateLog> {

	@Override
	public void insert(GateLog entity) {
		mapper.insert(entity);
	}

}
