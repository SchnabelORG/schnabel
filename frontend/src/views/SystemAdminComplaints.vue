<template>
    <div id="orders-main">
        <v-row>
        </v-row>
        <v-row>
                <v-card
                id="orders-card"
                elevation="2">
                    <v-card-title>Complaints</v-card-title>
                    <v-card-text>
                        <div>
                            <v-data-table :headers="headers"
                                            :items="complaints"
                                            :search="search"
                                            single-select>
                                <template v-slot:item="row">
                                    <tr>
                                        <td>{{row.item.id}}</td>
                                        <td>{{row.item.complaintText}}</td>
                                        <td>
                                            <v-btn @click="respondTo(row.item)">
                                                Respond
                                            </v-btn>
                                        </td>                   
                                    </tr>
                                </template>
                            </v-data-table>
                        </div>
                    </v-card-text>
                </v-card>
        </v-row>
        <v-dialog v-model="this.new" persistent>
            <v-card id="new-card">
                <v-card-title>Respond</v-card-title>
                <v-card-text>
                    <v-row>
                    <textarea
                        v-model="respondtext"
                    >
                    </textarea>
                    </v-row>
                    <v-row>
                    <v-btn class="primary" @click="respond()">
                        Respond
                    </v-btn>
                    <v-btn class="accent" @click="cancle()">
                        Cancel
                    </v-btn>
                    </v-row>
                </v-card-text>
            </v-card>
        </v-dialog>
    </div>
</template>


<script>
    export default {
        data() {
            return {
                complaints: [],
                complaint: {},
                respondtext: '',
                order: {},
                new: false,
                time: {},
                id: '',
                search: '',
                headers: [
                    { text: "Id" },
                    { text: "Complaint" },
                    { text: "Respond" },
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
            getComplaints: function() {
                this.axios.get("api/complaint/toRespond", {headers:{"Authorization": "Bearer " + this.currentUser}})
                .then(r =>
                {
                    if(r.data._embedded.complaints){
                        this.complaints = r.data._embedded.complaints;
                    } else {
                        this.complaints = [];
                    }

                })
                .catch(r => 
                {
                    console.log(r.data);
                })
            },
            respond: function(){
                var dto = {
                    id: this.complaint.id,
                    response: this.respondtext
                }
                this.axios.post("api/complaint/addRespond", dto, {headers:{"Authorization": "Bearer " + this.currentUser}})
                .then(r =>
                {
                    console.log(r.data);
                    this.getComplaints();
                    location.reload();
                })
                .catch(r => 
                {
                    console.log(r.data);
                })
            },
            respondTo: function(item){
                this.complaint = item;
                this.new = true;
            },
            cancle: function() {
                this.new = false;
                this.respondtext = '';
            }
        },
        mounted() {
            this.getComplaints();
        },
    }
</script>


<style scoped>
    #orders-main {
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
    #items-card {
        width: 30%;
        margin: 0 auto;
        justify-content: center;
    }
</style>