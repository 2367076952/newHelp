package com.nayuniot.modules.money.repository;

import com.nayuniot.modules.money.domain.RescueType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Set;

public interface RescueTypeRepository extends JpaRepository<RescueType,Long>, JpaSpecificationExecutor<RescueType> {
    /**
     * 根据id批量删除数据
     * @param id
     */
    void deleteAllByIdIn(Set<Long> id);

    List<RescueType> findAll();

}
