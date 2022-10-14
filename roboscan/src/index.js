import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import App from './App'
import Layout from './layout'
import {
    createTheme,
    ThemeProvider,
    StyledEngineProvider,
} from '@mui/material/styles'

const rootElement = document.getElementById('root')

const theme = createTheme({
    typography: {
        fontFamily:
            '"GoogleSans-Regular", "Segoe UI", Roboto, Helvetica Neue, Arial, sans-serif',
        fontSize: 16,
    },
    components: {
        MuiDialog: {
            defaultProps: {
                container: rootElement,
            },
        },
        MuiMenu: {
            defaultProps: {
                container: rootElement,
            },
        },
    },
})

const root = ReactDOM.createRoot(rootElement)

root.render(
    <ThemeProvider theme={theme}>
        <StyledEngineProvider injectFirst>
            <Layout>
                <App />
            </Layout>
        </StyledEngineProvider>
    </ThemeProvider>
)
