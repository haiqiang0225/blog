// axios.js
import axios from 'axios'
import router from '../router/index'

axios.defaults.baseURL = process.env.VUE_APP_AXIOS_BASEURL;

// 携带 cookie
axios.defaults.withCredentials = true

// 请求头，headers 信息
// axios.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'
// axios.defaults.headers['token'] = localGet('token') || ''

// 默认 post 请求，使用 multipart/form-data 形式
axios.defaults.headers.post["Content-Type"] = "multipart/form-data;charset=UTF-8";

// 添加token
let token = localStorage.getItem("token");
if (token) {
    axios.defaults.headers.token = token;
}

// 添加请求拦截器
axios.interceptors.request.use(
    function (config) {
        // 在发送请求之前做些什么
        return config
    },
    function (error) {
        // 对请求错误做些什么
        console.log(error)
        return Promise.reject(error)
    }
)

// 响应拦截器，内部根据返回值，重新组装，统一管理。
axios.interceptors.response.use(res => {
    if (typeof res !== 'object') {
        message.error('服务端异常！');
        console.log('服务端异常！');
        return Promise.reject(res)
    }
    if (!(res.status === 200 || res.status === '200')) {
        if (res) {
            console.error(res.data)
        }
        return Promise.reject(res)
    }

    return res
})

export default axios
