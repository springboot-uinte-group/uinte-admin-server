package com.uinte.admin.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.uinte.admin.biz.MenuBiz;
import com.uinte.admin.biz.UserBiz;
import com.uinte.admin.service.PermissionService;
import com.uinte.admin.vo.MenuTree;
import com.uinte.admin.vo.user.FrontUser;
import com.uinte.common.context.BaseContextHandler;
import com.uinte.common.entity.user.Menu;
import com.uinte.common.entity.user.User;
import com.uinte.common.msg.ObjectRestResponse;
import com.uinte.common.rest.BaseController;

@RestController
@RequestMapping("user")
public class UserController extends BaseController<UserBiz, User> {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private MenuBiz menuBiz;
    
    @Autowired
    private UserBiz userBiz;

    /**
     * 根据token得到该用户信息和菜单和按钮
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/front/info", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getUserInfoAndOther(String token) throws Exception {
        FrontUser userInfo = permissionService.getUserInfo(token);
        if(userInfo==null) {
            return ResponseEntity.status(401).body(false);
        } else {
            return ResponseEntity.ok(userInfo);
        }
    }

    /**
     * 根据用户的token 得到用户树形菜单
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/front/menus", method = RequestMethod.GET)
    public @ResponseBody
    List<MenuTree> getMenusByUsername(String token) throws Exception {
        return permissionService.getMenusByUsername(token);
    }

    /**
     * 得到系统的所有菜单
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/front/menu/all", method = RequestMethod.GET)
    public @ResponseBody
    List<Menu> getAllMenus() throws Exception {
        return menuBiz.selectListAll();
    }
    
    @PutMapping("/{id}")
    public ObjectRestResponse<User> update(@RequestBody User entity) {
    	entity.setUpdUser(BaseContextHandler.getUserID());
    	entity.setUpdName(BaseContextHandler.getUsername());
    	userBiz.updateSelectiveById(entity);
        return new ObjectRestResponse<User>();
    }

}
