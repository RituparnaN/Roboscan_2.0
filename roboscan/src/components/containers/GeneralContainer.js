import React, { useEffect, useState } from 'react'
import ReactEcharts from 'echarts-for-react'
import { Box, Typography, IconButton } from '@mui/material'
import { BsArrowUpRightCircle, BsArrowDownRightCircle } from 'react-icons/bs'
import { chartConfig } from 'data/dummyData'
import DataGrid from 'components/common/DataGrid'
import FormContainer from 'components/modules/FormContainer'

const GeneralContainer = (props) => {
    const {
        sectionId,
        title,
        children,
        expandable = false,
        divider = true,
        expanded,
        setExpanded,
        graphData,
        data,
        type,
        graphType,
        hasGraph,
        hasTable,
        hasForm,
        formData,
    } = props

    useEffect(() => {
        console.log('Type:-', type)
    })

    const toggleExpand = () => {
        if (expanded.filter((e) => e === sectionId).length < 1) {
            setExpanded([...expanded, sectionId])
        }
        if (expanded.filter((e) => e === sectionId).length > 0) {
            setExpanded(expanded.filter((e) => e !== sectionId))
        }
    }

    let options = hasGraph
        ? chartConfig.find((chart) => chart.type === graphType).option
        : []

    return (
        <Box
            className={
                expanded.filter((e) => e === sectionId).length > 0
                    ? 'bg-[#e1e4ef] px-8 py-3 mx-2 mb-5 min-h-[380px] '
                    : 'bg-[#f4f5fa] px-8 py-3 mx-2 mb-5 min-h-[380px] '
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
                        {expanded.filter((e) => e === sectionId).length > 0 ? (
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
            <Box className="mt-2">
                {type === 'graph' ? (
                    <ReactEcharts option={options} />
                ) : type === 'table' ? (
                    <DataGrid />
                ) : type === 'form' ? (
                    <FormContainer
                        sectionId={sectionId}
                        formData={formData}
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
