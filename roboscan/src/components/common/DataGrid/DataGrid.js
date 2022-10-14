import ReactDatagrid, { SelectColumn } from 'react-data-grid'
import { DraggableHeaderRenderer } from './utils/DraggableHeaderRenderer'
import React, { useState, useEffect, useMemo } from 'react'
import { tableData } from '../../../data/dummyData'
import { Box } from '@mui/material'
import Pagination from './Pagination/Pagination'
import { DndProvider } from 'react-dnd'
import { HTML5Backend } from 'react-dnd-html5-backend'

import moment from 'moment'

import './tableStyles.css'
import Toolbar from './Toolbar/Toolbar'

const DataGrid = (props) => {
    const { utilColumn = null, title } = props

    const [sortColumns, setSortColumns] = useState([])
    const [rows, setRows] = useState([])
    const [totalRows, setTotalRows] = useState([])
    const [columns, setColumns] = useState([])
    const [selectedRows, setSelectedRows] = useState(() => new Set())
    const [selectedRowsArray, setSelectedRowsArray] = useState([])
    const [isSelected, setIsSelected] = useState(false)
    const [showSelected, setShowSelected] = useState(false)
    // eslint-disable-next-line no-unused-vars
    const [direction, setDirection] = useState('ltr')
    const [checkedState, setCheckedState] = useState([])

    // Pagination States
    const [currentPage, setCurrentPage] = useState(1)
    const [dataPerPage, setDataPerPage] = useState(10)
    const [numOfPages, setNumOfPages] = useState(0)
    const [pageNumbers, setPageNumbers] = useState([])
    const [firstIndex, setFirstIndex] = useState(0)
    const [lastIndex, setLastIndex] = useState(0)

    function toTitleCase(str) {
        return str.replace(/\w\S*/g, function (txt) {
            return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase()
        })
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
            if (index === 0) {
                tempColumns.push({
                    key: header,
                    name: toTitleCase(header),
                    sortable: true,
                    filterable: true,
                    width: 150,
                    summaryFormatter({ row }) {
                        return <>{row.totalCount} records</>
                    },
                })
            } else {
                tempColumns.push({
                    key: header,
                    name: toTitleCase(header),
                    sortable: true,
                    filterable: true,
                    width: 150,
                })
            }
        })

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

        setColumns(tempColumns)
        console.log('tempColumns', tempColumns)
        setCheckedState(new Array(tempColumns.length).fill(false))
        setTotalRows(tempRows)
    }, [utilColumn])

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

    useEffect(() => {
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
    }, [selectedRows, isSelected, totalRows])

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
                className="mr-2 cursor-pointer"
            />
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
            className="rdg-light bg-[#f4f5fa] max-w-[89vw] overflow-hidden hover:overflow-auto border-solid min-h-[450px]"
            direction={direction}
            rowClass={(row) =>
                row.INDEX % 2 !== 0
                    ? 'bg-[#f4f5fa] border-none'
                    : 'bg-[#ebecf1] border-none'
            }
            renderers={{ checkboxFormatter: CheckboxFormatter }}
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

export default DataGrid
