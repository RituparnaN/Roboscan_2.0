import React, { useState } from 'react'
import {
    TextField,
    Box,
    Typography,
    Grid,
    Checkbox,
    Button,
    RadioGroup,
    Radio,
    FormControl,
    FormLabel,
    FormControlLabel,
} from '@mui/material'
import Modal from 'components/common/Modal'

const FormContainer = (props) => {
    const { formData, expanded, sectionId } = props

    const isExpanded =
        expanded.includes(sectionId) || sectionId === 1 ? true : false

    const [modalOpen, setModalOpen] = useState(false)
    const [modalContentType, setModalContentType] = useState('')

    const dataToShow =
        expanded.includes(sectionId) || sectionId === 1
            ? formData
            : formData.slice(0, 3)

    const functionByType = (field) => {
        if (field.function && field.function === 'modal') {
            setModalContentType(field.modalType || 'error_404')
            setModalOpen(true)
        }
    }

    return (
        <Box>
            <Grid container className="mt-8">
                {formData &&
                    dataToShow.map((field, index) => (
                        <React.Fragment key={index}>
                            {field.type === 'button' ? (
                                <Grid item xs={12}>
                                    <Box
                                        key={index}
                                        className="flex justify-center items-center mb-5"
                                    >
                                        <Button
                                            className="bg-white w-full text-app-dark"
                                            variant="contained"
                                            onClick={() =>
                                                functionByType(field)
                                            }
                                        >
                                            {field.value}
                                        </Button>
                                    </Box>
                                </Grid>
                            ) : (
                                <>
                                    {field.type === 'text' ? (
                                        <Grid
                                            item
                                            lg={
                                                !isExpanded ||
                                                (formData.length % 2 !== 0 &&
                                                    index ===
                                                        formData.length - 1)
                                                    ? 12
                                                    : 6
                                            }
                                            xs={12}
                                        >
                                            {/* <Box className="flex flex-row justify-start items-start mb-5"> */}
                                            <Box className="flex flex-col justify-start items-start mb-6 pr-5">
                                                <Typography className="mr-5 min-w-fit mb-2 ml-2">
                                                    {field.name}
                                                </Typography>

                                                <TextField
                                                    value={field.value}
                                                    variant="outlined"
                                                    className="w-full mr-5 bg-white"
                                                    disabled
                                                />
                                            </Box>
                                        </Grid>
                                    ) : field.type === 'textarea' ? (
                                        <Grid item xs={12}>
                                            {/* <Box className="flex flex-row justify-start items-start mb-5"> */}
                                            <Box className="flex flex-col justify-start items-start mb-6">
                                                <Typography className="mr-5 min-w-fit mb-2 ml-2">
                                                    {field.name}
                                                </Typography>

                                                <TextField
                                                    value={field.value}
                                                    variant="outlined"
                                                    className="w-full mr-5 bg-white"
                                                    multiline
                                                    rows={4}
                                                    disabled
                                                />
                                            </Box>
                                        </Grid>
                                    ) : field.type === 'check' ? (
                                        <Box key={index}>
                                            <Checkbox />
                                        </Box>
                                    ) : field.type === 'radio' ? (
                                        <Grid item xs={12} key={index}>
                                            <Box className="flex flex-row justify-start items-center mt-5 mb-5">
                                                <Typography className="min-w-fit mr-10">
                                                    {field.name}
                                                </Typography>
                                                <RadioGroup
                                                    name={field.name}
                                                    value={field.value}
                                                    className="flex flex-row justify-between items-center w-[25%]"
                                                >
                                                    {field.options.map(
                                                        (option, index) => (
                                                            <FormControlLabel
                                                                value={option}
                                                                control={
                                                                    <Radio />
                                                                }
                                                                label={option}
                                                                disabled
                                                                key={option}
                                                            />
                                                        )
                                                    )}
                                                </RadioGroup>
                                            </Box>
                                        </Grid>
                                    ) : (
                                        <Box key={index}>
                                            <Typography>
                                                Unknown Type of Input
                                            </Typography>
                                        </Box>
                                    )}
                                </>
                            )}
                        </React.Fragment>
                    ))}
                <Modal
                    contentType={modalContentType}
                    setModalOpen={setModalOpen}
                    modalOpen={modalOpen}
                />
            </Grid>
        </Box>
    )
}

export default FormContainer
