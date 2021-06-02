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
                <v-spacer></v-spacer>
                <v-btn class="primary" @click="addDermatologist()" :disabled="!name || !surname">
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
                this.id = id;
            },
            newDermatologist: function() {
                this.new = true;
            },
            addDermatologist: function() {
                this.new = false;

                this.name = '';
                this.surname = '';
            },
            cancel: function() {
                this.new = false;
            },
        },
        mounted() {
            this.getDermatologists();
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