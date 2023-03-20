package com.nayuniot.modules.approval.rest;


import com.nayuniot.annotation.Log;
import com.nayuniot.modules.approval.service.ApprovalService;
import com.nayuniot.modules.approval.service.dto.ApprovalQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/approval")
@RequiredArgsConstructor //写在类上可以代替@Autowired注解，需要注意的是在注入时需要用final定义，或者使用@notnull注解
@Api(tags = "审批:审批状态")
public class ApprovalController {
    private final ApprovalService approvalService;

    @Log("申请状态修改")
    @ApiOperation("申请状态修改")
    @PutMapping("{id}/{status}")
    @PreAuthorize("@el.check('approval:update')")
    public ResponseEntity<Object> updateEmployee(@PathVariable Long id, @PathVariable Integer status) {
        approvalService.update(id, status);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "条件查询")
    //@Log("查询人员")//日志打印
    @GetMapping
    @PreAuthorize("@el.check('approval:list')")//权限管理
    public ResponseEntity<Object> queryEmployeeApproval(ApprovalQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(approvalService.queryAllEmployeeApproval(criteria, pageable), HttpStatus.OK);
    }
}

