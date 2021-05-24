<template>
    <div id="makeorder-main">
        <v-card
        id="makeorder-card"
        elevation="2">
            <v-card-title>Make order</v-card-title>
            <v-card-text id="drug-card">
                <v-form id="tenders-add">
                    <v-combobox v-model="drugItem"
                                :items="drugs"
                                return-object="true"
                                label="Drug"
                                :rules="reqMedicationRule">
                        <template slot="selection" slot-scope="data" >
                            {{ data.item.name }}
                        </template>
                        <template slot="item" slot-scope="data">
                            {{ data.item.name }}
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
                                    :items="orderItems">
                        <template v-slot:item="row">
                            <tr>
                                <td>{{row.item.drug.name}}</td>
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
                drugItem: '',
                orderItems: [],
                addedOrderItems: [],
                neworder: '',
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
            getDrugs: function () {
				/*this.axios.get("/pswapi/drugs")
					.then(response => {
						this.drugs = response.data;
						console.log(response);
					})
					.catch(response => {
						console.log(response);
					})
					.finally(function(){
					})*/
            },
            add: function() {
                
                this.orderItems.push({ id: '', order: '', drug: this.drugItem, quantity : this.quantity });
                
                this.drugItem = '';
                this.quantity = '';

            },
            makeOrder: function() {
                let addorder = { id: '', description: this.description, deadline: this.deadline };

                this.axios.post("/api/order", addorder)
					.then(response => {
                        this.neworder = response.data;
						console.log(response);

                        for (var i in this.orderItems) 
                        {
                            this.orderItems[i].order = this.neworder;
                        }

                        this.axios.post("/api/orderitem", this.orderItems)
                            .then(response => {
                                console.log(response);
                                this.addedOrderItems = response.data;
                                this.neworder.orderItems = [...this.addedOrderItems];
                               
                                this.axios.post("/api/addorder", this.neworder)
                                .then(response => {
                                    console.log(response);
                                    this.order = '';
                                    this.orderItems = [];
                                    this.description = '';
                                    this.deadline = '';
                                })
                                .catch(response => {
                                    console.log(response);
                                })
                                .finally(function(){
                                });
                            })
                            .catch(response => {
                                console.log(response);
                            })
                            .finally(function(){
                            });

					})
					.catch(response => {
						console.log(response);
					})
					.finally(function(){
					});

            },
            save (date) {
                this.$refs.menu.save(date)
            },
        },
        mounted() {
			this.getDrugs();
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