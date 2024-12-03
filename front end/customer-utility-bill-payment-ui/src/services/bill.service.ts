import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class BillService {
  private apiUrl = 'http://localhost:8080/api/bills'; 
  customerId: any =sessionStorage.getItem('customerId');
  
  
  constructor(private http: HttpClient) { }

  getBills(): Observable<any> {
    return this.http.get<any>(this.apiUrl);
  }
  getUnpaidBillsByCustomerId(): Observable<any[]> {
  
    return this.http.get<any[]>(`${this.apiUrl}/customer/${this.customerId}`);
  }
  getBillById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }
}
