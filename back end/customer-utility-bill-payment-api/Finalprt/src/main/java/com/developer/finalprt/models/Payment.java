package com.developer.finalprt.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;
    private Double amountPaid;
    private LocalDate paymentDate;
    private String paymentMethod;
    private boolean isSuccessful;
    private String paymentId;
    private String transactionId;
    private boolean isPaidEarly;
    private boolean isPaidOnline;
}

