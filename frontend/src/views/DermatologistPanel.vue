<template>
    <div id="dermatologist-main">
        <main-navigation>
            <router-link to="/dermatologist/">Home</router-link>
            <router-link to="/dermatologist/dermatologistacc">Account</router-link>
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
                title: "Dermatologist panel",
            }

        }
    
    }
</script>

<style scoped>
    /* #pharmacist-main {
        display: grid;
        grid-template-columns: minmax(150px, 15vw) 1fr;
    } */
    #main-content {
        background-color: #fafafa;
    }
</style>