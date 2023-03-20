package com.nayuniot.modules.money.service.mapstruct;

import com.nayuniot.base.BaseMapper;
import com.nayuniot.modules.money.domain.Material;
import com.nayuniot.modules.money.service.dto.MaterialDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author GJQ
 * @date 2022-12-25
 * componentModel:插件可以自动生成实现类,实现DTO-DO各种模型之间的字段映射(不仅仅限制于DTO-DO)
 *,unmappedTargetPolicy = ReportingPolicy.IGNORE 目标属性未被映射到时的策略。WARN：发出警告。ERROR：抛出异常。IGNORE:忽略
 */
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MaterialMapper extends BaseMapper<MaterialDto, Material> {
}
