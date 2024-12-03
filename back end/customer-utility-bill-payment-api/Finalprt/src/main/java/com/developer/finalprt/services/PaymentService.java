package com.developer.finalprt.services;
import com.developer.finalprt.models.Payment;
import com.developer.finalprt.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public Payment findPaymentById(Long paymentId) {
        // Fetch the payment from the repository by ID
        Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);

        // Handle the case when the payment is not found
        if (!optionalPayment.isPresent()) {
            throw new IllegalArgumentException("Payment not found for ID: " + paymentId);
        }

        // Return the found payment
        return optionalPayment.get();
    }

}
