import request from '@/utils/request'



export default {

     //在统计表中生成统计数据
    create(day) {  
        return request({
            url:`/staService/statistics-daily/getRegisterCount/${day}`,
            method:'get'
        })
    },

    showData(searchObj) {
        return request({
            url:`/staService/statistics-daily/showData/${searchObj.type}/${searchObj.begin}/${searchObj.end}`,
            method:'get'
        }) 
    }

}

