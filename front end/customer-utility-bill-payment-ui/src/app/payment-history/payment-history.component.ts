import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { PaymentService } from '../../services/payment.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from '../header/header.component';
import { NgbNavModule } from '@ng-bootstrap/ng-bootstrap';
import { BillService } from '../../services/bill.service';
import { Router } from '@angular/router';
import { PaymentComponent } from '../payment/payment.component';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-payment-history',
  standalone: true,
  imports: [CommonModule,FormsModule,HeaderComponent,NgbNavModule, PaymentComponent],
  templateUrl: './payment-history.component.html',
  styleUrl: './payment-history.component.css'
})
  export class PaymentHistoryComponent implements OnInit {
    paymentHistory: any[] = [];
    transactionHistory: any[] = [];
    isPaymentVisible: boolean = false;
    billDetails: any;
    @ViewChild('invoice') invoiceElement!: ElementRef;
    customerId: any =  sessionStorage.getItem('customerId');
      
  
    constructor(private paymentService: PaymentService,private billService: BillService,private router:Router,private http: HttpClient) {}
  
    ngOnInit() {
      this.loadPaymentHistory();
      this.loadTransactionHistory();
    }
    onSubmitPay(bill: any){
      // this.router.navigate(['/payment']);
      this.billDetails = bill;
      this.isPaymentVisible = true;
    }
    loadTransactionHistory() {
      this.paymentService.getPaymentHistory().subscribe(data => {
        this.transactionHistory = data;
        console.log(this.transactionHistory)
      });
    }
    loadPaymentHistory() {
      this.billService.getUnpaidBillsByCustomerId().subscribe(data => {
        this.paymentHistory = data;
        console.log(this.paymentHistory)
      });
    }

    downloadInvoice(paymentId: number) {
      this.http.get(`/api/payments/download/${paymentId}`, { responseType: 'blob' }).subscribe((response: Blob) => {
        const blob = new Blob([response], { type: 'application/pdf' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'invoice.pdf';
        a.click();
        window.URL.revokeObjectURL(url);
      });
    }
  }
