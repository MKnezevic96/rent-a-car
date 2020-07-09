package com.rent_a_car.agentski_bekend.repository;

import com.rent_a_car.agentski_bekend.model.Pricing;
import com.rent_a_car.agentski_bekend.model.Reciept;
import com.rent_a_car.agentski_bekend.model.RecieptArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceiptRepository extends JpaRepository<Reciept, Integer> {


}
