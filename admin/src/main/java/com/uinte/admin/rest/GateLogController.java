package com.uinte.admin.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uinte.admin.biz.GateLogBiz;
import com.uinte.admin.entity.GateLog;
import com.uinte.common.rest.BaseController;

/**
 * ${DESCRIPTION}
 *
 */
@RestController
@RequestMapping("gateLog")
public class GateLogController extends BaseController<GateLogBiz, GateLog> {
	
}

