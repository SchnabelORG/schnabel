<template>
    <div id="dermatologists-main">
        <v-card
        id="dermatologists-card"
        elevation="2">
            <v-card-title>Dermatologists</v-card-title>
            <v-card-text>
                <v-btn @click="newDermatologist()">
                    New
                </v-btn>
                <div id="dermatologists-table">
                    <v-data-table :headers="headers"
                                    :items="dermatologists"
                                    :search="search">
                        <template v-slot:item="row">
                            <tr>
                                <td>{{row.item.name}}</td>
                                <td>{{row.item.surname}}</td>
                                <td>
                                    <v-btn @click="deleteDermatologist(row.item.id)">
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
                <v-card-title>Dermatologist</v-card-title>
                <v-combobox v-model="dermatologist"
                        :items="dermatologistsNotPharmacy"
                        return-object="true"
                        label="Dermatologist"
                        :rules="[rules.required]">
                <template slot="selection" slot-scope="data" >
                    {{ data.item.name }} {{ data.item.surname }}
                </template>
                <template slot="item" slot-scope="data">
                    {{ data.item.name }} {{ data.item.surname }}
                </template>
            </v-combobox>
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
                <v-btn class="primary" @click="addDermatologist()" :disabled="!dermatologist || !validFrom || !validUntil">
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
                dermatologists: [],
                dermatologistsNotPharmacy: [],
                dermatologist: '',
                new: false,
                name: '',
                surname: '',
                id: '',
                pharmacyId: '',
                validFrom: '',
                validUntil: '',
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
            getPharmacyAdmin: function() {
                this.refreshToken().then(response => {
                    localStorage.jws = response.data;
                    this.axios.get("api/pharmacyadmin", {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                        .then(response => {
                            this.pharmacyId = response.data.pharmacy.id;
                            this.getDermatologists();
                            this.getDermatologistsNotInPharmacy();
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
            getDermatologists: function() {
                 this.refreshToken().then(response => {
                    localStorage.jws = response.data;
                    this.axios.get("api/pharmacyadmin/dermatologist", {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                        .then(response => {
                            this.dermatologists = response.data._embedded.dermatologists;
                        })
                        .catch(response => {
                            console.log("Failed to get dermatologists", response.data);
                        });
                    })
                    .catch(response => {
                    console.log(response.data);
                    this.$router.push("/");
                });
            },
            deleteDermatologist: function(id) {
                this.refreshToken().then(response => {
                    localStorage.jws = response.data;
                    this.axios.delete("api/pharmacyadmin/removedermatologist/" + id, {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                        .then(response => {
                            console.log(response.data);
                            this.getDermatologists();
                        })
                        .catch(response => {
                            console.log("Failed to remove dermatologist", response.data);
                        });
                   })
                    .catch(response => {
                    console.log(response.data);
                    this.$router.push("/");
                });
            },
            getDermatologistsNotInPharmacy: function() {
                this.axios.get("api/dermatologist/notinpharmacy/" + this.pharmacyId)
                        .then(response => {
                            this.dermatologistsNotPharmacy = response.data._embedded.dermatologists;
                        })
                        .catch(response => {
                            console.log("Failed to get dermatologists not in pharmacy", response.data);
                        });
            },
            addDermatologist: function() {
                if(this.validFrom > this.validUntil) {
                    alert("Invalid period");
                    return;
                }
                
                let dermatologistRequest = { id: this.dermatologist.id, pharmacyId: this.pharmacyId, startTime: this.validFrom, endTime: this.validUntil};
                this.refreshToken().then(response => {
                    localStorage.jws = response.data;
                    this.axios.post("api/pharmacyadmin/addnewdermatologist", dermatologistRequest, {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                        .then(response => {
                            console.log(response.data);
                            this.getDermatologists();
                        })
                        .catch(response => {
                            console.log("Failed to add dermatologist", response.data);
                        });
                   })
                    .catch(response => {
                    console.log(response.data);
                    this.$router.push("/");
                });

                
                this.new = false;
                this.dermatologist = '';
                this.validFrom = '';
                this.validUntil = '';
            },
            newDermatologist: function() {
                this.new = true;
            },
            cancel: function() {
                this.new = false;
                this.dermatologist = '';
                this.validFrom = '';
                this.validUntil = '';
            },
        },
        mounted() {
            this.getPharmacyAdmin();
        },
    }
</script>

<style scoped>
    #dermatologists-main {
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