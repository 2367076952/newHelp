package com.nayuniot.modules.money.service.impl;

import com.nayuniot.modules.money.domain.Purpose;
import com.nayuniot.modules.money.repository.PurposeRepository;
import com.nayuniot.modules.money.service.PurposeService;
import com.nayuniot.modules.money.service.dto.PurposeQueryCriteria;
import com.nayuniot.modules.money.service.mapstruct.PurposeMapper;
import com.nayuniot.utils.PageUtil;
import com.nayuniot.utils.QueryHelp;
import com.nayuniot.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PurposeServiceImpl implements PurposeService {
    private final PurposeMapper purposeMapper;
    private final PurposeRepository purposeRepository;


    @Override
    public Map<String, Object> queryAll(PurposeQueryCriteria criteria, Pageable pageable) {
        Page<Purpose> page = purposeRepository.findAll((root, criteriaQuery, criteriaBuilder) ->
                QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);

        return PageUtil.toPage(page.map(purposeMapper::toDto).getContent(),page.getTotalElements());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        purposeRepository.deleteAllByIdIn(ids);
    }

    @Override
    public void create(Purpose purpose) {
        purposeRepository.save(purpose);
    }

    @Override
    public void update(Purpose purpose) {
        Purpose rePurpose = purposeRepository.findById(purpose.getId()).orElseGet(Purpose::new);
        ValidationUtil.isNull( purpose.getId(),"Purpose","id",rePurpose.getId());
        purpose.setId(rePurpose.getId());
        purposeRepository.save(purpose);
    }

    @Override
    public List<Purpose> queryAll() {
        return purposeRepository.findAll();
    }


}
