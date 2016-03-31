var elixir = require('laravel-elixir');
elixir.assetsPath = 'src/main/resources/assets';
elixir.publicPath = 'src/main/public';

require('laravel-elixir-webpack');

elixir(function(mix) {
    mix.sass('app.scss');
    mix.webpack('app.js');
    mix.browserSync({
        proxy: 'alonian.dev',
        ui: false,
        ghostMode: false
    });
});