<template>
    <div id="systemadmin-main">
        <main-navigation>
            <router-link to="/systemadmin/">Home</router-link>
            <v-divider vertical></v-divider>

        </main-navigation>
        <div id = "main-contetn">
            <router-view/>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                title: "SystemAdmin panel",
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
