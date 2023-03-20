package com.nayuniot.modules.line.service;

import com.nayuniot.modules.line.domain.NewEmployee;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface LineService {


    /**
     * 人员折线图数据
     * @return
     */
    public NewEmployee lineEmployee();

    //获取一个周期的时间
    public List<String> date();

    //计算当前地区的总人数
    public List<Integer> endNums();

    //救助原因百分比
    public List<Map<String, Long>> purposeSumPercentage();

    //还剩的审批数量
    public List<Integer> approvalSum();

    //每日新增审批量
    public List<Integer> approvalAdd();

    //每日处理审批数量
    public List<Integer> approvalLine();

    /**
     * 每日新增资金折线图
     * @return  key 属性名 value 值
     */
    Map<String, Long> fundLineChart() throws ParseException;


    /**
     * 每日资金规划折线图
     * @return  key 属性名 value 值
     */
    Map<String, Long> fundChartPlanning() throws ParseException;

    /**
     * 可用资金
     * @return 可用资金总额 总资金 - 已规划
     */
    Double fundSum();

    /**
     * 每日资金发放折线图
     * @return  key 属性名 value 值
     */
    Map<String, Long> fundDistributionLineChart() throws ParseException;

    /**
     * 待发放金额
     * @return  待发放金额
     */
    Double amountToBeReleased();

    /**
     * 资金发放完成率
     * @return 发放金额 /规划金额 *100
     */
    Double fundPercentageComplete();

    List<String> richSource();

    List<Map> fundRichSource();

}
