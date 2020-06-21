package com.rent_a_car.agentski_bekend.repository;

import com.rent_a_car.agentski_bekend.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findAll();
}
