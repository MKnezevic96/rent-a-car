package com.rent_a_car.agentski_bekend.service;

import com.rent_a_car.agentski_bekend.model.*;
import com.rent_a_car.agentski_bekend.model.enums.ServicesEnum;
import com.rent_a_car.agentski_bekend.repository.ReceiptRepository;
import com.rent_a_car.agentski_bekend.service.interfaces.ReceiptServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReceiptService implements ReceiptServiceInterface {


    @Autowired
    ReceiptRepository receiptRepository;

    @Override
    public List<RecieptArticle> generateReceiptArticles(RentRequest rr){

        List<RecieptArticle> articles = new ArrayList<>();

        Pricing p = rr.getCarId().getPricing();
        long daysBetween = ChronoUnit.DAYS.between(rr.getStartDate().toInstant(), rr.getEndDate().toInstant());

        RecieptArticle dailyRent  = new RecieptArticle();
        dailyRent.setService(ServicesEnum.DAILY_RENT);

        if (daysBetween > p.getDiscountDays()) {
            dailyRent.setPrice(daysBetween * calculateDiscount(p.getRegularPrice(), p.getDiscountPercent()));
        } else {
            dailyRent.setPrice(daysBetween * p.getRegularPrice());
        }

        dailyRent.setDeleted(false);

        articles.add(dailyRent);

        if (p.getCollisionDamage() != 0) {
            RecieptArticle waiver  = new RecieptArticle();
            waiver.setService(ServicesEnum.COLISION_DAMAGE_WAIVER);
            waiver.setPrice(p.getCollisionDamage());
            waiver.setDeleted(false);
            articles.add(waiver);
        }

        if (p.getOverusePrice() != 0) {

            double additionalKilometres = rr.getRentingReport().getAddedMileage() -  p.getDistanceLimit();

            if (additionalKilometres > 0) {

                RecieptArticle overusedPrice  = new RecieptArticle();
                overusedPrice.setService(ServicesEnum.KILOMETER_OVER_LIMIT);
                overusedPrice.setPrice(p.getOverusePrice() * additionalKilometres);
                overusedPrice.setDeleted(false);
                articles.add(overusedPrice);

            }
        }

        return articles;
    }


    @Override
    public double calculateDiscount(double price, double discountPercentage){
        return price * discountPercentage / 100.0f;
    }


    @Override
    public double calculateSum(List<RecieptArticle> articles) {

        double sum = 0;

        for (RecieptArticle ra : articles) {
            sum = sum + ra.getPrice();
        }

        return sum;
    }

    @Override
    public Reciept save(Reciept r) {
        return receiptRepository.save(r);
    }

    @Override
    public List<Reciept> findAll() {
        return receiptRepository.findAll();
    }


}
