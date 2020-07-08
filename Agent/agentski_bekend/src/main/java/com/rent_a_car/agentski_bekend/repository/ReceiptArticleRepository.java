package com.rent_a_car.agentski_bekend.repository;

import com.rent_a_car.agentski_bekend.model.Reciept;
import com.rent_a_car.agentski_bekend.model.RecieptArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptArticleRepository extends JpaRepository<RecieptArticle, Integer> {
}
