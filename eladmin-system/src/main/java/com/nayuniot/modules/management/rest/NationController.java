package com.nayuniot.modules.management.rest;

import com.nayuniot.modules.management.service.NationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/nation")
@RequiredArgsConstructor //写在类上可以代替@Autowired注解，需要注意的是在注入时需要用final定义，或者使用@notnull注解
@Api(tags = "人员:民族")
public class NationController {
    private final NationService nationService;

    @ApiOperation(value = "查询所有")//操作日志.
//    @Log("查询民族")
    @GetMapping("all")
    @PreAuthorize("@el.check('nation:list')")//权限管理
    public ResponseEntity<Object> queryFundRegistration() {
        return new ResponseEntity<>(nationService.queryAll(), HttpStatus.OK);
    }
}
