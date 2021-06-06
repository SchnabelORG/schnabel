<template>
    <div id="dermatologists-main">
        <v-card
        id="dermatologists-card"
        elevation="2">
            <v-card-title>Availability requests</v-card-title>
            <v-card-text>
                <div id="dermatologists-table">
                    <v-data-table :headers="headers"
                                    :items="requests"
                                    :search="search">
                        <template v-slot:item="row">
                            <tr>
                                <td>{{row.item.name}}</td>
                                <td>{{row.item.surname}}</td> 
                                <td>{{row.item.drugName}}</td>
                                <td>{{row.item.quantity}}</td>                  
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
                requests: [],
                pharmacyId: '',
                headers: [
                    { text: "Name" },
                    { text: "Surname" },
                    { text: "Drug name" },
                    { text: "Quantity" },
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
                            this.getRequests();
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
            getRequests: function() {
                this.axios.get("api/availabilityrequest/pharmacy/" + this.pharmacyId)
                        .then(response => {
                            this.requests = response.data._embedded.availabilityrequests;
                        })
                        .catch(response => {
                            console.log("Failed to get availability requests", response.data);
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