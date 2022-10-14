import React from 'react'
import { Box } from '@mui/material'
import SideNav from './sidenav'
import TopNav from './topnav'

const Layout = ({ children }) => {
    return (
        <Box className="w-[100vw]">
            <TopNav />
            <Box className="flex flex-row w-[100vw]">
                <Box className="min-w-[100px] h-[100vh]">
                    <SideNav />
                </Box>
                <Box className="w-[calc(100vw-100px)]">{children}</Box>
            </Box>
            {/* <Grid container>
                <Grid item className="w-[100px]">
                    <SideNav />
                </Grid>
                <Grid item xs>
                    <Box>{children}</Box>
                </Grid>
            </Grid> */}
        </Box>
    )
}

export default Layout
