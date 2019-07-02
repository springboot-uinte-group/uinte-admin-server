package com.uinte.admin.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.uinte.admin.biz.GroupBiz;
import com.uinte.admin.vo.AuthorityMenuTree;
import com.uinte.admin.vo.GroupTree;
import com.uinte.admin.vo.GroupUsers;
import com.uinte.common.constant.AdminConstant;
import com.uinte.common.entity.user.Group;
import com.uinte.common.msg.ObjectRestResponse;
import com.uinte.common.rest.BaseController;
import com.uinte.common.util.TreeUtil;

import tk.mybatis.mapper.entity.Example;

@RestController
@RequestMapping("group")
public class GroupController extends BaseController<GroupBiz, Group> {

	/**
	 * 根据用户组的名字或者用户组的类型模糊查询获取用户组信息
	 * 
	 * @param name
	 * @param groupType
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public List<Group> list(String name, String groupType) {
		if (StringUtils.isBlank(name) && StringUtils.isBlank(groupType)) {
			return new ArrayList<Group>();
		}
		Example example = new Example(Group.class);
		if (StringUtils.isNotBlank(name)) {
			example.createCriteria().andLike("name", "%" + name + "%");
		}
		if (StringUtils.isNotBlank(groupType)) {
			example.createCriteria().andEqualTo("groupType", groupType);
		}

		return baseBiz.selectByExample(example);
	}

	/**
	 * 为用户组分配用户传入用户组id 成员id字符串 领导id字符串 eg user?members=4,5&leaders=4
	 * 
	 * @param id
	 * @param members
	 * @param leaders
	 * @return
	 */
	@RequestMapping(value = "/{id}/user", method = RequestMethod.PUT)
	@ResponseBody
	public ObjectRestResponse modifiyUsers(@PathVariable String id, String members, String leaders) {
		baseBiz.modifyGroupUsers(id, members, leaders);
		return new ObjectRestResponse().rel(true);
	}

	/**
	 * 根据用户组id获取该角色组关联的成员和领导
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/user", method = RequestMethod.GET)
	@ResponseBody
	public ObjectRestResponse<GroupUsers> getUsers(@PathVariable String id) {
		return new ObjectRestResponse<GroupUsers>().rel(true).data(baseBiz.getGroupUsers(id));
	}

	/**
	 * 分配菜单 传入用户组id和菜单ids
	 * 
	 * @param id
	 * @param menuTrees
	 * @return
	 */
	@RequestMapping(value = "/{id}/authority/menu", method = RequestMethod.POST)
	@ResponseBody
	public ObjectRestResponse modifyMenuAuthority(@PathVariable String id, String menuTrees) {
		String[] menus = menuTrees.split(",");
		baseBiz.modifyAuthorityMenu(id, menus);
		return new ObjectRestResponse<>().rel(true);
	}

	/**
	 * 根据角色组的id得到该角色组的菜单
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/authority/menu", method = RequestMethod.GET)
	@ResponseBody
	public ObjectRestResponse<List<AuthorityMenuTree>> getMenuAuthority(@PathVariable String id) {
		return new ObjectRestResponse<List<AuthorityMenuTree>>().data(baseBiz.getAuthorityMenu(id)).rel(true);
	}

	/**
	 * 分配按钮通过
	 * 
	 * @param id        用户组id
	 * @param menuId    菜单id
	 * @param elementId 按钮或者url id
	 * @return
	 */
	@RequestMapping(value = "/{id}/authority/element/add", method = RequestMethod.POST)
	@ResponseBody
	public ObjectRestResponse addElementAuthority(@PathVariable String id, String menuId, String elementId) {
		baseBiz.modifyAuthorityElement(id, menuId, elementId);
		return new ObjectRestResponse().rel(true);
	}

	/**
	 * 取消分配按钮通过
	 * 
	 * @param id        用户组id
	 * @param menuId    菜单id
	 * @param elementId 按钮或者url id
	 * @return
	 */
	@RequestMapping(value = "/{id}/authority/element/remove", method = RequestMethod.POST)
	@ResponseBody
	public ObjectRestResponse removeElementAuthority(@PathVariable String id, String menuId, String elementId) {
		baseBiz.removeAuthorityElement(id, menuId, elementId);
		return new ObjectRestResponse().rel(true);
	}

	/**
	 * 根据角色组的id 得到获取群组关联的按钮id数组
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/authority/element", method = RequestMethod.GET)
	@ResponseBody
	public ObjectRestResponse<List<Integer>> getElementAuthority(@PathVariable String id) {
		return new ObjectRestResponse<List<Integer>>().data(baseBiz.getAuthorityElement(id)).rel(true);
	}

	/**
	 * 根据角色组类型获取角色组树形结构
	 * 
	 * @param name
	 * @param groupType
	 * @return
	 */
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	@ResponseBody
	public List<GroupTree> tree(String name, String groupType) {
		if (StringUtils.isBlank(name) && StringUtils.isBlank(groupType)) {
			return new ArrayList<GroupTree>();
		}
		Example example = new Example(Group.class);
		if (StringUtils.isNotBlank(name)) {
			example.createCriteria().andLike("name", "%" + name + "%");
		}
		if (StringUtils.isNotBlank(groupType)) {
			example.createCriteria().andEqualTo("groupType", groupType);
		}
		return getTree(baseBiz.selectByExample(example), AdminConstant.ROOT_PATH);
	}

	private List<GroupTree> getTree(List<Group> groups, String root) {
		List<GroupTree> trees = new ArrayList<GroupTree>();
		GroupTree node = null;
		for (Group group : groups) {
			node = new GroupTree();
			node.setLabel(group.getName());
			BeanUtils.copyProperties(group, node);
			trees.add(node);
		}
		return TreeUtil.bulid(trees, root);
	}
}
