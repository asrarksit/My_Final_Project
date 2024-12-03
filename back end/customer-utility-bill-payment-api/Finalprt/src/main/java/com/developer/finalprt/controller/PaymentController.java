package com.developer.finalprt.controller;
import com.developer.finalprt.models.Payment;
import com.developer.finalprt.models.RazorPay;
import com.developer.finalprt.services.InvoiceService;
import com.developer.finalprt.services.PaymentService;
import com.developer.finalprt.services.RazorPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private RazorPayService razorPayService;
    @Autowired
    private InvoiceService invoiceService;


    @PostMapping(value="/create-order/{id}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<RazorPay> createCustomerOrder(@PathVariable Long id) throws Exception {
        RazorPay createdOrder= razorPayService.createOrder(id);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PostMapping("/handle-payment-callback")
    public ResponseEntity<Map<String, String>> handlePaymentCallback(@RequestBody Map<String, String> respPayload){
        System.out.println("payload"+ respPayload);
        razorPayService.updateOrder(respPayload);
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Optional<Payment> payment = paymentService.getPaymentById(id);
        return payment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/download/{paymentId}")
    public ResponseEntity<byte[]> downloadInvoice(@PathVariable Long paymentId) {
        try {
            Payment payment = paymentService.findPaymentById(paymentId);  // Fetch the payment
            byte[] pdfBytes = invoiceService.generateInvoicePdf(payment);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "invoice.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


}

