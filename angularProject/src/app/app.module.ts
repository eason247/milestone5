import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Component } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AdminComponent } from './admin/admin.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { UserComponent } from './user/user.component';
import { RouterModule } from '@angular/router';
import { NgxEchartsModule} from 'ngx-echarts';
import { CompanyComponent } from './company/company.component';
import { NewCompanyComponent} from './company/newCompany/newCompany.component';
import { ImportDataComponent} from './importData/importData.component';
import { SummaryComponent} from './importData/summary/summary.component';
import { CompareComponent } from './compare/compare.component';
import { ChartComponent } from './chart/chart.component';
import { NewExchangeComponent} from './exchange/newExchange/newExchange.component';
import { ExchangeComponent} from './exchange/exchange.component';
import { IPOComponent} from './IPO/IPO.component';
import { NewIPOComponent} from './IPO/newIPO/newIPO.component';

@NgModule({
   declarations: [
      AppComponent,
      AdminComponent,
      UserComponent,
      CompanyComponent,
      NewCompanyComponent,
      ImportDataComponent,
      SummaryComponent,
      CompareComponent,
      ChartComponent,
      ExchangeComponent,
      NewExchangeComponent,
      IPOComponent,
      NewIPOComponent
   ],
   imports: [
      BrowserModule,
      AppRoutingModule,
      NgxEchartsModule,
      RouterModule.forRoot([{path:'admin', component : AdminComponent},
      {path:'user', component : UserComponent},
      {path:'company', component : CompanyComponent},
      {path:'newCompany', component : NewCompanyComponent},
      {path:'importData', component : ImportDataComponent},
      {path:'compare', component : CompareComponent},
      {path:'chart', component : ChartComponent},
      {path:'newExchange', component : NewExchangeComponent},
      {path:'exchange', component : ExchangeComponent},
      {path:'ipo', component : IPOComponent},
      {path:'newIpo', component : NewIPOComponent}])
   ],
   providers: [],
   bootstrap: [
      AppComponent
   ]
})
export class AppModule { }
