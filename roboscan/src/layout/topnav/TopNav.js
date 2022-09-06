import React, { useState } from 'react'
import {
    Box,
    Button,
    AppBar,
    Toolbar,
    Typography,
    Menu,
    MenuItem,
} from '@mui/material'
import { MdAdb } from 'react-icons/md'
import { pages } from '../../data/dummyData'

const TopNav = () => {
    const [anchorEl, setAnchorEl] = useState(null)

    const handleClick = (index, event) => {
        setAnchorEl({ [index]: event.currentTarget })
    }
    const handleClose = () => {
        setAnchorEl(null)
    }

    return (
        <AppBar position="sticky" className="bg-app-dark shadow-app-dark">
            <Toolbar disableGutters>
                <Box className="min-w-[100px] flex justify-center items-center">
                    <MdAdb size={38} />
                </Box>
                <Box
                    sx={{
                        flexGrow: 1,
                        display: { xs: 'none', md: 'flex' },
                    }}
                >
                    <Typography
                        noWrap
                        component="a"
                        href="/"
                        className="my-3"
                        sx={{
                            mr: 2,
                            display: { xs: 'none', md: 'flex' },
                            fontWeight: 500,
                            color: 'inherit',
                            textDecoration: 'none',
                            fontSize: '37px',
                        }}
                    >
                        Roboscan
                    </Typography>
                    {pages.map((page, index) => (
                        <React.Fragment key={page.id}>
                            <Button
                                id={page.title}
                                className={
                                    'text-white mx-0 px-11 hover:bg-[#2b313f] text-lg' +
                                    ' ' +
                                    (Boolean(anchorEl && anchorEl[index]) &&
                                        'bg-[#2b313f]')
                                }
                                aria-controls={
                                    Boolean(anchorEl && anchorEl[index])
                                        ? `basic-menu-${index}`
                                        : undefined
                                }
                                aria-haspopup="true"
                                aria-expanded={
                                    Boolean(anchorEl && anchorEl[index])
                                        ? 'true'
                                        : undefined
                                }
                                onClick={(e) => handleClick(index, e)}
                            >
                                {page.title}
                            </Button>
                            <Menu
                                id={`basic-menu-${index}`}
                                anchorEl={anchorEl && anchorEl[index]}
                                open={Boolean(anchorEl && anchorEl[index])}
                                onClose={handleClose}
                                MenuListProps={{
                                    'aria-labelledby': page.title,
                                    sx: {
                                        bgcolor: '#2b313f',
                                        minWidth: '160px',
                                        color: 'white',
                                    },
                                }}
                            >
                                {page.menu.map((sub, subIndex) => (
                                    <MenuItem
                                        sx={{
                                            '&:hover': {
                                                bgcolor: '#e0e0e0',
                                                color: '#1e212c',
                                            },
                                        }}
                                        onClick={handleClose}
                                        key={subIndex}
                                    >
                                        {sub}
                                    </MenuItem>
                                ))}
                            </Menu>
                        </React.Fragment>
                    ))}
                </Box>
            </Toolbar>
        </AppBar>
    )
}

export default TopNav
