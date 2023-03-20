package com.nayuniot.modules.money.service.impl;

import com.nayuniot.modules.money.domain.FundPlanning;
import com.nayuniot.modules.money.domain.FundRegistration;
import com.nayuniot.modules.money.repository.FundPlanningRepository;
import com.nayuniot.modules.money.repository.FundRegistrationRepository;
import com.nayuniot.modules.money.service.FundPlanningService;
import com.nayuniot.modules.money.service.FundRegistrationService;
import com.nayuniot.modules.money.service.dto.FundRegistrationDto;
import com.nayuniot.modules.money.service.dto.FundRegistrationQueryCriteria;
import com.nayuniot.modules.money.service.mapstruct.FundDetailsMapper;
import com.nayuniot.modules.money.service.mapstruct.FundRegistrationMapper;
import com.nayuniot.utils.FileUtil;
import com.nayuniot.utils.PageUtil;
import com.nayuniot.utils.QueryHelp;
import com.nayuniot.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
/**
 * 　　这个注解是加在类上，用于抽取缓存的公共配置。
 */
//@CacheConfig(cacheNames = "FundRegistration")
public class FundRegistrationServiceImpl implements FundRegistrationService {

    private final FundRegistrationMapper fundRegistrationMapper;
    private final FundRegistrationRepository fundRegistrationRepository;
    private final FundPlanningRepository fundPlanningRepository;
    private final FundDetailsMapper fundDetailsMapper;

    @Override
//    @Cacheable(key = "'1'")
    /**
     * 登记界面查询登记的资金
     */
    public Map<String, Object> queryAll(FundRegistrationQueryCriteria criteria, Pageable pageable) {
        Page<FundRegistration> page = fundRegistrationRepository.findAll((root, criteriaQuery, criteriaBuilder) ->
                QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(fundRegistrationMapper::toDto).getContent(), page.getTotalElements());
    }

    @Override
    /**
     * 导出资金数据
     */
    public List<FundRegistrationDto> queryAll(FundRegistrationQueryCriteria criteria) {
        List<FundRegistration> list = fundRegistrationRepository
                .findAll((root, criteriaQuery, criteriaBuilder)
                        -> QueryHelp.getPredicate(root, criteria, criteriaBuilder));
        return fundRegistrationMapper.toDto(list);
    }

    @Override
    /**
     * 资金规划界面的资金列表
     */
    public Map<String, Object> findAllByPurposeIdIn(FundRegistrationQueryCriteria criteria, Pageable pageable) {
//        Page<FundRegistration> page = fundRegistrationRepository.findAllByPurposeIdIn(list,pageable);
        Page<FundRegistration> page = fundRegistrationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            Join<FundRegistration, FundPlanning> join = root.join("fundPlanning");
            criteriaQuery.where(QueryHelp.getPredicate(root,criteria,criteriaBuilder)).having(criteriaBuilder.notEqual(criteriaBuilder.sum(criteriaBuilder.prod(join.get("planMoney"),join.get("planNumber"))),0));
            return criteriaQuery.orderBy(criteriaBuilder.desc(criteriaBuilder.sum(criteriaBuilder.prod(join.get("planMoney"),join.get("planNumber"))))).groupBy(root.get("id")).getRestriction();
        }, pageable);


        return PageUtil.toPage(fundDetailsMapper.toDto(page.getContent()), page.getTotalElements());
    }

    /**
     * @Transactional是spring中常用的注解之一，通常情况下我们 在需要对一个service方法添加事务时，加上这个注解，如果发生unchecked exception，就会发生rollback
     * MyBatis自动参与到spring事务管理中，无需额外配置，只要org.mybatis.spring.SqlSessionFactoryBean引用的数据源与DataSourceTransactionManager引用的数据源一致即可，否则事务管理会不起作用
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(FundRegistration resources) {
        fundRegistrationRepository.save(resources);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
//    @CacheEvict(key = "'1'")
    public void delete(Set<Long> ids) {
        fundRegistrationRepository.deleteAllByIdIn(ids);
    }

    @Override
//    @CachePut()
    public void edit(FundRegistration fundRegistration) {
        FundRegistration resource = fundRegistrationRepository.findById(fundRegistration.getId()).orElseGet(FundRegistration::new);
        ValidationUtil.isNull(fundRegistration.getId(), "FundRegistration", "id", resource.getId());
        fundRegistration.setId(resource.getId());
        fundRegistrationRepository.save(fundRegistration);
    }

    @Override
    public void download(List<FundRegistrationDto> registrationDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (FundRegistrationDto fundMoneyDto : registrationDtos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("序号", fundMoneyDto.getId());
            map.put("金额", fundMoneyDto.getMoney());
            map.put("用途", fundMoneyDto.getPurposeName());
            map.put("来源", fundMoneyDto.getSourceName());
            map.put("接收人", fundMoneyDto.getRecipient());
            map.put("接收单位", fundMoneyDto.getReceivingUnit());

            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }


}
