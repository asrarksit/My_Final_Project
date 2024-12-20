package com.developer.finalprt.repository;
import com.developer.finalprt.models.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByCustomerCustomerIdAndIsPaidFalse(String customerId);
}



