import React, { useEffect, useState } from 'react'
import ReactEcharts from 'echarts-for-react'

const PieChart = (props) => {

    const { chartData } = props;

    const options = {
        tooltip: {
            trigger: 'item',
        },
        legend: {
            orient: 'vertical',
            top: '5%',
            left: 'left',
        },
        series: [
            {
                name: 'Access From',
                type: 'pie',
                radius: ['50%', '90%'],
                avoidLabelOverlap: false,
                label: {
                    show: false,
                    position: 'center',
                },
                //   emphasis: {
                //       label: {
                //           show: true,
                //           fontSize: '40',
                //           fontWeight: 'bold',
                //       },
                //   },
                labelLine: {
                    show: false,
                },
                data: chartData.ATTRIBUTE.map((item, index)=>{
                    return{
                        value: chartData.VALUES[0][index],
                        name: item,
                        // itemStyle: {
                        //     color: index % 3 === 0 ? colorsList[0] : index % 2 === 0 ? colorsList[1] : colorsList[2],
                        // }
                    }
                }),
                realData: [
                    {
                        value: 750,
                        name: 'Search Engine',
                        itemStyle: {
                            color: '#ffc600',
                        },
                    },
                    {
                        value: 750,
                        name: 'Direct',
                        itemStyle: {
                            color: '#65d8cc',
                        },
                    },
                    {
                        value: 750,
                        name: 'Email',
                        itemStyle: {
                            color: '#313679',
                        },
                    },
                ],
            },
        ],
    };

    return(
        <>
            <ReactEcharts option={options} />
        </>
    )
}

export default PieChart;