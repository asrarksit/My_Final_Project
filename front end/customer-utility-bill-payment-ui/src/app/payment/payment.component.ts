import { Component, Input, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RazorpayService } from '../../services/razorpay.service';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from '../app.component';
import { HeaderComponent } from '../header/header.component';
import { PaymentService } from '../../services/payment.service';
import customers from 'razorpay/dist/types/customers';
import { BillService } from '../../services/bill.service';
import { UserService } from '../../services/user.service';
import { log } from 'console';
import { Router } from '@angular/router';

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [CommonModule,FormsModule,ReactiveFormsModule,HeaderComponent],
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {

  paymentForm: FormGroup;
  customerId: string = sessionStorage.getItem('customerId') || '';  // Fetching customerId from session storage
  billId: string = sessionStorage.getItem('billId') || '';
  currentDate = new Date();
  discountedAmount=0;
  discountMessage="";
  billDueMessage="";
  finalDueMessage="";


  @Input()billDetails: any;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private razorpayService: RazorpayService,
    private billService: BillService,
    private userService: UserService,
    private router: Router
  ) {
    this.paymentForm = this.fb.group({
      name: [''],
      email: [''],
      phone: [''],
      billId: [''],
      amount: [''],
      discountedAmount: ['']
    });
  }
  ngOnInit(): void {
    this.loadCustomerDetails();
    // this.loadBillDetails(); // Call to load the bill details
  }

  loadCustomerDetails() {

    this.billDueMessage="*Bill Due Amount : ₹"+this.billDetails.amountDue;
    this.discountMessage="*Discount Applied for Online Payment(5%)";
    let billDueDate = new Date(this.billDetails.billDueDate);
    console.log("Current Date",this.currentDate);
    console.log("bill due date", billDueDate);

    if(this.currentDate <= new Date(this.billDetails.billDueDate)){
      this.discountedAmount = this.billDetails?.amountDue - (this.billDetails?.amountDue * 0.05);
      this.discountMessage=`${this.discountMessage}+ Additional Discount for Early Payment(5%)`;
    }
    this.discountedAmount = this.billDetails?.amountDue - (this.billDetails?.amountDue * 0.05);
    this.finalDueMessage="*Amount to Pay : ₹"+this.discountedAmount;
    this.paymentForm.patchValue({
      name: this.billDetails?.customer?.name,
      email: this.billDetails?.customer?.email,
      phone: this.billDetails?.customer?.phoneNumber,
      billId: this.billDetails?.id,
      amount: this.billDetails?.amountDue,
      discountedAmount: this.discountedAmount
    });

    console.log(this.paymentForm.value);
  }

  loadBillDetails() {
    this.billService.getBills().subscribe((bill: any) => {
      this.paymentForm.patchValue({
        amount: bill.amount,
      });
      this.billId = bill.id;
      sessionStorage.setItem('billId', this.billId);
    });
  }

  onSubmit() {
    // console.log(this.paymentForm.value);
    const paymentDetails = {
      ...this.paymentForm.value
    };
    console.log(this.paymentForm.value.discountedAmount);
    this.razorpayService.createOrder(paymentDetails).subscribe((order: any) => {
      console.log(order);
      this.openRazorpay(order, paymentDetails);
    });
  }

  openRazorpay(order: any, paymentDetails: any) {
    console.log(order);
    const options = {
      "key": "rzp_test_8apreqTCDgqB22", 
      "amount": paymentDetails.discountedAmount*1000,
      "currency": "INR",
      "name": "BBC_UBP",
      "description": "Payment",
      "order_id": order.razorpayId,
      "prefill": {
        "name": paymentDetails.name,
        "email": paymentDetails.email,
        "contact": paymentDetails.phone
      },
      "theme": {
        "color": "#3399cc"
      },
      "handler": (response: any) => {
        console.log("Payment Successful", response);
        this.updateOrderStatus(response, order?.bill?.id, order.id);
      }
    };
    const rzp1 = new (window as any).Razorpay(options);
    rzp1.open();
  }

  updateOrderStatus(response: any, billId: any, orderId: any) {
    console.log(orderId)
    this.http.post('/api/payments/handle-payment-callback', {
      orderId: orderId,
      paymentId: response.razorpay_payment_id,
      billId: billId // Include the bill ID
    }).subscribe(result => {
      console.log('Order status updated successfully', result);
     
    });
    this.router.navigate(['/success-page']);
  }
}
