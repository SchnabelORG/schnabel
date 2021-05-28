<template>
    <div id="reservations-main">
        <v-card 
        min-width="50%">
            <v-card-title class="primary secondary--text">
                Medication Reservations
            </v-card-title>
            <v-card-text>
                <v-row>
                    <v-text-field
                        id="reservation-number-field"
                        v-model="reservationsNumber"
                        label="Reservation number"
                    
                    ></v-text-field>
                    <v-btn id="search-reservation-btn" elevation="2" @click="getReservation" class="accent white--text">
                        <v-icon left>
                            mdi-database-search
                        </v-icon>
                        Search
                    </v-btn>
                </v-row>
                <v-alert v-if="showInfo"
                    dense
                    type="info">
                    Search for drug reservation
                </v-alert>
                <v-alert v-else-if="!doesReservationExists"
                    dense
                    type="warning">
                    Wrong reservation number! Please try again
                </v-alert>
                <v-alert
                    v-else
                    dense
                    type="success">
                    Reservation found
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
                <v-btn id="dispensing-medication" :disabled="!doesReservationExists" elevation="2" @click="dispensing" class="accent white--text">
                        <v-icon left>
                            mdi-hand-heart
                        </v-icon>
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
                showInfo: true,

            }
        },
        methods: {
            getReservation: function(){
                this.axios.get("api/dreservation/" + this.reservationsNumber)
                    .then(response =>
                    {
                        this.drugReservation = response.data;
                        var today = new Date();
                        var date = new Date(this.drugReservation.endOfReservation);
                        if(today < date && !this.drugReservation.taken)
                            this.doesReservationExists = true;
                        else
                            this.doesReservationExists = false;

                        this.showInfo = false;
                    })
                    .catch(response =>
                    {
                        this.doesReservationExists = false;
                        this.showInfo = false;
                        console.log(response.data);
                    });
            },
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
    #dispensing-medication{
         width: 100%;
    }
</style>