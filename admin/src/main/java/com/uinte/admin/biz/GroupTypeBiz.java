package com.uinte.admin.biz;

import com.uinte.admin.entity.GroupType;
import com.uinte.admin.mapper.GroupTypeMapper;
import com.uinte.common.biz.BaseBiz;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class GroupTypeBiz extends BaseBiz<GroupTypeMapper, GroupType> {
	
}
