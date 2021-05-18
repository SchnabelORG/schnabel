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
                <v-spacer></v-spacer>
                <v-btn class="primary" @click="addPharmacist()" :disabled="!name || !surname">
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
                
            },
            deletePharmacist: function(id) {
                this.id = id;
            },
            newPharmacist: function() {
                this.new = true;
            },
            addPharmacist: function() {
                this.new = false;

                this.name = '';
                this.surname = '';
            },
            cancel: function() {
                this.new = false;
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