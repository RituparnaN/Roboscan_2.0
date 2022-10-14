import React from 'react'
import { Dialog } from '@mui/material'
import modalContentMapping from './content'

const Modal = ({ contentType, modalOpen, setModalOpen }) => {
    const handleClickOpen = () => {
        setModalOpen(true)
    }

    const handleClickClose = () => {
        setModalOpen(false)
    }

    let ModalContent = modalContentMapping[contentType]
        ? modalContentMapping[contentType]
        : modalContentMapping['error_404']

    return (
        <Dialog
            maxWidth={false}
            aria-labelledby="custom-modal"
            open={modalOpen}
            onClose={handleClickClose}
            PaperProps={{
                className:
                    'absolute left-0 right-0 mx-auto top-3 w-[93vw] rounded-lg',
            }}
        >
            <ModalContent
                handleModalClose={handleClickClose}
                handleModalOpen={handleClickOpen}
            />
        </Dialog>
    )
}

export default Modal
