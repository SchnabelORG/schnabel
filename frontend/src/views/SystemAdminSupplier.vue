<template>
    <div id="pharmacists-main">
        <v-card
        id="pharmacists-card"
        elevation="2">
            <v-card-title>Suppliers</v-card-title>
            <v-card-text>
                <v-btn @click="newSupplier()">
                    New
                </v-btn>
                <div id="pharmacists-table">
                    <v-data-table :headers="headers"
                                    :items="suppliers"
                                    :search="search">
                        <template v-slot:item="row">
                            <tr>
                                <td>{{row.item.firm}}</td>
                                <td>{{row.item.email}}</td>
                                <td>{{getAddress(row.item.address)}}</td>                   
                            </tr>
                        </template>
                    </v-data-table>
                </div>
            </v-card-text>
        </v-card>
        <v-dialog v-model="this.new" persistent>
            <v-card id="new-card">
                <v-card-title>Supplier</v-card-title>
                <v-spacer></v-spacer>
                <v-text-field
                        v-model="email"
                        :rules="[rules.required]"
                        label="Email"
                        ></v-text-field>
                <v-text-field
                        v-model="password"
                        :rules="[rules.required]"
                        label="Password"
                        ></v-text-field>
                <v-text-field
                        v-model="postcode"
                        :rules="[rules.required]"
                        label="Postcode"
                        ></v-text-field>
                <v-text-field
                        v-model="city"
                        :rules="[rules.required]"
                        label="City"
                        ></v-text-field>
                <v-text-field
                        v-model="street"
                        :rules="[rules.required]"
                        label="Street"
                        ></v-text-field>
                <v-text-field
                        v-model="streetNo"
                        :rules="[rules.required]"
                        label="StreetNo"
                        ></v-text-field>
                <v-text-field
                        v-model="firm"
                        :rules="[rules.required]"
                        label="Firm"
                        ></v-text-field>
                <v-spacer></v-spacer>
           
            <v-btn class="primary" @click="addSupplier()" :disabled="!firm || !street || !email">
                Add
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
                suppliers: [],
                new: false,
                name: '',
                surname: '',
                email: '',
                firm: '',
                password: '',
                postcode: '',
                city: '',
                street: '',
                streetNo: '',
                startTime: '',
                endTime: '',
                time: {},
                id: '',
                search: '',
                headers: [
                    { text: "Fimr" },
                    { text: "Email" },
                    { text: "Adress"},
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
            getSuppliers: function() {
                this.axios.get("api/supplier")
                .then(r =>
                {
                    this.suppliers = r.data._embedded.suppliers;
                })
                .catch(r => 
                {
                    console.log(r.data);
                })
            },
            getAddress: function(address) {
                var a = address.street + ' ' + address.streetNo + ', ' + address.city + ', ' + address.postcode; 
                return a;
            },
            /*
            getDermatologists: function() {
                this.refreshToken().then(response => {
                    localStorage.jws = response.data;
                    this.axios.get("api/pharmacyadmin/pharmacist", {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                        .then(response => {
                            this.pharmacists = response.data._embedded.pharmacists;
                        })
                        .catch(response => {
                            console.log("Failed to get pharmacists", response.data);
                        });
                   })
                    .catch(response => {
                    console.log(response.data);
                    this.$router.push("/");
                });
            },
            deletePharmacist: function(id) {
                this.refreshToken().then(response => {
                    localStorage.jws = response.data;
                    this.axios.delete("api/pharmacyadmin/removepharmacist/" + id, {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                        .then(response => {
                            console.log(response.data);
                            this.getPharmacists();
                        })
                        .catch(response => {
                            console.log("Failed to remove pharmacists", response.data);
                        });
                   })
                    .catch(response => {
                    console.log(response.data);
                    this.$router.push("/");
                });
            },*/

            //TODO fix later
            deleteSupplier: function(id) {
                this.axios.delete("api/supplier/remove/" + id)
                .then(r => 
                {
                    console.log(r);
                    this.getSuppliers();
                })
                .catch(r =>
                {
                    console.log("Failed to remove supplier", r.data);
                })
            },
            newSupplier: function() {
                this.new = true;
            },

            //TODO n
            
            addSupplier: function() {
                var adr = {postcode: this.postcode, city: this.city, street: this.street, streetNo: this.streetNo}
                let request = { name: this.name, surname: this.surname, email: this.email, password: this.password, address: adr, firm: this.firm };
                this.axios.post("api/supplier/", request, {headers:{"Authorization": "Bearer " + this.currentUser}})
                .then(response => {
                    console.log("Successfully added supplier", response.data);
                    this.getSuppliers();
                })
                .catch(response => {
                    console.log("Failed to add supplier", response.data);
                });
                
                this.new = false;
                this.name = '';
                this.surname = '';
                this.email = '';
                this.validFrom = '';
                this.validUntil = '';
                this.firm = '';
            },
            cancel: function() {
                this.new = false;
                this.name = '';
                this.surname = '';
                this.email = '';
                this.validFrom = '';
                this.validUntil = '';
                this.firm = '';
            },
        },
        mounted() {
            this.getSuppliers();
        },
    }
</script>

<style scoped>
    #pharmacists-main {
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
</style>