package com.uinte.drools.service;

import java.util.List;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.uinte.drools.bean.Person;
import com.uinte.drools.bean.base.EngineEntity;

@Service
public class ExecuteService {

	@Autowired
	private RulesService rulesService;

	/**
	 * 
	 * @param rule
	 * @param json
	 */
	public void execute(String rule, List<? extends EngineEntity> entrys) {
		KieSession kieSession = rulesService.getKieSession(rule);
		Gson gson = new Gson();
//		kieSession.insert(person);
		factEntityHandle(entrys, kieSession);
		int rules = kieSession.fireAllRules(); // 执行规则
		System.out.println("rules:" + rules);
		kieSession.dispose();// 释放 KieSession 资源
	}

	public void factEntityHandle(List<? extends EngineEntity> entrys, KieSession kieSession) {
		if (entrys != null && entrys.size() != 0) {
			entrys.forEach((e) -> {
				kieSession.insert(e);
			});
		}
	}

	public void getById(String ruleId) {
		rulesService.getById(ruleId);
	}

}
