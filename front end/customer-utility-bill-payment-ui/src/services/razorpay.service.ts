import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { log } from 'console';

@Injectable({
  providedIn: 'root'
})
export class RazorpayService {

  constructor(private http: HttpClient) { }

  createOrder(paymentDetails: any): Observable<any> {
    return this.http.post('/api/payments/create-order/'+paymentDetails.billId, paymentDetails);
  }
}
