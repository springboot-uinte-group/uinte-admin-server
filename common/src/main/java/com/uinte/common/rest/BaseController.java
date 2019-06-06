package com.uinte.common.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.uinte.common.biz.BaseBiz;
import com.uinte.common.context.BaseContextHandler;
import com.uinte.common.msg.ObjectRestResponse;
import com.uinte.common.msg.TableResultResponse;
import com.uinte.common.util.Query;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 *
 * @param <Biz>  可以认为是service
 * @param <T>  实体类
 */
public class BaseController<Biz extends BaseBiz, T> {
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
        return new ObjectRestResponse<T>();
    }

    /**
     * 根据实体的id来查询实体
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ObjectRestResponse<T> get(@PathVariable int id) {
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
    public ObjectRestResponse<T> remove(@PathVariable int id) {
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
