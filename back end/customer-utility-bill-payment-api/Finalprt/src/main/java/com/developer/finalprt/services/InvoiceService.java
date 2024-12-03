package com.developer.finalprt.services;
import com.developer.finalprt.models.Bill;
import com.developer.finalprt.models.Payment;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class InvoiceService {

    public byte[] generateInvoicePdf(Payment payment) throws DocumentException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // Add invoice header
        document.add(new Paragraph("Payment Invoice"));
        document.add(new Paragraph("Bharat Bijili Corporation (BBC)"));
        document.add(new Paragraph("Customer Service: +91 12345 67890 | Email: support@bbc.com\n\n"));

        // Add payment details
        document.add(new Paragraph("Payment Details"));
        document.add(new Paragraph("Payment ID: " + payment.getPaymentId()));
        document.add(new Paragraph("Amount Paid: " + payment.getAmountPaid()));
        document.add(new Paragraph("Payment Date: " + payment.getPaymentDate()));
        document.add(new Paragraph("Payment Method: " + payment.getPaymentMethod()));

        // Add bill details
        Bill bill = payment.getBill();
        document.add(new Paragraph("\nBill ID: " + bill.getId()));
        document.add(new Paragraph("Customer ID: " + bill.getCustomer().getCustomerId()));
        document.add(new Paragraph("Unit Consumed: " + bill.getUnitConsumed()));
        document.add(new Paragraph("Bill Due Date: " + bill.getBillDueDate()));
        document.add(new Paragraph("Duration (Months): " + bill.getDurationInMonths()));
        document.add(new Paragraph("Original Amount Due: " + bill.getAmountDue()));

        // Add final payment details
        document.add(new Paragraph("\nDiscount Applied: 5%")); // Sample discount
        document.add(new Paragraph("Net Final Amount Due: " + payment.getAmountPaid()));
        document.add(new Paragraph("Is Paid Early: " + (payment.isPaidEarly() ? "Yes" : "No")));

        document.add(new Paragraph("\nThank you for your payment!"));

        document.close();
        return outputStream.toByteArray();
    }
}

