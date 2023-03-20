package com.nayuniot.modules.management.repository;

import com.nayuniot.modules.management.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Set;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    @Override
    @EntityGraph(value = "employee", type = EntityGraph.EntityGraphType.FETCH)
    Page<Employee> findAll(Specification<Employee> specification, Pageable pageable);

    @EntityGraph(value = "employee", type = EntityGraph.EntityGraphType.FETCH)
    void deleteAllByIdIn(Set<Long> ids);

    /**
     * 根据身份证号查询
     *
     * @param IdNumber
     * @return
     */
    @EntityGraph(value = "employee", type = EntityGraph.EntityGraphType.FETCH)
    Employee findAllByIdNumberEquals(String IdNumber);

    /**
     * 没有规划人员的数据
     * @param id 已规划人员id
     * @return 人员列表
     */
    @EntityGraph(value = "employee", type = EntityGraph.EntityGraphType.FETCH)
    List<Employee> findByIdNotIn(Set<Long> id);

    @EntityGraph(value = "employee", type = EntityGraph.EntityGraphType.FETCH)
    Employee findByPhone(String phone);

}
