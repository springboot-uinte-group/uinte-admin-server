package com.uinte.admin.biz;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uinte.admin.mapper.ElementMapper;
import com.uinte.common.biz.BaseBiz;
import com.uinte.common.entity.user.Element;

/**
 *
 * @create 2017-06-23 20:27
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ElementBiz extends BaseBiz<ElementMapper, Element> {
    /**
     * 根据用户的id 查询用户的按钮
     * @param userId
     * @return
     */
    public List<Element> getAuthorityElementByUserId(String userId){
       return mapper.selectAuthorityElementByUserId(userId);
    }

    /**
     * 得到所有菜单和按钮
     * @return
     */
    public List<Element> getAllElementPermissions(){
        return mapper.selectAllElementPermissions();
    }

}
