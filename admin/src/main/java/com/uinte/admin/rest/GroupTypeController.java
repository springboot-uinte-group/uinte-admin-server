package com.uinte.admin.rest;

import com.uinte.admin.biz.GroupTypeBiz;
import com.uinte.admin.entity.GroupType;
import com.uinte.common.rest.BaseController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("groupType")
public class GroupTypeController extends BaseController<GroupTypeBiz, GroupType> {

}
