import React, {useEffect, useState} from 'react';
import { 
    Box,
    Typography, 
    Grid,
    TextField,
    Radio,
    RadioGroup,
    FormControlLabel
} from '@mui/material'

const ExecutiveSummary = (props) => {

    const {
        summaryDetails = [],
    } = props;

    return(
        <Box
            className={'bg-[#f4f5fa] px-8 py-3 mx-2 mb-5 min-h-[450px]'}
        >
            {summaryDetails !== [] &&(
                <>
                    <Box className="mt-3 mb-4">
                        <Typography className="text-xl" display="inline">
                            The &nbsp;
                            <b>{summaryDetails.HEADER_ALERTNAME}</b>
                            &nbsp; scenario was breached for &nbsp;
                            <b>{summaryDetails.HEADER_CUSTOMER_NAME}</b>. The account number on
                            which the alert is breached is &nbsp;
                            <b>{summaryDetails.HEADER_ACCOUNTNO}</b>. A total of &nbsp;
                            <b>{summaryDetails.HEADER_ALERTS_BREACHED}</b>
                            &nbsp; alert(s) were involved in this breach. Total
                            of &nbsp;
                            <b>{summaryDetails.HEADER_ALERTSCOUNT}</b>
                            &nbsp; alert(s) have been combined to form this
                            case. This customer &nbsp;
                            <b>{summaryDetails.HEADER_HASANYOLDCASES}</b>
                            &nbsp; historical STR cases with the bank. The risk
                            rating of the breached alert is&nbsp;
                            <b>{summaryDetails.HEADER_CASE_RATING}</b>.
                        </Typography>
                    </Box>
                    <hr className="border-1 border-[#4f4f4f] bg-[#4f4f4f]" />
                    <Box className="mt-4">
                        <Typography
                            as="h5"
                            className="font-bold text-2xl text-[#171923]"
                        >
                            Executive Summary
                        </Typography>
                    </Box>
                    <Grid container className="mt-8">
                        <Grid item lg={6} xs={12} >
                            {/* <Box className="flex flex-row justify-start items-start mb-5"> */}
                            <Box className="flex flex-col justify-start items-start mb-6 pr-5">
                                <Typography className="mr-5 min-w-fit mb-2 ml-2">
                                    Case No.
                                </Typography>

                                <TextField
                                    value={summaryDetails.HEADER_CASE_ID}
                                    variant="outlined"
                                    className="w-full mr-5 bg-white"
                                    disabled
                                />
                            </Box>
                        </Grid>
                        <Grid item lg={6} xs={12} >
                            {/* <Box className="flex flex-row justify-start items-start mb-5"> */}
                            <Box className="flex flex-col justify-start items-start mb-6 pr-5">
                                <Typography className="mr-5 min-w-fit mb-2 ml-2">
                                    Scenario(s) Breached
                                </Typography>

                                <TextField
                                    value={summaryDetails.HEADER_CASEALERTSCOUNT}
                                    variant="outlined"
                                    className="w-full mr-5 bg-white"
                                    disabled
                                />
                            </Box>
                        </Grid>
                        <Grid item xs={12}>
                            {/* <Box className="flex flex-row justify-start items-start mb-5"> */}
                            <Box className="flex flex-col justify-start items-start mb-6 mr-5">
                                <Typography className="mr-5 min-w-fit mb-2 ml-2">
                                    Description Of Breached Scenario(s)
                                </Typography>

                                <TextField
                                    value={summaryDetails.HEADER_DESCRIPTION}
                                    variant="outlined"
                                    className="w-full bg-white"
                                    multiline
                                    rows={4}
                                    disabled
                                />
                            </Box>
                        </Grid>
                        <Grid item xs={12}>
                            <Box className="flex flex-row justify-start items-center mt-5 mb-5">
                                <Typography className="min-w-fit mr-10">
                                    Is Bank Employee
                                </Typography>
                                <RadioGroup
                                    name={'isEmployee'}
                                    value={summaryDetails.HEADER_ISBANK_EMPLOYEE}
                                    className="flex flex-row justify-between items-center w-[25%]"
                                >
                                    <FormControlLabel
                                        value={'Y'}
                                        control={
                                            <Radio />
                                        }
                                        label={'Yes'}
                                        disabled
                                    />
                                    <FormControlLabel
                                        value={'N'}
                                        control={
                                            <Radio />
                                        }
                                        label={'No'}
                                        disabled
                                    />
                                    <FormControlLabel
                                        value={'UK'}
                                        control={
                                            <Radio />
                                        }
                                        label={'Unknown'}
                                        disabled
                                    />
                                </RadioGroup>
                            </Box>
                        </Grid>
                    </Grid>
                </>
            )}
        </Box>
    )
}

export default ExecutiveSummary;