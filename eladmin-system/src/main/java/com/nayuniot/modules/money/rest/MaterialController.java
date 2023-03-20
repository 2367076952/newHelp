package com.nayuniot.modules.money.rest;


import com.nayuniot.annotation.Log;
import com.nayuniot.modules.money.domain.Material;
import com.nayuniot.modules.money.domain.Purpose;
import com.nayuniot.modules.money.service.MaterialService;
import com.nayuniot.modules.money.service.dto.MaterialQueryCriteria;
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
@Api(tags = "资金:资金物资规划")
@RequestMapping("/api/material")
public class MaterialController {

    private final MaterialService materialService;


    @ApiOperation(value = "查询物资")
    @GetMapping
    @PreAuthorize("@el.check('material:list')")
    public ResponseEntity<Object> queryMaterial(MaterialQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(materialService.queryAll(criteria, pageable),HttpStatus.OK);
    }

    @Log("删除物资")
    @ApiOperation("删除物资")
    @DeleteMapping
    @PreAuthorize("@el.check('material:del')")
    public ResponseEntity<Object> deleteMaterial(@RequestBody Set<Long> ids){
        materialService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("新增物资")
    @ApiOperation("新增物资")
    @PostMapping
    @PreAuthorize("@el.check('material:add')")
    public ResponseEntity<Object> createMaterial(@Validated @RequestBody Material resources){
        materialService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("修改物资")
    @ApiOperation("修改物资")
    @PutMapping
    @PreAuthorize("@el.check('material:update')")
    public ResponseEntity<Object> updateMaterial(@Validated(Purpose.Update.class) @RequestBody Material material){
        materialService.update(material);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "查询所有物资")
    @GetMapping("materialAll")
    @PreAuthorize("@el.check('material:list')")
    public ResponseEntity<Object> queryMaterial(){

        return new ResponseEntity<>(materialService.queryAll(),HttpStatus.OK);
    }
}
