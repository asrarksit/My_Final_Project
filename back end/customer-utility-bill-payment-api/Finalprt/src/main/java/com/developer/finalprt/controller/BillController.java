package com.developer.finalprt.controller;
import com.developer.finalprt.models.Bill;
import com.developer.finalprt.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/bills")
public class BillController {
    private final BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping()
    public ResponseEntity<List<Bill>> getBills() {
        List<Bill> bills = billService.findAllBills();
        return ResponseEntity.ok(bills);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable Long id) {
        return billService.getBillById(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Bill>> getUnpaidBillsByCustomerId(@PathVariable String customerId) {
        List<Bill> unPaidBills = billService.getUnpaidBillsByCustomerId(customerId);
        return ResponseEntity.ok(unPaidBills);
    }
    @PostMapping
    public ResponseEntity<Bill> createBill(@RequestBody Bill bill) {
        Bill savedBill = billService.createBill(bill);
        return new ResponseEntity<>(savedBill, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable Long id) {
        if (billService.deleteBill(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

