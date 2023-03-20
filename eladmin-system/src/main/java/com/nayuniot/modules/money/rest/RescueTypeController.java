package com.nayuniot.modules.money.rest;


import com.nayuniot.annotation.Log;
import com.nayuniot.modules.money.domain.RescueType;
import com.nayuniot.modules.money.service.RescueTypeService;
import com.nayuniot.modules.money.service.dto.RescueTypeQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor //写在类上可以代替@Autowired注解，需要注意的是在注入时需要用final定义，或者使用@notnull注解
@Api(tags = "资金:资金来源")
@RequestMapping("/api/rescueType")
public class RescueTypeController {

    private final RescueTypeService rescueTypeService;


    @ApiOperation(value = "查询救助类型")
    @GetMapping
    @PreAuthorize("@el.check('rescueType:list')")
    public ResponseEntity<Object> queryRescueType(RescueTypeQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(rescueTypeService.queryAll(criteria, pageable),HttpStatus.OK);
    }

    @Log("删除救助类型")
    @ApiOperation("删除救助类型")
    @DeleteMapping
    @PreAuthorize("@el.check('rescueType:del')")
    public ResponseEntity<Object> deleteRescueType(@RequestBody Set<Long> ids){
        rescueTypeService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("新增救助类型")
    @ApiOperation("新增救助类型")
    @PostMapping
    @PreAuthorize("@el.check('rescueType:add')")
    public ResponseEntity<Object> createRescueType(@Validated @RequestBody RescueType resources){
        rescueTypeService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("修改救助类型")
    @ApiOperation("修改救助类型")
    @PutMapping
    @PreAuthorize("@el.check('rescueType:update')")
    public ResponseEntity<Object> updateRescueType(@Validated(RescueType.Update.class) @RequestBody RescueType rescueType){
        rescueTypeService.update(rescueType);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "查询所有救助类型")
    @GetMapping("rescueTypeAll")
    @PreAuthorize("@el.check('rescueType:list')")
    public ResponseEntity<Object> queryRescueType(){
        return new ResponseEntity<>(rescueTypeService.queryAll(),HttpStatus.OK);
    }

}
