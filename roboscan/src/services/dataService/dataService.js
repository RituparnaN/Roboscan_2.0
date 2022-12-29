import httpService from "services/httpService/httpService";

export const fetchAllData = (caseNo, userCode) => {
    return new Promise((resolve, reject)=>{
        httpService.get('/api/roboscanapi/fetchRoboscanData', {
            params: {
                CaseNo: caseNo,
                userRole: userCode,
            }
        })
        .then(res=>{
            if(res.status === 200){
                resolve(res)
            }
            else{
                reject(res.data.err)
            }
        })
    })
}