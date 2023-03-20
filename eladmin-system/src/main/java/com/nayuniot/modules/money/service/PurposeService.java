package com.nayuniot.modules.money.service;

import com.nayuniot.modules.money.domain.Purpose;
import com.nayuniot.modules.money.service.dto.PurposeQueryCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PurposeService {
    /**
     * 分页查询
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Map<String,Object> queryAll(PurposeQueryCriteria criteria, Pageable pageable);

    public void delete(Set<Long> ids);

    public void create(Purpose purpose);

    public void update(Purpose purpose);

    public List<Purpose> queryAll();
}
