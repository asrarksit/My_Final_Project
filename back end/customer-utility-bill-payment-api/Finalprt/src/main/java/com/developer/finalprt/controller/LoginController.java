package com.developer.finalprt.controller;
import com.developer.finalprt.models.Customer;
import com.developer.finalprt.repository.CustomerRepository;
import com.developer.finalprt.services.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@CrossOrigin
@RequestMapping("/api/login")
public class LoginController {

    private final EmailService emailService;
    private final CustomerRepository customerRepository;
    private final Map<String, String> otpStorage = new HashMap<>();

    @Autowired
    public LoginController(EmailService emailService, CustomerRepository customerRepository) {
        this.emailService = emailService;
        this.customerRepository = customerRepository;
    }

    @PostMapping("/send-otp")
    public Map<String, String> sendOtp(@RequestBody Map<String, String> request) throws MessagingException {
        String customerId = request.get("customerId");

        Customer customer = customerRepository.findByCustomerId(customerId);
        String otp = generateOtp();
        otpStorage.put(customerId, otp);

        emailService.sendOtp(customer.getEmail(), "OTP for Login in BBC-UBP Portal", otp);

        Map<String, String> response = new HashMap<>();
        response.put("message", "OTP sent to your email."+otp+" ");
        return response;
    }

    @PostMapping("/verify-otp")
    public boolean verifyOtp(@RequestBody Map<String, String> otpData) {
        String customerId = otpData.get("customerId");
        String otp = otpData.get("otp");

        if (otpStorage.containsKey(customerId) && otp.equals(otpStorage.get(customerId))) {
            return true;
        } else {
            return false;
        }
    }

    private String generateOtp() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }
}
