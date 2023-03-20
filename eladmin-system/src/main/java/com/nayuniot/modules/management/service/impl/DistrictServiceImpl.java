package com.nayuniot.modules.management.service.impl;

import com.nayuniot.modules.management.domain.District;
import com.nayuniot.modules.management.repository.DistrictRepository;
import com.nayuniot.modules.management.service.DistrictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DistrictServiceImpl implements DistrictService {
    private final DistrictRepository districtRepository;


    @Override
    @Cacheable(key = "'district:' + #pid",value = "district")
    public List<District> queryProvince(String pid) {
        return districtRepository.findAllByPid(pid);
    }
}
