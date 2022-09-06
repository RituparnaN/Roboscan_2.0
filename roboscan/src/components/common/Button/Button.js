import React from 'react'
import { Button } from '@mui/material'

const CustomButton = (props) => {
    const { children, variants, className } = props
    return (
        <Button
            variant={variants === 'primary' ? 'contained' : 'outlined'}
            type="reset"
            className={className + ' ' + 'min-w-[200px]'}
            sx={variants == 'primary' ? styles.primary : styles.secondary}
        >
            {children}
        </Button>
    )
}

const styles = {
    common: {
        borderRadius: '50px',
        borderColor: '#171923',
    },
    primary: {
        borderRadius: '50px',
        borderColor: '#171923',
        color: 'white',
        bgcolor: '#171923',
        '&:hover': {
            borderColor: '#171923',
            bgcolor: '#171923',
        },
    },
    secondary: {
        borderRadius: '50px',
        borderColor: '#171923',
        color: '#171923',
        '&:hover': {
            borderColor: '#171923',
            bgcolor: '#171923',
            color: 'white',
        },
    },
}

export default CustomButton
