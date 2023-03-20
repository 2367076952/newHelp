package com.nayuniot.modules.money.rest;


import com.nayuniot.annotation.Log;
import com.nayuniot.modules.money.domain.FundPlanning;
import com.nayuniot.modules.money.service.FundPlanningService;
import com.nayuniot.modules.money.service.FundRegistrationService;
import com.nayuniot.modules.money.service.dto.FundPlanningQueryCriteria;
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
@Api(tags = "资金:资金规划")
@RequestMapping("/api/fundPlanning")
public class FundPlanningController {

    private final FundPlanningService fundPlanningService;
    private final FundRegistrationService fundRegistrationService;

//    @Log("查询规划")
    @ApiOperation(value = "查询规划")
    @GetMapping
    @PreAuthorize("@el.check('fundPlanning:list')")
    public ResponseEntity<Object> queryFundPlanning(FundPlanningQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(fundPlanningService.queryAll(criteria, pageable),HttpStatus.OK);
    }


    /**
     * 资金规划时候根据id查询登记的id
     * @param id
     * @return
     */
    @ApiOperation(value = "查询金额")
    @GetMapping("SelFund/{id}")
    @PreAuthorize("@el.check('fundPlanning:list')")
    public ResponseEntity<Object> queryFund(@PathVariable long id){
        return new ResponseEntity<>(fundPlanningService.findByIdEquals(id),HttpStatus.OK);
    }


    @Log("删除规划")
    @ApiOperation("删除规划")
    @DeleteMapping
    @PreAuthorize("@el.check('fundPlanning:del')")
    public ResponseEntity<Object> deleteFundDistribution(@RequestBody Set<Long> ids){
        fundPlanningService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("新增规划")
    @ApiOperation("新增规划")
    @PostMapping
    @PreAuthorize("@el.check('fundPlanning:add')")
    public ResponseEntity<Object> createFundDistribution(@Validated @RequestBody FundPlanning resources){
        fundPlanningService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
//
    @Log("修改规划")
    @ApiOperation("修改规划")
    @PutMapping
    @PreAuthorize("@el.check('fundPlanning:update')")
    public ResponseEntity<Object> updateFundDistribution(@Validated(FundPlanning.Update.class) @RequestBody FundPlanning fundPlanning){
        fundPlanningService.update(fundPlanning);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
