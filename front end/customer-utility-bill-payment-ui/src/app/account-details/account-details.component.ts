import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '../../services/user.service'; // Adjust the import based on your services
import { Router } from '@angular/router';
import { HeaderComponent } from '../header/header.component';

@Component({
  selector: 'app-account-details',
  templateUrl: './account-details.component.html',
  styleUrls: ['./account-details.component.css'],
  standalone: true,
  imports: [CommonModule,HeaderComponent] // Import CommonModule here
})
export class AccountDetailsComponent implements OnInit {
  user: any = {};
  walletBalance: number = 0;

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit() {
    if (typeof window !== 'undefined' && window.sessionStorage) {
      this.loadUserDetails();
    } else {
      this.router.navigate(['/login']); // You can handle it accordingly
    }
  }
  
  loadUserDetails() {
    const customerId = sessionStorage.getItem('customerId');
    if (customerId) {
      this.userService.getUserDetails(customerId).subscribe(data => {
        this.user = data;
        this.walletBalance = data.walletBalance;
      }, error => {
        console.error('Error loading user details', error);
        this.router.navigate(['/login']);
      });
    } else {
      this.router.navigate(['/login']);
    }
  }
  
}


