package com.developer.finalprt.repository;

import com.developer.finalprt.models.RazorPay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RazorPayRepository extends JpaRepository<RazorPay, Integer> {
    public RazorPay findByRazorpayId(String orderId);
}