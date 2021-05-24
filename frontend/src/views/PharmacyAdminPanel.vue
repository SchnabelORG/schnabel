<template>
    <div id="main-home">
        <main-navigation>
        <router-link to="/pharmacyadmin">Home</router-link>
        <router-link to="/pharmacyadmin/account">Account</router-link>
        </main-navigation>
        <div id="main-content">
            <router-view/>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                title: "PharmacyAdmin panel",
            }
        }
    }
</script>

<style scoped>
    #main-content {
        min-height: 100vh;
        background-color: #fafafa;
    }
</style>