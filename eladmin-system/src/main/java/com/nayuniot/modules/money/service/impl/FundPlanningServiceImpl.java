package com.nayuniot.modules.money.service.impl;

import com.nayuniot.modules.money.domain.FundPlanning;
import com.nayuniot.modules.money.domain.FundRegistration;
import com.nayuniot.modules.money.repository.FundPlanningRepository;
import com.nayuniot.modules.money.repository.FundRegistrationRepository;
import com.nayuniot.modules.money.service.FundPlanningService;
import com.nayuniot.modules.money.service.dto.FundPlanningQueryCriteria;
import com.nayuniot.modules.money.service.mapstruct.FundDetailsMapper;
import com.nayuniot.modules.money.service.mapstruct.FundPlanningMapper;
import com.nayuniot.utils.PageUtil;
import com.nayuniot.utils.QueryHelp;
import com.nayuniot.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FundPlanningServiceImpl implements FundPlanningService {
    private final FundPlanningMapper fundPlanningMapper;
    private final FundPlanningRepository fundPlanningRepository;
    private final FundDetailsMapper fundDetailsMapper;
    private final FundRegistrationRepository fundRegistrationRepository;

    @Override
    public Map<String, Object> queryAll(FundPlanningQueryCriteria criteria, Pageable pageable) {
        Page<FundPlanning> page = fundPlanningRepository.findAll((root, criteriaQuery, criteriaBuilder) ->
                QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);

        return PageUtil.toPage(page.map(fundPlanningMapper::toDto).getContent(), page.getTotalElements());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        fundPlanningRepository.deleteAllByIdIn(ids);
    }

    @Override
    public void create(FundPlanning fundPlanning) {
        //        如果剩余金额为0 那么把状态变为FALSE
        if (fundPlanning.getRemainingFunds() - fundPlanning.getPlanMoney() == 0) {
            FundRegistration byId = fundRegistrationRepository.findAllById(fundPlanning.getRegistrationId());
            byId.setStatus(0);
            ValidationUtil.isNull(fundPlanning.getRegistrationId(), "FundRegistration", "id", byId.getId());
            fundRegistrationRepository.save(byId);
        }
        fundPlanningRepository.save(fundPlanning);
    }

    @Override
    public void update(FundPlanning fundPlanning) {
//        如果剩余金额为0 那么把状态变为FALSE
        if (fundPlanning.getRemainingFunds() - fundPlanning.getPlanMoney() == 0) {
            FundRegistration byId = fundRegistrationRepository.findAllById(fundPlanning.getRegistrationId());
            byId.setStatus(0);
            ValidationUtil.isNull(fundPlanning.getRegistrationId(), "FundRegistration", "id", byId.getId());
            fundRegistrationRepository.save(byId);
        }

//        修改资金规划
        FundPlanning resource = fundPlanningRepository.findById(fundPlanning.getId()).orElseGet(FundPlanning::new);
        ValidationUtil.isNull(fundPlanning.getId(), "FundPlanning", "id", fundPlanning.getId());
        fundPlanning.setId(resource.getId());
        fundPlanningRepository.save(fundPlanning);
    }

    @Override
    public FundRegistration findByIdEquals(long id) {
        return fundRegistrationRepository.findAllById(id);
    }


}
