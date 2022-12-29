import ReactDatagrid, { SelectColumn } from 'react-data-grid'
import React, { useState, useEffect, useMemo, } from 'react'
import { tableData } from '../../../data/dummyData'
import { 
    Box, 
    IconButton, 
    Menu, 
    MenuItem, 
    ClickAwayListener,
    Divider,
    Accordion,
    AccordionSummary,
    AccordionDetails,
    Typography,
    styled,
    Checkbox,
    ListItem,
    InputLabel,
    Select,
    FormControl
} from '@mui/material'
import Pagination from './Pagination/Pagination'
import { BsInfoCircleFill, BsCaretRightFill, BsFilter, BsChevronDown } from 'react-icons/bs'
import { AiOutlineSortAscending, AiOutlineSortDescending } from 'react-icons/ai'

import moment from 'moment'

import './tableStyles.css'
import Toolbar from './Toolbar/Toolbar'

const DataGrid = (props) => {
    const { utilColumn = null, title, expanded } = props

    const [sortColumns, setSortColumns] = useState([])
    const [rows, setRows] = useState([])
    const [tempRowState, setTempRowState] = useState([])
    const [totalRows, setTotalRows] = useState([])
    const [columns, setColumns] = useState([])
    const [selectedRows, setSelectedRows] = useState(() => new Set())
    const [selectedRowsArray, setSelectedRowsArray] = useState([])
    const [isSelected, setIsSelected] = useState(false)
    const [showSelected, setShowSelected] = useState(false)

    // eslint-disable-next-line no-unused-vars
    const [direction, setDirection] = useState('ltr')
    const [checkedState, setCheckedState] = useState([])
    const [searchString, setSearchString] = useState('')

    // Pagination States
    const [currentPage, setCurrentPage] = useState(1)
    const [dataPerPage, setDataPerPage] = useState(10)
    const [numOfPages, setNumOfPages] = useState(0)
    const [pageNumbers, setPageNumbers] = useState([])
    const [firstIndex, setFirstIndex] = useState(0)
    const [lastIndex, setLastIndex] = useState(0)

    //Filters States
    const [filterActive, setFilterActive] = useState(false)

    const toTitleCase = (str) => {
        return str.replace(/\w\S*/g, function (txt) {
            return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase()
        })
    }

    const getLastValue = (set) => {
        let value
        for (value of set);
        return value
    }

    //Setting first and last index of current Page
    useEffect(() => {
        let tempLastIndex = currentPage * dataPerPage
        setFirstIndex(tempLastIndex - dataPerPage)
        setLastIndex(tempLastIndex)
    }, [currentPage, dataPerPage])

    useEffect(() => {
        let tempColumns = []
        let tempRows = []

        if (utilColumn === 'select') {
            tempColumns.push(SelectColumn)
        }
        if (utilColumn === 'singleSelect') {
            tempColumns.push({
                ...SelectColumn,
                headerRenderer(props) {
                    return ''
                },
            })
        }
        // if (utilColumn === 'singleSelect') {
        //     const SELECT_COLUMN_KEY = 'select-row'
        //     let customSelectColumn = {
        //         key: SELECT_COLUMN_KEY,
        //         name: '',
        //         width: 35,
        //         minWidth: 35,
        //         maxWidth: 35,
        //         resizable: false,
        //         sortable: false,
        //         frozen: true,
        //     }
        //     tempColumns.push(customSelectColumn)
        // }

        tableData.DATA.forEach((data, dataIndex) => {
            let tempData = data.map((dval) => {
                return dval === null ? 'N.A' : dval
            })
            let tempObj = {}
            tempData.forEach((item, index) => {
                tempObj[tableData.HEADER[index]] = toTitleCase(item)
            })
            tempObj['INDEX'] = dataIndex + 1
            tempRows.push(tempObj)
        })

        tempColumns.push({
            key: 'INDEX',
            name: 'Sr. No.',
            sortable: true,
            filterable: true,
            width: 20,
            summaryFormatter() {
                return <strong>Total</strong>
            },
        })

        tableData.HEADER.forEach((header, index) => {
            let columnObject = {};
            if (index === 0) {
                columnObject = {
                    key: header,
                    name: toTitleCase(header),
                    sortable: true,
                    filterable: true,
                    width: 180,
                    formatter(props) {
                        const value = props.row.CASENO
                        return (
                            <a href="/" id={value} name={value}>
                                {value}
                            </a>
                        )
                    },
                }
            } else {
                columnObject = {
                    key: header,
                    name: toTitleCase(header),
                    sortable: true,
                    filterable: true,
                    width: 180,
                }
            }

            if(filterActive === true){
                columnObject = {
                    ...columnObject,
                    headerRenderer: (p) => <FiltersRenderer 
                        {...p}
                        rows={tempRows}
                    />,
                }
            }

            tempColumns.push(columnObject);
        })

        

        setColumns(tempColumns)
        console.log('tempColumns', tempColumns)
        setCheckedState(new Array(tempColumns.length).fill(false))
        setTotalRows(tempRows)
        setTempRowState(tempRows)
    }, [utilColumn, filterActive])

    //Setting Visible Rows For Pagination
    useEffect(() => {
        let currentRecords =
            isSelected === true && showSelected === true
                ? selectedRowsArray.slice(firstIndex, lastIndex)
                : totalRows.slice(firstIndex, lastIndex)

        setRows(currentRecords)
    }, [
        totalRows,
        firstIndex,
        lastIndex,
        selectedRowsArray,
        showSelected,
        isSelected,
    ])

    useEffect(() => {
        if (searchString !== '') {
            const newRecords = tempRowState.filter((e) =>
                Object.values(e).find((f) => {
                    if (typeof f === 'string' && f.includes(searchString)) {
                        return true
                    } else return false
                })
            )
            setTotalRows(newRecords)
        } else {
            setTotalRows(tempRowState)
        }
    }, [searchString, tempRowState])

    // Setting Number Of Pages For Pagination
    useEffect(() => {
        let rowsToCeil =
            isSelected === true && showSelected === true
                ? selectedRowsArray
                : totalRows
        setNumOfPages(Math.ceil(rowsToCeil.length / dataPerPage))
    }, [totalRows, dataPerPage, isSelected, showSelected, selectedRowsArray])

    // Creating Page Numbers Array for Pagination
    useEffect(() => {
        setPageNumbers([...Array(numOfPages + 1).keys()].slice(1))
    }, [numOfPages])

    //Handle Selected Cells for multiselect
    useEffect(() => {
        if (utilColumn === 'select') {
            console.log(selectedRows)
            if (selectedRows.size > 0) {
                if (isSelected === false) {
                    setIsSelected(true)
                }
                // console.log('Current Rows:-', sortedRows)
                setSelectedRowsArray(
                    totalRows.filter((e) => selectedRows.has(e.INDEX))
                )
            } else {
                if (isSelected === true) {
                    setIsSelected(false)
                }
                setShowSelected(false)
            }
        }
        if (utilColumn === 'singleSelect') {
            if (selectedRows.size > 1) {
                setSelectedRows(new Set([getLastValue(selectedRows)]))
            }
        }
    }, [selectedRows, isSelected, totalRows, utilColumn])

    useEffect(() => {
        console.log('Selected Rows', selectedRowsArray)
    }, [selectedRowsArray])

    const summaryRows = useMemo(() => {
        const summaryRow = {
            id: 'total_0',
            totalCount: totalRows.length,
            yesCount: totalRows.filter((r) => r.available).length,
        }
        return [summaryRow]
    }, [totalRows])

    const rowKeyGetter = (row) => {
        return row.INDEX || 0
    }

    const getComparator = (sortColumn) => {
        let dateRegex = /\d{2}\/\d{2}\/\d{4}/g

        return (a, b) => {
            if (sortColumn === 'INDEX') {
                return a[sortColumn] - b[sortColumn]
            } else if (
                typeof a[sortColumn] === 'string' &&
                typeof b[sortColumn] === 'string' &&
                a[sortColumn].match(dateRegex) &&
                b[sortColumn].match(dateRegex)
            ) {
                return (
                    moment(a[sortColumn], 'MM-DD-YYYY') -
                    moment(b[sortColumn], 'MM-DD-YYYY')
                )
            } else return a[sortColumn].localeCompare(b[sortColumn])
        }
    }

    const sortedRows = useMemo(() => {
        if (sortColumns.length === 0) return rows

        return [...rows].sort((a, b) => {
            for (const sort of sortColumns) {
                const comparator = getComparator(sort.columnKey)
                const compResult = comparator(a, b)
                if (compResult !== 0) {
                    return sort.direction === 'ASC' ? compResult : -compResult
                }
            }
            return 0
        })
    }, [rows, sortColumns])

    const CheckboxFormatter = ({ disabled, onChange, ...props }, ref) => {
        function handleChange(e) {
            onChange(e.target.checked, e.nativeEvent.shiftKey)
        }

        return (
            <input
                type="checkbox"
                ref={ref}
                {...props}
                onChange={handleChange}
                className="cursor-pointer"
            />
        )
    }

    const RadioFormatter = ({ disabled, onChange, ...props }, ref) => {
        function handleChange(e) {
            console.log('Row Target:', e.target.tabIndex)
            onChange(e.target.checked, e.nativeEvent.shiftKey)
        }
        function handleClick(e) {
            if (e.target.tabIndex === 0 && selectedRows.size > 0) {
                e.target.checked = false
                setSelectedRows(new Set([]))
            }
        }
        return (
            <input
                type="radio"
                ref={ref}
                {...props}
                onChange={handleChange}
                onClick={handleClick}
                className="mr-2 cursor-pointer"
                s
            />
        )
    }
    const EmptyRowsRenderer = () => {
        return (
            <div
                style={{ textAlign: 'center', gridColumn: '1/-1' }}
                className="align-middle my-auto text-2xl place-items-center"
            >
                <BsInfoCircleFill size={18} className="mr-3" />
                No Rows Found
            </div>
        )
    }

    const gridElement = (custRows) => (
        <ReactDatagrid
            rowKeyGetter={rowKeyGetter}
            columns={columns}
            rows={custRows}
            defaultColumnOptions={{
                sortable: true,
                resizable: true,
            }}
            selectedRows={selectedRows}
            onSelectedRowsChange={setSelectedRows}
            onRowsChange={setRows}
            sortColumns={sortColumns}
            onSortColumnsChange={setSortColumns}
            summaryRows={summaryRows}
            className={`rdg-light text-base bg-[#f4f5fa] max-w-[89vw] overflow-hidden hover:overflow-auto ${expanded ? 'h-[450px]' : 'h-[220px]'} max-h-[450px] border-[1.5px] ${filterActive===true&&'border-solid border-app-dark '}`}
            direction={direction}
            rowClass={(row, index) =>
                `border-none ${
                    selectedRows.has(row.INDEX)
                        ? 'bg-[#d9d9d9]'
                        : row.INDEX % 2 !== 0
                        ? 'bg-[#f4f5fa]'
                        : 'bg-[#ebecf1]'
                }`
            }
            renderers={{
                checkboxFormatter:
                    utilColumn === 'singleSelect'
                        ? RadioFormatter
                        : CheckboxFormatter,
                noRowsFallback: <EmptyRowsRenderer />,
            }}
        />
    )

    return (
        <div className="mt-5">
            <Box className=" px-5 pt-2  border-[0.5px] border-solid border-[#a4acb6] bg-white rounded-md">
                <Toolbar
                    checkedState={checkedState}
                    setCheckedState={setCheckedState}
                    columns={columns}
                    setColumns={setColumns}
                    gridElement={gridElement(totalRows)}
                    selectedGridElement={gridElement(selectedRowsArray)}
                    title={title}
                    utilColumn={utilColumn}
                    isSelected={isSelected}
                    selectedRows={selectedRows}
                    showSelected={showSelected}
                    setShowSelected={setShowSelected}
                    setCurrentPage={setCurrentPage}
                    searchString={searchString}
                    setSearchString={setSearchString}
                    filterActive={filterActive}
                    setFilterActive={setFilterActive}
                />
                {gridElement(sortedRows)}
                <Pagination
                    currentPage={currentPage}
                    setCurrentPage={setCurrentPage}
                    numOfPages={numOfPages}
                    dataPerPage={dataPerPage}
                    setDataPerPage={setDataPerPage}
                    pageNumbers={pageNumbers}
                    setPageNumbers={setPageNumbers}
                    firstIndex={firstIndex}
                    lastIndex={lastIndex}
                    totalRows={totalRows}
                />
            </Box>
        </div>
    )
}

