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
                id: '',
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
            getAppointments: function() {
                this.id = this.$route.params.id;
                this.axios.get("api/appointment/dermatology/pharmacy/" + this.id)
                        .then(response => {
                            this.appointments = response.data._embedded.appointments;
                        })
                        .catch(response => {
                            console.log("Failed to get appointments", response.data);
                        });
            },
        },
        mounted() {
            this.getAppointments();
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