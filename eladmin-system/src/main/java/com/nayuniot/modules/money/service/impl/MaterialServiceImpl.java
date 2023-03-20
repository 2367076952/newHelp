package com.nayuniot.modules.money.service.impl;

import com.nayuniot.modules.money.domain.Material;
import com.nayuniot.modules.money.repository.MaterialRepository;
import com.nayuniot.modules.money.service.MaterialService;
import com.nayuniot.modules.money.service.dto.MaterialQueryCriteria;
import com.nayuniot.modules.money.service.mapstruct.MaterialMapper;
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
public class MaterialServiceImpl implements MaterialService {
    private final MaterialMapper materialMapper;
    private final MaterialRepository materialRepository;


    @Override
    public Map<String, Object> queryAll(MaterialQueryCriteria criteria, Pageable pageable) {
        Page<Material> page = materialRepository.findAll((root, criteriaQuery, criteriaBuilder) ->
                QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);

        return PageUtil.toPage(page.map(materialMapper::toDto).getContent(),page.getTotalElements());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        materialRepository.deleteAllByIdIn(ids);
    }

    @Override
    public void create(Material material) {
        materialRepository.save(material);
    }

    @Override
    public void update(Material material) {
        Material resource = materialRepository.findById(material.getId()).orElseGet(Material::new);
        ValidationUtil.isNull( resource.getId(),"Material","id",material.getId());
        material.setId(resource.getId());
        materialRepository.save(material);
    }

    @Override
    public List<Material> queryAll() {
        return materialRepository.findAll();
    }


}