const FiltersRenderer = ({isCellSelected, column, children, rows}) => {

    const [anchorEl, setAnchorEl] = useState(null)
    const [rowData, setRowData] = useState([])
    const [uniqueRowData, setUniqueRowData] = useState([])
    const [checkedState, setCheckedState] = useState([])
    const [condition, setCondition] = useState({})

    const handleClick = (key, event) => {
        setAnchorEl({ [key]: event.currentTarget })
    }
    const handleClose = () => {
        setAnchorEl(null)
    }

    useEffect(()=>{

        let colValues = [];

        // console.log("Column in Filters:-", column)
        // console.log("Rows in Filters:-", rows)

        rows.forEach((row)=>{
            colValues.push(row[column.key])
        })

        console.log("Filtered Row:-", colValues)
        let uniqueValues = colValues.filter((v, i, a) => a.indexOf(v) === i)
        console.log("Unique Values in Row:-", uniqueValues)

        setRowData(colValues);
        setUniqueRowData(uniqueValues);
        setCheckedState(new Array(uniqueValues.length).fill(true))

    },[column, rows])

    const handleCheckbox = (pos) => {
        let updatedCheckedState = checkedState.map((item, index)=>
            index === pos ? !item : item
        )

        setCheckedState(updatedCheckedState)
    }

    const StyledAccordionSummary = styled(AccordionSummary)(({theme})=>({
        
        minHeight: '20px',
        maxHeight: '20px',
        '& .MuiAccordionSummary-expandIconWrapper.Mui-expanded': {
            transform: 'rotate(90deg)',
        },
        '& .MuiAccordionSummary-content': {
            padding: 0,
            margin: 0,
        }
    }))

    const handleCondition = (event) => {
        setCondition({[column.key]: event.target.value})
    }

    
    return (
        <>
            {/* <ClickAwayListener onClickAway={handleClose} > */}
                <div className='flex flex-row justify-between items-center px-2 bg-app-dark text-white'>
                    {column.name}
                    <IconButton className="p-0" onClick={(e) => handleClick(column.key, e)}>
                        <BsFilter color="white" size={16} />
                    </IconButton>
                
                    <Menu
                        id={`filter-dropdown-${column.key}`}
                        MenuListProps={{
                            'aria-labelledby': 'export-dropdown-button',
                            className: 'min-w-[260px]',
                        }}
                        anchorEl={anchorEl && anchorEl[column.key]}
                        open={Boolean(anchorEl && anchorEl[column.key])}
                        onClose={handleClose}
                        anchorOrigin={{
                            vertical: 'bottom',
                            horizontal: 'right'
                        }}
                        transformOrigin={{
                            horizontal: 'right',
                            vertical: 'top',
                        }}
                        className="text-sm"
                    >
                        <MenuItem className='text-sm' >
                            Sort Ascending <AiOutlineSortAscending className='ml-2' size={20} />
                        </MenuItem>
                        <MenuItem className='text-sm'>
                            Sort Descending <AiOutlineSortDescending className='ml-2' size={20} />
                        </MenuItem>
                        <Divider />
                        <ListItem className='text-sm' >
                            <Accordion 
                                className='border-none shadow-none p-0 bg-transparent w-[100%]' 
                            >
                                <StyledAccordionSummary 
                                    className='p-0 h-[0px] m-0'  
                                    sx={{
                                        '&.Mui-expanded':{
                                            maxHeight: '20px',
                                            minHeight: '20px',
                                        }
                                    }}
                                    expandIcon={<BsCaretRightFill size={12} />}
                                >
                                    <Typography className='p-0 m-0 text-sm' >Filter By Condition</Typography>
                                </StyledAccordionSummary>
                                <AccordionDetails className='p-0'>
                                    <FormControl sx={{ m: 1, minWidth: 120 }} size="small" className='text-sm w-[90%] mt-5'>
                                        <InputLabel id="demo-select-small" className='text-sm'>Condition</InputLabel>
                                        <Select
                                            labelId="demo-select-small"
                                            id="demo-select-small"
                                            value={condition[column.key]}
                                            label="Condition"
                                            onChange={handleCondition}
                                            className='text-sm w-[100%]'
                                            MenuProps={{
                                                className: 'max-h-[240px]'
                                            }}
                                        >
                                            <MenuItem className='text-sm' value="">
                                            <em>None</em>
                                            </MenuItem>
                                            <MenuItem className='text-sm' value={'empty'}>Is Empty</MenuItem>
                                            <MenuItem className='text-sm' value={'notEmpty'}>Is Not Empty</MenuItem>
                                            <Divider />
                                            <MenuItem className='text-sm' value={'contains'}>Text Contains</MenuItem>
                                            <MenuItem className='text-sm' value={'dontcontain'}>Text Does Not Contain</MenuItem>
                                            <MenuItem className='text-sm' value={'startswith'}>Text Starts With</MenuItem>
                                            <MenuItem className='text-sm' value={'endswith'}>Text Ends With</MenuItem>
                                            <MenuItem className='text-sm' value={'matches'}>Text Matches</MenuItem>
                                            <Divider />
                                            <MenuItem className='text-sm' value={'range'}>Range</MenuItem>
                                            <MenuItem className='text-sm' value={'greaterThan'}>Is Greater Than</MenuItem>
                                            <MenuItem className='text-sm' value={'lessThan'}>Is Less Than</MenuItem>
                                            <MenuItem className='text-sm' value={'equals'}>Is Equals To</MenuItem>
                                        </Select>
                                    </FormControl>
                                </AccordionDetails>
                            </Accordion>
                        </ListItem>
                        {/* <Divider /> */}
                        <ListItem className='text-sm' >
                            <Accordion 
                                className='border-none shadow-none p-0 bg-transparent w-[100%]' 
                                defaultExpanded
                            >
                                <StyledAccordionSummary 
                                    className='p-0 h-[0px] m-0'  
                                    expandIcon={<BsCaretRightFill size={12} />}
                                    sx={{
                                        '&.Mui-expanded':{
                                            maxHeight: '20px',
                                            minHeight: '20px',
                                        }
                                    }}
                                >
                                    <Typography className='p-0 m-0 text-sm' >Filter By Values</Typography>
                                </StyledAccordionSummary>
                                <AccordionDetails className='p-0' >
                                    <Box className="max-h-[200px] overflow-auto mt-3">
                                        {uniqueRowData.map((data, index)=>(
                                            <Box key={data} className='flex flex-row justify-start items-center'>
                                                <Checkbox size='small' checked={checkedState[index]} onChange={()=>handleCheckbox(index)} /> 
                                                <Typography className='text-sm'>
                                                    {data}
                                                </Typography>
                                            </Box>
                                        ))}
                                    </Box>
                                </AccordionDetails>
                            </Accordion>
                        </ListItem>
                    </Menu>   
                </div>
            {/* </ClickAwayListener> */}
          
        </>
    );
}

export default DataGrid
