package com.renting_service.repository;

import com.renting_service.model.RecieptArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptArticleRepository extends JpaRepository<RecieptArticle, Integer> {
}
