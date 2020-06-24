import { Component, OnInit } from '@angular/core';
import { NgModule } from '@angular/core';
import { NgxEchartsModule} from 'ngx-echarts';
// import * as echarts from 'echarts';
// import * as numeral from 'numeral';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})

export class UserComponent implements OnInit {
  options: any;
  constructor() { }

  ngOnInit() {

    this.options = {

        // 标题
        title: {
          text: 'ECharts 入门示例',
          subtext:'东软出品',
          left:'left',
          borderColor:'red',
          borderWidth:3
      },
      // 工具箱
      toolbox: {
          show: true,
          feature:{
              saveAsImage:{
                  show:true
              },
              restore:{
                  show:true
              },
              dataView:{
                  show:true
              },
              dataZoom:{
                  show:true
              },
              magicType:{
                  type:['line','bar']
              }

          }
      },
      tooltip:{
          trigger:'axis'
      },
      // 图例
      legend: {
          data: ['销量']
      },
      // x轴
      xAxis: {
          data: ["衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"]
      },
      yAxis: {},
      // 数据
      series: [{
          name: '销量',
          type: 'bar',
          data: [5, 20, 36, 10, 10, 20],
          markPoint:{
              data:[
                  {type:'max',name:'最大值'},
                  {type:'min',name:'最小值',symbol:'arrow'}
              ]
          },
          markLine:{
              data:[
                  {type: 'average',name:'平均值'}
              ]
          }
      }
      ]
    }
  }

}
