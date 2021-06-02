<template>
    <div id="orders-main">
        <v-card
        id="orders-card"
        elevation="2">
            <v-card-title>Open orders</v-card-title>
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
                                    <v-btn @click="showOffers(row.item)">
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
                                <td>{{row.item.supplier.firm}}</td>
                                <td>{{row.item.price}}</td>
                                <td>{{row.item.dateOfDelivery}}</td>
                                <td>                              
                                    <v-btn @click="acceptOffer(row.item.id)" v-if="check()">
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
                orders: [],
                offers: [],
                dialog: false,
                order: '',
                pharmacyAdmin: '',
                pharmacyId: '',
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
            getPharmacyAdmin: function() {
                this.refreshToken().then(response => {
                    localStorage.jws = response.data;
                    this.axios.get("api/pharmacyadmin", {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                        .then(response => {
                            this.pharmacyAdmin = response.data;
                            this.pharmacyId = this.pharmacyAdmin.pharmacy.id;
                            this.getOrders();
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
            getOrders: function() {
                this.axios.get("api/order/pharmacy/" + this.pharmacyId)
                    .then(response => {
                        this.orders = response.data._embedded.orders;
                    })
                    .catch(response => {
                        console.log("Failed to get orders", response.data);
                    });
            },
            showOffers: function(o) {
                this.order = o;
                this.offers = [];
                this.axios.get("api/offer/order/" + o.id)
                    .then(response => {
                        this.offers = response.data._embedded.offers;
                    })
                    .catch(response => {
                        console.log("Failed to get offers", response.data);
                    });
                this.dialog = true;
            },
            /*acceptOffer: function(offerId) {
                this.dialog = false;



            },*/
            check: function() {
                if(this.pharmacyAdmin.id == this.order.pharmacyAdmin.id && this.order.deadline < new Date().toISOString().substr(0, 10))
                {
                    return true;
                }
                return false;
            },
            disableDialog: function() {
                this.dialog = false;
            },
        },
        mounted() {
            this.getPharmacyAdmin();
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