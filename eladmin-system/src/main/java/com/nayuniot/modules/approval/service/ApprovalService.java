package com.nayuniot.modules.approval.service;

import com.nayuniot.modules.approval.service.dto.ApprovalQueryCriteria;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface ApprovalService {

    /**
     * 分页查询
     *
     * @param employee 条件
     * @param pageable 分页参数
     * @return /
     */
    Map<String, Object> queryAllEmployeeApproval(ApprovalQueryCriteria employee, Pageable pageable);

    //修改审批状态
    public void update(Long id, Integer status);


}
