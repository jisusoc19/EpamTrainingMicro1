package com.task3.Repository;


import java.util.Optional;

import com.task3.Entity.Trainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.task3.Entity.Trainee;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

public interface iTraineedao extends CrudRepository<Trainee,Long> {


	@Query("SELECT t FROM Trainee t WHERE t.userid.username = :username")
	Optional<Trainee> findByUsername(@Param("username") String username);

	@Query("SELECT t FROM Trainer t WHERE t.userid.username = :username AND t.userid.password = :password")
	Optional<Trainee> loggin(String username, String password );

    @Modifying
    @Query("DELETE FROM Trainee t WHERE t.userid.username = :username")
    void deleteByUsername(String username);
    
   

}
