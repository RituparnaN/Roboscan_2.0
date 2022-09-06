import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import App from './App'
import reportWebVitals from './reportWebVitals'
import Layout from './layout'
import {
    createTheme,
    ThemeProvider,
    styled,
    StyledEngineProvider,
} from '@mui/material/styles'

const theme = createTheme({
    typography: {
        fontFamily:
            '"GoogleSans-Regular", "Segoe UI", Roboto, Helvetica Neue, Arial, sans-serif',
        fontSize: 16,
    },
})

const root = ReactDOM.createRoot(document.getElementById('root'))

root.render(
    <React.StrictMode>
        <StyledEngineProvider injectFirst>
            <ThemeProvider theme={theme}>
                <Layout>
                    <App />
                </Layout>
            </ThemeProvider>
        </StyledEngineProvider>
    </React.StrictMode>
)

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals()
