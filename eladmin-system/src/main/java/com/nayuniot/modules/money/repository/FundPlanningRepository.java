package com.nayuniot.modules.money.repository;

import com.nayuniot.modules.money.domain.FundPlanning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FundPlanningRepository extends JpaRepository<FundPlanning, Long>, JpaSpecificationExecutor<FundPlanning> {


    /**
     * 根据id批量删除数据
     *
     * @param id
     */
    public void deleteAllByIdIn(Set<Long> id);


    List<FundPlanning> findAllByRegistrationId(String id);

    @Override
    @EntityGraph(value = "FundPlanning", type = EntityGraph.EntityGraphType.FETCH)
    Page<FundPlanning> findAll(Specification<FundPlanning> specification, Pageable pageable);

    /**
     * 每收人资金的折线图
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    @Query(value = "select substring(fp.createTime,1,10) as time,sum(fp.planMoney*fp.planNumber) as sumMoney from FundPlanning fp " +
            "where fp.createTime between :startTime and :endTime " +
            "group by substring(fp.createTime,1,10) ")
    List<Map> fundPlanningLineChart(@Param("startTime") Timestamp startTime, @Param("endTime") Timestamp endTime);

    //    发放金额数量
    @Query(value = "select sum(f.planNumber*f.planMoney) from FundPlanning f")
    Double fundPlanningSum();

}
