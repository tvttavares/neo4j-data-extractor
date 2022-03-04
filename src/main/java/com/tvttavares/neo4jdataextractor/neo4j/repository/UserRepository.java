package com.tvttavares.neo4jdataextractor.neo4j.repository;

import com.tvttavares.neo4jdataextractor.neo4j.model.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface UserRepository extends Repository<User, String> {

    @Query("MATCH (n:User) RETURN n ")
    List<User> getAllUsers();
}