package com.renting_service.repository;

import com.renting_service.model.Reciept;
import com.renting_service.model.RecieptArticle;
import com.renting_service.model.RentRequest;

import java.util.List;

public interface ReceiptServiceInterface {

    List<RecieptArticle> generateReceiptArticles(RentRequest rr);
    double calculateDiscount(double price,  double discountPercentage);
    double calculateSum(List<RecieptArticle> articles);
    Reciept save(Reciept r);
    List<Reciept> findAll();

}
