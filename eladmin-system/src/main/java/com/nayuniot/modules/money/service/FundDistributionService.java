package com.nayuniot.modules.money.service;

import com.nayuniot.modules.money.domain.EmployeeDistribution;
import com.nayuniot.modules.money.domain.FundDistribution;
import com.nayuniot.modules.money.service.dto.FundDistributionDto;
import com.nayuniot.modules.money.service.dto.FundDistributionQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FundDistributionService {
    /**
     * 分页查询
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Map<String, Object> queryAll(FundDistributionQueryCriteria criteria, Pageable pageable);

    List<FundDistributionDto> queryAll(FundDistributionQueryCriteria criteria);

    public void delete(Set<Long> ids);

    public void create(FundDistribution fundDistribution);

    void download(List<FundDistributionDto> fundDistributionDtos, HttpServletResponse response) throws IOException;

    void exportEmployee(List<EmployeeDistribution> employeeDtos, HttpServletResponse response) throws IOException;

    List<Long> selFundDistribution(Long fundPlanningId);

    Long findSumByFundPlanning(Long fundPlanningId);

    List<FundDistribution> selFundDistributionByEmployeeId(Long employeeId);

    List<EmployeeDistribution> selFundDistributionByEmployeeIdIn(List<Long> employeeId);
//

}
