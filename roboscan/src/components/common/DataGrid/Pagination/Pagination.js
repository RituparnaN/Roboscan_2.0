import React, { useState, useEffect, useMemo } from 'react'
import {
    FaAngleLeft,
    FaAngleRight,
    FaAngleDoubleLeft,
    FaAngleDoubleRight,
} from 'react-icons/fa'
import {
    Box,
    Button,
    IconButton,
    InputBase,
    Typography,
    Select,
    MenuItem,
    InputLabel,
    FormControl,
} from '@mui/material'

const Pagination = (props) => {
    const {
        currentPage,
        setCurrentPage,
        numOfPages,
        dataPerPage,
        setDataPerPage,
        pageNumbers,
        setPageNumbers,
        firstIndex,
        lastIndex,
        totalRows,
    } = props

    const [pageInput, setPageInput] = useState(1)

    const lastRowArray =
        currentPage === numOfPages
            ? [currentPage - 2, currentPage - 1, currentPage]
            : [currentPage - 1, currentPage, currentPage + 1]

    const lastRowCheck =
        currentPage === numOfPages || currentPage === numOfPages - 1
            ? false
            : true

    return (
        <Box className="flex flex-row justify-between items-center w-[100%] overflow-auto">
            <Box className="flex flex-row justify-start items-center">
                <InputLabel className="mr-3">Records Per Page:</InputLabel>
                <Select
                    labelId="records-per-page-label"
                    id="records-per-page"
                    value={dataPerPage}
                    onChange={(e) => {
                        setDataPerPage(e.target.value)
                        setCurrentPage(1)
                    }}
                    className="w-[80px] h-[40px] bg-white"
                >
                    <MenuItem value={5}>5</MenuItem>
                    <MenuItem value={10}>10</MenuItem>
                    <MenuItem value={15}>15</MenuItem>
                    <MenuItem value={30}>30</MenuItem>
                </Select>
                <Typography className="ml-5">
                    Results: {firstIndex + 1} -{' '}
                    {currentPage === numOfPages ? totalRows.length : lastIndex}{' '}
                    of {totalRows.length}
                </Typography>
            </Box>
            <Box className="flex flex-row justify-end items-center">
                <IconButton
                    onClick={() => {
                        setCurrentPage(1)
                    }}
                    disabled={currentPage === 1 ? true : false}
                    className="p-1 pr-0 rounded-none"
                >
                    <FaAngleDoubleLeft
                        size={24}
                        className={
                            currentPage === 1
                                ? `text-slate-500`
                                : 'text-app-dark'
                        }
                    />
                </IconButton>
                <IconButton
                    onClick={() => {
                        setCurrentPage(currentPage - 1)
                    }}
                    disabled={currentPage === 1 ? true : false}
                    className="p-1 pl-0 rounded-none"
                >
                    <FaAngleLeft
                        size={24}
                        className={
                            currentPage === 1
                                ? `text-slate-500`
                                : 'text-app-dark'
                        }
                    />
                </IconButton>
                <ul className="flex flex-row justify-end mx-1 px-0">
                    {numOfPages > 5 ? (
                        <>
                            {currentPage > 4 ? (
                                <>
                                    <li className="list-none">
                                        <button
                                            onClick={() => setCurrentPage(1)}
                                            className={`text-lg border-solid border-[0.5px] w-8 border-slate-500 cursor-pointer rounded-md mx-1
                                                        ${
                                                            currentPage === 1
                                                                ? ' bg-app-dark text-white'
                                                                : ' bg-white text-app-dark'
                                                        }
                                                `}
                                        >
                                            {1}
                                        </button>
                                    </li>
                                    <li className="list-none text-3xl flex justify-center items-center px-1">
                                        ...
                                    </li>
                                    {lastRowArray.map((pgNumber) => (
                                        <li
                                            key={pgNumber}
                                            className="list-none"
                                        >
                                            <button
                                                onClick={() =>
                                                    setCurrentPage(pgNumber)
                                                }
                                                className={`text-lg border-solid border-[0.5px] w-8 border-slate-500 cursor-pointer rounded-md mx-1
                                                            ${
                                                                currentPage ===
                                                                pgNumber
                                                                    ? ' bg-app-dark text-white'
                                                                    : ' bg-white text-app-dark'
                                                            }
                                                    `}
                                            >
                                                {pgNumber}
                                            </button>
                                        </li>
                                    ))}
                                    {lastRowCheck === true && (
                                        <>
                                            <li className="list-none text-3xl flex justify-center items-center px-1">
                                                ...
                                            </li>
                                            <li className="list-none">
                                                <button
                                                    onClick={() =>
                                                        setCurrentPage(
                                                            numOfPages
                                                        )
                                                    }
                                                    className={`text-lg border-solid border-[0.5px] w-8 border-slate-500 cursor-pointer rounded-md mx-1
                                                                ${
                                                                    currentPage ===
                                                                    numOfPages
                                                                        ? ' bg-app-dark text-white'
                                                                        : ' bg-white text-app-dark'
                                                                }
                                                        `}
                                                >
                                                    {numOfPages}
                                                </button>
                                            </li>
                                        </>
                                    )}
                                </>
                            ) : (
                                <>
                                    {pageNumbers.slice(0, 5).map((pgNumber) => (
                                        <li
                                            key={pgNumber}
                                            className="list-none"
                                        >
                                            <button
                                                onClick={() =>
                                                    setCurrentPage(pgNumber)
                                                }
                                                className={`text-lg border-solid border-[0.5px] w-8 border-slate-500 cursor-pointer rounded-md mx-1
                                                                ${
                                                                    currentPage ===
                                                                    pgNumber
                                                                        ? ' bg-app-dark text-white'
                                                                        : ' bg-white text-app-dark'
                                                                }
                                                        `}
                                            >
                                                {pgNumber}
                                            </button>
                                        </li>
                                    ))}
                                    <li className="list-none text-3xl flex justify-center items-center px-1">
                                        ...
                                    </li>
                                    <li className="list-none">
                                        <button
                                            onClick={() =>
                                                setCurrentPage(numOfPages)
                                            }
                                            className={`text-lg border-solid border-[0.5px] w-8 border-slate-500 cursor-pointer rounded-md mx-1
                                                        ${
                                                            currentPage ===
                                                            numOfPages
                                                                ? ' bg-app-dark text-white'
                                                                : ' bg-white text-app-dark'
                                                        }
                                                `}
                                        >
                                            {numOfPages}
                                        </button>
                                    </li>
                                </>
                            )}
                        </>
                    ) : (
                        <>
                            {pageNumbers.map((pgNumber) => (
                                <li key={pgNumber} className="list-none">
                                    <button
                                        onClick={() => setCurrentPage(pgNumber)}
                                        className={`text-lg border-solid border-[0.5px] w-8 border-slate-500 cursor-pointer rounded-md mx-1
                                                        ${
                                                            currentPage ===
                                                            pgNumber
                                                                ? ' bg-app-dark text-white'
                                                                : ' bg-white text-app-dark'
                                                        }
                                                `}
                                    >
                                        {pgNumber}
                                    </button>
                                </li>
                            ))}
                        </>
                    )}
                </ul>
                <IconButton
                    onClick={() => {
                        setCurrentPage(currentPage + 1)
                    }}
                    disabled={currentPage === numOfPages ? true : false}
                    className="p-1 pr-0 rounded-none"
                >
                    <FaAngleRight
                        size={24}
                        className={
                            currentPage === numOfPages
                                ? `text-slate-500`
                                : 'text-app-dark'
                        }
                    />
                </IconButton>
                <IconButton
                    onClick={() => {
                        setCurrentPage(numOfPages)
                    }}
                    disabled={currentPage === numOfPages ? true : false}
                    className="p-1 pl-0 rounded-none"
                >
                    <FaAngleDoubleRight
                        size={24}
                        className={
                            currentPage === numOfPages
                                ? `text-slate-500`
                                : 'text-app-dark'
                        }
                    />
                </IconButton>
                <Typography className="mr-2 ml-4 text-base">
                    Go To Page:
                </Typography>
                <Box className="bg-white w-[40px] px-2 rounded-sm border-solid border-[0.5px] border-black">
                    <InputBase
                        type="number"
                        inputProps={{ min: '1', max: numOfPages }}
                        onChange={(e) => {
                            setPageInput(e.target.value)
                        }}
                    />
                </Box>
                <Button
                    onClick={() => {
                        if (pageInput > numOfPages) {
                            setCurrentPage(numOfPages)
                        } else if (pageInput < 1) {
                            setCurrentPage(1)
                        } else {
                            setCurrentPage(parseInt(pageInput))
                        }
                    }}
                    variant="text"
                    className="text-app-dark hover:bg-transparent mx-2 normal-case"
                >
                    <Typography>Go</Typography>
                    <FaAngleDoubleRight size={18} className="ml-1" />
                </Button>
            </Box>
        </Box>
    )
}

export default Pagination
