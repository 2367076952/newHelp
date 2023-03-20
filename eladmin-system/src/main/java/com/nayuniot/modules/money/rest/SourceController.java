package com.nayuniot.modules.money.rest;


import com.nayuniot.annotation.Log;
import com.nayuniot.modules.money.domain.Source;
import com.nayuniot.modules.money.service.SourceService;
import com.nayuniot.modules.money.service.dto.SourceQueryCriteria;
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
@RequestMapping("/api/source")
public class SourceController {

    private final SourceService sourceService;


    @ApiOperation(value = "查询来源")
    @GetMapping
    @PreAuthorize("@el.check('source:list')")
    public ResponseEntity<Object> querySource(SourceQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(sourceService.queryAll(criteria, pageable),HttpStatus.OK);
    }

    @Log("删除来源")
    @ApiOperation("删除来源")
    @DeleteMapping
    @PreAuthorize("@el.check('source:del')")
    public ResponseEntity<Object> deleteSource(@RequestBody Set<Long> ids){
        sourceService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("新增来源")
    @ApiOperation("新增来源")
    @PostMapping
    @PreAuthorize("@el.check('source:add')")
    public ResponseEntity<Object> createSource(@Validated @RequestBody Source resources){
        sourceService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("修改来源")
    @ApiOperation("修改来源")
    @PutMapping
    @PreAuthorize("@el.check('source:update')")
    public ResponseEntity<Object> updateSource(@Validated(Source.Update.class) @RequestBody Source source){
        sourceService.update(source);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "查询所有来源")
    @GetMapping("sourceAll")
    @PreAuthorize("@el.check('source:list')")
    public ResponseEntity<Object> querySource(){
        return new ResponseEntity<>(sourceService.queryAll(),HttpStatus.OK);
    }


}
