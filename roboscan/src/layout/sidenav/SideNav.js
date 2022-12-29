import React, { useState } from 'react'
import { Box, MenuList, MenuItem, Tooltip, tooltipClasses } from '@mui/material'
import { BsCardText } from 'react-icons/bs'
// import { sections } from '../../data/dummyData'
import { styled } from '@mui/material/styles'
import getIconByKey from '../../assets'
import HoverImage from 'react-hover-image'
import { useSectionData } from 'store/store'

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

const SideNav = (props) => {

    const {expanded, setExpanded} = props

    const allSections = useSectionData(state=>state.sectionDetails)

    const handleSelect = (sec) => {
        const sectionLocation = document.getElementById(`section-${sec.id}`)
        console.log('Section', sectionLocation)
        if (expanded.filter((e) => e === sec.id).length === 0) {
            setExpanded([...expanded, sec.id])
            if(sectionLocation !== null){
                sectionLocation.scrollIntoView({behavior: 'smooth', block: 'nearest', inline: 'start'})
            }
            return
        }
        if (expanded.filter((e) => e === sec.id).length > 0) {
            setExpanded(expanded.filter((e) => e !== sec.id))
            if(sectionLocation !== null){
                sectionLocation.scrollIntoView({behavior: 'smooth', block: 'nearest', inline: 'start'})
            }
            return
        }

        
    }

    return (
        <Box className="overflow-hidden hover:overflow-auto h-[calc(100vh-78px)] bg-app-dark transition-all">
            <MenuList className="px-auto pb-10">
                {allSections.map((sec) => (
                    <CustomTooltip
                        placement={'right'}
                        key={sec.id}
                        title={sec.name}
                    >
                        <MenuItem
                            className={
                                'flex justify-center items-center my-4 hover:bg-[#e0e0e0e0]  hover:text-app-dark rounded-full w-12 h-12 mx-auto hover:scale-110 transition-all p-3' +
                                ' ' +
                                (expanded.filter((e) => e === sec.id).length > 0
                                    ? 'bg-[#e0e0e0] text-app-dark scale-110'
                                    : 'text-white')
                            }
                            onClick={() => handleSelect(sec)}
                        >
                            {/* <BsCardText size={24} /> */}

                            {sec.icon ? (
                                <>
                                    {expanded.filter((e) => e === sec.id)
                                        .length > 0 ? (
                                        <img
                                            src={getIconByKey(
                                                `${sec.icon}Active`
                                            )}
                                            className="w-[40px] h-auto"
                                            alt={sec.name}
                                        />
                                    ) : (
                                        <HoverImage
                                            src={getIconByKey(
                                                `${sec.icon}Inactive`
                                            )}
                                            hoverSrc={getIconByKey(
                                                `${sec.icon}Active`
                                            )}
                                            className="w-[40px] h-auto"
                                            alt={sec.name}
                                        />
                                    )}
                                </>
                            ) : (
                                <BsCardText size={24} />
                            )}
                        </MenuItem>
                    </CustomTooltip>
                ))}
            </MenuList>
        </Box>
    )
}

export default SideNav
