package com.nayuniot.modules.money.rest;


import com.nayuniot.annotation.Log;
import com.nayuniot.modules.money.domain.Purpose;
import com.nayuniot.modules.money.service.PurposeService;
import com.nayuniot.modules.money.service.dto.PurposeQueryCriteria;
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
@Api(tags = "资金:资金用途")
@RequestMapping("/api/purpose")
public class PurposeController {

    private final PurposeService purposeService;


    @ApiOperation(value = "查询用途")
    @GetMapping
    @PreAuthorize("@el.check('purpose:list')")
    public ResponseEntity<Object> queryPurpose(PurposeQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(purposeService.queryAll(criteria, pageable),HttpStatus.OK);
    }

    @Log("删除用途")
    @ApiOperation("删除用途")
    @DeleteMapping
    @PreAuthorize("@el.check('purpose:del')")
    public ResponseEntity<Object> deletePurpose(@RequestBody Set<Long> ids){
        purposeService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("新增用途")
    @ApiOperation("新增用途")
    @PostMapping
    @PreAuthorize("@el.check('purpose:add')")
    public ResponseEntity<Object> createPurpose(@Validated @RequestBody Purpose resources){
        purposeService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("修改用途")
    @ApiOperation("修改用途")
    @PutMapping
    @PreAuthorize("@el.check('purpose:update')")
    public ResponseEntity<Object> updatePurpose(@Validated(Purpose.Update.class) @RequestBody Purpose source){
        purposeService.update(source);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "查询所有用途")
    @GetMapping("purposeAll")
    @PreAuthorize("@el.check('purpose:list')")
    public ResponseEntity<Object> queryPurpose(){

        return new ResponseEntity<>(purposeService.queryAll(),HttpStatus.OK);
    }
}
