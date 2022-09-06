import ReactDatagrid, { SelectColumn } from 'react-data-grid'
import React, { useState, useEffect, useMemo } from 'react'
import { tableData } from '../../../data/dummyData'
import './tableStyles.css'

const DataGrid = (props) => {
    const [sortColumns, setSortColumns] = useState([])
    const [rows, setRows] = useState([])
    const [columns, setColumns] = useState([])
    const [selectedRows, setSelectedRows] = useState(() => new Set())
    const [direction, setDirection] = useState('ltr')

    const { utilColumn } = props

    useEffect(() => {
        console.log('Table Data:-', tableData)
        let tempColumns = []
        let tempRows = []

        if (utilColumn === 'select') {
            tempColumns.push(SelectColumn)
        }
        tableData.HEADER.forEach((header, index) => {
            tempColumns.push({
                key: header,
                name: header,
                sortable: true,
                filterable: true,
            })
        })
        tableData.DATA.forEach((data, index) => {
            let tempData = data.map((dval) => {
                return dval === null ? 'N.A' : dval
            })
            console.log('Temp Data: ', tempData)
            let tempObj = {}
            tempData.forEach((item, index) => {
                tempObj[tableData.HEADER[index]] = item
            })
            console.log('Temp Object->', tempObj)
            tempRows.push(tempObj)
        })
        console.log('tempRows', tempRows)
        console.log('tempColumns', tempColumns)

        setColumns(tempColumns)
        setRows(tempRows)
    }, [utilColumn])

    const summaryRows = useMemo(() => {
        const summaryRow = {
            id: 'total_0',
            totalCount: rows.length,
            yesCount: rows.filter((r) => r.available).length,
        }
        return [summaryRow]
    }, [rows])

    const rowKeyGetter = (row) => {
        return row.id || 0
    }

    const getComparator = (sortColumn) => {
        return (a, b) => {
            return a[sortColumn].localeCompare(b[sortColumn])
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

    return (
        <div className="mt-5">
            <ReactDatagrid
                rowKeyGetter={rowKeyGetter}
                columns={columns}
                rows={sortedRows}
                defaultColumnOptions={{
                    sortable: true,
                }}
                selectedRows={selectedRows}
                onSelectedRowsChange={setSelectedRows}
                onRowsChange={setRows}
                sortColumns={sortColumns}
                onSortColumnsChange={setSortColumns}
                summaryRows={summaryRows}
                className="rdg-light bg-[#f4f5fa] border-none max-w-[89vw]"
                direction={direction}
                rowClass={() => 'bg-[#f4f5fa] border-none'}
            />
        </div>
    )
}

export default DataGrid
