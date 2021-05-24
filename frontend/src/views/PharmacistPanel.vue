<template>
    <div id="pharmacist-main">
        <main-navigation>
            <router-link to="/pharmacist/">Home</router-link>
            <v-divider vertical></v-divider>
            <router-link to="/pharmacist/calendar">Calendar</router-link>
            <v-divider vertical></v-divider>
            <router-link to="/pharmacist/consultationReport">New consultation</router-link>
            <v-divider vertical></v-divider>
            <router-link to="/pharmacist/medicationReservations">Reservations</router-link>
            <v-divider vertical></v-divider>
            <router-link to="/pharmacist/pharmacistacc">Account</router-link>
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
                title: "Pharmacist panel",
            }
        }
    }
</script>

<style scoped>
    #main-content {
        background-color: #fafafa;
    }
</style>