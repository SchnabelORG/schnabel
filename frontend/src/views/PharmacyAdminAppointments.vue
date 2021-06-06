<template>
    <div id="home-main">
        <v-card id="month-card">
            <v-card-title>Count of appointments by months in this year ({{new Date().getFullYear()}})</v-card-title>
            <v-sparkline
                :value="sparkLineValuesMonth"
                :auto-draw="true"
                :auto-draw-duration="1000"
                color="primary"
                height="100"
                padding="24"
                stroke-linecap="round"
                smooth>
                <template v-slot:label="item">
                {{ item.value }}
                </template>
            </v-sparkline>
        </v-card>
        <v-card id="year-card">
            <v-card-title>Count of appointments by years (previous ({{new Date().getFullYear() - 1}}), this ({{new Date().getFullYear()}}))</v-card-title>
            <v-sparkline
                :value="sparkLineValuesYear"
                :auto-draw="true"
                :auto-draw-duration="1000"
                color="primary"
                height="100"
                padding="24"
                stroke-linecap="round"
                smooth>
                <template v-slot:label="item">
                {{ item.value }}
                </template>
            </v-sparkline>
        </v-card>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                sparkLineValuesMonth: [0,0,0,0,0,0,0,0,0,0,0,0],
                sparkLineValuesYear: [0,0],
                pharmacyId: '',
            }
        },
        methods:{
            getPharmacyAdmin: function() {
                this.refreshToken().then(response => {
                    localStorage.jws = response.data;
                    this.axios.get("api/pharmacyadmin", {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                        .then(response => {
                            this.pharmacyId = response.data.pharmacy.id;
                            this.getCountAppointmentsByMonth();
                            this.getCountAppointmentsByYear();
                        })
                        .catch(response => {
                            console.log("Failed to get pharmacy admin", response.data);
                        });
                   })
                    .catch(response => {
                    console.log(response.data);
                    this.$router.push("/");
                });
            },
            getCountAppointmentsByMonth: function() {
                this.axios.get("api/appointment/pharmacymonth/" + this.pharmacyId)
                    .then(response =>
                    {
                        this.sparkLineValuesMonth = response.data;
                    })
                    .catch(response =>
                    {
                        console.log(response.data);
                    });
            },
            getCountAppointmentsByYear: function() {
                this.axios.get("api/appointment/pharmacyyear/" + this.pharmacyId)
                    .then(response =>
                    {
                        this.sparkLineValuesYear = response.data;
                    })
                    .catch(response =>
                    {
                        console.log(response.data);
                    });
            },
        },
        mounted(){
            this.getPharmacyAdmin();
        },
    }
</script>

<style scoped>
    #home-main {
        display:grid;
        grid-template-columns:auto;
        place-items: center;
        min-height: 92vh;
        background-color: #fbecdd;
    }
    #month-card, #year-card {
        display: flex;
        flex-direction: column;
        margin: 0 auto;
        width: 40%;
    }
    #home-main {
        display: flex;
        flex-direction: column;
        justify-content: center;
        width: 100vw;
        min-height: 100vh;
    }
</style>