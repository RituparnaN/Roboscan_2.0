import create from 'zustand';
import { fetchAllData } from 'services/dataService/dataService';
import { mountStoreDevtool } from 'simple-zustand-devtools';


export const useSectionData = create((set)=>({

    sectionDetails: [],
    headerDetails: {},
    optionDetails: {},
    loading: true,

    fetchSections: async () => {

        const queryParams = new URLSearchParams(window.location.search)
        const userCode = queryParams.get('userRole');
        const caseNo = queryParams.get('CaseNo');

        await fetchAllData(caseNo, userCode).then(res=>{
            if(res.status === 200){
                set((state)=>({sectionDetails: Object.values(res.data.sectionsData.sectionDetails)}))
                set((state)=>({headerDetails: res.data.headerOptionData.header}))
                set((state)=>({optionDetails: res.data.headerOptionData.option}))
                set((state)=>({loading: false}))
            }
        })
    },

    clearSections: () => set({sectionDetails: []}),
}))

if(process.env.NODE_ENV === 'development'){
    mountStoreDevtool('Section Store', useSectionData);
}
