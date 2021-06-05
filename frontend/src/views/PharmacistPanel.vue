<template>
    <div id="pharmacist-main">
        <main-navigation>
            <router-link to="/pharmacist/">Home</router-link>
            <router-link to="/pharmacist/calendar">Calendar</router-link>
            <router-link to="/pharmacist/consultationReport">Consultation</router-link>
            <router-link to="/pharmacist/medicationReservations">Reservations</router-link>
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
    /* #pharmacist-main {
        display: grid;
        grid-template-columns: minmax(150px, 15vw) 1fr;
    } */
    #main-content {
        min-height: 100vh;
        background-color: #fafafa;
    }
</style>