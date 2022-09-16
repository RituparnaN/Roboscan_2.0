import React, { useState, useEffect, useMemo, useRef } from 'react'
import {
    Box,
    IconButton,
    Typography,
    Grid,
    Button,
    MenuList,
    MenuItem,
    ListItemText,
    ListItemIcon,
    InputBase,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
} from '@mui/material'
import {
    BsArrowDownLeftCircle,
    BsReplyAll,
    BsReply,
    BsReplyAllFill,
    BsReplyFill,
    BsForward,
    BsForwardFill,
    BsFolder,
    BsFolderFill,
    BsTrash,
    BsTrashFill,
    BsInbox,
    BsInboxFill,
    BsFileText,
    BsFileTextFill,
    BsPencil,
} from 'react-icons/bs'

import { BiCircle, BiSearch } from 'react-icons/bi'

import { IoMdHeart, IoMdHeartEmpty, IoMdClose } from 'react-icons/io'

import { IoPaperPlane, IoPaperPlaneOutline } from 'react-icons/io5'

import JoditEditor from 'jodit-react'

const menuOptions = [
    {
        id: '1',
        name: 'Inbox',
        icon: <BsInbox size={24} />,
        iconAlt: <BsInboxFill size={24} />,
    },
    {
        id: '2',
        name: 'Liked',
        icon: <IoMdHeartEmpty size={24} />,
        iconAlt: <IoMdHeart size={24} />,
    },
    {
        id: '3',
        name: 'Draft',
        icon: <BsFileText size={24} />,
        iconAlt: <BsFileTextFill size={24} />,
    },
    {
        id: '4',
        name: 'Sent',
        icon: <IoPaperPlaneOutline size={24} />,
        iconAlt: <IoPaperPlane size={24} />,
    },
    {
        id: '5',
        name: 'Trash',
        icon: <BsTrash size={24} />,
        iconAlt: <BsTrashFill size={24} />,
    },
]

const labelOptions = [
    {
        id: '1',
        name: 'Work',
        color: '#525ce1',
    },
    {
        id: '2',
        name: 'Clients',
        color: '#009eff',
    },
    {
        id: '3',
        name: 'Personal',
        color: '#e14066',
    },
]

const dummyMails = [
    {
        id: '1',
        toName: 'Sarah Townseed',
        subject: 'MoodBoard Update',
        body: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus dolor augue, mattis ut ligula id, porttitor pretium velit. In in ornare felis, sed interdum mi. Phasellus rutrum augue gravida nisl malesuada iaculis. Aenean auctor est quis nisl consequat condimentum. Pellentesque eget posuere mi. Duis luctus est ac ligula aliquam, sit amet tristique mauris egestas. Nulla laoreet arcu nec venenatis accumsan. Sed vehicula urna sed sollicitudin condimentum. Fusce et magna posuere, egestas justo in, tristique lacus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Suspendisse nec rhoncus felis, sed interdum felis. Praesent gravida maximus massa vel accumsan.',
        label: 'Work',
        color: '#525ce1',
    },
    {
        id: '2',
        toName: 'Sarah Townseed',
        subject: 'MoodBoard Update',
        body: 'Lorem Ipsum Dolor Sit Amet Consectur Alpin Dolor Sit Conscetore Amit Lorem Dem Ipsum!',
        label: 'Personal',
        color: '#e14066',
    },
    {
        id: '3',
        toName: 'Sarah Townseed',
        subject: 'MoodBoard Update',
        body: 'Lorem Ipsum Dolor Sit Amet Consectur Alpin Dolor Sit Conscetore Amit Lorem Dem Ipsum!',
        label: 'Work',
        color: '#525ce1',
    },
    {
        id: '4',
        toName: 'Sarah Townseed',
        subject: 'MoodBoard Update',
        body: 'Lorem Ipsum Dolor Sit Amet Consectur Alpin Dolor Sit Conscetore Amit Lorem Dem Ipsum!',
        label: 'Clients',
        color: '#009eff',
    },
    {
        id: '5',
        toName: 'Sarah Townseed',
        subject: 'MoodBoard Update',
        body: 'Lorem Ipsum Dolor Sit Amet Consectur Alpin Dolor Sit Conscetore Amit Lorem Dem Ipsum!',
        label: 'Personal',
        color: '#e14066',
    },
    {
        id: '6',
        toName: 'Sarah Townseed',
        subject: 'MoodBoard Update',
        body: 'Lorem Ipsum Dolor Sit Amet Consectur Alpin Dolor Sit Conscetore Amit Lorem Dem Ipsum!',
        label: 'Clients',
        color: '#009eff',
    },
]

