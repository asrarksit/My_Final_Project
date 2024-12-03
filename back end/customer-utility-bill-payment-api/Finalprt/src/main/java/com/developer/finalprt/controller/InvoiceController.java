//package com.developer.finalprt.controller;
//import com.developer.finalprt.models.Bill;
//import com.developer.finalprt.models.Customer;
//import com.developer.finalprt.models.Payment;
//import com.developer.finalprt.services.InvoiceService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//
//@RestController
//@RequestMapping("/api/invoice")
//public class InvoiceController {
//
//    @Autowired
//    private InvoiceService invoiceService;
//
//    @GetMapping("/download/{paymentId}")
//    public ResponseEntity<byte[]> downloadInvoice(@PathVariable Long paymentId) {
//        try {
//            Payment payment = findPaymentById(paymentId); // Assume this method exists and fetches payment details
//            byte[] pdfBytes = invoiceService.generateInvoicePdf(payment);
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_PDF);
//            headers.setContentDispositionFormData("attachment", "invoice.pdf");
//
//            return ResponseEntity.ok()
//                    .headers(headers)
//                    .body(pdfBytes);
//
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
//
//    // Dummy method for payment details
//    private Payment findPaymentById(Long paymentId) {
//
//    }
//}
//
