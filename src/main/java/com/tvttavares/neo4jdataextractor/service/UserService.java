package com.tvttavares.neo4jdataextractor.service;

import com.tvttavares.neo4jdataextractor.format.UserPaymentCsvDataFormat;
import com.tvttavares.neo4jdataextractor.format.UserVehicleCsvDataFormat;
import com.tvttavares.neo4jdataextractor.neo4j.model.User;
import com.tvttavares.neo4jdataextractor.neo4j.model.UserPaymentDTO;
import com.tvttavares.neo4jdataextractor.neo4j.model.UserVehicleDTO;
import com.tvttavares.neo4jdataextractor.neo4j.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAllUsers() {
        return userRepository.getAllUsers();
    }

    public List<UserPaymentDTO> findAllUsersAndPaymentIDs() {
        List<User> userList = findAllUsers();
        List<UserPaymentDTO> userPaymentDTOList = new ArrayList<>();

        userList.forEach(user -> {
            user.getCardMap().values().forEach(card -> {
                userPaymentDTOList.add(new UserPaymentDTO(user.getReefCloudId(), card));
            });
        });

        return userPaymentDTOList;
    }

    public void generateUserPaymentIdCSV(HttpServletResponse response) {
        List<UserPaymentDTO> userPaymentDTOList = findAllUsersAndPaymentIDs();
        UserPaymentCsvDataFormat userPaymentCsvDataFormat = new UserPaymentCsvDataFormat(userPaymentDTOList);

        try {
            log.info("Starting generating Csv file");
            userPaymentCsvDataFormat.generateFile(response);
            log.info("Csv file generated with success.");
        } catch (IOException e) {
            log.info("Failure generating csv file.");
            log.error(e.getMessage());
        }
    }

    public List<UserVehicleDTO> findAllUsersAndVehicleIDs() {
        List<User> userList = findAllUsers();
        List<UserVehicleDTO> userVehicleDTOList = new ArrayList<>();

        userList.forEach(user -> {
            user.getVehicleMap().values().forEach(vehicle -> {
                userVehicleDTOList.add(new UserVehicleDTO(user.getReefCloudId(), vehicle));
            });
        });

        return userVehicleDTOList;
    }

    public void generateUserVehicleIdCSV(HttpServletResponse response) {
        List<UserVehicleDTO> userVehicleDTOList = findAllUsersAndVehicleIDs();
        UserVehicleCsvDataFormat userPaymentCsvDataFormat = new UserVehicleCsvDataFormat(userVehicleDTOList);

        try {
            log.info("Starting generating Csv file");
            userPaymentCsvDataFormat.generateFile(response);
            log.info("Csv file generated with success.");
        } catch (IOException e) {
            log.info("Failure generating csv file.");
            log.error(e.getMessage());
        }
    }
}