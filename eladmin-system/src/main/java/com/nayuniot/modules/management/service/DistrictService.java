package com.nayuniot.modules.management.service;


import com.nayuniot.modules.management.domain.District;

import java.util.List;

public interface DistrictService {
    /**
     * 获取每层地址  我上司的花她让放在公司了
     * @param pid
     * @return
     */
    List<District> queryProvince(String pid);


}
