<template>
    <div id="pharmacists-main">
        <v-card
        id="pharmacists-card"
        elevation="2">
            <v-card-title>Dermatologists</v-card-title>
            <v-card-text>
                <v-btn @click="newDermatologist()">
                    New
                </v-btn>
                <div id="pharmacists-table">
                    <v-data-table :headers="headers"
                                    :items="dermatologists"
                                    :search="search">
                        <template v-slot:item="row">
                            <tr>
                                <td>{{row.item.name}}</td>
                                <td>{{row.item.surname}}</td>
                                <td>{{row.item.email}}</td>                      
                            </tr>
                        </template>
                    </v-data-table>
                </div>
            </v-card-text>
        </v-card>
        <v-dialog v-model="this.new" persistent>
            <v-card id="new-card">
                <v-card-title>Dermatologist</v-card-title>
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
           
            <v-btn class="primary" @click="addDermatologist()" :disabled="!name || !surname || !email">
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
                time: {},
                id: '',
                search: '',
                headers: [
                    { text: "Name" },
                    { text: "Surname" },
                    { text: "Email"},
                ],
                rules: {
                    required: value => !!value || 'Required.',
                },
            }
        },
        computed: {
            currentUser() {
                return this.$store.state.auth.user;
            }
        },
        methods: {
            getDermatologists: function() {
                this.axios.get("api/dermatologist")
                .then(r =>
                {
                    this.dermatologists = r.data._embedded.dermatologists;
                })
                .catch(r => 
                {
                    console.log(r.data);
                })
            },

            //TODO fix later
            deleteDermatologist: function(id) {
                this.axios.delete("api/dermatologist/remove/" + id)
                .then(r => 
                {
                    console.log(r.data);
                    this.getDermatologists();
                })
                .catch(r =>
                {
                    console.log("Failed to remove dermatologist", r.data);
                })
            },
            newDermatologist: function() {
                this.new = true;
            },

            //TODO n
            
            addDermatologist: function() {
                var adr = {postcode: this.postcode, city: this.city, street: this.street, streetNo: this.streetNo}
                let request = { name: this.name, surname: this.surname, email: this.email, password: this.password, address: adr, startTime: this.validFrom, endTime: this.validUntil };
                this.axios.post("api/dermatologist/register", request, {headers:{"Authorization": "Bearer " + this.currentUser}})
                .then(response => {
                    console.log("Successfully added dermatologist", response.data);
                    this.getDermatologists();
                })
                .catch(response => {
                    console.log("Failed to add dermatologist", response.data);
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
            this.getDermatologists();
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