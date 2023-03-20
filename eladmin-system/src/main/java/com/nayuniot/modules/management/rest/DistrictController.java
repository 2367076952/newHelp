package com.nayuniot.modules.management.rest;

import com.nayuniot.modules.management.service.DistrictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.Cacheable;

@RestController
@RequestMapping("api/district")
@RequiredArgsConstructor //写在类上可以代替@Autowired注解，需要注意的是在注入时需要用final定义，或者使用@notnull注解
@Api(tags = "地址:地址信息")
public class DistrictController {
    private final DistrictService districtService;

    @Cacheable(value = "districtCache")
    @ApiOperation(value = "查询地址")
//    @Log("查询地址")//操作日志.
    @GetMapping("allProvince/{pid}")
    @PreAuthorize("@el.check('district:list')")//权限管理
    public ResponseEntity<Object> queryProvince(@PathVariable String pid) {
        return new ResponseEntity<>(districtService.queryProvince(pid), HttpStatus.OK);
    }
}
