package com.nayuniot.modules.management.rest;

import com.nayuniot.annotation.Log;
import com.nayuniot.modules.approval.domain.Approval;
import com.nayuniot.modules.management.domain.Employee;
import com.nayuniot.modules.management.service.EmployeeService;
import com.nayuniot.modules.management.service.dto.EmployeeQueryCriteria;
import com.nayuniot.modules.system.domain.Dept;
import com.nayuniot.modules.system.service.DeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor //写在类上可以代替@Autowired注解，需要注意的是在注入时需要用final定义，或者使用@notnull注解
@Api(tags = "人员:人员信息")
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    private final DeptService deptService;

    @ApiOperation(value = "条件查询")
//    @Log("查询人员")//日志打印
    @GetMapping
    @PreAuthorize("@el.check('employee:list')")//权限管理
    public ResponseEntity<Object> queryEmployee(EmployeeQueryCriteria criteria, Pageable pageable) {
        //判断id是否存在
        if (!ObjectUtils.isEmpty(criteria.getDeptId())) {
            criteria.getDeptIds().add(criteria.getDeptId());
            //查找是否存在子节点
            List<Dept> data = deptService.findByPid(criteria.getDeptId());
            criteria.getDeptIds().addAll(deptService.getDeptChildren(data));
        }
        return new ResponseEntity<>(employeeService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "查询没有规划的人员")
    @PostMapping("employeeAllNotIn")
    @PreAuthorize("@el.check('employee:list')")//权限管理
    public List<Employee> findByIdNotIn(@RequestBody Set<Long> id) {
        return employeeService.findByIdNotIn(id);
    }

    @Log("新增人员")
    @ApiOperation("新增人员")
    @PostMapping
    @PreAuthorize("@el.check('employee:add')")
    public ResponseEntity<Object> createEmployee(@Validated @RequestBody Employee resources) {
        employeeService.save(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("删除人员")
    @ApiOperation("删除人员")
    @DeleteMapping
    @PreAuthorize("@el.check('employee:del')")
    public ResponseEntity<Object> deleteEmployee(@RequestBody Set<Long> ids) {
        employeeService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("修改人员信息")
    @ApiOperation("修改人员信息")
    @PutMapping
    @PreAuthorize("@el.check('employee:edit')")
    public ResponseEntity<Object> updateEmployee(@RequestBody Employee employee) {
        employeeService.update(employee);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Log("申请原因")
    @ApiOperation("申请原因")
    @PutMapping(value = "/update")
    @PreAuthorize("@el.check('employee:editPut')")
    public ResponseEntity<Object> updateEmployeeApproval(@RequestBody Approval approval) {
        employeeService.updateApproval(approval);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation("导出人员信息")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('employee:list')")
    public void exportJob(HttpServletResponse response, EmployeeQueryCriteria criteria) throws IOException {
        employeeService.download(employeeService.queryAll(criteria), response);
    }

    @PostMapping("/uploadEmployee")
    @ApiOperation("导入人员")
    @PreAuthorize("@el.check('employee:upload')")
    public ResponseEntity<Object> uploadEmployee(String path) throws IOException {
        String s = employeeService.uploadEmployee(path);
        return new ResponseEntity<>(s,HttpStatus.OK);
    }

    /**
     *
     * @param path 表格地址
     * @param errorEmployeeList 错误信息列表
     * @throws IOException
     */
    @PostMapping("/errorEmployeeDownload")
    @ApiOperation("错误人员数据下载")
    @PreAuthorize("@el.check('employee:upload')")
    public void errorEmployeeDownload(String path,String errorEmployeeList,HttpServletResponse response) throws IOException {
            employeeService.errorEmployeeDownload(path,errorEmployeeList,response);
    }

}
