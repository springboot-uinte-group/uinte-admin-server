package com.uinte.admin.rest;

import com.github.pagehelper.PageHelper;
import com.uinte.admin.biz.GateLogBiz;
import com.uinte.admin.entity.GateLog;
import com.uinte.common.msg.TableResultResponse;
import com.uinte.common.rest.BaseController;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

/**
 * ${DESCRIPTION}
 *
 */
@RestController
@RequestMapping("gateLog")
public class GateLogController extends BaseController<GateLogBiz, GateLog> {
}

