import { Component, OnInit } from '@angular/core';
import { HttpClient } from "@angular/common/http";

@Component({
  selector: 'app-IPO',
  templateUrl: './IPO.component.html',
  styleUrls: ['./IPO.component.css']
})
export class IPOComponent implements OnInit {
  public anyList:any
  items = [
    {companyName:"IBM",stockExhange:"BSE",pricePerShare:"23",openDateTime:"1423130269432","TotalNumbersOfshare":"253","remarks":"dqwd"},
    {companyName:"Ali",stockExhange:"NSE",pricePerShare:"53",openDateTime:"1423130969432","TotalNumbersOfshare":"223","remarks":"d1wd"}
    
  ];
  constructor(private http:HttpClient) { }

  ngOnInit() {
    this.http.get("http://192.168.1.2:7070/admin/admin/getCompanyIPOS")
    .subscribe(res=>{ this.anyList = res })
  }

}
