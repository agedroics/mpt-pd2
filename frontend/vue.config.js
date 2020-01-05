module.exports = {
    outputDir: '../target/classes/static',
    productionSourceMap: false,
    devServer: {
        proxy: 'http://localhost:8080'
    }
};
