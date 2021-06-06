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
                                <td>                              
                                    <v-btn @click="deleteOrder(row.item.id)">
                                        Delete
                                    </v-btn>
                                </td>
                                <td>                              
                                    <v-btn @click="editOrder(row.item)">
                                        Edit
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
                                    <v-btn @click="acceptOffer(row.item.id)" v-if="checkOffer()">
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
        <v-dialog v-model="this.editdialog" persistent>
            <v-card id="order-card">
                <v-card-title>Order</v-card-title>
                <div id="drug-table">
                    <v-data-table :headers="drugHeaders"
                                    :items="showOrderItems">
                        <template v-slot:item="row">
                            <tr>
                                <td>{{row.item.drug}}</td>
                                <td>{{row.item.quantity}}</td>
                            </tr>
                        </template>
                    </v-data-table>
                </div>
                <v-text-field v-model="description"
                            label="Description"
                            :rules="[rules.required]">
                </v-text-field>
                <template>
                    <v-menu
                        ref="menu"
                        v-model="menu"
                        :close-on-content-click="false"
                        transition="scale-transition"
                        offset-y
                        min-width="auto"
                        >
                        <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                            v-model="deadline"
                            label="Deadline"
                            prepend-icon="mdi-calendar"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                        ></v-text-field>
                        </template>
                        <v-date-picker
                        ref="picker"
                        v-model="deadline"
                        :min="new Date().toISOString().substr(0, 10)"
                        @change="save"
                        ></v-date-picker>
                    </v-menu>
                </template>
                <v-btn @click="disableEditDialog()">
                    Back
                </v-btn>
                <v-btn class="primary" @click="saveEdit()" :disabled="!this.deadline || !this.description">
                    Edit
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
                editdialog: false,
                order: '',
                pharmacyAdmin: '',
                pharmacyId: '',
                description: '',
                showOrderItems: [],
                deadline: '',
                orderHeaders: [
                    { text: "Description" },
                    { text: "Deadline" },
                    { text: "Show offers" },
                    { text: "Delete"},
                    { text: "Edit"},
                ],
                offerHeaders: [
                    { text: "Supplier" },
                    { text: "Price" },
                    { text: "Delivery" },
                    { text: "Accept" },
                ],
                drugHeaders: [
                    { text: "Name"}, 
                    { text: "Quantity" },
                ],
                rules: {
                    required: value => !!value || 'Required.',
                },
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

                    this.refreshToken().then(response => {
                    localStorage.jws = response.data;
                    this.axios.get("api/order/pharmacy/" + this.pharmacyId, {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                    .then(response => {
                        this.orders = response.data._embedded.orders;
                    })
                    .catch(response => {
                        console.log("Failed to get orders", response.data);
                    });
                   })
                    .catch(response => {
                    console.log(response.data);
                    this.$router.push("/");
                });

                    
            },
            showOffers: function(o) {
                this.order = o;
                this.offers = [];
                this.refreshToken().then(response => {
                localStorage.jws = response.data;
                this.axios.get("api/offer/order/" + o.id, {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                    .then(response => {
                        this.offers = response.data._embedded.offers;
                    })
                    .catch(response => {
                        console.log("Failed to get offers", response.data);
                    });
                })
                    .catch(response => {
                    console.log(response.data);
                    this.$router.push("/");
                });
                this.dialog = true;
            },
            acceptOffer: function(offerId) {
                this.refreshToken().then(response => {
                    localStorage.jws = response.data;
                    this.axios.post("api/offer/acceptoffer", offerId, {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                        .then(response => {
                            console.log(response);
                            this.getOrders();
                        })
                        .catch(response => {
                            console.log("Failed to accept offer", response.data);
                        });
                   })
                    .catch(response => {
                    console.log(response.data);
                    this.$router.push("/");
                });
                this.dialog = false;
            },
            checkOffer: function() {
                if(this.pharmacyAdmin.id == this.order.pharmacyAdmin.id && this.order.deadline < new Date().toISOString().substr(0, 10))
                {
                    return true;
                }
                return false;
            },
            deleteOrder: function(orderId) {
                this.refreshToken().then(response => {
                    localStorage.jws = response.data;
                    this.axios.delete("api/order/" + orderId, {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                        .then(response => {
                            console.log("Successfully deleted order", response.data);
                            this.getOrders();
                        })
                        .catch(response => {
                            console.log("Failed to delete order", response.data);
                        });
                   })
                    .catch(response => {
                    console.log(response.data);
                    this.$router.push("/");
                });
            },
            editOrder: function(o){
                this.order = o;
                this.deadline = o.deadline;
                this.description = o.description;
                this.showOrderItems = o.orderItems;
                this.editdialog = true;
            },
            disableDialog: function() {
                this.dialog = false;
            },
            disableEditDialog: function() {
                this.editdialog = false;
            },
            saveEdit: function() {
                if(!this.description || !this.deadline)
                {
                    return;
                }
                this.refreshToken().then(response => {
                    console.log(response.data);
                    localStorage.jws = response.data;
                    let orderUpdateRequest = { id: this.order.id, description: this.description, deadline: this.deadline };
                    this.axios.put("api/order", orderUpdateRequest, {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                        .then(response => {
                            console.log("Successfully updated order", response.data);
                            this.getOrders();
                        })
                        .catch(response => {
                            console.log("Failed to update order", response.data);
                        });
                   })
                    .catch(response => {
                    console.log(response.data);
                    this.$router.push("/");
                });
                this.editdialog = false;
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
    #offers-card, #order-card {
        display:grid;
        grid-template-columns:auto;
        margin: 0 auto;
        width: 40%;
    }
</style>