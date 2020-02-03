import axios from 'axios'
export default axios.create({
    baseURL: "http://192.168.31.122:8197/api",
    headers: {
       "Content-type": "application/json",
    }
  });