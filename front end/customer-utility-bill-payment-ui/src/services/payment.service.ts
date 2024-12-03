import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  private baseUrl = 'http://localhost:8080/api/payments';
  customerId: string = sessionStorage.getItem('customerId') || '';;
  
  constructor(private http: HttpClient) {}

  payBill(billId: number): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/create-order/${billId}`, null);
  }
  getPaymentHistory(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}`);
  }
  downloadInvoice(transactionId: string): Observable<Blob> {
    return this.http.get(`${this.baseUrl}/invoice/${transactionId}`, { responseType: 'blob' });
  }
  getBillsByCustomerId(): Observable<any[]> {
  
    return this.http.get<any[]>(`http://localhost:8080/api/bills/customer/${this.customerId}`);
  }
  getBills(): Observable<any[]> {
    return this.http.get<any[]>(`http://localhost:8080/api/bills`);
  }
}
