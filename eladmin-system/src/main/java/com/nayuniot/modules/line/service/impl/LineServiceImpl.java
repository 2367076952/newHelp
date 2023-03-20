package com.nayuniot.modules.line.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nayuniot.modules.line.domain.FundLineChart;
import com.nayuniot.modules.line.domain.NewEmployee;
import com.nayuniot.modules.line.repository.LineRepository;
import com.nayuniot.modules.line.service.LineService;
import com.nayuniot.modules.money.repository.FundDistributionRepository;
import com.nayuniot.modules.money.repository.FundPlanningRepository;
import com.nayuniot.modules.money.repository.FundRegistrationRepository;
import com.nayuniot.modules.money.repository.SourceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LineServiceImpl implements LineService {

    private final LineRepository lineRepository;
    private final FundRegistrationRepository fundRegistrationRepository;
    private final FundPlanningRepository fundPlanningRepository;
    private final FundDistributionRepository fundDistributionRepository;
    private final SourceRepository sourceRepository;



    /**
     * 获取数据  生成人员折线图
     *
     * @return
     */
    @Override
    public NewEmployee lineEmployee() {
        //新增人数
        List<Integer> createTime = new ArrayList<>();
        //每日总人数
        List<Integer> todaySum = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            try {
                LocalDate localDate = LocalDate.now().minusDays(i);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date start = df.parse(localDate + " " + "23:59:59");
                Date end = df.parse(localDate + " " + "00:00:00");
                Integer sum = lineRepository.TodaySum(start);
                Integer create = lineRepository.findCreateTime(start, end);
                createTime.add(create);
                todaySum.add(sum);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Collections.reverse(createTime);
        Collections.reverse(todaySum);
        NewEmployee newEmployee = new NewEmployee();
        newEmployee.setExpectedData(todaySum);
        newEmployee.setActualData(createTime);
        return newEmployee;
    }


    /**
     * 一周的日期
     *
     * @return
     */
    @Override
    public List<String> date() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate localDate = LocalDate.now().minusDays(i);
            list.add(localDate + "");
        }
        Collections.reverse(list);
        return list;
    }

    /**
     * 计算当前区的总人数
     *
     * @return
     */
    @Override
    public List<Integer> endNums() {
        return lineRepository.endNums();
    }

    /**
     * 救助类型百分比
     *
     * @return
     */
    @Override
    public List<Map<String, Long>> purposeSumPercentage() {
        return lineRepository.purposeSumPercentage();
    }


    /**
     * 计算待审批数量
     *
     * @return
     */
    @Override
    public List<Integer> approvalSum() {
        return lineRepository.approvalSum();
    }

    /**
     * 每日新增审批数量
     *
     * @return
     */
    @Override
    public List<Integer> approvalAdd() {

//        return approvalRepository.approvalAdd();
        List<Integer> addList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            try {
                LocalDate localDate = LocalDate.now().minusDays(i);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date start = df.parse(localDate + " " + "23:59:59");
                Date end = df.parse(localDate + " " + "00:00:00");
                Integer integers = lineRepository.approvalAdd(start, end);
                addList.add(integers);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Collections.reverse(addList);
        return addList;
    }

    @Override
    public List<Integer> approvalLine() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            try {
                LocalDate localDate = LocalDate.now().minusDays(i);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date start = df.parse(localDate + " " + "23:59:59");
                Date end = df.parse(localDate + " " + "00:00:00");
                Integer integers = lineRepository.approvalLine(start, end);
                list.add(integers);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Collections.reverse(list);
        return list;
    }

    /**
     * 每日新增资金折线图
     *
     * @return key 属性名 value 值
     */
    @Override
    public Map<String, Long> fundLineChart() throws ParseException {
        //获取当前日期时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endTime = dateFormat.format(System.currentTimeMillis());

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 7);
        List<Map> mapList = fundRegistrationRepository.fundLineChart(Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()) + " 00:00:00"), Timestamp.valueOf(endTime));
        JSONArray array = JSONArray.parseArray(JSON.toJSONString(mapList));
        List<FundLineChart> list = JSONObject.parseArray(array.toJSONString(), FundLineChart.class);
        Map<String, Long> sumMoney = list.stream().collect(Collectors.toMap(FundLineChart::getTime, FundLineChart::getSumMoney));

        return sumMoney;
    }

    /**
     * 每日资金规划折线图
     *
     * @return key 属性名 value 值
     */
    @Override
    public Map<String, Long> fundChartPlanning() throws ParseException {
        //获取当前日期时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endTime = dateFormat.format(System.currentTimeMillis());

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 7);
        List<Map> mapList = fundPlanningRepository.fundPlanningLineChart(Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()) + " 00:00:00"), Timestamp.valueOf(endTime));
        JSONArray array = JSONArray.parseArray(JSON.toJSONString(mapList));
        List<FundLineChart> list = JSONObject.parseArray(array.toJSONString(), FundLineChart.class);

        return list.stream().collect(Collectors.toMap(FundLineChart::getTime, FundLineChart::getSumMoney));
    }

    /**
     * 可用资金
     * 
     * @return 可用资金总额 总资金 - 已规划
     */
    @Override
    public Double fundSum() {
        return fundRegistrationRepository.fundSum();
    }

    @Override
    public Map<String, Long> fundDistributionLineChart() throws ParseException {
        //获取当前日期时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endTime = dateFormat.format(System.currentTimeMillis());

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 7);
        List<Map> mapList = fundDistributionRepository.fundDistributionLineChart(Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()) + " 00:00:00"), Timestamp.valueOf(endTime));
        JSONArray array = JSONArray.parseArray(JSON.toJSONString(mapList));
        List<FundLineChart> list = JSONObject.parseArray(array.toJSONString(), FundLineChart.class);

        return list.stream().collect(Collectors.toMap(FundLineChart::getTime, FundLineChart::getSumMoney));

    }

    /**
     * 待发放金额
     * @return  规划金额 -发放金额
     */
    @Override
    public Double amountToBeReleased() {
        return fundPlanningRepository.fundPlanningSum()-fundDistributionRepository.fundDistributionSum();
    }

    /**
     * 资金发放完成率
     * @return  发放金额 /规划金额 *100
     */
    @Override
    public Double fundPercentageComplete() {
        return  Math.round(((fundDistributionRepository.fundDistributionSum()/fundPlanningRepository.fundPlanningSum())*100)*100)*0.01d;
    }

    @Override
    public List<String> richSource() {
        return sourceRepository.richSource();
    }

    @Override
    public List<Map> fundRichSource() {
        List<Map> mapList = fundRegistrationRepository.fundRichSource();
        for (Map map : mapList) {
            map.forEach((k,v)->{
                System.out.println(k);
                System.err.println(v);
            });
        }
        return mapList;
    }
}
