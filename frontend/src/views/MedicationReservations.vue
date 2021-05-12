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
                    <v-btn id="search-reservation-btn" elevation="2" @click="addMedication" class="deep-orange white--text">
                        <v-icon left>
                            mdi-database-search
                        </v-icon>
                        Search
                    </v-btn>
                </v-row>
                <v-alert v-if="false"
                    dense
                    type="warning">
                    Wrong reservation number! Please search again
                </v-alert>
                <v-alert
                    dense
                    type="success">
                    Reservation found
                </v-alert>
                <table style="width:100%">
                    <tr>
                        <td><b>Medication:</b></td>
                        <td>Brufen</td>
                        <td><b>Name:</b></td>
                        <td>Radovan</td>
                    </tr>
                    <tr>
                        <td><b>Dosage:</b></td>
                        <td>400mg</td>
                        <td><b> Surname:</b></td>
                        <td>Zupunski</td>
                    </tr>
                    <tr>
                        <td><b>Quantity:</b></td>
                        <td>5</td>
                        <td><b>Email:</b></td>
                        <td>radovan.zupunski@gmail.com</td>
                    </tr>
                </table>     
            </v-card-text>
            <v-card-actions>
                <v-btn id="dispensing-medication" elevation="2" @click="dispensing" class="deep-orange white--text">
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
            }
        },
    }

</script>

<style scoped>
    #reservations-main{
        display:grid;
        grid-template-columns:auto;
        place-items: center;
        min-height: 100%;
        background: rgb(50,74,94);
        background: linear-gradient(90deg, rgba(63,81,181,1) 5%, rgba(197,202,233,1) 100%); 
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