package com.uinte.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uinte.common.entity.user.Menu;

import tk.mybatis.mapper.common.Mapper;

public interface MenuMapper extends Mapper<Menu> {
    public List<Menu> selectMenuByAuthorityId(@Param("authorityId") String authorityId, @Param("authorityType") String authorityType);

    /**
     * 根据用户和组的权限关系查找用户可访问菜单
     * @param userId
     * @return
     */
    public List<Menu> selectAuthorityMenuByUserId(@Param("userId") String userId);
}