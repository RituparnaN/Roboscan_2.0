import React, { useState } from 'react'
import {
    TextField,
    Box,
    Typography,
    Grid,
    Checkbox,
    Button,
} from '@mui/material'
import Modal from 'components/common/Modal'

const FormContainer = (props) => {
    const { formData, expanded = false, sectionId } = props

    const [modalOpen, setModalOpen] = useState(false)
    const [modalContentType, setModalContentType] = useState('')

    const dataToShow = expanded.includes(sectionId)
        ? formData
        : formData.slice(0, 4)

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
