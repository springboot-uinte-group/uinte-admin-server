package com.uinte.admin.biz;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uinte.admin.mapper.MenuMapper;
import com.uinte.common.biz.BaseBiz;
import com.uinte.common.constant.AdminConstant;
import com.uinte.common.entity.user.Menu;

@Service
@Transactional(rollbackFor = Exception.class)
@CacheConfig(cacheNames = "MenuBiz")
public class MenuBiz extends BaseBiz<MenuMapper, Menu> {
	
	@Override
	public List<Menu> selectListAll() {
		return super.selectListAll();
	}

	@Override
	public void insertSelective(Menu entity) {
		if (AdminConstant.ROOT_PATH.equalsIgnoreCase(entity.getParentId())) {
			entity.setPath("/" + entity.getCode());
		} else {
			Menu parent = this.selectById(entity.getParentId());
			entity.setPath(parent.getPath() + "/" + entity.getCode());
		}
		super.insertSelective(entity);
	}

	@Override
	public void updateById(Menu entity) {
		if (AdminConstant.ROOT_PATH.equalsIgnoreCase(entity.getParentId())) {
			entity.setPath("/" + entity.getCode());
		} else {
			Menu parent = this.selectById(entity.getParentId());
			entity.setPath(parent.getPath() + "/" + entity.getCode());
		}
		super.updateById(entity);
	}

	@Override
	public void updateSelectiveById(Menu entity) {
		super.updateSelectiveById(entity);
	}

	/**
	 * 获取用户可以访问的菜单
	 *
	 * @param id
	 * @return
	 */
	public List<Menu> getUserAuthorityMenuByUserId(String id) {
		return mapper.selectAuthorityMenuByUserId(id);
	}

}
