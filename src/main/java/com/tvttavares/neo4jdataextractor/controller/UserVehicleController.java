package com.tvttavares.neo4jdataextractor.controller;

import com.tvttavares.neo4jdataextractor.neo4j.model.UserVehicleDTO;
import com.tvttavares.neo4jdataextractor.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.tvttavares.neo4jdataextractor.config.SwaggerLinks.*;

@RestController
@RequestMapping("/v1")
@Api(tags = USER_V)
@Slf4j
public class UserVehicleController {

    private final UserService userService;

    public UserVehicleController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/users/vehicle")
    @ApiOperation(value = USER_VEHICLE)
    public ResponseEntity<List<UserVehicleDTO>> getUsersVehicleIds(HttpServletResponse response) {
        log.info("Starting request for all user vehicles in Json format");
        List<UserVehicleDTO> userList = userService.findAllUsersAndVehicleIDs();
        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }

    @GetMapping(path = "/users/vehicle-csv")
    @ApiOperation(value = USER_VEHICLE_CSV)
    public ResponseEntity<Object> getUserVehicleIdCSV(HttpServletResponse response) {
        log.info("Starting request for all users in CSV format");
        userService.generateUserVehicleIdCSV(response);

        return ResponseEntity.status(response.getContentType() == null ? HttpStatus.NOT_FOUND : HttpStatus.OK)
                .body(null);
    }
}
