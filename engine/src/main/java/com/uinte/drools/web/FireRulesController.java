package com.uinte.drools.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uinte.drools.bean.Golfer;
import com.uinte.drools.bean.LearningClass;
import com.uinte.drools.bean.Person;
import com.uinte.drools.bean.Rules;
import com.uinte.drools.bean.base.EngineEntity;
import com.uinte.drools.common.exception.ReturnData;
import com.uinte.drools.service.ExecuteService;
import com.uinte.drools.service.RulesService;

import io.swagger.annotations.ApiOperation;

/**
 * Drools执行规则
 */
@RequestMapping(value = "/execute")
@RestController
public class FireRulesController {

	@Autowired
	private ExecuteService executeService;
	
	@Autowired
	private RulesService rulesService;

	@ApiOperation(value = "执行规则")
	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ReturnData<Person> execute(@RequestParam(value = "ruleId") String ruleId,
			@RequestBody(required = true) Person person) {
		Rules rule = rulesService.getById(ruleId);
		List<EngineEntity> ps = new ArrayList<EngineEntity>(1);
		ps.add(person);
		ps.add(new Golfer("国际高尔夫俱乐部", "green", 1));
		ps.add(new LearningClass("语文培训班", 1));
		executeService.execute(rule.getRule(), ps);
		return new ReturnData<Person>(person);
	}

}
