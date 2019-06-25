package com.uinte.admin.mapper;

import org.apache.ibatis.annotations.Param;

import com.uinte.admin.entity.Group;
import com.uinte.admin.entity.GroupLeader;
import com.uinte.admin.entity.GroupMember;

import tk.mybatis.mapper.common.Mapper;

public interface GroupMapper extends Mapper<Group> {
	public void deleteGroupMembersById(@Param("groupId") String groupId);

	public void deleteGroupLeadersById(@Param("groupId") String groupId);

	public void insertGroupMembersById(GroupMember group);

	public void insertGroupLeadersById(GroupLeader group);
}