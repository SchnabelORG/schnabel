<template>
    <div id="vacations-main">
        <v-card
        id="vacations-card"
        elevation="2">
            <v-card-title>Vacations</v-card-title>
            <v-card-text>
                <div id="vacations-table">
                    <v-data-table :headers="headers"
                                    :items="vacations"
                                    :search="search">
                        <template v-slot:item="row">
                            <tr>
                                <td>{{row.item.Pharmacist}}</td>
                                <td>{{row.item.Period}}</td>
                                <td>{{row.item.Period}}</td>                        
                                <td>
                                    <v-btn @click="acceptVacation(row.item.id)">
                                        Accept
                                    </v-btn>
                                </td>
                                <td>
                                    <v-btn @click="denyVacation(row.item.id)">
                                        Deny
                                    </v-btn>
                                </td>
                            </tr>
                        </template>
                    </v-data-table>
                </div>
            </v-card-text>
        </v-card>
        <v-dialog v-model="this.dialog" persistent>
            <v-card id="reason-card">
                <v-card-title>Reason for denial</v-card-title>
                <v-spacer></v-spacer>
                <v-textarea
                v-model="reason"
                color="teal"
                :rules="[rules.required]"
                >
                <template v-slot:label>
                    <div>
                        Reason for denial
                    </div>
                </template>
                </v-textarea>
                <v-spacer></v-spacer>
                <v-btn class='primary' @click="confirmDenial()" :disabled="!reason">
                    Confirm
                </v-btn>
                <v-btn class='accent' @click="cancel()">
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
                vacations: [{Pharmacist: "Pera"}],
                dialog: false,
                id: '',
                reason: '',
                headers: [
                    { text: "Pharmacist" },
                    { text: "Start date" },
                    { text: "End date" },
                    { text: "Accept" },
                    { text: "Deny" },
                ],
                rules: {
                    required: value => !!value || 'Required.',
                },
            }
        },
        methods: {
            getVacations: function() {
                


            },
            acceptVacation: function(id) {
                this.id = id;

            },
            denyVacation: function(id) {
                this.id = id;
                this.dialog = true;
            },
            confirmDenial: function() {
                this.dialog = false;



                
                this.reason = '';
            },
            cancel: function() {
                this.dialog = false;
            },
        },
        mounted() {
            this.getVacations();
        },
    }
</script>

<style scoped>
    #vacations-main {
        display:grid;
        grid-template-columns:auto;
        place-items: center;
        min-height: 92vh;
        background-color: #fbecdd;
    }
    #vacations-card {
        display: flex;
        flex-direction: column;
        margin: 0 auto;
        width: 40%;
    }
    #vacations-main {
        display: flex;
        flex-direction: column;
        justify-content: center;
        width: 100vw;
        min-height: 100vh;
    }
    #reason-card {
        width: 60%;
        margin: 0 auto;
        justify-content: center;
    }
</style>
