<template>
    <div id="pricelist-main">
        <v-card
        id="pricelist-card"
        elevation="1">
            <v-card-title>Pricelist</v-card-title>
            <div id="pricelist-table">
                <v-data-table :headers="headers"
                                :items="warehouseitems"
                                :search="search">
                    <template v-slot:item="row">
                        <tr>
                            <td>{{row.item.drug.name}}</td>
                            <td>{{row.item.drug.description}}</td>
                            <td>{{row.item.drugPrice.price}}</td>
                            <td>
                                <v-btn @click="define(row.item)">
                                    Define
                                </v-btn>
                            </td>
                        </tr>
                    </template>
                </v-data-table>
            </div>
        </v-card>
        <v-dialog v-model="this.dialog" persistent>
            <v-card id="define-card">
                <v-card-title>Define drug price</v-card-title>
                <v-spacer></v-spacer>
                    <v-text-field
                        v-model="name"
                        label="Name"
                        disabled
                        ></v-text-field>
                    <v-text-field
                        v-model="description"
                        label="Description"
                        disabled
                        ></v-text-field>
                    <v-text-field
                        v-model="drugPrice"
                        label="Price"
                        type="number"
                        :rules="[rules.required]"
                    ></v-text-field>
            <v-menu
                ref="menu"
                v-model="menu"
                :close-on-content-click="false"
                :return-value.sync="date"
                transition="scale-transition"
                offset-y
                min-width="auto"
            >
                <template v-slot:activator="{ on, attrs }">
                <v-text-field
                    v-model="validFrom"
                    label="Valid from"
                    prepend-icon="mdi-calendar"
                    readonly
                    v-bind="attrs"
                    v-on="on"
                ></v-text-field>
                </template>
                <v-date-picker
                    v-model="validFrom"
                    no-title
                    scrollable
                    :min="new Date().toISOString().substr(0, 10)"
                >
                    <v-spacer></v-spacer>
                    <v-btn
                        text
                        color="primary"
                        @click="menu = false"
                    >
                        Cancel
                    </v-btn>
                    <v-btn
                        text
                        color="primary"
                        @click="$refs.menu.save(date)"
                    >
                        OK
                    </v-btn>
                </v-date-picker>
            </v-menu>
            <v-menu
                    ref="menu2"
                    v-model="menu2"
                    :close-on-content-click="false"
                    :return-value.sync="date"
                    transition="scale-transition"
                    offset-y
                    min-width="auto"
                >
                    <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                        v-model="validUntil"
                        label="Valid until"
                        prepend-icon="mdi-calendar"
                        readonly
                        v-bind="attrs"
                        v-on="on"
                    ></v-text-field>
                    </template>
                    <v-date-picker
                        v-model="validUntil"
                        no-title
                        scrollable
                        :min="this.validFrom"
                    >
                        <v-spacer></v-spacer>
                        <v-btn
                            text
                            color="primary"
                            @click="menu2 = false"
                        >
                            Cancel
                        </v-btn>
                        <v-btn
                            text
                            color="primary"
                            @click="$refs.menu2.save(date)"
                        >
                            OK
                        </v-btn>
                    </v-date-picker>
            </v-menu>
                <v-spacer></v-spacer>
                <v-btn class="primary" @click="save()" :disabled="!validFrom || !validUntil || !drugPrice">
                    Save
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
                warehouseitem: '',
                warehouseitems: [],
                price: '',
                validUntil: '',
                name: '',
                description: '',
                drugPrice: '',
                dialog: false,
                headers: [
					{ text: "Name" },
                    { text: "Description" },
                    { text: "Price" },
                    { text: "Define" },
                ],
                rules: {
                    required: value => !!value || 'Required.',
                },
            }
        },
        methods: {
            getDrugs: function () {
				this.refreshToken().then(response => {
                    localStorage.jws = response.data;
                    this.axios.get("api/pharmacyadmin/drug", {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                        .then(response => {
                            this.warehouseitems = response.data._embedded.warehouseitems;
                        })
                        .catch(response => {
                            console.log("Failed to get drugs", response.data);
                        });
                   })
                    .catch(response => {
                    console.log(response.data);
                    this.$router.push("/");
                });
            },
            define: function(wareHouseItem) {
                this.dialog = true;
                this.warehouseitem = wareHouseItem;
                this.name = wareHouseItem.drug.name;
                this.description = wareHouseItem.drug.description;
            },
            save: function() {
                if(this.validFrom > this.validUntil)
                {
                    alert("Invalid period");
                    return;
                }
                if(!this.drugPrice || !this.validFrom || !this.validUntil)
                {
                    return;
                }
                let drugPriceRequest = { price: this.drugPrice, priceStartDate: this.validFrom, priceEndDate: this.validUntil, wareHouseItemId: this.warehouseitem.id };

                this.refreshToken().then(response => {
                    localStorage.jws = response.data;
                    this.axios.post("api/warehouseitem/drugprice", drugPriceRequest, {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                        .then(response => {
                            console.log(response.data);
                            alert("Successfully added drug price");
                        })
                        .catch(response => {
                            console.log("Failed to add drugprice", response.data);
                        });
                   })
                    .catch(response => {
                    console.log(response.data);
                    this.$router.push("/");
                });
                this.dialog = false;
                this.description = '';
                this.drugPrice = '';
                this.name = '';
                this.validFrom = '';
                this.validUntil = '';
            },
            cancel: function() {
                this.dialog = false;
                this.description = '';
                this.drugPrice = '';
                this.name = '';
                this.validFrom = '';
                this.validUntil = '';
            },
        },
        mounted() {
			this.getDrugs();
		}
    }
</script>

<style scoped>
    #pricelist-main {
        display:grid;
        grid-template-columns:auto;
        place-items: center;
        min-height: 92vh;
        background-color: #fbecdd;
    }
    #pricelist-card {
        display: flex;
        flex-direction: column;
        margin: 0 auto;
        width: 40%;
    }
    #pricelist-main {
        display: flex;
        flex-direction: column;
        justify-content: center;
        width: 100vw;
        min-height: 100vh;
    }
    #define-card {
        display:grid;
        grid-template-columns:auto;
        margin: 0 auto;
        width: 40%;
    }
</style>