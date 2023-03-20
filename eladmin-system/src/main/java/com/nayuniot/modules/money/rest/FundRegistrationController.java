package com.nayuniot.modules.money.rest;


import com.nayuniot.annotation.Log;
import com.nayuniot.modules.money.domain.FundRegistration;
import com.nayuniot.modules.money.service.FundRegistrationService;
import com.nayuniot.modules.money.service.dto.FundRegistrationQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@RestController
@RequiredArgsConstructor //写在类上可以代替@Autowired注解，需要注意的是在注入时需要用final定义，或者使用@notnull注解
@Api(tags = "资金:资金信息")
@RequestMapping(value = "/api/fundRegistration")

public class FundRegistrationController {

    private final FundRegistrationService fundRegistrationService;


    @ApiOperation("查询资金")
//    @Log("查询资金")
    @GetMapping
    @PreAuthorize("@el.check('fundRegistration:list')")
    public ResponseEntity<Object> queryFundRegistration(FundRegistrationQueryCriteria criteria, Pageable pageable) {
       return new ResponseEntity<>(fundRegistrationService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @ApiOperation("查询资金")
//    @Log("查询资金")
    @GetMapping("/findAllByPurposeIdIn")
    @PreAuthorize("@el.check('fundRegistration:list')")

    public ResponseEntity<Object> findAllByPurposeIdIn(@RequestParam("id") Set<Integer> purposeId, Pageable pageable) {
        FundRegistrationQueryCriteria criteria = new FundRegistrationQueryCriteria();
        criteria.setPurposeId(purposeId) ;
        return new ResponseEntity<>(fundRegistrationService.findAllByPurposeIdIn(criteria,pageable), HttpStatus.OK);
    }

    @Log("新增资金")
    @ApiOperation("新增资金")
    @PostMapping
    @PreAuthorize("@el.check('fundRegistration:add')")
    public ResponseEntity<Object> createFundRegistration(@Validated @RequestBody FundRegistration resources) {
        fundRegistrationService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("删除资金")
    @ApiOperation("删除资金")
    @DeleteMapping
    @PreAuthorize("@el.check('fundRegistration:del')")
    public ResponseEntity<Object> deleteFundRegistration(@RequestBody Set<Long> ids) {
        fundRegistrationService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("修改资金")
    @ApiOperation("修改资金")
    @PutMapping
    @PreAuthorize("@el.check('fundRegistration:update')") 
    public ResponseEntity<Object> updateFundRegistration(@Validated(FundRegistration.Update.class) @RequestBody FundRegistration fundRegistration) {
        fundRegistrationService.edit(fundRegistration);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation("导出资金登记数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('fundRegistration:list')")
    public void exportFundRegistration(HttpServletResponse response, FundRegistrationQueryCriteria criteria) throws IOException {
        fundRegistrationService.download(fundRegistrationService.queryAll(criteria), response);
    }

}
