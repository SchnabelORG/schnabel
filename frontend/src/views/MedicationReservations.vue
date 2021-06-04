<template>
    <div id="reservations-main">
        <v-card 
        min-width="50%">
            <v-card-title class="primary secondary--text">
                Medication Reservations
            </v-card-title>
            <v-card-text>
                <v-row id="reservation-number-row">
                    <v-text-field
                        id="reservation-number-field"
                        v-model="reservationsNumber"
                        label="Reservation number"
                    ></v-text-field>
                    <v-btn id="search-reservation-btn" elevation="2" @click="getReservation" class="accent white--text">
                         <i class="fa fa-database fa-fw"></i>
                        Search
                    </v-btn>
                </v-row>
                <v-alert
                    dense
                    :type=alertType>
                    {{alertMessage}}
                </v-alert>
                <table v-if="doesReservationExists" style="width:100%">
                    <tr>
                        <td><b>Medication:</b></td>
                        <td>{{drugReservation.drugName}}</td>
                        <td><b>Name:</b></td>
                        <td>{{drugReservation.patientName}}</td>
                    </tr>
                    <tr>
                        <td><b>Reservation date:</b></td>
                        <td>{{drugReservation.reservationDate.slice(0, 10)}}</td>
                        <td><b> Surname:</b></td>
                        <td>{{drugReservation.patientSurname}}</td>
                    </tr>
                    <tr>
                        <td><b>Quantity:</b></td>
                        <td>{{drugReservation.quantity}}</td>
                        <td><b>Email:</b></td>
                        <td>{{drugReservation.patientEmail}}</td>
                    </tr>
                </table>     
            </v-card-text>
            <v-card-actions>
                <v-btn id="dispensing-medication" :disabled="!doesReservationExists" elevation="2" @click="dispensing()" class="accent white--text">
                        Dispensing
                    </v-btn>
            </v-card-actions>
        </v-card>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                reservationsNumber: '',
                drugReservation: {},
                doesReservationExists: false,
                pharmacist: {},
                alertType: 'info',
                alertTypes: ['info', 'warning', 'success'],
                alertMessage: 'Search for drug reservation',
            }
        },
        methods: {
            refreshToken: async function() {
                let jws = window.localStorage.getItem('jwt');
                if(!jws) {
                    this.$router.push("/");
                }
                return this.axios.get("/api/auth/refresh", {headers: {"Authorization": "Bearer " + jws}});
            },
             getPharmacist: function() {
                console.log("Getting pharmacist");
                let jws = window.localStorage.getItem('jwt');
                console.log(jws)
                this.axios.get("api/pharmacist", {headers:{"Authorization": "Bearer " + jws}})
                    .then(response => {
                        console.log(response.data);
                        this.pharmacist = response.data;
                    })
                    .catch(response => {
                        console.log("Failed to get patient", response.data);
                        this.refreshToken()
                            .then(response => {
                                window.localStorage.jwt = response.data;
                                this.$router.go();
                            })
                            .catch(response => {
                                console.log(response.data);
                                this.$router.push("/");
                            });
                    });
            },
            getReservation: function(){
                let jws = window.localStorage.getItem('jwt');
                this.axios.get("api/dreservation/" + this.reservationsNumber + "/" +this.pharmacist.pharmacy.id, {headers: {"Authorization": "Bearer " + jws}})
                    .then(response =>
                    {
                        this.drugReservation = response.data;
                        var today = new Date();
                        var date = new Date(this.drugReservation.endOfReservation);
                        if(today < date){
                            this.doesReservationExists = true;
                            this.alertType = 'success';
                            this.alertMessage = 'Reservation found';
                        }else{
                            this.alertType = 'warning';
                            this.alertMessage = 'Wrong reservation number! Please try again!';
                        }
                    })
                    .catch(response =>
                    {
                        this.alertType = 'warning';
                        this.alertMessage = 'Wrong reservation number! Please try again!';
                        console.log(response.data);
                    });
            },
            dispensing: function(){
                let jws = window.localStorage.getItem('jwt');
                this.axios.get("api/dreservation/dispensing/" + this.drugReservation.id, {headers: {"Authorization": "Bearer " + jws}})
                    .then(response =>
                    {
                        console.log(response.data)
                        this.reservationsNumber = '';
                        this.doesReservationExists = false;
                        this.alertType = 'success';
                        this.alertMessage = 'Reservation number ' + this.drugReservation.id + ' successfully dispensed';
                        this.drugReservation = {};
                    })
                    .catch(response =>
                    {
                        console.log(response.data);
                        this.alertType = 'warning';
                        this.alertMessage = 'Something went wrong! Please try again!';
                    });
            }
        },
        mounted(){
            this.getPharmacist();
        }
    }

</script>

<style scoped>
    #reservations-main{
        display:grid;
        grid-template-columns:auto;
        place-items: center;
        min-height: 92vh;
        background-color: #fbecdd;
    }
    #search-reservation-btn{
        margin: 0;
        margin-top: 1.5%;
        width: 15%;
    }
    #reservation-number-field{
        width: 80%;
    }
    #reservation-number-row{
        margin-top: 1%;

    }
    #dispensing-medication{
         width: 100%;
    }
</style>