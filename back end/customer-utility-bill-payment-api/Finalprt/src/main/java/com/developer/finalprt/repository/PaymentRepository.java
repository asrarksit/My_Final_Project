package com.developer.finalprt.repository;
import com.developer.finalprt.models.Bill;
import com.developer.finalprt.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
