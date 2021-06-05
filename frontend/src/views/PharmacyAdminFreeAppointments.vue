<template>
    <div id="dermatologists-main">
        <v-card
        id="dermatologists-card"
        elevation="2">
            <v-card-title>Free dermatologist appointments</v-card-title>
            <v-card-text>
                <div id="dermatologists-table">
                    <v-data-table :headers="headers"
                                    :items="appointments"
                                    :search="search">
                        <template v-slot:item="row">
                            <tr>
                                <td>{{row.item.medicalEmployee.name}}</td>
                                <td>{{row.item.medicalEmployee.surname}}</td> 
                                <td>{{row.item.date}}</td>
                                <td>{{row.item.start}}</td>
                                <td>{{row.item.duration}}</td>
                                <td>{{row.item.price}}</td>                      
                            </tr>
                        </template>
                    </v-data-table>
                </div>
            </v-card-text>
        </v-card>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                appointments: [],
                pharmacyId: '',
                headers: [
                    { text: "Name" },
                    { text: "Surname" },
                    { text: "Date" },
                    { text: "Start" },
                    { text: "Duration"},
                    { text: "Price" },
                ],
            }
        },
        methods: {
            getPharmacyAdmin: function() {
                this.refreshToken().then(response => {
                    localStorage.jws = response.data;
                    this.axios.get("api/pharmacyadmin", {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                        .then(response => {
                            this.pharmacyId = response.data.pharmacy.id;
                            this.getAppointments();
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
            getAppointments: function() {
                this.axios.get("api/appointment/dermatology/pharmacy/" + this.pharmacyId)
                        .then(response => {
                            this.appointments = response.data._embedded.appointments;
                        })
                        .catch(response => {
                            console.log("Failed to get appointments", response.data);
                        });
            },
        },
        mounted() {
            this.getPharmacyAdmin();
        },
    }
</script>

<style scoped>
    #dermatologists-main {
        display:grid;
        grid-template-columns:auto;
        place-items: center;
        min-height: 92vh;
        background-color: #fbecdd;
    }
</style>