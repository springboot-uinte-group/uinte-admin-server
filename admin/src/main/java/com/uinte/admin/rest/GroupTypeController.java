package com.uinte.admin.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uinte.admin.biz.GroupTypeBiz;
import com.uinte.admin.entity.GroupType;
import com.uinte.common.rest.BaseController;

/**
 *
 */
@RestController
@RequestMapping("groupType")
public class GroupTypeController extends BaseController<GroupTypeBiz, GroupType> {

}
