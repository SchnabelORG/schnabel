<template>
    <v-app>
        <router-view></router-view>
    </v-app>
</template>

<script>
    export default {
        name: 'App'
    };
</script>
<style>
    body {
        font-family: "Poppins", sans-serif !important;
    }
    .v-application {
        font-family: "Poppins", sans-serif !important;
    }

    .v-application > .title {
        font-family: "Poppins", sans-serif !important;
    }

    .err {
        color: #f00;
        font-weight: 500;
    }
</style>