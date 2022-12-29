import React from 'react'
import ReactEcharts from 'echarts-for-react'
import { Box, Typography, IconButton } from '@mui/material'
import { BsArrowUpRightCircle, BsArrowDownRightCircle } from 'react-icons/bs'
import { chartConfig } from 'data/dummyData'
import DataGrid from 'components/common/DataGrid'
import FormContainer from 'components/containers/formContainer'
import { createNoise2D } from 'simplex-noise'
import ChartContainer from './chartContainer/ChartContainer'
import 'echarts-gl'

const GeneralContainer = (props) => {
    const {
        sectionId,
        title,
        children,
        expandable = false,
        divider = true,
        expanded,
        setExpanded,
        // graphData,
        // data,
        type,
        graphDetails,
        hasGraph,
        // hasTable,
        // hasForm,
        formDetails,
        summaryDetails,
        tableDetails
    } = props

    const toggleExpand = () => {
        if (expanded.filter((e) => e === sectionId).length < 1) {
            setExpanded([...expanded, sectionId])
        }
        if (expanded.filter((e) => e === sectionId).length > 0) {
            setExpanded(expanded.filter((e) => e !== sectionId))
        }
    }


    return (
        <Box
            className={
                expanded.filter((e) => e === sectionId).length > 0
                    ? 'bg-[#e1e4ef] px-8 py-3 mx-2 mb-5 min-h-[450px] '
                    : 'bg-[#f4f5fa] px-8 py-3 mx-2 mb-5 min-h-[450px] '
            }
        >
            <Box className="flex justify-between items-center mb-2">
                <Typography
                    as="h5"
                    className="font-bold text-2xl text-[#171923]"
                >
                    {title}
                </Typography>
                {expandable && (
                    <IconButton onClick={() => toggleExpand()}>
                        {expanded.filter((e) => e === sectionId)
                            .length > 0 ? (
                            <BsArrowDownRightCircle size={28} />
                        ) : (
                            <BsArrowUpRightCircle size={28} />
                        )}
                    </IconButton>
                )}
            </Box>

            {divider && (
                <hr className="border-1 border-[#4f4f4f] bg-[#4f4f4f]" />
            )}
            <Box className="mt-5">
                {type === 'graph' ? (
                    <ChartContainer 
                        hasGraph={hasGraph} 
                        graphDetails={graphDetails} 
                    />
                ) : type === 'table' ? (
                    <DataGrid expanded={expanded.includes(sectionId)} tableData={tableDetails} title={title} utilColumn={'select'} />
                ) : type === 'form' ? (
                    <FormContainer
                        sectionId={sectionId}
                        formDetails={formDetails}
                        expanded={expanded}
                    />
                ) : (
                    <>{children}</>
                )}
            </Box>
        </Box>
    )
}

export default GeneralContainer
