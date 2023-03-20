package com.nayuniot.modules.money.service;

import com.nayuniot.modules.money.domain.FundPlanning;
import com.nayuniot.modules.money.domain.FundRegistration;
import com.nayuniot.modules.money.service.dto.FundPlanningQueryCriteria;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Set;

public interface FundPlanningService {
    /**
     * 分页查询
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Map<String,Object> queryAll(FundPlanningQueryCriteria criteria, Pageable pageable);

    public void delete(Set<Long> ids);

    public void create(FundPlanning fundPlanning);

    public void update(FundPlanning fundPlanning);

    public FundRegistration findByIdEquals(long id);


}
