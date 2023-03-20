package com.nayuniot.modules.money.repository;

import com.nayuniot.modules.money.domain.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Set;

/**
 * 物资
 */
public interface MaterialRepository extends JpaRepository<Material, Long>, JpaSpecificationExecutor<Material> {

    /**
     * 根据id批量删除数据
     *
     * @param id
     */
    public void deleteAllByIdIn(Set<Long> id);

}