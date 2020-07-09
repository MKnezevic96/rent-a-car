package com.rent_a_car.agentski_bekend.service.interfaces;

import com.rent_a_car.agentski_bekend.model.Reciept;
import com.rent_a_car.agentski_bekend.model.RecieptArticle;
import com.rent_a_car.agentski_bekend.model.RentRequest;
import com.rent_a_car.agentski_bekend.service.ReceiptService;

import java.util.List;

public interface ReceiptServiceInterface {

    List<RecieptArticle> generateReceiptArticles(RentRequest rr);
    double calculateDiscount(double price,  double discountPercentage);
    double calculateSum(List<RecieptArticle> articles);
    Reciept save(Reciept r);
    List<Reciept> findAll();


}
