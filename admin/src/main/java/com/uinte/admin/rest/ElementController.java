package com.uinte.admin.rest;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.uinte.admin.biz.ElementBiz;
import com.uinte.admin.biz.UserBiz;
import com.uinte.admin.entity.Element;
import com.uinte.common.msg.ObjectRestResponse;
import com.uinte.common.msg.TableResultResponse;
import com.uinte.common.rest.BaseController;

import tk.mybatis.mapper.entity.Example;

/**
 * 控制层 抽取公共部分BaseController 其他控制器基础基础控制器BaseController
 */
@RestController
@RequestMapping("element")
public class ElementController extends BaseController<ElementBiz, Element> {

	@Autowired
	private UserBiz userBiz;

	/**
	 * 根据菜单id获取该菜单下的按钮
	 * 
	 * @param limit
	 * @param offset
	 * @param name
	 * @param menuId
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public TableResultResponse<Element> page(@RequestParam(defaultValue = "10") int limit,
			@RequestParam(defaultValue = "1") int offset, String name,
			@RequestParam(defaultValue = "0") String menuId) {
		Example example = new Example(Element.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("menuId", menuId);
		if (StringUtils.isNotBlank(name)) {
			criteria.andLike("name", "%" + name + "%");
		}
		List<Element> elements = baseBiz.selectByExample(example);
		return new TableResultResponse<Element>(elements.size(), elements);
	}

	/**
	 * 根据用户的id 查询用户的按钮
	 * 
	 * @return
	 */
	@RequestMapping(value = "/user/menu", method = RequestMethod.GET)
	@ResponseBody
	public ObjectRestResponse<List<Element>> getAuthorityElement() {
		String userId = userBiz.getUserByUsername(getCurrentUserName()).getId();
		List<Element> elements = baseBiz.getAuthorityElementByUserId(userId);
		return new ObjectRestResponse<List<Element>>().data(elements);
	}
}
