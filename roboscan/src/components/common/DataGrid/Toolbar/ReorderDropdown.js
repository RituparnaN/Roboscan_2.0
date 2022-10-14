import React, { useState, useEffect } from 'react'
import {
    Button,
    Menu,
    MenuItem,
    FormControlLabel,
    Checkbox,
    Typography,
    MenuList,
} from '@mui/material'
import { move } from 'move-position'
import SpringList from 'react-spring-dnd'
import { Draggable, Droppable, DragDropContext } from 'react-beautiful-dnd'
import { FaAngleDown } from 'react-icons/fa'
import { MdReorder, MdViewColumn } from 'react-icons/md'

const ReorderDropdown = (props) => {
    const { checkedState, setCheckedState, columns, setColumns, utilColumn } =
        props

    const [anchorEl, setAnchorEl] = useState(null)
    const [newColumns, setNewColumns] = useState([])

    useEffect(() => {
        let newTempCols = utilColumn !== null ? columns.slice(1) : columns
        console.log('Current Columns', newTempCols)
        setNewColumns(newTempCols)
    }, [utilColumn, columns])

    const open = Boolean(anchorEl)

    const handleOpen = (event) => {
        setAnchorEl(event.currentTarget)
    }
    const handleClose = () => {
        setAnchorEl(null)
    }

    function reorder(arr, index, n) {
        const temp = [...Array(n)]

        let tempArray = arr
        let tempIndex = index

        // arr[i] should be present at index[i] index
        for (var i = 0; i < n; i++) temp[tempIndex[i]] = tempArray[i]

        // Copy temp[] to arr[]
        for (var i = 0; i < n; i++) {
            tempArray[i] = temp[i]
            tempIndex[i] = i
        }

        return tempArray
    }

    const handleDrag = (item) => {
        console.log('Old Columns', item)
        console.log(newColumns)
        const result = move(newColumns, {
            from: item.source.index,
            to: item.destination.index,
        })
        setNewColumns(result)
        // const result = reorder(newColumns, order, newColumns.length)
        // console.log('newColumns', result)
        let updatedArray = []
        if (utilColumn !== null) {
            updatedArray.push(columns[0])
            result.map((item, index) => {
                updatedArray.push(item)
                return null
            })
        } else {
            updatedArray = result
        }
        setColumns(updatedArray)
    }

    return (
        <div>
            <Button
                id="freeze-dropdown-button"
                aria-controls={open ? 'freeze-dropdown-menu' : undefined}
                aria-haspopup="true"
                aria-expanded={open ? 'true' : undefined}
                className="rounded-lg px-5 py-2 normal-case bg-app-dark hover:bg-black mx-2 my-3 text-sm"
                variant="contained"
                size="small"
                onClick={handleOpen}
                endIcon={<FaAngleDown size={12} />}
            >
                <MdViewColumn size={16} className="mr-4" />
                Column Order
            </Button>

            <Menu
                id="freeze-dropdown-menu"
                anchorEl={anchorEl}
                open={open}
                onClose={handleClose}
            >
                <DragDropContext onDragEnd={handleDrag}>
                    <Droppable droppableId="list">
                        {(provided, snapshot) => (
                            <MenuList
                                ref={provided.innerRef}
                                {...provided.droppableProps}
                                aria-labelledby={'freeze-dropdown-button'}
                                sx={{
                                    height: '300px',
                                    pt: 0,
                                    mt: 0,
                                }}
                            >
                                {newColumns.map((col, index) => (
                                    <Draggable
                                        key={col.key}
                                        draggableId={col.key}
                                        index={index}
                                    >
                                        {(provided, snapshot) => (
                                            <MenuItem
                                                ref={provided.innerRef}
                                                {...provided.draggableProps}
                                                {...provided.dragHandleProps}
                                            >
                                                <MdReorder
                                                    size={16}
                                                    style={{
                                                        marginRight: '10px',
                                                    }}
                                                />
                                                <Typography>
                                                    {col.name}
                                                </Typography>
                                            </MenuItem>
                                        )}
                                    </Draggable>
                                ))}
                                {provided.placeholder}
                            </MenuList>
                        )}
                    </Droppable>
                </DragDropContext>
            </Menu>
        </div>
    )
}

export default ReorderDropdown
