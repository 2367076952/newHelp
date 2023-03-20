package com.nayuniot.modules.money.service.mapstruct;

import com.nayuniot.base.BaseMapper;
import com.nayuniot.modules.money.domain.FundRegistration;
import com.nayuniot.modules.money.service.dto.FundRegistrationDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FundRegistrationMapper extends BaseMapper<FundRegistrationDto, FundRegistration> {

}
