package com.nayuniot.modules.management.service.mapstruct;

import com.nayuniot.base.BaseMapper;
import com.nayuniot.modules.management.domain.Employee;
import com.nayuniot.modules.management.service.dto.EmployeeDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper extends BaseMapper<EmployeeDto, Employee> {
}
