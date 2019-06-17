package com.uinte.admin.biz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.uinte.admin.constants.AdminConstant;
import com.uinte.admin.entity.Group;
import com.uinte.admin.entity.Menu;
import com.uinte.admin.entity.ResourceAuthority;
import com.uinte.admin.mapper.GroupMapper;
import com.uinte.admin.mapper.MenuMapper;
import com.uinte.admin.mapper.ResourceAuthorityMapper;
import com.uinte.admin.mapper.UserMapper;
import com.uinte.admin.vo.AuthorityMenuTree;
import com.uinte.admin.vo.GroupUsers;
import com.uinte.common.biz.BaseBiz;
import com.uinte.common.util.UUIDUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class GroupBiz extends BaseBiz<GroupMapper, Group> {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ResourceAuthorityMapper resourceAuthorityMapper;
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public void insertSelective(Group entity) {
        if (AdminConstant.ROOT_PATH == entity.getParentId()) {
            entity.setPath("/" + entity.getCode());
        } else {
            Group parent = this.selectById(entity.getParentId());
            entity.setPath(parent.getPath() + "/" + entity.getCode());
        }
        super.insertSelective(entity);
    }

    @Override
    public void updateById(Group entity) {
        if (AdminConstant.ROOT_PATH == entity.getParentId()) {
            entity.setPath("/" + entity.getCode());
        } else {
            Group parent = this.selectById(entity.getParentId());
            entity.setPath(parent.getPath() + "/" + entity.getCode());
        }
        super.updateById(entity);
    }

    /**
     * 根据用户组id获取该用户组关联的用户
     *
     * @param groupId
     * @return
     */
    public GroupUsers getGroupUsers(String groupId) {
        return new GroupUsers(userMapper.selectMemberByGroupId(groupId), userMapper.selectLeaderByGroupId(groupId));
    }

    /**
     * 变更群主所分配用户
     *
     * @param groupId
     * @param members
     * @param leaders
     */
    public void modifyGroupUsers(String groupId, String members, String leaders) {
        //先删除之前关联的成员和用户组关系，领导和用户组关系
        mapper.deleteGroupLeadersById(groupId);
        mapper.deleteGroupMembersById(groupId);
        if (!StringUtils.isEmpty(members)) {
            String[] mem = members.split(",");
            for (String m : mem) {
                //插入成员
                mapper.insertGroupMembersById(groupId, m);
            }
        }
        if (!StringUtils.isEmpty(leaders)) {
            String[] mem = leaders.split(",");
            for (String m : mem) {
                //插入领导
                mapper.insertGroupLeadersById(groupId, m);
            }
        }
    }

    /**
     * 变更群组关联的菜单
     *
     * @param groupId
     * @param menus
     */
    public void modifyAuthorityMenu(String groupId, String[] menus) {
        //先删除该用户组绑定的菜单
        resourceAuthorityMapper.deleteByAuthorityIdAndResourceType(groupId, AdminConstant.RESOURCE_TYPE_MENU);
        //获取所有菜单
        List<Menu> menuList = menuMapper.selectAll();
        Map<String, String> map = new HashMap<String, String>();
        for (Menu menu : menuList) {
            map.put(menu.getId().toString(), menu.getParentId().toString());
        }
        //todo 1
        Set<String> relationMenus = new HashSet<String>();
        //菜单去重
        relationMenus.addAll(Arrays.asList(menus));
        ResourceAuthority authority = null;
        for (String menuId : menus) {
            findParentID(map, relationMenus, menuId);
        }
        for (String menuId : relationMenus) {
            authority = new ResourceAuthority(AdminConstant.AUTHORITY_TYPE_GROUP, AdminConstant.RESOURCE_TYPE_MENU);
            authority.setId(UUIDUtils.uuidPK());
            authority.setAuthorityId(groupId);
            authority.setResourceId(menuId);
            authority.setParentId("-1");
            resourceAuthorityMapper.insertSelective(authority);
        }
    }

    private void findParentID(Map<String, String> map, Set<String> relationMenus, String id) {
        String parentId = map.get(id);
        if (String.valueOf(AdminConstant.ROOT).equals(id)) {
            return;
        }
        relationMenus.add(parentId);
        findParentID(map, relationMenus, parentId);
    }

    /**
     * 分配资源权限
     *
     * @param groupId
     * @param menuId
     * @param elementId
     */
    public void modifyAuthorityElement(String groupId, String menuId, String elementId) {
        ResourceAuthority authority = new ResourceAuthority(AdminConstant.AUTHORITY_TYPE_GROUP, AdminConstant.RESOURCE_TYPE_BTN);
        authority.setId(UUIDUtils.uuidPK());
        authority.setAuthorityId(groupId);
        authority.setResourceId(elementId);
        authority.setParentId("-1");
        resourceAuthorityMapper.insertSelective(authority);
    }

    /**
     * 移除资源权限
     *
     * @param groupId
     * @param menuId
     * @param elementId
     */
    public void removeAuthorityElement(String groupId, String menuId, String elementId) {
        ResourceAuthority authority = new ResourceAuthority();
        authority.setAuthorityId(groupId);
        authority.setResourceId(elementId);
        authority.setParentId("-1");
        resourceAuthorityMapper.delete(authority);
    }


    /**
     * 获取群主关联的菜单
     *
     * @param groupId
     * @return
     */
    public List<AuthorityMenuTree> getAuthorityMenu(String groupId) {
        List<Menu> menus = menuMapper.selectMenuByAuthorityId(groupId, AdminConstant.AUTHORITY_TYPE_GROUP);
        List<AuthorityMenuTree> trees = new ArrayList<AuthorityMenuTree>();
        AuthorityMenuTree node = null;
        for (Menu menu : menus) {
            node = new AuthorityMenuTree();
            node.setText(menu.getTitle());
            BeanUtils.copyProperties(menu, node);
            trees.add(node);
        }
        return trees;
    }

    /**
     * 获取群组关联的按钮id数组
     *
     * @param groupId
     * @return
     */
    public List<Integer> getAuthorityElement(String groupId) {
        ResourceAuthority authority = new ResourceAuthority(AdminConstant.AUTHORITY_TYPE_GROUP, AdminConstant.RESOURCE_TYPE_BTN);
        authority.setAuthorityId(groupId + "");
        List<ResourceAuthority> authorities = resourceAuthorityMapper.select(authority);
        List<Integer> ids = new ArrayList<Integer>();
        for (ResourceAuthority auth : authorities) {
            ids.add(Integer.parseInt(auth.getResourceId()));
        }
        return ids;
    }
}
