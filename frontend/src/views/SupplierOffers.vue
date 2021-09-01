<template>
    <div id="orders-main">
        <v-row>
        </v-row>
        <v-row>
                <v-card
                id="orders-card"
                elevation="2">
                    <v-card-title>Orders</v-card-title>
                    <v-card-text>
                        <div id="orders-table">
                            <v-radio-group v-model="filter" row >
                                <v-radio
                                    @change="getOffers()"
                                    label="All"
                                    value="ALL"
                                ></v-radio>
                                <v-radio
                                    @change="getOffers()"
                                    label="Created"
                                    value="CREATED"
                                ></v-radio>
                                <v-radio
                                    @change="getOffers()"
                                    label="Accepted"
                                    value="ACCEPTED"
                                ></v-radio>
                                <v-radio
                                    @change="getOffers()"
                                    label="Rejected"
                                    value="REJECTED"
                                ></v-radio>
                            </v-radio-group>
                            <v-data-table :headers="headers"
                                            :items="offers"
                                            :search="search"
                                            single-select>
                                <template v-slot:item="row">
                                    <tr>
                                        <td>{{row.item.id}}</td>
                                        <td>{{row.item.price}}</td>
                                        <td>{{row.item.dateOfDelivery}}</td>
                                        <td>{{row.item.orderStatus}}</td>
                                                              
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
                filter: 'ALL',
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
            getOffers: function() {
                if(this.filter === 'ALL') {
                    this.getAllOffers();
                } else {
                    this.getFiltered();
                }
            },
            getFiltered: function() {
                this.axios.get("api/offer/supplier/filter", this.filter, {headers:{"Authorization": "Bearer " + this.currentUser}})
                .then(r =>
                {
                    this.offers = r.data._embedded.offers;
                })
                .catch(r => 
                {
                    console.log(r.data);
                })
            },
            getAllOffers: function() {
                this.axios.get("api/offer/supplier", {headers:{"Authorization": "Bearer " + this.currentUser}})
                .then(r =>
                {
                    this.offers = r.data._embedded.offers;
                })
                .catch(r => 
                {
                    console.log(r.data);
                })
            },
        },
        mounted() {
            this.getOffers();
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