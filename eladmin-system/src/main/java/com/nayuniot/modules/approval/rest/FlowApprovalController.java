package com.nayuniot.modules.approval.rest;

import com.nayuniot.annotation.Log;
import com.nayuniot.modules.approval.domain.Approval;
import com.nayuniot.modules.management.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor //写在类上可以代替@Autowired注解，需要注意的是在注入时需要用final定义，或者使用@notnull注解
@Api(tags = "申请:审批申请")
@RequestMapping("/api/approval")
public class FlowApprovalController {

    private final EmployeeService employeeService;


    @Log("申请原因")
    @ApiOperation("申请原因")
    @PutMapping(value = "/update")
    @PreAuthorize("@el.check('employee:editPut')")
    public ResponseEntity<Object> updateEmployeeApproval(@RequestBody Approval approval) {
        employeeService.updateApproval(approval);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
