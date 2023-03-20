package com.nayuniot.modules.money.service;

import com.nayuniot.modules.money.domain.Material;
import com.nayuniot.modules.money.service.dto.MaterialQueryCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MaterialService {
    /**
     * 分页查询
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Map<String,Object> queryAll(MaterialQueryCriteria criteria, Pageable pageable);

    public void delete(Set<Long> ids);

    public void create(Material material);

    public void update(Material material);

    public List<Material> queryAll();
}
