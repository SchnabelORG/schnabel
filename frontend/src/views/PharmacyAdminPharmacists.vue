<template>
    <div id="pharmacists-main">
        <v-card
        id="pharmacists-card"
        elevation="2">
            <v-card-title>Pharmacists</v-card-title>
            <v-card-text>
                <v-btn @click="newPharmacist()">
                    New
                </v-btn>
                <div id="pharmacists-table">
                    <v-data-table :headers="headers"
                                    :items="pharmacists"
                                    :search="search">
                        <template v-slot:item="row">
                            <tr>
                                <td>{{row.item.name}}</td>
                                <td>{{row.item.surname}}</td>
                                <td>
                                    <v-btn @click="deletePharmacist(row.item.id)">
                                        Delete
                                    </v-btn>
                                </td>                       
                            </tr>
                        </template>
                    </v-data-table>
                </div>
            </v-card-text>
        </v-card>
        <v-dialog v-model="this.new" persistent>
            <v-card id="new-card">
                <v-card-title>Pharmacist</v-card-title>
                <v-spacer></v-spacer>
                 <v-text-field
                        v-model="name"
                        :rules="[rules.required]"
                        label="Name"
                        ></v-text-field>
                <v-spacer></v-spacer>
                <v-text-field
                        v-model="surname"
                        :rules="[rules.required]"
                        label="Surname"
                        ></v-text-field>
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
                <v-spacer></v-spacer>
                <v-card-title>Shift</v-card-title>
                <v-spacer></v-spacer>
                <v-menu
                ref="menu"
                v-model="menu"
                :close-on-content-click="false"
                :return-value.sync="time"
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
                <v-time-picker
                    v-model="validFrom"
                    no-title
                    scrollable
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
                        @click="$refs.menu.save(time)"
                    >
                        OK
                    </v-btn>
                </v-time-picker>
            </v-menu>
            <v-menu
                    ref="menu2"
                    v-model="menu2"
                    :close-on-content-click="false"
                    :return-value.sync="time"
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
                    <v-time-picker
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
                            @click="$refs.menu2.save(time)"
                        >
                            OK
                        </v-btn>
                    </v-time-picker>
            </v-menu>
            <v-btn class="primary" @click="addPharmacist()" :disabled="!name || !surname || !email">
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
                pharmacists: [],
                new: false,
                name: '',
                surname: '',
                email: '',
                password: '',
                postcode: '',
                city: '',
                street: '',
                streetNo: '',
                startTime: '',
                endTime: '',
                id: '',
                headers: [
                    { text: "Name" },
                    { text: "Surname" },
                    { text: "Delete" },
                ],
                rules: {
                    required: value => !!value || 'Required.',
                },
            }
        },
        methods: {
            getPharmacists: function() {
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
                            console.log("Successfully removed pharmacist", response.data);
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
            },
            newPharmacist: function() {
                this.new = true;
            },
            addPharmacist: function() {
                if(this.validFrom > this.validUntil) {
                    alert("Invalid period");
                    return;
                }
                let pharmacistRequest = { name: this.name, surname: this.surname, email: this.email, password: this.password, postcode: this.postcode, city: this.city, street: this.street, streetNo: this.streetNo, startTime: this.validFrom, endTime: this.validUntil };
                this.refreshToken().then(response => {
                    localStorage.jws = response.data;
                    this.axios.post("api/pharmacyadmin/addpharmacist", pharmacistRequest, {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                        .then(response => {
                            console.log("Successfully added pharmacist", response.data);
                            this.getPharmacists();
                        })
                        .catch(response => {
                            console.log("Failed to add pharmacists", response.data);
                        });
                   })
                    .catch(response => {
                    console.log(response.data);
                    this.$router.push("/");
                });
                
                this.new = false;
                this.name = '';
                this.surname = '';
                this.email = '';
                this.validFrom = '';
                this.validUntil = '';
            },
            cancel: function() {
                this.new = false;
                this.name = '';
                this.surname = '';
                this.email = '';
                this.validFrom = '';
                this.validUntil = '';
            },
        },
        mounted() {
            this.getPharmacists();
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