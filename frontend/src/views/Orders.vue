<template>
    <div id="orders-main">
        <v-card
        id="orders-card"
        elevation="2">
            <v-card-title>Order</v-card-title>
            <v-card-text>
                <div id="orders-table">
                    <v-data-table :headers="orderHeaders"
                                    :items="orders"
                                    :search="search">
                        <template v-slot:item="row">
                            <tr>
                                <td>{{row.item.description}}</td>
                                <td>{{row.item.deadline}}</td>
                                <td>                              
                                    <v-btn @click="showOffers(row.item.id)">
                                        Show
                                    </v-btn>
                                </td>
                            </tr>
                        </template>
                    </v-data-table>
                </div>
            </v-card-text>
        </v-card>
        <v-dialog v-model="this.dialog" persistent>
            <v-card id="offers-card">
                <v-card-title>Offers</v-card-title>
                <v-spacer></v-spacer>
                    <v-data-table :headers="offerHeaders"
                                    :items="offers"
                                    :search="search">
                        <template v-slot:item="row">
                            <tr>
                                <td>{{row.item.supplier}}</td>
                                <td>{{row.item.price}}</td>
                                <td>{{row.item.dateOfDelivery}}</td>
                                <td>                              
                                    <v-btn @click="acceptOffer(row.item.id)">
                                        Accept
                                    </v-btn>
                                </td>
                            </tr>
                        </template>
                    </v-data-table>
                    <v-btn class="primary" @click="disableDialog()">
                        Back
                    </v-btn>
            </v-card>
        </v-dialog>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                orders: [{description: "dfgf"}],
                offers: [{supplier: "Pera"}],
                dialog: false,
                id: '',
                orderHeaders: [
                    { text: "Description" },
                    { text: "Deadline" },
                    { text: "Show offers" },
                ],
                offerHeaders: [
                    { text: "Supplier" },
                    { text: "Price" },
                    { text: "Delivery" },
                    { text: "Accept" },
                ],
            }
        },
        methods: {
            getOrders: function() {
                


            },
            showOffers: function(id) {
                this.offers = [];
                this.id = id;
                
                this.dialog = true;
            },
            acceptOffer: function(id) {
                this.dialog = false;
                this.id = id;
            },
            disableDialog: function() {
                this.dialog = false;
            },
        },
        mounted() {
            this.getOrders();
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
    #orders-card {
        display: flex;
        flex-direction: column;
        margin: 0 auto;
        width: 40%;
    }
    #orders-main {
        display: flex;
        flex-direction: column;
        justify-content: center;
        width: 100vw;
        min-height: 100vh;
    }
    #offers-card {
        display:grid;
        grid-template-columns:auto;
        margin: 0 auto;
        width: 40%;
    }
</style>