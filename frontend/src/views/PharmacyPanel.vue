<template>
    <div id="pharmacist-main">
        <main-navigation>
            <router-link :to="'/pharmacy/' + this.$route.params.id">Home</router-link>
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
                title: "Pharmacy panel",
            }
        }
    }
</script>

<style scoped>
    #main-content {
        background-color: #fafafa;
    }
</style>