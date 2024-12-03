package com.developer.finalprt.services;
import com.developer.finalprt.models.Bill;
import com.developer.finalprt.models.Payment;
import com.developer.finalprt.models.RazorPay;
import com.developer.finalprt.repository.BillRepository;
import com.developer.finalprt.repository.PaymentRepository;
import com.developer.finalprt.repository.RazorPayRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class RazorPayService {

    private static final double EARLY_PAYMENT_DISCOUNT = 0.05; // 5% discount
    private static final double ONLINE_PAYMENT_DISCOUNT = 0.05;

    @Autowired
    private RazorPayRepository razorPayRepository;

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private BillRepository billRepository;
    @Value("${razorpay.key.id}")
    private String razorPayKey;

    @Value("${razorpay.secret.key}")
    private String razorPaySecret;

    private RazorpayClient client;

    public RazorPay createOrder(Long billId) throws Exception {
        JSONObject orderRequest = new JSONObject();
        var bill = billRepository.findById(billId).orElseThrow();
        var razorPay = new RazorPay();
        razorPay.setBill(bill);
        double amount = razorPay.getBill().getAmountDue();

        if (LocalDate.now().isBefore(razorPay.getBill().getBillDueDate())) {
            amount -= amount * EARLY_PAYMENT_DISCOUNT;
        }
        amount -= amount * ONLINE_PAYMENT_DISCOUNT;


        orderRequest.put("amount", (int)(amount * 100)); // Amount in paise for Razorpay
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", bill.getCustomer().getEmail());

        this.client = new RazorpayClient(razorPayKey, razorPaySecret);
        Order razorPayOrder = client.orders.create(orderRequest);

        razorPay.setRazorpayId(razorPayOrder.get("id"));
        razorPayRepository.save(razorPay);

        return razorPay;
    }

    public RazorPay updateOrder(Map<String, String> responsePayLoad) {
        String razorPayOrderId = responsePayLoad.get("billId");
        System.out.println(razorPayOrderId);
        Bill bill = billRepository.findById(Long.parseLong(razorPayOrderId)).orElseThrow();
        bill.setPaid(true);
        Payment payment = new Payment();
        System.out.println("billId Sets TRue");
        bill.setPaid(true);
        System.out.println("billId Sets TRue");
        payment.setBill(bill);
        payment.setAmountPaid(bill.getAmountDue());
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentMethod("ONLINE");
        payment.setSuccessful(true);

        payment.setPaidEarly(LocalDate.now().isBefore(bill.getBillDueDate()));
        payment.setPaidOnline(true);

        paymentRepository.save(payment);

        RazorPay order = razorPayRepository.findByRazorpayId(razorPayOrderId);



        razorPayRepository.save(order);

        return order;
    }
}
