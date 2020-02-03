'use strict'

module.exports = {
devServer: {
    proxy: { // proxyTable 설정
        'login': {
            target: 'https://github.com/login/',
            changeOrigin: true
            }
        }
    }
}