import React, { useState } from 'react'
import { Box, MenuList, MenuItem, Tooltip, tooltipClasses } from '@mui/material'
import { BsCardText } from 'react-icons/bs'
import { sections } from '../../data/dummyData'
import { styled } from '@mui/material/styles'

const CustomTooltip = styled(({ className, ...props }) => (
    <Tooltip {...props} arrow classes={{ popper: className }} />
))(({ theme }) => ({
    [`& .${tooltipClasses.tooltip}`]: {
        backgroundColor: '#e0e0e0',
        color: '#1e212c',
        padding: '15px 20px',
        fontSize: '16px',
        fontWeight: 'bold',
    },
    [`& .${tooltipClasses.arrow}`]: {
        color: '#e0e0e0',
    },
}))

const SideNav = () => {
    const [selected, setSelected] = useState([])

    const handleSelect = (sec) => {
        if (selected.filter((e) => e === sec.id).length === 0) {
            setSelected([...selected, sec.id])
            return
        }
        if (selected.filter((e) => e === sec.id).length > 0) {
            setSelected(selected.filter((e) => e !== sec.id))
            return
        }
    }

    return (
        <Box className="h-[calc(100vh-78px)] overflow-auto bg-app-dark">
            <MenuList className="px-auto py-0">
                {sections.map((sec) => (
                    <CustomTooltip
                        placement={'right'}
                        key={sec.id}
                        title={sec.name}
                    >
                        <MenuItem
                            className={
                                'flex justify-center items-center my-3 hover:bg-[#e0e0e0e0]  hover:text-app-dark rounded-full w-12 h-12 mx-auto hover:scale-110 transition-all p-3' +
                                ' ' +
                                (selected.filter((e) => e === sec.id).length > 0
                                    ? 'bg-[#e0e0e0] text-app-dark scale-110'
                                    : 'text-white')
                            }
                            onClick={() => handleSelect(sec)}
                        >
                            <BsCardText size={24} />
                        </MenuItem>
                    </CustomTooltip>
                ))}
            </MenuList>
        </Box>
    )
}

export default SideNav
