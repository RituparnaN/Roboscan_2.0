import React, { useState } from 'react'
import {
    Button,
    Menu,
    MenuItem,
    FormControlLabel,
    Checkbox,
} from '@mui/material'
import { BsLayoutSidebarInset } from 'react-icons/bs'
import { FaAngleDown } from 'react-icons/fa'

const FreezeDropdown = (props) => {
    const { checkedState, setCheckedState, columns, setColumns, utilColumn } =
        props

    const [freezeAnchor, setFreezeAnchor] = useState(null)
    const freezeOpen = Boolean(freezeAnchor)

    const handleOpenFreeze = (event) => {
        setFreezeAnchor(event.currentTarget)
    }
    const handleCloseFreeze = () => {
        setFreezeAnchor(null)
    }

    const handleCheckbox = (position) => {
        const updatedCheckedState = checkedState.map((item, index) =>
            index === position ? !item : item
        )

        setCheckedState(updatedCheckedState)

        const updatedColumns = columns.map((item, index) => {
            if (utilColumn !== null) {
                if (updatedCheckedState[index] === true || index === 0) {
                    return {
                        ...item,
                        frozen: true,
                    }
                } else {
                    return {
                        ...item,
                        frozen: false,
                    }
                }
            } else {
                if (updatedCheckedState[index] === true) {
                    return {
                        ...item,
                        frozen: true,
                    }
                } else {
                    return {
                        ...item,
                        frozen: false,
                    }
                }
            }
        })

        setColumns(updatedColumns)
    }

    return (
        <div>
            <Button
                id="freeze-dropdown-button"
                aria-controls={freezeOpen ? 'freeze-dropdown-menu' : undefined}
                aria-haspopup="true"
                aria-expanded={freezeOpen ? 'true' : undefined}
                className="rounded-lg px-5 py-2 normal-case bg-app-dark hover:bg-black mx-2 my-3 text-sm"
                variant="contained"
                size="small"
                onClick={handleOpenFreeze}
                endIcon={<FaAngleDown size={12} />}
            >
                <BsLayoutSidebarInset size={16} className="mr-4" />
                Freeze
            </Button>

            <Menu
                id="freeze-dropdown-menu"
                MenuListProps={{
                    'aria-labelledby': 'freeze-dropdown-button',
                    sx: {
                        height: '300px',
                    },
                }}
                anchorEl={freezeAnchor}
                open={freezeOpen}
                onClose={handleCloseFreeze}
            >
                {columns.map((col, index) => {
                    if (utilColumn !== null) {
                        if (index !== 0) {
                            return (
                                <MenuItem
                                    sx={{
                                        padding: 0,
                                        margin: 0,
                                    }}
                                    key={col.key}
                                >
                                    <FormControlLabel
                                        control={<Checkbox size="small" />}
                                        label={col.name}
                                        checked={
                                            checkedState.length
                                                ? checkedState[index]
                                                : false
                                        }
                                        onChange={() => handleCheckbox(index)}
                                        sx={{
                                            padding: 0,
                                            margin: 0,
                                            paddingRight: '15px',
                                        }}
                                    />
                                </MenuItem>
                            )
                        } else return null
                    } else {
                        return (
                            <MenuItem
                                sx={{
                                    padding: 0,
                                    margin: 0,
                                }}
                                key={col.key}
                            >
                                <FormControlLabel
                                    control={<Checkbox size="small" />}
                                    label={col.name}
                                    checked={
                                        checkedState.length
                                            ? checkedState[index]
                                            : false
                                    }
                                    onChange={() => handleCheckbox(index)}
                                    sx={{
                                        padding: 0,
                                        margin: 0,
                                        paddingRight: '15px',
                                    }}
                                />
                            </MenuItem>
                        )
                    }
                })}
            </Menu>
        </div>
    )
}

export default FreezeDropdown
