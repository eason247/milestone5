import { Component, OnInit } from '@angular/core';
import { HttpClient } from "@angular/common/http";

@Component({
  selector: 'app-exchange',
  templateUrl: './exchange.component.html',
  styleUrls: ['./exchange.component.css']
})
export class ExchangeComponent implements OnInit {
  public anyList:any
items = [
  {exchangeName:"BSE",briefDescription:"dsaf",contactAddress:"dqw",remarks:"1423130269432"},
 {exchangeName:"NSE",briefDescription:"vwevwe",contactAddress:"qvwwq",remarks:"1423160269432"}
];
  constructor(private http:HttpClient) { }

  ngOnInit() {
    this.http.get("http://192.168.1.2:7070/admin/admin/getStockExchange")
    .subscribe(res=>{ this.anyList = res })
  }

}
