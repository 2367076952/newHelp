package com.nayuniot.modules.money.repository;

import com.nayuniot.modules.money.domain.EmployeeDistribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface EmployeeDistributionRepository extends JpaRepository<EmployeeDistribution, Long>, JpaSpecificationExecutor<EmployeeDistribution> {
    List<EmployeeDistribution> findAllByIdIn(List<Long> id);


}
