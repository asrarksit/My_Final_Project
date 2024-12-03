import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private apiUrl = 'http://localhost:8080/api/login';

  constructor(private http: HttpClient) {}

  sendOtp(customerId: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/send-otp`, { customerId });
  }

  verifyOtp(customerId: string, otp: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/verify-otp`, { customerId, otp });
  }
}
