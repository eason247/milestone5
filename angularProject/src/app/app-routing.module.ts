import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NgxEchartsModule} from 'ngx-echarts';


const routes: Routes = [];

@NgModule({
  imports: [RouterModule.forRoot(routes),NgxEchartsModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
