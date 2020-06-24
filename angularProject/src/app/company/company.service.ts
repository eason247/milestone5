import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  items = [
    {companyName:"IBM",stockExhange:"BSE",CEO:"jack",ipoDate:"1423130269432"},
    {companyName:"Microsoft",stockExhange:"NSE",CEO:"Bob",ipoDate:"1423130299432"},
    {companyName:"Ali",stockExhange:"BSE",CEO:"mayun",ipoDate:"1423160269432"}
  ];

  getItems(){
    return this.items;
  }

constructor() { }

}
