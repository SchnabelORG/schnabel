<template>
    <div id="pharmacist-main">
<<<<<<< HEAD
        <sidebar title="Pharmacist panel" user="">
            <li><router-link to="/pharmacist/">Home</router-link></li>
            <li><router-link to="/pharmacist/account">My account</router-link></li>
            <li><router-link to="/pharmacist/calendar">My Calendar</router-link></li>
            <li><router-link to="/pharmacist/consultationReport">New consultation</router-link></li>
            <li><router-link to="/pharmacist/medicationReservations">Medication Reservations</router-link></li>
        </sidebar>
=======
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

>>>>>>> develop
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
    #pharmacist-main {
        display: grid;
        grid-template-columns: minmax(150px, 15vw) 1fr;
    }
    #main-content {
        min-height: 100vh;
        background-color: #fafafa;
    }
</style>