package com.nayuniot.modules.management.service.impl;

import com.nayuniot.modules.management.domain.Nation;
import com.nayuniot.modules.management.repository.NationRepository;
import com.nayuniot.modules.management.service.NationService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NationServiceImpl implements NationService {

    private final NationRepository nationRepository;

    @Override
    @Cacheable(key = "'nation:'",value = "nation")
    public List<Nation> queryAll() {
        return nationRepository.findAll();

    }
}
