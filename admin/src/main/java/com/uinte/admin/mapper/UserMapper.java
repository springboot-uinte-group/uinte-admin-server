package com.uinte.admin.mapper;


import org.apache.ibatis.annotations.Param;

import com.uinte.admin.entity.User;

import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {
    /**
     * 根据用户组id返回用户组成员
     * @param groupId
     * @return
     */
     List<User> selectMemberByGroupId(@Param("groupId") String groupId);

    /**
     * 根据用户组id返回用户组领导
     * @param groupId
     * @return
     */
     List<User> selectLeaderByGroupId(@Param("groupId") String groupId);
}