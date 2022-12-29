import React, { useState, useEffect } from 'react'
import { Grid, CircularProgress } from '@mui/material'
import { GeneralContainer } from './components/containers'
import ExecutiveSummary from 'components/modules/Header/ExecutiveSummary'
// import { sections } from './data/dummyData'
import { useSectionData } from 'store/store'

function App(props) {
    
    const {expanded, setExpanded} = props

    const allSections = useSectionData(state=>state.sectionDetails);
    const headerDetails = useSectionData(state=>state.headerDetails);
    const loading = useSectionData(state=>state.loading)

    const fetchAllData = useSectionData((state)=>state.fetchSections)
    const clearSectionData = useSectionData((state)=>state.clearSections)

    useEffect(() => {
        if(allSections.length<1 ){
            fetchAllData()
        }
    }, [allSections, fetchAllData]);

    useEffect(()=> {
        window.addEventListener("beforeunload", ()=>{
            clearSectionData()
        })
    })

    const sizeCondition = (section, size) => {
        if (
            section.type === 'upload' ||
            expanded.includes(section.id)
        ) {
            return 12
        } else if(section.type === 'table') {
            return 8
        } else if (expanded.includes(section.id + 1)) {
            return true
        } else if (section.id !== allSections.length && allSections.filter(e=>e.id === section.id + 1)[0].type==='table') {
            return true
        } else return size === 'lg' ? 4 : size === 'md' ? 6 : 12
    }

    return (
        <Grid
            container
            className="h-[calc(100vh-79px)] px-2 py-8 overflow-auto"
        >
            {loading===true?(
                <>
                    <Grid item xs={12} className="flex justify-center items-center" >
                        <CircularProgress size={60} color={'inherit'} />
                    </Grid>
                </>
            ):(
                <>
                    <Grid item xs={12}>
                        <ExecutiveSummary summaryDetails={headerDetails} />
                    </Grid>
                    {allSections.length > 0 &&
                        allSections.map((sec, index) => (
                            <Grid
                                item
                                xs={sizeCondition(sec, 'xs')}
                                md={sizeCondition(sec, 'md')}
                                lg={sizeCondition(sec, 'lg')}
                                key={sec.id}
                                className="transition-all duration-500"
                                id={`section-${sec.id}`}
                            >
                                <GeneralContainer
                                    expandable={sec.expandable}
                                    title={sec.name}
                                    sectionId={sec.id}
                                    key={sec.id}
                                    type={sec.type}
                                    graphDetails={sec.graphDetails}
                                    hasGraph={sec.hasGraph}
                                    hasForm={sec.hasForm}
                                    hasTable={sec.hasTable}
                                    formDetails={sec.formDetails || []}
                                    tableDetails={sec.tableDetails || {HEADER:[], DATA:[]}}
                                    expanded={expanded}
                                    setExpanded={setExpanded}
                                    {...sec}
                                >
                                    Other Container
                                </GeneralContainer>
                            </Grid>
                        ))
                    }
                </>
            )}
            
            {/* <Grid item xs={12} className="flex justify-end my-5 px-2">
                <Button variants="primary" className="mr-3">
                    Scan
                </Button>
                <Button variants="secondary">View Match</Button>
            </Grid> */}
        </Grid>
    )
}

export default App
