package com.nayuniot.modules.management.repository;

import com.nayuniot.modules.management.domain.Nation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NationRepository extends JpaRepository<Nation,Long>, JpaSpecificationExecutor<Nation> {
    /**
     * 根据名称查询民族
     * @param nation
     * @return
     */
    Nation findFirstByNationLike(String nation);
}
