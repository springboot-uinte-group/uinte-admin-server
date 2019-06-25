package com.uinte.drools.service;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uinte.drools.bean.Person;
import com.uinte.drools.bean.Rules;
import com.uinte.drools.common.bean.ExceptionType;
import com.uinte.drools.common.exception.BusinessException;
import com.uinte.drools.dao.RulesDao;

@Service
public class RulesService {

	@Autowired
	private RulesDao rulesDao;

	public Rules getById(String ruleId) {
		return rulesDao.getById(ruleId);
	}

	/**
	 * 动态获取KieSession
	 *
	 * @param rules rule
	 * @return KieSession
	 */
	public KieSession getKieSession(String rules) {
		KieServices kieServices = KieServices.Factory.get();
		KieFileSystem kfs = kieServices.newKieFileSystem();
		kfs.write("src/main/resources/rules/rules.drl", rules.getBytes());
		KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
		Results results = kieBuilder.getResults();
		if (results.hasMessages(org.kie.api.builder.Message.Level.ERROR)) {
			System.out.println(results.getMessages());
			throw new BusinessException(300003, results.getMessages().toString(), 4);
		}
		KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
		KieBase kieBase = kieContainer.getKieBase();

		return kieBase.newKieSession();
	}

	/**
	 * 动态加载已经部署的rule
	 *
	 * @param id rule id
	 * @param t  对象
	 * @return 结果对象
	 */
	public Person getRulesWrite(String id, Person t) {
		String rules;
		Rules ru = rulesDao.getById(id);
		if (ru != null && ru.getRule() != null) {
			rules = ru.getRule();
		} else
			throw new BusinessException(ExceptionType.RULE_IS_NULL);

		KieServices kieServices = KieServices.Factory.get();
		KieFileSystem kfs = kieServices.newKieFileSystem();
		kfs.write("src/main/resources/rules/rules.drl", rules.getBytes());
		KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
		Results results = kieBuilder.getResults();
		if (results.hasMessages(org.kie.api.builder.Message.Level.ERROR)) {
			System.out.println(results.getMessages());
			throw new IllegalStateException("### errors ###");
		}
		KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
		KieBase kieBase = kieContainer.getKieBase();
		KieSession ksession = kieBase.newKieSession();

		ksession.insert(t);
		ksession.fireAllRules();
		return t;
	}
}
