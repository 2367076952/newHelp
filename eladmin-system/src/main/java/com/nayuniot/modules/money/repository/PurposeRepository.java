package com.nayuniot.modules.money.repository;

import com.nayuniot.modules.money.domain.Purpose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Set;

/**
 * 用途
 */
public interface PurposeRepository extends JpaRepository<Purpose, Long>, JpaSpecificationExecutor<Purpose> {

    /**
     * 根据id批量删除数据
     *
     * @param id
     */
    public void deleteAllByIdIn(Set<Long> id);

    public Purpose findAllByNameEquals(String name);


}