package com.uinte.admin.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uinte.admin.biz.GateLogBiz;
import com.uinte.common.entity.user.GateLog;
import com.uinte.common.msg.TableResultResponse;
import com.uinte.common.rest.BaseController;
import com.uinte.common.util.Query;

import tk.mybatis.mapper.entity.Example;

@RestController
@RequestMapping("gateLog")
public class GateLogController extends BaseController<GateLogBiz, GateLog> {
	
	@Autowired
    private GateLogBiz gateLogBiz;
	
	/**
	 * 分页获取所有实体
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/page")
	public TableResultResponse<GateLog> list(@RequestParam Map<String, Object> params) {
		//查询列表数据
        Query query = new Query(params);
        Example example = new Example(GateLog.class);
        example.orderBy("crtTime").desc();
        return gateLogBiz.selectByQuerySpe(query,example);
	}
}
