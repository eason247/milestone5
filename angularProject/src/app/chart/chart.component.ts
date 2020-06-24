import { Component, OnInit } from '@angular/core';
import { NgxEchartsModule} from 'ngx-echarts';
@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css']
})
export class ChartComponent implements OnInit {

  options: any;
  constructor() { }

  ngOnInit() {

    this.options = {

        // 标题
        title: {
          text: 'compare stock price',
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
          data: ['']
      },
      // x轴
      xAxis: {
          data: ["IBM", "Microsoft"]
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
