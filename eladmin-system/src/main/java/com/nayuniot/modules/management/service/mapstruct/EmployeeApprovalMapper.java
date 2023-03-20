package com.nayuniot.modules.management.service.mapstruct;

import com.nayuniot.base.BaseMapper;
import com.nayuniot.modules.approval.domain.Approval;
import com.nayuniot.modules.approval.service.dto.ApprovalDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

//审批
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeApprovalMapper extends BaseMapper<ApprovalDto, Approval> {

}
