module.exports = {
    outputDir: '../target/classes/static',
    productionSourceMap: false,
    devServer: {
        port: 8081,
        proxy: 'http://localhost:8080'
    }
};
