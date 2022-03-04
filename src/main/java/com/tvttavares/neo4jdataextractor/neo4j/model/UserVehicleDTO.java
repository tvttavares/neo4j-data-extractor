package com.tvttavares.neo4jdataextractor.neo4j.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserVehicleDTO {

    private Integer reefCloudId;

    private String vehicleId;
}
