import { Component } from '@angular/core';
import { HeaderComponent } from "../header/header.component";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [HeaderComponent],  // Ensure HeaderComponent is standalone, or remove this line
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']  // Corrected: styleUrls instead of styleUrl
})
export class HomeComponent { }

