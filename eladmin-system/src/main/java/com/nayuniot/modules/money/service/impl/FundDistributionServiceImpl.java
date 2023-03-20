package com.nayuniot.modules.money.service.impl;

import com.nayuniot.modules.management.domain.Employee;
import com.nayuniot.modules.money.domain.EmployeeDistribution;
import com.nayuniot.modules.money.domain.FundDistribution;
import com.nayuniot.modules.money.repository.EmployeeDistributionRepository;
import com.nayuniot.modules.money.repository.FundDistributionRepository;
import com.nayuniot.modules.money.service.FundDistributionService;
import com.nayuniot.modules.money.service.dto.FundDistributionDto;
import com.nayuniot.modules.money.service.dto.FundDistributionQueryCriteria;
import com.nayuniot.modules.money.service.mapstruct.FundDistributionMapper;
import com.nayuniot.utils.FileUtil;
import com.nayuniot.utils.PageUtil;
import com.nayuniot.utils.QueryHelp;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FundDistributionServiceImpl implements FundDistributionService {
    private final EmployeeDistributionRepository employeeDistributionRepository;
    private final FundDistributionRepository fundDistributionRepository;
    private final FundDistributionMapper fundDistributionMapper;

    @Override
    public Map<String, Object> queryAll(FundDistributionQueryCriteria criteria, Pageable pageable) {
        Page<FundDistribution> page = fundDistributionRepository.findAll((root, criteriaQuery, criteriaBuilder) ->
                QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(fundDistributionMapper::toDto).getContent(), page.getTotalElements());
    }

    @Override
    public List<FundDistributionDto> queryAll(FundDistributionQueryCriteria criteria) {
        List<FundDistribution> list = fundDistributionRepository
                .findAll((root, criteriaQuery, criteriaBuilder)
                        -> QueryHelp.getPredicate(root, criteria, criteriaBuilder));
        return fundDistributionMapper.toDto(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        fundDistributionRepository.deleteAllByIdIn(ids);
    }

    @Override
    public void create(FundDistribution distribution) {
        for (Employee employee : distribution.getEmployees()) {
            FundDistribution fundDistribution = new FundDistribution();
            fundDistribution.setEmployee(employee);
            fundDistribution.setNumber(distribution.getNumber());
            fundDistribution.setFundPlanning(distribution.getFundPlanning());
            fundDistribution.setRemarks(distribution.getRemarks());
            fundDistributionRepository.save(fundDistribution);
            distribution.setId(null);
        }
    }

    @Override
    public void download(List<FundDistributionDto> fundDistributionDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (FundDistributionDto fundDistributionDto : fundDistributionDtos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("序号", fundDistributionDto.getEmployee().getId());
            map.put("姓名", fundDistributionDto.getEmployee().getName());
            map.put("联系电话", fundDistributionDto.getEmployee().getPhone());
            map.put("身份证号", fundDistributionDto.getEmployee().getIdNumber());
            map.put("性别", fundDistributionDto.getEmployee().getGender());
            map.put("年龄", fundDistributionDto.getEmployee().getAge());
            map.put("民族", fundDistributionDto.getEmployee().getNation().getNation());
            map.put("家庭住址", fundDistributionDto.getEmployee().getAddress().getName() + fundDistributionDto.getEmployee().getDetailed());
            map.put("户籍所在地", fundDistributionDto.getEmployee().getProvinceId().getName() + fundDistributionDto.getEmployee().getCityId().getName() + fundDistributionDto.getEmployee().getRegister().getName());
            map.put("救助原因", fundDistributionDto.getEmployee().getSuccourName());
            map.put("备注", fundDistributionDto.getEmployee().getRemarks());
            map.put("发放物品", fundDistributionDto.getFundPlanning().getMaterial().getMaterialName());
            map.put("发放数量", fundDistributionDto.getNumber());
            map.put("所需金额", fundDistributionDto.getFundPlanning().getPlanMoney());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public void exportEmployee(List<EmployeeDistribution> employeeDistributions, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (EmployeeDistribution employeeDistribution : employeeDistributions) {
            stringBuilder.delete(0, stringBuilder.length());
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("序号", employeeDistribution.getId());
            map.put("姓名", employeeDistribution.getName());
            map.put("联系电话", employeeDistribution.getPhone());
            map.put("身份证号", employeeDistribution.getIdNumber());
            map.put("性别", employeeDistribution.getGender());
            map.put("年龄", employeeDistribution.getAge());
            map.put("民族", employeeDistribution.getNation().getNation());
            map.put("家庭住址", employeeDistribution.getAddress().getName()+employeeDistribution.getDetailed());
            map.put("户籍所在地", employeeDistribution.getProvinceId().getName()+employeeDistribution.getCityId().getName()+employeeDistribution.getRegister().getName());
            map.put("救助原因", employeeDistribution.getSuccourName());
            map.put("备注", employeeDistribution.getRemarks());
            map.put("发放物品*数量*单价", employeeDistribution.getDistribution());

            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public List<Long> selFundDistribution(Long fundPlanningId) {
        return fundDistributionRepository.selFundDistribution(fundPlanningId);
    }

    @Override
    public Long findSumByFundPlanning(Long fundPlanningId) {
        return fundDistributionRepository.findSumByFundPlanning(fundPlanningId);
    }

    @Override
    public List<FundDistribution> selFundDistributionByEmployeeId(Long employeeId) {
        return fundDistributionRepository.selFundDistributionByEmployeeId(employeeId);
    }

    @Override
    public List<EmployeeDistribution> selFundDistributionByEmployeeIdIn(List<Long> employeeId) {
        return employeeDistributionRepository.findAllByIdIn(employeeId);
    }


}
