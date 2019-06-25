package com.uinte.drools.dao;


import org.apache.ibatis.annotations.*;

import com.uinte.drools.bean.Rules;

import java.util.List;

@Mapper
public interface RulesDao {

    @Select("SELECT * FROM UINTE_RULE_DEFINE where ID = #{id}")
    Rules getById(@Param("id") String id);

    @Insert("INSERT INTO UINTE_RULE_DEFINE(NAME,RULE) VALUE(#{name},#{rule})")
    Integer setRule(@Param("name") String name,@Param("rule") String rule);

    @Select("SELECT * FROM UINTE_RULE_DEFINE order by CREATE_TIME DESC")
    List<Rules> getRuleList();

    @Update("UPDATE UINTE_RULE_DEFINE SET STATUS='0' WHERE ID = #{id}")
    Integer deleteRule(@Param("id") String id);

    @Update("UPDATE UINTE_RULE_DEFINE SET RULE= #{rule} AND NAME = #{name} WHERE ID = #{id}")
    Integer updateRule(@Param("id") String id,@Param("name") String name,@Param("rule") String rule);
}
