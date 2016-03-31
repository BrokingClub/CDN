var elixir = require('laravel-elixir');

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