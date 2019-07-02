package com.uinte.admin.biz;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uinte.admin.mapper.GroupTypeMapper;
import com.uinte.common.biz.BaseBiz;
import com.uinte.common.entity.user.GroupType;

@Service
@Transactional(rollbackFor = Exception.class)
public class GroupTypeBiz extends BaseBiz<GroupTypeMapper, GroupType> {
	
}
