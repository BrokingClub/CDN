module.exports = {
    externals: {
        vue: 'Vue',
        'vue-router': 'VueRouter'
    },
    module: {
        loaders: [
            {
                test: /\.js$/,
                exclude: /node_modules/,
                loader: 'babel-loader'
            },
            {
                test: /\.vue$/,
                loader: 'vue'
            },
            {
                test: /\.png$/,
                loader: 'file-loader'
            }
        ]
    },
    devtool: 'source-map'
};