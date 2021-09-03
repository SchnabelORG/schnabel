<template>
    <div id="orders-main">
        <v-row>
        </v-row>
        <v-row>
                <v-card
                id="orders-card"
                elevation="2">
                    <v-card-title>Offers</v-card-title>
                    <v-card-text>
                        <div id="orders-table">
                            <v-row>
                                <v-btn
                                    @click="getAllOffers()"
                                >
                                    All
                                </v-btn>
                                <v-btn
                                    @click="getCreated()"
                                >
                                    CREATED
                                </v-btn>
                                <v-btn
                                    @click="getRejected()"
                                >
                                    REJECTED
                                </v-btn>
                                <v-btn
                                    @click="getAccepted()"
                                >
                                    ACCEPTED
                                </v-btn>
                            </v-row>
                            <v-data-table :headers="headers"
                                            :items="offers"
                                            :search="search"
                                            single-select>
                                <template v-slot:item="row">
                                    <tr>
                                        <td>{{row.item.id}}</td>
                                        <td>{{row.item.price}}</td>
                                        <td>{{row.item.dateOfDelivery}}</td>
                                        <td>{{row.item.offerStatus}}</td>
                                        <td>
                                            <v-btn v-if="editable(row.item.id)"
                                            >
                                                Edit
                                            </v-btn>
                                        </td>
                                                              
                                    </tr>
                                </template>
                            </v-data-table>
                        </div>
                    </v-card-text>
                </v-card>
        </v-row>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                offers: [],
                order: {},
                filterby: 'ALL',
                new: false,
                menu: false,
                time: {},
                id: '',
                search: '',
                headers: [
                    { text: "Id" },
                    { text: "Price" },
                    { text: "Date Of Delivery" },
                    { text: "Order Status" },
                ],
                headers2: [
                    { text: "Drug" },
                    { text: "Quantity" },
                ],
                rules: {
                    required: value => !!value || 'Required.',
                },
            }
        },
        computed:{
            currentUser() {
                    return this.$store.state.auth.user;
                }
        },
        methods: {
            getCreated: function() {
                this.axios.get("api/offer/supplier/filter/CREATED",{headers:{"Authorization": "Bearer " + this.currentUser}})
                .then(r =>
                {
                    this.offers = [];
                    this.offers = r.data._embedded.offers;
                })
                .catch(r => 
                {
                    console.log(r.data);
                })
            },
            getRejected: function() {
                this.axios.get("api/offer/supplier/filter/REJECTED",{headers:{"Authorization": "Bearer " + this.currentUser}})
                .then(r =>
                {
                    this.offers = [];
                    this.offers = r.data._embedded.offers;
                })
                .catch(r => 
                {
                    console.log(r.data);
                })
            },
            getAccepted: function() {
                this.axios.get("api/offer/supplier/filter/ACCEPTED",{headers:{"Authorization": "Bearer " + this.currentUser}})
                .then(r =>
                {
                    this.offers = [];
                    this.offers = r.data._embedded.offers;
                })
                .catch(r => 
                {
                    console.log(r.data);
                })
            },
            getAllOffers: function() {
                console.log("standard")
                this.axios.get("api/offer/supplier", {headers:{"Authorization": "Bearer " + this.currentUser}})
                .then(r =>
                {
                    this.offers = [];
                    this.offers = r.data._embedded.offers;
                })
                .catch(r => 
                {
                    console.log(r.data);
                })
            },
            editable: function(id) {
                console.log(id);
                this.axios.get("api/offer/editable/"+id)
                .then(r =>
                {
                    console.log(r.data);
                    return true;
                })
                .catch(r => 
                {
                    console.log(r.data);
                    return false;
                })
            }
        },
        mounted() {
            this.getAllOffers();
        },
    }
</script>

<style scoped>
    #orders-main {
        display:grid;
        grid-template-columns:auto;
        place-items: center;
        min-height: 92vh;
        background-color: #fbecdd;
    }
    #new-card {
        width: 60%;
        margin: 0 auto;
        justify-content: center;
    }
    #items-card {
        width: 30%;
        margin: 0 auto;
        justify-content: center;
    }
</style>