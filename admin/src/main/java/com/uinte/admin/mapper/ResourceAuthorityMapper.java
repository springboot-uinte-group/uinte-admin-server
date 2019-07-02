package com.uinte.admin.mapper;

import org.apache.ibatis.annotations.Param;

import com.uinte.common.entity.user.ResourceAuthority;

import tk.mybatis.mapper.common.Mapper;

public interface ResourceAuthorityMapper extends Mapper<ResourceAuthority> {
     void deleteByAuthorityIdAndResourceType(@Param("authorityId") String authorityId, @Param("resourceType") String resourceType);
}