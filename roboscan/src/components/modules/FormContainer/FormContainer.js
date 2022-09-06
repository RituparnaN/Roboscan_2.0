import React from 'react'
import {
    TextField,
    Box,
    Typography,
    Grid,
    Checkbox,
    Button,
} from '@mui/material'

const FormContainer = (props) => {
    const { formData, expanded = false } = props

    const dataToShow = expanded ? formData : formData.slice(0, 4)

    return (
        <Box>
            <Grid container className="mt-8">
                {formData &&
                    dataToShow.map((field, index) => (
                        <>
                            {field.type === 'button' ? (
                                <Grid item xs={12}>
                                    <Box
                                        key={index}
                                        className="flex justify-center items-center mb-5"
                                    >
                                        <Button
                                            className="bg-white w-full text-app-dark"
                                            variant="contained"
                                        >
                                            {field.value}
                                        </Button>
                                    </Box>
                                </Grid>
                            ) : (
                                <Grid item xs={expanded ? 6 : 12}>
                                    {field.type === 'text' ? (
                                        <Grid
                                            container
                                            className="mb-2"
                                            alignItems={'center'}
                                            spacing={2}
                                            key={index}
                                        >
                                            <Grid item xs={4}>
                                                <Typography align="right">
                                                    {field.name}
                                                </Typography>
                                            </Grid>
                                            <Grid item xs={8}>
                                                <TextField
                                                    value={field.value}
                                                    variant="outlined"
                                                    className="w-full"
                                                    disabled
                                                />
                                            </Grid>
                                        </Grid>
                                    ) : field.type === 'check' ? (
                                        <Box key={index}>
                                            <Checkbox />
                                        </Box>
                                    ) : (
                                        <Box key={index}>
                                            <Typography>
                                                Unknown Type of Input
                                            </Typography>
                                        </Box>
                                    )}
                                </Grid>
                            )}
                        </>
                    ))}
            </Grid>
        </Box>
    )
}

export default FormContainer
