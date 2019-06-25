package com.uinte.common.rest;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.uinte.common.biz.BaseBiz;
import com.uinte.common.context.BaseContextHandler;
import com.uinte.common.entity.BaseEntity;
import com.uinte.common.msg.ObjectRestResponse;
import com.uinte.common.msg.TableResultResponse;
import com.uinte.common.util.Query;
import com.uinte.common.util.UUIDUtils;

/**
 *
 * @param <Biz>  可以认为是service
 * @param <T>  实体类
 */
public class BaseController<Biz extends BaseBiz, T extends BaseEntity> {
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected Biz baseBiz;

    /**
     * 添加一个实体
     *
     * @param entity
     * @return
     */
    @PostMapping
    public ObjectRestResponse<T> add(@RequestBody T entity) {
        baseBiz.insertSelective(entity);
        return new ObjectRestResponse<T>().data(entity);
    }

    /**
     * 根据实体的id来查询实体
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ObjectRestResponse<T> get(@PathVariable String id) {
        ObjectRestResponse<T> entityObjectRestResponse = new ObjectRestResponse<>();
        Object o = baseBiz.selectById(id);
        entityObjectRestResponse.data((T) o);
        return entityObjectRestResponse;
    }

    /**
     * 修改根据id
     *
     * @param
     * @return
     */
    @PutMapping("/{id}")
    public ObjectRestResponse<T> update(@RequestBody T entity) {
        baseBiz.updateSelectiveById(entity);
        return new ObjectRestResponse<T>();
    }

    /**
     * 删除通过id
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ObjectRestResponse<T> remove(@PathVariable String id) {
        baseBiz.deleteById(id);
        return new ObjectRestResponse<T>();
    }

    /**
     * 获取所有实体
     *
     * @return
     */
    @GetMapping("/all")
    public List<T> all() {
        return baseBiz.selectListAll();
    }

    /**
     * 分页获取所有实体
     *
     * @param params
     * @return
     */
    @GetMapping("/page")
    public TableResultResponse<T> list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        return baseBiz.selectByQuery(query);
    }

    public String getCurrentUserName() {
        return BaseContextHandler.getUsername();
    }
}
