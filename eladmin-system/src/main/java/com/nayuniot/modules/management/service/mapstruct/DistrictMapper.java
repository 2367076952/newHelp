package com.nayuniot.modules.management.service.mapstruct;

import com.nayuniot.base.BaseMapper;
import com.nayuniot.modules.management.domain.District;
import com.nayuniot.modules.management.service.dto.DistrictDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DistrictMapper extends BaseMapper<DistrictDto, District> {
}