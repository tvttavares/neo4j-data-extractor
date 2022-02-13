package com.tvttavares.neo4jdataextractor.service;

import com.tvttavares.neo4jdataextractor.format.UserPaymentCsvDataFormat;
import com.tvttavares.neo4jdataextractor.neo4j.model.User;
import com.tvttavares.neo4jdataextractor.neo4j.model.UserPaymentDTO;
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

    public void findAllUsersAndPaymentIDs(HttpServletResponse response) {
        List<User> userList = findAllUsers();
        List<UserPaymentDTO> userPaymentDTOList = new ArrayList<>();

        userList.forEach(user -> {
            user.getCardMap().values().forEach(card -> {
                userPaymentDTOList.add(new UserPaymentDTO(user.getReefCloudId(), card));
            });
        });

        UserPaymentCsvDataFormat userPaymentCsvDataFormat = new UserPaymentCsvDataFormat(userPaymentDTOList);

        try {
            log.info("Starting generating Csv file");
            userPaymentCsvDataFormat.generateFile(response);
            log.info("Csv file generate with success.");
        } catch (IOException e) {
            log.info("Failure generating csv file.");
            log.error(e.getMessage());
        }
    }
}