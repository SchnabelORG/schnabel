<template>
    <div id="makeorder-main">
        <v-card
        id="makeorder-card"
        elevation="2">
            <v-card-title>Create order</v-card-title>
            <b class="err">{{error}}</b>
            <div v-if="success" id="success-form">
                <p id="success-icon"><i class="fa fa-check"></i></p>
                <p>Order created!</p>
            </div>
            <v-card-text id="drug-card">
                <v-form id="tenders-add">
                    <v-combobox v-model="drugItem"
                                :items="warehouseitems"
                                return-object="true"
                                label="Drug"
                                :rules="reqMedicationRule">
                        <template slot="selection" slot-scope="data" >
                            {{ data.item.drug.name }}
                        </template>
                        <template slot="item" slot-scope="data">
                            {{ data.item.drug.name }}
                        </template>
                    </v-combobox>
                    <v-text-field v-model="quantity"
                                    label="Quantity"
                                    :rules="quantityRule"
                                    type="number"
                                    hide-details />
                    <v-btn elevation="2" @click="add()" class="accent" :disabled="!quantity">
                        Add to order
                    </v-btn>
                </v-form>
                <v-divider></v-divider>
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
                            :rules="descRule">
                </v-text-field>
            </v-card-text>
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
            <v-btn id="add-btn" class="primary" elevation="0" @click="makeOrder" :disabled="!deadline || !description || !orderItems">
                Make offer
            </v-btn>
        </v-card>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                deadline: '',
                quantity: '',
                description: '',
                menu: false,
                drugs: [],
                warehouseitems: '',
                drugItem: '',
                orderItems: [],
                showOrderItems: [],
                addedOrderItems: [],
                neworder: '',
                pharmacyId: '',
                pharmacyAdmin: '',
                success: false,
                drugHeaders: [
                    { text: "Name"}, 
                    { text: "Quantity" },
                ],
                reqMedicationRule: [
                    v => !!v || "Medication is required",
                ],
                quantityRule: [
                    v => !!v || "Quntity is required",
                ],
                descRule: [
                    v => !!v || "Description is required",
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
                            this.getDrugs();
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
            getDrugs: function () {
                this.axios.get("api/warehouseitem/pharmacy/" + this.pharmacyId)
                    .then(response => {
                        this.warehouseitems = response.data._embedded.warehouseitems;
                    })
                    .catch(response => {
                        console.log("Failed to get warehouseitems", response.data);
                    });
            },
            add: function() {
                
                this.orderItems.push({ drugId: this.drugItem.drug.id, quantity : this.quantity });
                this.showOrderItems.push({ drug: this.drugItem.drug.name, quantity: this.quantity });
                
                this.drugItem = '';
                this.quantity = '';

            },
            makeOrder: function() {
                this.refreshToken().then(response => {
                    console.log(response.data);
                    localStorage.jws = response.data;
                    let orderRequest = { description: this.description, deadline: this.deadline, orderItems: this.orderItems };
                    this.axios.post("/api/order", orderRequest, {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                        .then(response =>
                        {
                            console.log(response);
                            this.success = true;
                            this.showOrderItems = [];
                            this.orderItems = [];
                            this.description = '';
                            this.deadline = '';
                        })
                        .catch(response =>
                        {
                            this.error = "Creating new order unsuccessfull";
                            console.log(response.data);
                        });
                })
               .catch(response => {
                    console.log(response.data);
                    this.$router.push("/");
                });
            },
            save (date) {
                this.$refs.menu.save(date)
            },
        },
        mounted() {
            this.getPharmacyAdmin();
		}
    }
</script>

<style scoped>
    #makeorder-main {
        display:grid;
        grid-template-columns:auto;
        place-items: center;
        min-height: 92vh;
        background-color: #fbecdd;
    }
    #makeorder-card {
        display: flex;
        flex-direction: column;
        margin: 0 auto;
        width: 40%;
    }
    #makeorder-main {
        display: flex;
        flex-direction: column;
        justify-content: center;
        width: 100vw;
        min-height: 100vh;
    }
</style>