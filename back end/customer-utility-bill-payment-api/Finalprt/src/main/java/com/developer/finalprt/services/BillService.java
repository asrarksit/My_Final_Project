package com.developer.finalprt.services;
import com.developer.finalprt.models.Bill;
import com.developer.finalprt.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    public List<Bill> findAllBills() {
        return billRepository.findAll();
    }

    public Optional<Bill> getBillById(Long id) {
        return billRepository.findById(id);
    }
    public List<Bill> getUnpaidBillsByCustomerId(String customerId) {
        return billRepository.findByCustomerCustomerIdAndIsPaidFalse(customerId);
    }
    public Bill createBill(Bill bill) {
        return billRepository.save(bill);
    }

    public boolean deleteBill(Long id) {
        if (billRepository.existsById(id)) {
            billRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

