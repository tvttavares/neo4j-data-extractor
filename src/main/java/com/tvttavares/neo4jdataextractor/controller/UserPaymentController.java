package com.tvttavares.neo4jdataextractor.controller;

import com.tvttavares.neo4jdataextractor.neo4j.model.UserPaymentDTO;
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
@Api(tags = USER_P)
@Slf4j
public class UserPaymentController {

    private final UserService userService;

    public UserPaymentController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/users/payment")
    @ApiOperation(value = USER_PAYMENT)
    public ResponseEntity<List<UserPaymentDTO>> getUsersPaymentIds(HttpServletResponse response) {
        log.info("Starting request for all user payments in Json format");
        List<UserPaymentDTO> userList = userService.findAllUsersAndPaymentIDs();
        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }

    @GetMapping(path = "/users/payment-csv")
    @ApiOperation(value = USER_PAYMENT_CSV)
    public ResponseEntity<Object> getUserPaymentIdCSV(HttpServletResponse response) {
        log.info("Starting request for all users in CSV format");
        userService.generateUserPaymentIdCSV(response);

        return ResponseEntity.status(response.getContentType() == null ? HttpStatus.NOT_FOUND : HttpStatus.OK)
                .body(null);
    }
}
