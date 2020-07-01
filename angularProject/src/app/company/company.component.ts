import { Component, OnInit } from '@angular/core';
import { CompanyService } from './company.service';
import { HttpClient } from "@angular/common/http";

@Component({
  selector: 'app-company',
  templateUrl: './company.component.html',
  styleUrls: ['./company.component.css']
})
export class CompanyComponent implements OnInit {
  items;
  backgroundColor = "red";
  public anyList:any
  constructor(private companyService:CompanyService,private http:HttpClient) { 
    this.items = this.companyService.getItems();
  }

  ngOnInit() {
    this.http.get("http://192.168.1.2:7070/admin/admin/getMatchingCompanies")
    .subscribe(res=>{ this.anyList = res })
  }

}
