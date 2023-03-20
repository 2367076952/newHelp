package com.nayuniot.modules.line.repository;

import com.nayuniot.modules.management.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface LineRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    /**
     * 计算待审批总数量
     *
     * @return
     */
    @Query(value = "select count(ea) as count from Approval ea where ea.status=0")
    List<Integer> approvalSum();

    /**
     * 每日新增审批数量
     *
     * @return
     */
    @Query(value = "select count(ep) from Approval ep where ep.status =0 and ep.updateTime<=:start and ep.updateTime>=:end")
    Integer approvalAdd(Date start,Date end);

    /**
     * 每日处理审批数量
     */
    @Query(value = "select count(ep) from Approval ep where ep.status in (1,2,3) and ep.updateTime<=:start and ep.updateTime>=:end")
    Integer approvalLine(Date start,Date end);


    /**
     * 每天新增人数
     * @return
     */
    @Query(value = "select count(ep) from Employee ep where ep.createTime<=:start and ep.createTime>=:end")
    Integer findCreateTime(Date start,Date end);

    /**
     * 当天日期的总数居量
     *
     * @return
     */
    @Query(value = "select count(ep) from Employee ep where ep.createTime<=:end")
    Integer TodaySum(Date end);

    /**
     * 一个地区内的总人数
     *
     * @return
     */
    @Query(value = "select count(e) as count from Employee e")
    List<Integer> endNums();


    /**
     * 救助类型所在百分比
     * @return
     */
    @Query(value = " select p.name as name,count(p.id) as value from EmployeePurpose ep left join Purpose p on ep.purposeId=p.id group by p.id")
    List<Map<String, Long>> purposeSumPercentage();

    /**
     * 大屏展示每个社区的救助人数
     */
    @Query(nativeQuery = true,value = "SELECT\n" +
            "\tdept.NAME AS name,\n" +
            "\tll.mp AS purposeName,\n" +
            "\tcount( dept.NAME ) AS count,\n" +
            "\te.create_time as time \n" +
            "FROM\n" +
            "\temployee e\n" +
            "\tLEFT JOIN sys_dept dept \n" +
            "\tON e.address = dept.dept_id\n" +
            "\tLEFT JOIN ( SELECT \n" +
            "\temployee_id, \n" +
            "\tmoney_purpose.NAME AS mp\n" +
            "\tFROM employee_purpose \n" +
            "\tLEFT JOIN money_purpose \n" +
            "\tON employee_purpose.purpose_id = money_purpose.id ) ll \n" +
            "\tON ll.employee_id = e.employee_id \n" +
            "\tGROUP BY\n" +
            "\tNAME,\n" +
            "\tmp")
    List<Map<String,Integer>> purposePeople();
}
