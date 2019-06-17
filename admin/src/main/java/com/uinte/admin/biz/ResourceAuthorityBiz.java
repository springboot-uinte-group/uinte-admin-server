package com.uinte.admin.biz;

import com.uinte.admin.entity.ResourceAuthority;
import com.uinte.admin.mapper.ResourceAuthorityMapper;
import com.uinte.common.biz.BaseBiz;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class ResourceAuthorityBiz extends BaseBiz<ResourceAuthorityMapper, ResourceAuthority> {
	
}
