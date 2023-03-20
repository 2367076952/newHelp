/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.nayuniot.modules.money.service.dto;

import com.nayuniot.annotation.Query;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
 * @author gjq
 * @date 2019-6-4 14:49:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundRegistrationQueryCriteria {

    @Query(propName = "name", type = Query.Type.INNER_LIKE, joinName = "source")
    private String source;

    /**
     * 大于等于
     */
    @Query(type = Query.Type.GREATER_THAN, propName = "money")
    private Integer moneyMin;

    /**
     * 小于等于
     */
    @Query(type = Query.Type.LESS_THAN, propName = "money")
    private Integer moneyMax;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> receiptTime;

    @Query(type = Query.Type.OR_LIKE, propName = "purposeList")
    private Set<Integer> purposeId;

    @Query(type = Query.Type.NOT_EQUAL)
    private Integer status;

}