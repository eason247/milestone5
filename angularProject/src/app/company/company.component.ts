import { Component, OnInit } from '@angular/core';
import { CompanyService } from './company.service';

@Component({
  selector: 'app-company',
  templateUrl: './company.component.html',
  styleUrls: ['./company.component.css']
})
export class CompanyComponent implements OnInit {
  items;
  backgroundColor = "red";
  constructor(private companyService:CompanyService) { 
    this.items = this.companyService.getItems();
  }

  ngOnInit() {
  }

}
