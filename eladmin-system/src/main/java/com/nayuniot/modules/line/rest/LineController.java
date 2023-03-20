package com.nayuniot.modules.line.rest;

import com.nayuniot.modules.line.domain.NewEmployee;
import com.nayuniot.modules.line.service.LineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor //写在类上可以代替@Autowired注解，需要注意的是在注入时需要用final定义，或者使用@notnull注解
@Api(tags = "首页:折线图")
@RequestMapping("api/line")
public class LineController {
    private final LineService lineService;

    /**
     * 每日新增人数
     *
     * @return
     */
    @GetMapping
    @ApiOperation("每日增加人数")
    @PreAuthorize("@el.check('employee:list')")
    public NewEmployee line() {
        return lineService.lineEmployee();
    }

    /**
     * 查到的一周的日期
     *
     * @return
     */
    @GetMapping("/date")
    @ApiOperation("一周的日期")
    @PreAuthorize("@el.check('employee:list')")
    public List<String> date() {
        return lineService.date();
    }

    @GetMapping("/endSum")
    @ApiOperation("地区内总人数")
    @PreAuthorize("@el.check('employee:list')")
    public List<Integer> sum() {
        return lineService.endNums();
    }


    @GetMapping("/purposeSumPercentage")
    @ApiOperation("救助类型百分比")
    @PreAuthorize("@el.check('employee:list')")
    public List<Map<String, Long>> purposeSumPercentage() {
        return lineService.purposeSumPercentage();

    }

    @GetMapping("/approvalSum")
    @PreAuthorize("@el.check('approval:list')")//权限管理
    public List<Integer> approvalSum() {
        return lineService.approvalSum();
    }


    @ApiOperation(value = "每日新增审批数量")
    @GetMapping("/approvalAdd")
    @PreAuthorize("@el.check('approval:list')")//权限管理
    public List<Integer> approvalAdd() {
        return lineService.approvalAdd();
    }

    @ApiOperation(value = "每日完成审批数量")
    @GetMapping("/approvalLine")
    @PreAuthorize("@el.check('approval:list')")//权限管理
    public List<Integer> approvalLine() {
        return lineService.approvalLine();
    }



}



