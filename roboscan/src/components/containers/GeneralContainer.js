import React from 'react'
import ReactEcharts from 'echarts-for-react'
import { Box, Typography, IconButton } from '@mui/material'
import { BsArrowUpRightCircle, BsArrowDownRightCircle } from 'react-icons/bs'
import { chartConfig } from 'data/dummyData'
import DataGrid from 'components/common/DataGrid'
import FormContainer from 'components/modules/FormContainer'
import { createNoise2D } from 'simplex-noise'
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
        graphType,
        hasGraph,
        // hasTable,
        // hasForm,
        formData,
        summaryDetails,
    } = props

    const toggleExpand = () => {
        if (expanded.filter((e) => e === sectionId).length < 1) {
            setExpanded([...expanded, sectionId])
        }
        if (expanded.filter((e) => e === sectionId).length > 0) {
            setExpanded(expanded.filter((e) => e !== sectionId))
        }
    }

    const generate3dData = () => {
        let data = []

        const noise = createNoise2D()
        for (var i = 0; i <= 10; i++) {
            for (var j = 0; j <= 10; j++) {
                var value = noise(i / 5, j / 5)
                data.push([i, j, value * 2 + 4])
            }
        }

        return data
    }

    let series = []
    for (var i = 0; i < 10; i++) {
        series.push({
            type: 'bar3D',
            data: generate3dData(),
            stack: 'stack',
            shading: 'lambert',
            emphasis: {
                label: {
                    show: false,
                },
            },
        })
    }

    let options = hasGraph
        ? chartConfig.find((chart) => chart.type === graphType).option
        : []

    let option3d = {
        xAxis3D: {
            type: 'value',
        },
        yAxis3D: {
            type: 'value',
        },
        zAxis3D: {
            type: 'value',
        },
        grid3D: {
            viewControl: {
                // autoRotate: true
            },
            light: {
                main: {
                    shadow: true,
                    quality: 'ultra',
                    intensity: 1.5,
                },
            },
        },
        series: series,
    }

    return (
        <Box
            className={
                expanded.filter((e) => e === sectionId).length > 0
                    ? 'bg-[#e1e4ef] px-8 py-3 mx-2 mb-5 min-h-[450px] '
                    : 'bg-[#f4f5fa] px-8 py-3 mx-2 mb-5 min-h-[450px] '
            }
        >
            {type === 'header' ? (
                <>
                    <Box className="mt-3 mb-4">
                        <Typography className="text-xl" display="inline">
                            The &nbsp;
                            <b>{summaryDetails[0]}</b>
                            &nbsp; scenario was breached for &nbsp;
                            <b>{summaryDetails[1]}</b>. The account number on
                            which the alert is breached is &nbsp;
                            <b>{summaryDetails[2]}</b>. A total of &nbsp;
                            <b>{summaryDetails[3]}</b>
                            &nbsp; alert(s) were involved in this breach. Total
                            of &nbsp;
                            <b>{summaryDetails[4]}</b>
                            &nbsp; alert(s) have been combined to form this
                            case. This customer &nbsp;
                            <b>{summaryDetails[5]}</b>
                            &nbsp; historical STR cases with the bank. The risk
                            rating of the breached alert is&nbsp;
                            <b>{summaryDetails[6]}</b>.
                        </Typography>
                    </Box>
                    <hr className="border-1 border-[#4f4f4f] bg-[#4f4f4f]" />
                    <Box className="mt-4">
                        <Typography
                            as="h5"
                            className="font-bold text-2xl text-[#171923]"
                        >
                            {title}
                        </Typography>
                    </Box>
                    <FormContainer
                        sectionId={sectionId}
                        formData={formData}
                        expanded={expanded}
                    />
                </>
            ) : (
                <>
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
                            <ReactEcharts
                                option={
                                    graphType === 'BAR3D' ? option3d : options
                                }
                            />
                        ) : type === 'table' ? (
                            <DataGrid title={title} utilColumn={'select'} />
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
                </>
            )}
        </Box>
    )
}

export default GeneralContainer
