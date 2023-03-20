package com.nayuniot.modules.line.rest;

import com.nayuniot.modules.line.service.LineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor //写在类上可以代替@Autowired注解，需要注意的是在注入时需要用final定义，或者使用@notnull注解
@Api(tags = "首页大屏:资金")
@RequestMapping("/api/fundChart")
public class FundController {
    private final LineService lineService;

    @GetMapping("/fundChartReceipts")
    @ApiOperation("每日收入的资金")
    @PreAuthorize("@el.check('fundChart:list')")
    public Map<String, Long> fundChartReceipts() throws ParseException {
        return lineService.fundLineChart();
    }

    @GetMapping("/fundChartPlanning")
    @ApiOperation("每日规划的资金")
    @PreAuthorize("@el.check('fundChart:list')")
    public Map<String, Long> fundChartPlanning() throws ParseException {
        return lineService.fundChartPlanning();
    }

    @GetMapping("/fundSum")
    @ApiOperation("可用资金")
    @PreAuthorize("@el.check('fundChart:list')")
    public Double fundSum() throws ParseException {
        return lineService.fundSum();
    }

    @GetMapping("/fundDistributionLineChart")
    @ApiOperation("每日发放的资金")
    @PreAuthorize("@el.check('fundChart:list')")
    public Map<String, Long> fundDistributionLineChart() throws ParseException {
        return lineService.fundDistributionLineChart();
    }
    @GetMapping("/amountToBeReleased")
    @ApiOperation("待发放的资金")
    @PreAuthorize("@el.check('fundChart:list')")
    public Double amountToBeReleased(){
        return lineService.amountToBeReleased();
    }

    @GetMapping("/fundPercentageComplete")
    @ApiOperation("资金发放完成率")
    @PreAuthorize("@el.check('fundChart:list')")
    public Double fundPercentageComplete(){
        return lineService.fundPercentageComplete();
    }

    @GetMapping("/richSource")
    @ApiOperation("资金来源饼图列表")
    @PreAuthorize("@el.check('fundChart:list')")
    public List<String> richSource(){
        return lineService.richSource();
    }


    @GetMapping("/fundRichSource")
    @ApiOperation("资金来源饼图数据展示")
    @PreAuthorize("@el.check('fundChart:list')")
    public  List<Map> fundRichSource(){
        return lineService.fundRichSource();
    }
}