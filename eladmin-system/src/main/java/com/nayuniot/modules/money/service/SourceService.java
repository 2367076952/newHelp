package com.nayuniot.modules.money.service;

import com.nayuniot.modules.money.domain.Source;
import com.nayuniot.modules.money.service.dto.SourceQueryCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SourceService {
    /**
     * 分页查询
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Map<String,Object> queryAll(SourceQueryCriteria criteria, Pageable pageable);

    public void delete(Set<Long> ids);

    public void create(Source source);

    public void update(Source source);

    public List<Source> queryAll();


}