const ComposeComponent = ({ handleModalClose, handleModalOpen }) => {
    const placeholder = 'Start Typing...'

    const editor = useRef(null)

    const [content, setContent] = useState('')
    const [composing, setComposing] = useState(false)
    const [expandedMail, setExpandedMail] = useState(null)
    const [alertOpen, setAlertOpen] = useState(false)

    const handleAlertOpen = () => {
        setAlertOpen(true)
    }
    const handleAlertClose = () => {
        setAlertOpen(false)
    }
    const handleAlertAccept = () => {
        setComposing(false)
        setAlertOpen(false)
    }
    const handleAlertDismiss = () => {
        setContent('')
        setComposing(false)
        setAlertOpen(false)
    }
    const config = useMemo(
        () => ({
            readonly: false,
            placeholder: placeholder || 'Start Typing...',
            height: 400,
            showCharsCounter: false,
            showWordsCounter: false,
            showXPathInStatusbar: false,
            allowResizeX: false,
            allowResizeY: false,
            buttons:
                'bold,italic,underline,strikethrough,eraser,ul,ol,font,fontsize,paragraph,lineHeight,superscript,subscript,spellcheck,cut,copy,paste,selectall,link',
        }),
        [placeholder]
    )

    useEffect(() => {
        console.log('Content:-', content)
    }, [content])

    const handleCompose = () => {
        if (composing === false) {
            setComposing(true)
            return
        }
        if (composing === true) {
            if (content !== '') {
                setAlertOpen(true)
                return
            }
            setComposing(false)
            return
        }
    }

    const handleMailClick = (mail) => {
        if (expandedMail === null) {
            setExpandedMail(mail)
        }
    }

    return (
        <Box className=" bg-[#f5f6fa] overflow-hidden max-h-[89vh]">
            <Grid container className="transition-all duration-300" wrap="wrap">
                {/* Grid One */}

                <Grid item xs={2} className="w-[100%] bg-[#fff] py-12 px-16">
                    <Button
                        variant="contained"
                        className="bg-app-dark text-white hover:bg-[#000] normal-case py-3 px-5 mb-16 rounded-lg text-xl"
                        onClick={() => handleCompose()}
                    >
                        {composing ? (
                            <>
                                <IoMdClose size={18} className="mr-4" />
                                Close
                            </>
                        ) : (
                            <>
                                <BsPencil size={18} className="mr-4" />
                                Compose
                            </>
                        )}
                    </Button>
                    <MenuList className="mb-12">
                        {menuOptions.map((option, index) => (
                            <MenuItem className="mb-6">
                                <ListItemIcon className="mr-4">
                                    {option.icon}
                                </ListItemIcon>
                                <ListItemText>{option.name}</ListItemText>
                            </MenuItem>
                        ))}
                    </MenuList>
                    <Typography variant="h6" className="font-bold mb-4">
                        Labels
                    </Typography>
                    <MenuList className="mb-6">
                        {labelOptions.map((option, index) => (
                            <MenuItem className="mb-3">
                                <ListItemIcon className="mr-4">
                                    <BiCircle size={24} color={option.color} />
                                </ListItemIcon>
                                <ListItemText>{option.name}</ListItemText>
                            </MenuItem>
                        ))}
                    </MenuList>
                </Grid>

                {/* Grid Two */}

                <Grid
                    item
                    xs={composing ? 4 : 10}
                    className="w-[100%] bg-[#f5f6fa] py-12 px-16 transition-all duration-300"
                >
                    <Box className="flex flex-row justify-between items-center mb-10">
                        <Box className="bg-[#e0e1e7] p-2 rounded-lg flex flex-row justify-between items-center w-[100%]">
                            <InputBase
                                sx={{ ml: 1, flex: 1 }}
                                placeholder="Search"
                                inputProps={{ 'aria-label': 'search mails' }}
                            />
                            <IconButton>
                                <BiSearch size={24} />
                            </IconButton>
                        </Box>
                        {!composing && (
                            <IconButton
                                onClick={() => handleModalClose()}
                                className="ml-8"
                            >
                                <BsArrowDownLeftCircle color="black" />
                            </IconButton>
                        )}
                    </Box>

                    <MenuList className="max-h-[70vh] overflow-hidden hover:overflow-auto">
                        {dummyMails.map((mail, index) => (
                            <MenuItem
                                onClick={() => handleMailClick(mail)}
                                className="bg-white hover:bg-[#ddd] p-0 py-4 mb-4 flex flex-col justify-center items-start"
                            >
                                {/* <Box> */}
                                <Box
                                    className="pl-10 pr-3 py-3 mt-5 mb-5 flex flex-row justify-between items-center"
                                    sx={{
                                        borderLeftColor: mail.color,
                                        borderLeftStyle: 'solid',
                                        borderLeftWidth: 5,
                                        width: composing ? '80%' : '90%',
                                    }}
                                >
                                    <Box className="flex flex-row justify-start items-center">
                                        <Typography className="text-xl font-bold">
                                            {mail.toName}
                                        </Typography>
                                    </Box>
                                    <Box>
                                        <Typography className="text-sm">
                                            few minutes ago
                                        </Typography>
                                    </Box>
                                </Box>
                                <Box className="pl-11 w-[80%] pr-5 py-5">
                                    <Typography className="mb-5">
                                        {mail.subject}
                                    </Typography>
                                    <Typography className="truncate text-sm">
                                        {mail.body}
                                    </Typography>
                                </Box>
                                {/* </Box> */}
                            </MenuItem>
                        ))}
                    </MenuList>
                </Grid>

                {/* Grid Three */}

                {composing && (
                    <Grid
                        item
                        xs={6}
                        className="bg-[#fff] py-12 px-8 transition-all"
                    >
                        <Box className="flex flex-row justify-between items-center w-[100%] mb-14 ">
                            <Box className="flex flex-row justify-start items-center">
                                <IconButton className="mr-2">
                                    <IoMdHeartEmpty size={30} />
                                </IconButton>
                                <IconButton className="mr-2">
                                    <BsReply size={30} />
                                </IconButton>
                                <IconButton className="mr-2">
                                    <BsReplyAll size={30} />
                                </IconButton>
                                <IconButton className="mr-2">
                                    <BsForward size={30} />
                                </IconButton>
                                <IconButton className="mr-2">
                                    <BsFolder size={30} />
                                </IconButton>
                                <IconButton className="mr-2">
                                    <BsTrash size={30} />
                                </IconButton>
                            </Box>
                            <IconButton onClick={() => handleModalClose()}>
                                <BsArrowDownLeftCircle color="black" />
                            </IconButton>
                        </Box>
                        <Box>
                            <Typography variant="h5" fontWeight={'bold'}>
                                Compose mail
                            </Typography>
                        </Box>
                        <Box className="mt-8">
                            <Box className="bg-[#e4e8ec] p-2 rounded-t-lg flex flex-row justify-between items-center">
                                <Typography className="font-bold mr-3 ml-1">
                                    To:
                                </Typography>
                                <InputBase
                                    sx={{ ml: 1, flex: 1 }}
                                    placeholder="example@email.com...."
                                />
                            </Box>
                            <Box className="bg-[#e4e8ec] p-2 flex flex-row justify-between items-center">
                                <Typography className="font-bold mr-3 ml-1">
                                    Subject:
                                </Typography>
                                <InputBase
                                    sx={{ ml: 1, flex: 1 }}
                                    placeholder="Subject of the mail...."
                                />
                            </Box>
                            <JoditEditor
                                ref={editor}
                                value={content}
                                config={config}
                                tabIndex={1} // tabIndex of textarea
                                onBlur={(newContent) => setContent(newContent)} // preferred to use only this option to update the content for performance reasons
                                onChange={(newContent) => {}}
                            />
                        </Box>
                    </Grid>
                )}
            </Grid>
            <Dialog
                open={alertOpen}
                onClose={handleAlertClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">
                    {'Save To Draft?'}
                </DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                        You have Unsaved Changes in your editor. Would you like
                        to save them to draft?
                    </DialogContentText>
                </DialogContent>
                <DialogActions className="pb-5 pr-5">
                    <Button
                        variant="contained"
                        className="bg-green-600 text-white hover:bg-green-800"
                        onClick={handleAlertAccept}
                        autoFocus
                    >
                        Save
                    </Button>
                    <Button
                        variant="contained"
                        className="bg-red-600 text-white hover:bg-red-800"
                        onClick={handleAlertDismiss}
                    >
                        Don't Save
                    </Button>
                    <Button
                        variant="contained"
                        className="bg-app-dark text-white hover:bg-[#000]"
                        onClick={handleAlertClose}
                    >
                        Cancel
                    </Button>
                </DialogActions>
            </Dialog>
        </Box>
    )
}

export default ComposeComponent
