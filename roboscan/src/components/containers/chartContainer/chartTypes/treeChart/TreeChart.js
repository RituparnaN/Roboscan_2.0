import React, { useEffect, useState } from 'react'
import ReactEcharts from 'echarts-for-react'
import { treeData } from '../../chartConfig';

const TreeChart = (props) => {

    const { chartData } = props;

    const options = {
        tooltip: {
          trigger: 'item',
          triggerOn: 'mousemove'
        },
        series: [
          {
            type: 'tree',
            data: [treeData],
            top: '1%',
            left: '7%',
            bottom: '1%',
            right: '20%',
            symbolSize: 7,
            label: {
              position: 'left',
              verticalAlign: 'middle',
              align: 'right',
              fontSize: 9
            },
            leaves: {
              label: {
                position: 'right',
                verticalAlign: 'middle',
                align: 'left'
              }
            },
            emphasis: {
              focus: 'descendant'
            },
            expandAndCollapse: true,
            animationDuration: 550,
            animationDurationUpdate: 750,
            lineStyle: {
                color: '#000',
                width: 0.8,
            },
            itemStyle: {
                color: '#333',
                size: 7
            }
          }
        ]
      };

    return(
        <>
            <ReactEcharts option={options} style={{height: '350px', maxWidth: '800px'}}  />
        </>
    )
}

export default TreeChart;