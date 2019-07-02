package com.uinte.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uinte.common.entity.user.Element;

import tk.mybatis.mapper.common.Mapper;

public interface ElementMapper extends Mapper<Element> {
    /**
     * 根据用户的id查询用户的按钮
     * @param userId
     * @return
     */
     List<Element> selectAuthorityElementByUserId(@Param("userId") String userId);
     List<Element> selectAllElementPermissions();

}