import { Component, NgModule, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HomeComponent } from '../home/home.component';
import { NgbNavModule } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { UserService } from '../../services/user.service';
@Component({
  selector: 'app-header',
  standalone: true,
  imports: [HomeComponent, NgbNavModule, CommonModule],
  schemas: [],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {
  user: any = {};

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit() {
    if (typeof window !== 'undefined' && window.sessionStorage) {
      this.loadUserDetails();
    } else {
      this.router.navigate(['/login']);
    }
  }
  
  loadUserDetails() {
    const customerId = sessionStorage.getItem('customerId');
    if (customerId) {
      this.userService.getUserDetails(customerId).subscribe(data => {
        this.user = data;
      }, error => {
        console.error('Error loading user details', error);
        this.router.navigate(['/login']);
      });
    } else {
      this.router.navigate(['/login']);
    }
  }
  active = 1;

  home() {
    this.router.navigate(['/home']);
  }
  accountDetails() {
    this.router.navigate(['/account-details']);
  }
  payment() {
    this.router.navigate(['/payments']);
  }
  
  logout() {
    sessionStorage.removeItem('customerId'); 
    this.router.navigate(['/login']);
  }
  showProfileMenu = false;

  // Toggle function to show/hide profile menu
  toggleProfileMenu() {
    this.showProfileMenu = !this.showProfileMenu;
  }
}
