package com.nayuniot.modules.money.repository;

import com.nayuniot.modules.money.domain.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface SourceRepository extends JpaRepository<Source,Long>, JpaSpecificationExecutor<Source> {
    /**
     * 根据id批量删除数据
     * @param id
     */
    void deleteAllByIdIn(Set<Long> id);

    List<Source> findAll();

    @Query(value = "select s.name from Source s")
    List<String> richSource();

}
