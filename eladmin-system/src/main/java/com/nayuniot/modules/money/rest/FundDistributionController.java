package com.nayuniot.modules.money.rest;


import com.nayuniot.annotation.Log;
import com.nayuniot.modules.management.service.EmployeeService;
import com.nayuniot.modules.money.domain.FundDistribution;
import com.nayuniot.modules.money.service.FundDistributionService;
import com.nayuniot.modules.money.service.dto.FundDistributionQueryCriteria;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor //写在类上可以代替@Autowired注解，需要注意的是在注入时需要用final定义，或者使用@notnull注解
@Api(tags = "资金:资金发放")
@RequestMapping("/api/fundDistribution")
public class FundDistributionController {

    private final FundDistributionService fundDistributionService;
    private final EmployeeService employeeService;

    //    @Log("查询规划")
    @ApiOperation(value = "查询资金发放")
    @GetMapping
    @PreAuthorize("@el.check('fundDistribution:list')")
    public ResponseEntity<Object> queryFundPlanning(FundDistributionQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(fundDistributionService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "根据规划id查询人员")
    @GetMapping("/selFundDistribution/{fundPlanningId}")
    @PreAuthorize("@el.check('fundDistribution:list')")
    public List<Long> selFundDistributionByPlanning(@PathVariable Long fundPlanningId) {
        return fundDistributionService.selFundDistribution(fundPlanningId);
    }

    @ApiOperation(value = "根据资金id查询资金发放")
    @GetMapping("/selFundPlanningId/{id}")
    public ResponseEntity<Object> querySelFundPlanning(Pageable pageable, @PathVariable Integer id) {
        FundDistributionQueryCriteria criteria = new FundDistributionQueryCriteria();
        criteria.setFundPlanning(id);
        return new ResponseEntity<>(fundDistributionService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("删除资金发放")
    @ApiOperation("删除资金发放")
    @DeleteMapping
    @PreAuthorize("@el.check('fundDistribution:del')")
    public ResponseEntity<Object> deleteFundPlanning(@RequestBody Set<Long> ids) {
        fundDistributionService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("新增资金发放")
    @ApiOperation("新增资金发放")
    @PostMapping
    @PreAuthorize("@el.check('fundDistribution:add')")
    public ResponseEntity<Object> createFundPlanning(@Validated @RequestBody FundDistribution resources) {
        fundDistributionService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation("导出资金发放数据自己表为主")
    @PostMapping(value = "/downloadFundPlanning")
    @PreAuthorize("@el.check('fundDistribution:list')")
    public void exportFundPlanning(HttpServletResponse response, @RequestBody Integer planningId) throws IOException {
        FundDistributionQueryCriteria criteria = new FundDistributionQueryCriteria();
        criteria.setFundPlanning(planningId);
        fundDistributionService.download(fundDistributionService.queryAll(criteria), response);
    }

    @ApiOperation("导出资金发放数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('fundDistribution:list')")
    public void export(HttpServletResponse response,FundDistributionQueryCriteria criteria) throws IOException {
        fundDistributionService.download(fundDistributionService.queryAll(criteria), response);
    }

    @ApiOperation("导出资金发放数据人员表为主")
    @PostMapping(value = "/downloadEmployee")
    @PreAuthorize("@el.check('fundDistribution:list')")
    public void exportEmployee(HttpServletResponse response, @RequestBody ArrayList<Long> employeeId) throws IOException {
        fundDistributionService.exportEmployee(fundDistributionService.selFundDistributionByEmployeeIdIn(employeeId), response);
    }

    @ApiOperation(value = "根据人员id查询人员发放的资金")
    @GetMapping("/selFundDistributionByEmployeeId/{id}")
    public ResponseEntity<Object> selFundDistributionByEmployeeId(@PathVariable Long id) {
        return new ResponseEntity<>(fundDistributionService.selFundDistributionByEmployeeId(id), HttpStatus.OK);
    }

    @ApiOperation(value = "根据资金id查询资金发放")
    @GetMapping("/findSumByFundPlanning/{id}")
    public Long findSumByFundPlanning(@PathVariable Long id) {
        return fundDistributionService.findSumByFundPlanning(id);
    }

}
