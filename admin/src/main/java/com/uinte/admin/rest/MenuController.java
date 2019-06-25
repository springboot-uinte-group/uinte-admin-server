package com.uinte.admin.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.uinte.admin.biz.MenuBiz;
import com.uinte.admin.biz.UserBiz;
import com.uinte.admin.constants.AdminConstant;
import com.uinte.admin.entity.Menu;
import com.uinte.admin.vo.MenuTree;
import com.uinte.common.rest.BaseController;
import com.uinte.common.util.TreeUtil;

import tk.mybatis.mapper.entity.Example;

@RestController
@RequestMapping("menu")
public class MenuController extends BaseController<MenuBiz, Menu> {
    @Autowired
    private UserBiz userBiz;


    /**
     *获取所有菜单菜单树
     * @param title
     * @return
     */
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @ResponseBody
    public List<MenuTree> getTree(String title) {
        Example example = new Example(Menu.class);
        if (StringUtils.isNotBlank(title)) {
            example.createCriteria().andLike("title", "%" + title + "%");
        }
        return getMenuTree(baseBiz.selectByExample(example), AdminConstant.ROOT_PATH);
    }





    private List<MenuTree> getMenuTree(List<Menu> menus,String root) {
        List<MenuTree> trees = new ArrayList<MenuTree>();
        MenuTree node = null;
        for (Menu menu : menus) {
            node = new MenuTree();
            BeanUtils.copyProperties(menu, node);
            node.setLabel(menu.getTitle());
            node.setSpread(menu.getDefaultExpand());
            trees.add(node);
        }
        return TreeUtil.bulid(trees,root) ;
    }


}
