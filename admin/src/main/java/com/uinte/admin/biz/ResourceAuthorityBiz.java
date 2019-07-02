package com.uinte.admin.biz;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uinte.admin.mapper.ResourceAuthorityMapper;
import com.uinte.common.biz.BaseBiz;
import com.uinte.common.entity.user.ResourceAuthority;


@Service
@Transactional(rollbackFor = Exception.class)
public class ResourceAuthorityBiz extends BaseBiz<ResourceAuthorityMapper, ResourceAuthority> {
	
}
