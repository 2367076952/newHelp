package com.nayuniot.modules.money.service;

import com.nayuniot.modules.money.domain.RescueType;
import com.nayuniot.modules.money.service.dto.RescueTypeQueryCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RescueTypeService {
    /**
     * 分页查询
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Map<String,Object> queryAll(RescueTypeQueryCriteria criteria, Pageable pageable);

    public void delete(Set<Long> ids);

    public void create(RescueType source);

    public void update(RescueType source);

    public List<RescueType> queryAll();

}
