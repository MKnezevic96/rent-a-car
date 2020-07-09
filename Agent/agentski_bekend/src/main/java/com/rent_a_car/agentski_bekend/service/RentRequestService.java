package com.rent_a_car.agentski_bekend.service;
import com.rent_a_car.agentski_bekend.model.RentRequest;
import com.rent_a_car.agentski_bekend.model.UserRequest;
import com.rent_a_car.agentski_bekend.model.enums.RequestStatus;
import com.rent_a_car.agentski_bekend.repository.RentRequestRepository;
import com.rent_a_car.agentski_bekend.repository.UserRequestRepository;
import com.rent_a_car.agentski_bekend.service.interfaces.RentRequestServiceInterface;
import com.sun.xml.messaging.saaj.packaging.mime.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@Service
public class RentRequestService implements RentRequestServiceInterface {

    @Autowired
    private RentRequestRepository userRequestRepository;




    @Override
    public RentRequest findById(Integer id) {
        return userRequestRepository.findById(id).orElse(null);
    }

    @Override
    public RentRequest save(RentRequest request) {
        return userRequestRepository.save(request);
    }

    @Override
    public List<RentRequest> findAll() {
        List<RentRequest> result = userRequestRepository.findAll();
        return result;
    }

    @Override
    public void canclePendingReservations(Date startDate, Date endDate, Integer id) {

        List<RentRequest> rrList = findAll();

        for (RentRequest rr : rrList) {
            if (rr.getCarId().getId().equals(id)) {
                if (rr.getStatus().equals(RequestStatus.PENDING)) {
                    if (startDate.before(rr.getStartDate()) && endDate.after(rr.getStartDate())) {
                        rr.setStatus(RequestStatus.CANCELED);
                    }
                    if (startDate.after(rr.getStartDate()) && endDate.before(rr.getEndDate())) {
                        rr.setStatus(RequestStatus.CANCELED);
                    }
                    if (startDate.before(rr.getEndDate()) && endDate.after(rr.getEndDate())) {
                        rr.setStatus(RequestStatus.CANCELED);
                    }
                    save(rr);
                }
            }
        }
    }

}