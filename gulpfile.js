var elixir = require('laravel-elixir');
var webpackConfig = require('./webpack.config');
elixir.config.assetsPath = 'src/main/resources/assets';
elixir.config.publicPath = 'public';

require('laravel-elixir-webpack');

elixir(function(mix) {
    mix.sass('app.scss');
    mix.webpack('app.js', webpackConfig);
    mix.browserSync({
        proxy: 'localhost:9000',
        ui: false,
        ghostMode: false
    });
});