package com.tvttavares.neo4jdataextractor.neo4j.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserPaymentDTO {

    private Integer reefCloudId;

    private String paymentId;

}
