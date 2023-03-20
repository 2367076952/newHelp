package com.nayuniot.modules.money.service;

import com.nayuniot.modules.money.domain.FundRegistration;
import com.nayuniot.modules.money.service.dto.FundRegistrationDto;
import com.nayuniot.modules.money.service.dto.FundRegistrationQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FundRegistrationService {
    /**
     * 分页查询
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Map<String,Object> queryAll(FundRegistrationQueryCriteria criteria, Pageable pageable);

    /**
     * 分页查询
     * @param criteria 条件
     * @return /
     */
    List<FundRegistrationDto> queryAll(FundRegistrationQueryCriteria criteria);

    /**
     * 资金规划界面的资金列表根据用途查询
     * @param pageable
     * @return
     */
    Map<String, Object> findAllByPurposeIdIn(FundRegistrationQueryCriteria criteria, Pageable pageable);
//    Map<String,Object> findAllByPurposeIdIn(String purposeList, Pageable pageable);

    /**
     * 创建
     * @param resources /
     */
    void create(FundRegistration resources);

    void delete(Set<Long> ids);

    void edit(FundRegistration fundRegistration);

    void download(List<FundRegistrationDto> registrationDtos, HttpServletResponse response) throws IOException;



}
