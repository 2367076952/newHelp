package com.nayuniot.modules.money.repository;

import com.nayuniot.modules.money.domain.FundRegistration;
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

public interface FundRegistrationRepository extends JpaRepository<FundRegistration, Long>, JpaSpecificationExecutor<FundRegistration> {

    @Override
    @EntityGraph(value = "fundRegistration", type = EntityGraph.EntityGraphType.FETCH)
    Page<FundRegistration> findAll(Specification<FundRegistration> specification, Pageable pageable);


//    Page<FundRegistration> findAllByPurposeId(Specification<FundRegistration> specification, Pageable pageable);

//    @EntityGraph(value = "fundRegistration", type = EntityGraph.EntityGraphType.FETCH)
//    @Query(value = "select fr " +
//            "from FundRegistration fr " +
//            "left join FundPlanning  fp on fr.id = fp.registrationId " +
//            "where fr.purposeList LIKE (:purposeList)" +
//            "group by fr.id " +
//            "order by fr.money -COALESCE(sum( fp.planMoney * fp.planNumber ),0) desc ")
//    Page<FundRegistration> findAllByPurposeIdIn(@Param(value = "purposeList")List<String> purposeList, Pageable pageable);

    /**
     * 根据id批量删除数据
     *
     * @param id
     */
    public void deleteAllByIdIn(Set<Long> id);

    FundRegistration findAllById(Long id);

    /**
     * 每收人资金的折线图
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    @Query(value = "select substring(fr.createTime,1,10) as time,sum(fr.money) as sumMoney from FundRegistration fr " +
            "where fr.createTime between :startTime and :endTime " +
            "group by substring(fr.createTime,1,10) ")
    List<Map> fundLineChart(@Param("startTime") Timestamp startTime, @Param("endTime") Timestamp endTime);

    /**
     * 可用资金
     *
     * @return 可用资金总数
     */
    @Query(value = "select sum(fr.money)-sum (fp.planNumber*fp.planMoney)from FundRegistration fr left join FundPlanning fp on fp.registrationId = fr.id")
    Double fundSum();

    /**
     * 资金来源饼图数据
     *
     * @return
     */
    @Query(value = "select sum(fr.money) as value,fr.source.name as name from FundRegistration  fr group by fr.source.id")
    List<Map> fundRichSource();

}
