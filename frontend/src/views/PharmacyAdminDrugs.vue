<template>
    <div id="drugs-main">
        <v-card
        id="drugs-card"
        elevation="2">
            <v-card-title>Drugs</v-card-title>
            <v-card-text>
                <v-btn @click="newDrug()">
                    New
                </v-btn>
                <div id="drugs-table">
                    <v-data-table :headers="headers"
                                    :items="drugs"
                                    :search="search">
                        <template v-slot:item="row">
                            <tr>
                                <td>{{row.item.name}}</td>                     
                                <td>
                                    <v-btn @click="editDrug(row.item.id)">
                                        Edit
                                    </v-btn>
                                </td>
                                <td>
                                    <v-btn @click="deleteDrug(row.item.id)">
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
                <v-card-title>Drug</v-card-title>
                <v-spacer></v-spacer>
                 <v-text-field
                        v-model="name"
                        :rules="[rules.required]"
                        label="Name"
                        ></v-text-field>
                <v-spacer></v-spacer>
                <v-btn class="primary" @click="addDrug()" :disabled="!name">
                    Add
                </v-btn>
                <v-btn class="accent" @click="cancel()">
                    Cancel
                </v-btn>
            </v-card>
        </v-dialog>
        <v-dialog v-model="this.edit" persistent>
            <v-card id="new-card">
                <v-card-title>Reason for denial</v-card-title>
                <v-spacer></v-spacer>
                 <v-text-field
                        v-model="name"
                        :rules="[rules.required]"
                        label="Name"
                        ></v-text-field>
                <v-spacer></v-spacer>
                <v-btn class="primary" @click="saveDrug()" :disabled="!name">
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
                drugs: [{name: "Brufen"}],
                new: false,
                edit: false,
                name: '',
                id: '',
                headers: [
                    { text: "Name" },
                    { text: "Edit" },
                    { text: "Delete" },
                ],
                rules: {
                    required: value => !!value || 'Required.',
                },
            }
        },
        methods: {
            getDrugs: function() {
                


            },
            editDrug: function(id) {
                this.id = id;
                this.edit = true;
            },
            deleteDrug: function(id) {
                this.id = id;
            },
            newDrug: function() {
                this.new = true;
            },
            addDrug: function() {
                this.new = false;



            },
            saveDrug: function() {
                this.edit = false;
            },
            cancel: function() {
                this.dialog = false;
            },
        },
        mounted() {
            this.getDrugs();
        },
    }
</script>

<style scoped>
    #drugs-main {
        display:grid;
        grid-template-columns:auto;
        place-items: center;
        min-height: 92vh;
        background-color: #fbecdd;
    }
    #drugs-card {
        display: flex;
        flex-direction: column;
        margin: 0 auto;
        width: 40%;
    }
    #drugs-main {
        display: flex;
        flex-direction: column;
        justify-content: center;
        width: 100vw;
        min-height: 100vh;
    }
    #edit-card {
        width: 60%;
        margin: 0 auto;
        justify-content: center;
    }
</style>