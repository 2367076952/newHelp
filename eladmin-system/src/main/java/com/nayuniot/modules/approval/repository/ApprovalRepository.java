package com.nayuniot.modules.approval.repository;


import com.nayuniot.modules.approval.domain.Approval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Set;


//审批表
public interface ApprovalRepository extends JpaRepository<Approval, Long>, JpaSpecificationExecutor<Approval> {

    void deleteAllByIdIn(Set<Long> ids);

    //url地址查找
    List<Approval> findAllByUrlAddressEquals(String urlAddress);


}

