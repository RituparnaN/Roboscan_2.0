import React from 'react'
import { Box } from '@mui/material'
import SideNav from './sidenav'
import TopNav from './topnav'

const Layout = ({ children }) => {
    return (
        <Box>
            <TopNav />
            <Box className="flex flex-row ">
                <Box className="min-w-[100px]">
                    <SideNav />
                </Box>
                <Box>{children}</Box>
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
