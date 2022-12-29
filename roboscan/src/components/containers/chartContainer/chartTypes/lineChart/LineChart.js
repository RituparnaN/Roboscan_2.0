import React, { useEffect, useState } from 'react'
import ReactEcharts from 'echarts-for-react'

const LineChart = (props) => {

    const { chartData } = props;

    const options = {
        legend: {},
        tooltip: {},
        dataset: {
            source: [
                ['product', '2015', '2016', '2017', '2018', '2019'],
                ['Matcha Latte', 43.3, 85.8, 93.7, 81.3, 75],
                ['Milk Tea', 83.1, 73.4, 55.1, 61, 24],
            ],
        },
        xAxis: { type: 'category' },
        yAxis: {},
        barWidth: 10,
        barGap: '90%',
        // Declare several bar series, each will be mapped
        // to a column of dataset.source by default.
        series: [
            {
                type: 'bar',
                itemStyle: {
                    color: '#313679',
                },
            },
            {
                type: 'bar',
                itemStyle: {
                    color: '#65d8cc',
                },
            },
            {
                type: 'bar',
                itemStyle: {
                    color: '#ffc600',
                },
            },
            {
                type: 'bar',
                itemStyle: {
                    color: '#65d8cc',
                },
            },
            {
                type: 'bar',
                itemStyle: {
                    color: '#ffc600',
                },
            },
        ],
    };

    return(
        <>
            <ReactEcharts option={options} />
        </>
    )
}

export default LineChart;