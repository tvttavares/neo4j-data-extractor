package com.tvttavares.neo4jdataextractor.neo4j.model;

import lombok.*;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Properties;

import java.util.Map;

@NodeEntity
@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@EqualsAndHashCode
public class User {

    @Id
    private String hangTagId;

    private String idToken;

    private int expiryDate;

    @Properties
    private Map<String, String> vehicleMap;

    @Properties
    private Map<String, String> cardMap;

    private String previousPrimaryCardId;

    private String status;

    private Integer reefCloudId;

    @Builder.Default
    private String lastAddedCCStatus = "";

}
