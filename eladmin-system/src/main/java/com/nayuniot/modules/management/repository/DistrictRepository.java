package com.nayuniot.modules.management.repository;

import com.nayuniot.modules.management.domain.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface DistrictRepository extends JpaRepository<District, Long>, JpaSpecificationExecutor<District> {


    List<District> findAllByPid(String level);


    District findByCodeEquals(String code);

}

