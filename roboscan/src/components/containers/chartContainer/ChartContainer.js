import React, { useEffect, useState } from 'react'
import ReactEcharts from 'echarts-for-react'
import { Box } from '@mui/material'
import { treeData } from './chartConfig'
import { 
    BarChart, 
    LineChart, 
    PieChart, 
    TreeChart 
} from './chartTypes'
import 'echarts-gl'


const ChartContainer = (props) => {


    const {
        hasGraph,
        graphDetails,
    } = props;

    const [chartType, setChartType] = useState(hasGraph?graphDetails.graphType:null)

    useEffect(() => {
        console.log("Chart Data", graphDetails)
    },[graphDetails]);

    return(
        <>
            {  
                chartType === 'bar' ? (
                    <BarChart chartData={graphDetails.graphData} />
                ) : chartType === 'pie' ? (
                    <PieChart chartData={graphDetails.graphData} />
                ) : chartType === 'line' ? (
                    <LineChart chartData={graphDetails.graphData} />
                ) : chartType === 'hierarchical' ? (
                    <TreeChart chartData={graphDetails.graphData} />
                ) : (
                    <Box>
                        No Chart
                    </Box>
                )
            }
            
        </>
    )
}

export default ChartContainer;