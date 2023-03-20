package com.nayuniot.modules.money.service.impl;

import com.nayuniot.modules.money.domain.Source;
import com.nayuniot.modules.money.repository.SourceRepository;
import com.nayuniot.modules.money.service.SourceService;
import com.nayuniot.modules.money.service.dto.SourceQueryCriteria;
import com.nayuniot.modules.money.service.mapstruct.SourceMapper;
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
public class SourceServiceImpl implements SourceService {
    private final SourceMapper sourceMapper;
    private final SourceRepository sourceRepository;


    @Override
    public Map<String, Object> queryAll(SourceQueryCriteria criteria, Pageable pageable) {
        Page<Source> page = sourceRepository.findAll((root, criteriaQuery, criteriaBuilder) ->
                QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);

        return PageUtil.toPage(page.map(sourceMapper::toDto).getContent(),page.getTotalElements());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        sourceRepository.deleteAllByIdIn(ids);
    }

    @Override
    public void create(Source source) {
        sourceRepository.save(source);
    }

    @Override
    public void update(Source source) {
        Source resource = sourceRepository.findById(source.getId()).orElseGet(Source::new);
        ValidationUtil.isNull( source.getId(),"Source","id",resource.getId());
        source.setId(resource.getId());
        sourceRepository.save(source);
    }

    @Override
    public List<Source> queryAll() {
        return sourceRepository.findAll();
    }




}
