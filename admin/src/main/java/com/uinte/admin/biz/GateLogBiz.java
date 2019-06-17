package com.uinte.admin.biz;

import com.uinte.admin.entity.GateLog;
import com.uinte.admin.mapper.GateLogMapper;
import com.uinte.common.biz.BaseBiz;
import com.uinte.common.util.UUIDUtils;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class GateLogBiz extends BaseBiz<GateLogMapper, GateLog> {

	@Override
	public void insert(GateLog entity) {
		mapper.insert(entity);
	}

}
