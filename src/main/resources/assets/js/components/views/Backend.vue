<template>
    <div class="row">
        <div class="col-xs-12 col-sm-8 col-md-6 col-lg-4 center-block">
            <h1>Backend</h1>
            <hr>
            <div class="form-group">
                <label for="name">Benutzername</label>
                <input id="name" type="text" class="form-control" placeholder="Benutzername" v-model="name">
            </div>
            <div class="form-group">
                <label for="password">Passwort</label>
                <input id="password" type="password" class="form-control" placeholder="Passwort" v-model="password">
            </div>
            <hr>
            <button type="button" class="btn btn-primary btn-block" @click="login()">Anmelden</button>
        </div>
    </div>
</template>