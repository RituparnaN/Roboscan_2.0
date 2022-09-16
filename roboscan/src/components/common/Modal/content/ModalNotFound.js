import React, { useState, useEffect } from 'react'
import { Box, IconButton, Typography } from '@mui/material'
import { BsArrowDownLeftCircle } from 'react-icons/bs'

const ModalNotFound = ({ handleModalClose, handleModalOpen }) => {
    return (
        <Box>
            <Box
                sx={{
                    display: 'flex',
                    flexDirection: 'row',
                    justifyContent: 'flex-end',
                    alignItems: 'center',
                    width: '100%',
                }}
            >
                <IconButton onClick={() => handleModalClose()}>
                    <BsArrowDownLeftCircle color="black" />
                </IconButton>
            </Box>
            <Box>
                <Typography>Error Component</Typography>
            </Box>
        </Box>
    )
}

export default ModalNotFound
