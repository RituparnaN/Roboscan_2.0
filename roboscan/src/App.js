import React, { useState } from 'react'
import { Grid } from '@mui/material'
import { GeneralContainer } from './components/containers'
import { sections } from './data/dummyData'
import Button from './components/common/Button'

function App() {
    const [expanded, setExpanded] = useState([])

    const sizeCondition = (section, size) => {
        if (
            section.type === 'table' ||
            section.type === 'upload' ||
            section.id === 1 ||
            expanded.includes(section.id)
        ) {
            return 12
        } else if (expanded.includes(section.id + 1)) {
            return true
        } else return size === 'lg' ? 4 : size === 'md' ? 6 : 12
    }

    return (
        <Grid
            container
            className="h-[calc(100vh-79px)] px-2 py-8 overflow-auto"
        >
            {sections &&
                sections.map((sec, index) => (
                    <Grid
                        item
                        xs={sizeCondition(sec, 'xs')}
                        md={sizeCondition(sec, 'md')}
                        lg={sizeCondition(sec, 'lg')}
                        key={sec.id}
                        className="transition-all duration-500"
                    >
                        <GeneralContainer
                            expandable={sec.expandable}
                            title={sec.name}
                            sectionId={sec.id}
                            key={sec.id}
                            type={sec.type}
                            graphType={sec.graph}
                            hasGraph={sec.hasGraph}
                            hasForm={sec.hasForm}
                            hasTable={sec.hasTable}
                            formData={sec.formData || []}
                            expanded={expanded}
                            setExpanded={setExpanded}
                            {...sec}
                        >
                            Other Container
                        </GeneralContainer>
                    </Grid>
                ))}
            <Grid item xs={12} className="flex justify-end my-5 px-2">
                <Button variants="primary" className="mr-3">
                    Scan
                </Button>
                <Button variants="secondary">View Match</Button>
            </Grid>
        </Grid>
    )
}

export default App
