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
                                <td>{{row.item.medicalEmployee.name}}</td>
                                <td>{{row.item.medicalEmployee.surname}}</td>
                                <td>{{new Date(row.item.period.startTime).toISOString().substr(0, 10)}}</td>
                                <td>{{new Date(row.item.period.endTime).toISOString().substr(0, 10)}}</td>                        
                                <td>
                                    <v-btn @click="acceptVacation(row.item.id)">
                                        Accept
                                    </v-btn>
                                </td>
                                <td>
                                    <v-btn @click="rejectVacation(row.item.id)">
                                        Reject
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
                <v-card-title>Reason for rejection</v-card-title>
                <template>
                    <v-spacer></v-spacer>
                    <v-textarea
                    v-model="reason"
                    color="teal"
                    :rules="[rules.required]"
                    >
                    <template v-slot:label>
                        <div>
                            Reason for rejection
                        </div>
                    </template>
                    </v-textarea>
                    <v-spacer></v-spacer>
                </template>
                <v-btn class='accent' @click="confirmRejection()" :disabled="!reason">
                    Confirm
                </v-btn>
                <v-btn class='primary' @click="cancel()">
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
                vacations: [],
                dialog: false,
                reason: '',
                id: '',
                headers: [
                    { text: "Name" },
                    { text: "Surname" },
                    { text: "Start date" },
                    { text: "End date" },
                    { text: "Accept" },
                    { text: "Reject" },
                ],
                rules: {
                    required: value => !!value || 'Required.',
                },
            }
        },
        methods: {
            getVacations: function() {
                this.refreshToken().then(response => {
                    localStorage.jws = response.data;
                    this.axios.get("api/vacation/created", {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                        .then(response => {
                            this.vacations = response.data._embedded.vacations;
                        })
                        .catch(response => {
                            console.log("Failed to get vacations", response.data);
                        });
                   })
                    .catch(response => {
                    console.log(response.data);
                    this.$router.push("/");
                });
            },
            acceptVacation: function(id) {
                this.refreshToken().then(response => {
                    localStorage.jws = response.data;
                    this.axios.put("api/vacation/accept", id, {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                        .then(response => {
                            console.log("Successfully accepted vacation", response.data);
                            this.getVacations();
                        })
                        .catch(response => {
                            console.log("Failed to accept vacations", response.data);
                        });
                   })
                    .catch(response => {
                    console.log(response.data);
                    this.$router.push("/");
                });
            },
            rejectVacation: function(id) {
                this.id = id;
                this.dialog = true;
            },
            confirmRejection: function() {
                let vacationRequest = { id: this.id, reason: this.reason };
                this.refreshToken().then(response => {
                    localStorage.jws = response.data;
                    this.axios.put("api/vacation/reject", vacationRequest, {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                        .then(response => {
                            console.log("Successfully rejected vacation", response.data);
                            this.getVacations();
                        })
                        .catch(response => {
                            console.log("Failed to reject vacations", response.data);
                        });
                   })
                    .catch(response => {
                    console.log(response.data);
                    this.$router.push("/");
                });
                this.dialog = false;
                this.reason = '';
                this.id = '';
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
