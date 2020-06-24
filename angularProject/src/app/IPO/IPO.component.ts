import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-IPO',
  templateUrl: './IPO.component.html',
  styleUrls: ['./IPO.component.css']
})
export class IPOComponent implements OnInit {
  items = [
    {companyName:"IBM",stockExhange:"BSE",pricePerShare:"23",openDateTime:"1423130269432","TotalNumbersOfshare":"253","remarks":"dqwd"},
    {companyName:"Ali",stockExhange:"NSE",pricePerShare:"53",openDateTime:"1423130969432","TotalNumbersOfshare":"223","remarks":"d1wd"}
    
  ];
  constructor() { }

  ngOnInit() {
  }

}
