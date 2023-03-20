package com.nayuniot.modules.management.service;

import com.nayuniot.modules.approval.domain.Approval;
import com.nayuniot.modules.management.domain.Employee;
import com.nayuniot.modules.management.service.dto.EmployeeDto;
import com.nayuniot.modules.management.service.dto.EmployeeQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 人员信息
 */
public interface EmployeeService {

    /**
     * 分页查询
     *
     * @param employee 条件
     * @param pageable 分页参数
     * @return /
     */
    Map<String, Object> queryAll(EmployeeQueryCriteria employee, Pageable pageable);

    /**
     * 分页查询
     *
     * @return /
     */
    public void delete(Set<Long> ids);

    public void save(Employee source);

    public void update(Employee employee);

    //申请修改
    public void updateApproval(Approval employee);

    /**
     * 分页查询
     *
     * @param criteria 条件
     * @return /
     */
    List<EmployeeDto> queryAll(EmployeeQueryCriteria criteria);

    /**
     * 导出数据
     *
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    public void download(List<EmployeeDto> queryAll, HttpServletResponse response) throws IOException;

    List<Employee> findByIdNotIn(Set<Long> id);

    public String uploadEmployee(String path) throws IOException;

    public void errorEmployeeDownload(String path,String errorList, HttpServletResponse response) throws IOException;


}
