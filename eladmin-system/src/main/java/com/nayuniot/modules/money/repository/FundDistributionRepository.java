package com.nayuniot.modules.money.repository;

import com.nayuniot.modules.money.domain.FundDistribution;
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

public interface FundDistributionRepository extends JpaRepository<FundDistribution, Long>, JpaSpecificationExecutor<FundDistribution> {


    /**
     * 根据id批量删除数据
     *
     * @param id
     */
    public void deleteAllByIdIn(Set<Long> id);


    @Override
    @EntityGraph(value = "FundDistribution", type = EntityGraph.EntityGraphType.FETCH)
    Page<FundDistribution> findAll(Specification<FundDistribution> specification, Pageable pageable);

    @Override
    @EntityGraph(value = "FundDistribution", type = EntityGraph.EntityGraphType.FETCH)
    List<FundDistribution> findAll(Specification<FundDistribution> specification);

    /**
     * 根据规划id 查询人员id集合
     * @param fundPlanning
     * @return
     */
    @Query(value = "SELECT fd.employee.id FROM FundDistribution AS fd where fd.fundPlanning.id = ?1")
    List<Long> selFundDistribution(Long fundPlanning);

//    查询该条规划的数据已经发放的条数
    @Query(value = "select sum(fd.number) FROM FundDistribution fd where fd.fundPlanning.id=?1")
    Long findSumByFundPlanning(Long fundPlanningId);

//    根据人员id查询集合
    @Query(value = "FROM FundDistribution AS fd where fd.employee.id = ?1")
    @EntityGraph(value = "FundDistribution", type = EntityGraph.EntityGraphType.FETCH)
    List<FundDistribution> selFundDistributionByEmployeeId(Long employeeId);


//     查询七天每天发放的金额
    @Query(value = "select substring(fd.createTime,1,10) as time,sum(fd.number*fd.fundPlanning.planMoney) as sumMoney from FundDistribution fd " +
            "where fd.createTime between :startTime and :endTime " +
            "group by substring(fd.createTime,1,10) ")
    List<Map> fundDistributionLineChart(@Param("startTime") Timestamp startTime, @Param("endTime") Timestamp endTime);

//    发放金额数量
    @Query(value = "select sum(f.number *f.fundPlanning.planMoney) from FundDistribution f")
    Double fundDistributionSum();



}
