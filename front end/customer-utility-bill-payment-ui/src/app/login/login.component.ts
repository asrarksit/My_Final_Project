import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../../services/login.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  customerId: string = '';
  otp: string = '';
  otpSent: boolean = false;

  constructor(private http: HttpClient, private router: Router) {}

  // Method to send OTP
  sendOtp() {
    const requestBody = { customerId: this.customerId };
    
    this.http.post('http://localhost:8080/api/login/send-otp', requestBody).subscribe({
      next: (response: any) => {
        console.log(response.message); 
        this.otpSent = true; 
      },
      error: (error) => {
        console.error('Error sending OTP:', error);
      }
    });
  }

  // Method to verify OTP
  verifyOtp() {
    if (this.otp === '' || this.customerId === '') {
      alert("Please enter both customer ID and OTP."); 
      return;
    }

    const requestBody = { customerId: this.customerId, otp: this.otp };

    this.http.post<boolean>('http://localhost:8080/api/login/verify-otp', requestBody).subscribe({
      next: (isValid) => {
            if (isValid) {
              sessionStorage.setItem('customerId', this.customerId); 
              console.log('Customer ID set in SessionStorage:', this.customerId);
              this.router.navigate(['/home']);
            } else {
         alert('Invalid OTP. Please try again.');
        }
      },
      error: (error) => {
        console.error('Error verifying OTP:', error);
       alert('Error verifying OTP. Please try again.');
      }
    });
  }
}
