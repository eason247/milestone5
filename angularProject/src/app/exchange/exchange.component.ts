import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-exchange',
  templateUrl: './exchange.component.html',
  styleUrls: ['./exchange.component.css']
})
export class ExchangeComponent implements OnInit {
items = [
  {exchangeName:"BSE",briefDescription:"dsaf",contactAddress:"dqw",remarks:"1423130269432"},
 {exchangeName:"NSE",briefDescription:"vwevwe",contactAddress:"qvwwq",remarks:"1423160269432"}
];
  constructor() { }

  ngOnInit() {
  }

}
