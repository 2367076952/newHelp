package com.nayuniot.modules.money.service.impl;

import com.nayuniot.modules.money.domain.RescueType;
import com.nayuniot.modules.money.repository.RescueTypeRepository;
import com.nayuniot.modules.money.service.RescueTypeService;
import com.nayuniot.modules.money.service.dto.RescueTypeQueryCriteria;
import com.nayuniot.modules.money.service.mapstruct.RescueTypeMapper;
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
public class RescueTypeServiceImpl implements RescueTypeService {
    private final RescueTypeMapper rescueTypeMapper;
    private final RescueTypeRepository repository;


    @Override
    public Map<String, Object> queryAll(RescueTypeQueryCriteria criteria, Pageable pageable) {
        Page<RescueType> page = repository.findAll((root, criteriaQuery, criteriaBuilder) ->
                QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);

        return PageUtil.toPage(page.map(rescueTypeMapper::toDto).getContent(),page.getTotalElements());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        repository.deleteAllByIdIn(ids);
    }

    @Override
    public void create(RescueType rescueType) {
        repository.save(rescueType);
    }

    @Override
    public void update(RescueType rescueType) {
        RescueType resource = repository.findById(rescueType.getId()).orElseGet(RescueType::new);
        ValidationUtil.isNull( rescueType.getId(),"RescueType","id",resource.getId());
        rescueType.setId(resource.getId());
        repository.save(rescueType);
    }

    @Override
    public List<RescueType> queryAll() {
        return repository.findAll();
    }



}
