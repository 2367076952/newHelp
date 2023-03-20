package com.nayuniot.modules.approval.service.impl;

import com.nayuniot.modules.approval.domain.Approval;
import com.nayuniot.modules.approval.repository.ApprovalRepository;
import com.nayuniot.modules.approval.service.ApprovalService;
import com.nayuniot.modules.approval.service.dto.ApprovalQueryCriteria;
import com.nayuniot.modules.line.repository.LineRepository;
import com.nayuniot.modules.management.service.mapstruct.EmployeeApprovalMapper;
import com.nayuniot.utils.PageUtil;
import com.nayuniot.utils.QueryHelp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApprovalServiceImpl implements ApprovalService {

    private final ApprovalRepository approvalRepository;
    private final EmployeeApprovalMapper approvalMapper;
    private final LineRepository lineRepository;


    //查找审批
    @Override
    public Map<String, Object> queryAllEmployeeApproval(ApprovalQueryCriteria approvalQueryCriteria, Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.ASC, "status");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Page<Approval> page = approvalRepository.findAll((root, criteriaQuery, criteriaBuilder) ->
                QueryHelp.getPredicate(root, approvalQueryCriteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(approvalMapper::toDto).getContent(), page.getTotalElements());
    }

    //修改审批状态
    @Override
    public void update(Long id, Integer status) {
        Approval approval = approvalRepository.findById(id).orElseGet(Approval::new);
        approval.setStatus(status);
        approval.setId(id);
        approvalRepository.save(approval);
    }

}
