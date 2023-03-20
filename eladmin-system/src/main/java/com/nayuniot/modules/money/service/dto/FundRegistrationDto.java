package com.nayuniot.modules.money.service.dto;

import com.nayuniot.base.BaseDTO;
import com.nayuniot.modules.money.domain.Purpose;
import com.nayuniot.modules.money.domain.Source;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
public class FundRegistrationDto extends BaseDTO implements Serializable {

    private Long id;

    private Double money;

    private Source source;

    private Set<Purpose> purpose;

    private String sourceName;
    //
    private String purposeName;

    private String recipient;

    private String receivingUnit;

    private Timestamp receiptTime;

    private String remarks;
}
