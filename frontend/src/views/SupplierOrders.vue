<template>
    <div id="orders-main">
        <v-row>
            <v-col 
                id="col-orders"
                cols="8"
            >
                <v-card
                id="orders-card"
                elevation="2">
                    <v-card-title>Orders</v-card-title>
                    <v-card-text>
                        <div id="orders-table">
                            <v-data-table :headers="headers"
                                            :items="orders"
                                            :search="search"
                                            single-select>
                                <template v-slot:item="row">
                                    <tr>
                                        <td>{{row.item.id}}</td>
                                        <td>{{row.item.description}}</td>
                                        <td>{{row.item.deadline}}</td>
                                        <td>
                                            <v-btn @click="seeItems(row.item.orderItems)">
                                                See items
                                            </v-btn>
                                        </td>   
                                        <td>
                                            <v-btn @click="makeOffer(row.item)">
                                                Make offer
                                            </v-btn>
                                        </td>                       
                                    </tr>
                                </template>
                            </v-data-table>
                        </div>
                    </v-card-text>
                </v-card>
            </v-col>
            <v-col
            cols="4" >
                <v-card id>
                    <v-card-title>Order items</v-card-title>
                    <v-card-text>
                        <div id="orders-table">
                            <v-data-table :headers="headers2"
                                            :items="orderitems"
                                            >
                                <template v-slot:item="row">
                                    <tr>
                                        <td>{{row.item.drug}}</td>
                                        <td>{{row.item.quantity}}</td>                   
                                    </tr>
                                </template>
                            </v-data-table>
                        </div>
                    </v-card-text>
                </v-card>
            </v-col>
        </v-row>
        <v-dialog v-model="this.new" persistent>
            <v-card id="new-card">
                <v-card-title>Make offer</v-card-title>
                    <v-text-field
                        v-model="order.id"
                        label="ID"
                        disabled
                    ></v-text-field>
                    <v-text-field
                        v-model="order.description"
                        label="Description"
                        disabled
                    ></v-text-field>
                    <v-text-field
                        v-model="order.deadline"
                        label="Deadline"
                        disabled
                    ></v-text-field>

                <div id="orders-table">
                    <v-data-table :headers="headers2"
                                    :items="order.orderItems"
                                    >
                        <template v-slot:item="row">
                            <tr>
                                <td>{{row.item.drug}}</td>
                                <td>{{row.item.quantity}}</td>                   
                            </tr>
                        </template>
                    </v-data-table>
                </div>
                <v-spacer></v-spacer>
                <v-text-field
                    v-model="price"
                    :rules="[rules.required]"
                    label="Price"
                ></v-text-field>
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
                                v-model="delivery"
                                label="Delivery"
                                prepend-icon="mdi-calendar"
                                readonly
                                v-bind="attrs"
                                v-on="on"
                            ></v-text-field>
                        </template>
                        <v-date-picker
                            ref="picker"
                            v-model="delivery"
                            :min="new Date().toISOString().substr(0, 10)"
                            :max="order.deadline"
                            @change="save"
                        ></v-date-picker>
                    </v-menu>
                </template>

                <v-spacer></v-spacer>
                
                <v-spacer></v-spacer>
           
            <v-btn class="primary" @click="sendOffer()" :disabled="!price || !delivery">
                Make offer
            </v-btn>
            <v-btn class="accent" @click="cancel()">
                Cancel
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
                orderitems: [],
                order: {},
                new: false,
                price: '',
                menu: false,
                delivery: '',
                time: {},
                id: '',
                search: '',
                headers: [
                    { text: "Id" },
                    { text: "Description" },
                    { text: "Deadline" },
                    { text: "See items" },
                    { text: "Make offer" },
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
            getOrders: function() {
                this.axios.get("api/order/new", {headers:{"Authorization": "Bearer " + this.currentUser}})
                .then(r =>
                {
                    this.orders = r.data._embedded.orders;
                })
                .catch(r => 
                {
                    console.log(r.data);
                })
            },
            seeItems: function(item) {
                console.log(item);
                this.orderitems = item;
            },
            makeOffer: function(item) {
                this.order = item;
                this.new = true;
            },
            save (date) {
                this.$refs.menu.save(date)
            },
            sendOffer: function() {
                let dto = {
                    price: this.price,
                    dateOfDelivery: this.delivery,
                    orderId: this.order.id
                }
                this.axios.post("api/offer/makeoffer", dto, {headers:{"Authorization": "Bearer " + this.currentUser}})
                .then(r => 
                {
                    console.log(r.data);
                    this.cancel();
                    this.getOrders();
                })
                .catch(r => 
                {
                    console.log(r.data);
                })
            },

            cancel: function() {
                this.new = false;
                this.delivery = '';
                this.price = '';
                this.order = '';
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