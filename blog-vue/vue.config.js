const {defineConfig} = require('@vue/cli-service')
const AutoImport = require('unplugin-auto-import/webpack')
const Components = require('unplugin-vue-components/webpack')
const {ElementPlusResolver} = require('unplugin-vue-components/resolvers')
const CompressionWebpackPlugin = require('compression-webpack-plugin')    //引入插件
const productionGzipExtensions = ['js', 'css']      //压缩js 和css文件


module.exports = defineConfig({
    transpileDependencies: true,
    lintOnSave: false,
    devServer: {
        proxy: {
            '/proxy': {
                target: process.env.VUE_APP_BACKEND_TARGET_BASEURL,
                changeOrigin: true,
                pathRewrite: {'^/proxy': ''},
            },
            'backend': {
                target: process.env.VUE_APP_BACKEND_TARGET_BASEURL,
                changeOrigin: true,
                pathRewrite: {'^/backend': ''},
            },
            'api': {
                target: process.env.VUE_APP_BACKEND_TARGET_BASEURL,
                changeOrigin: true,
                pathRewrite: {'^/api': ''},
            }
        }
    },
    configureWebpack: {
        plugins: [
            AutoImport({
                resolvers: [ElementPlusResolver()],
            }),
            Components({
                resolvers: [ElementPlusResolver()],
            }),
            // 打包时压缩
            // new CompressionWebpackPlugin({
            //         filename: '[path][base].gz', //压缩后的文件名
            //         algorithm: 'gzip', //压缩算法/功能
            //         test: /\.(js|css)(\?.*)?$/i, //匹配项目文件，以js或者以css结尾的才执行压缩
            //         threshold: 10240, //仅处理大于此大小的资产（以字节为单位）
            //         minRatio: 0.8, //仅压缩比该比率更好的资产（minRatio = Compressed Size / Original Size）
            //         deleteOriginalAssets: true //是否删除原始文件
            //     }
            // ),
        ]
    }
})
