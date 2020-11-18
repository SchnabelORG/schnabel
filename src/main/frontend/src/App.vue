<template>
    <div>
        <v-content transition="slide-x-transition">
            <router-view></router-view>
        </v-content>
    </div>
</template>

<script>
    export default {
        name: 'App'
    };
</script>

<style>
</style>