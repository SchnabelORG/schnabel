<template>
    <div id="appt-main">
        <div id="appt-container">
            <p>Dermatologist appointments</p>
            <h2>Schedule an appointment</h2>
            <div id="appt-form">
                <b class="err">{{error}}</b>
                <v-data-table
                :headers="headers"
                :items="appointments"
                @click:row="selectRow">
                </v-data-table>
            </div>
            <v-dialog
            v-model="dialog"
            max-width="600px"
            transition="dialog-bottom-transition"
            >

                <div v-if="success" id="success-form">
                    <p id="success-icon"><i class="fa fa-check"></i></p>
                    <p>Appointment scheduled!</p>
                </div>
                <v-card v-else>
                    <v-card-title class="primary white--text">
                        Appointment preview
                        <v-spacer></v-spacer>
                        {{selected.date}}
                    </v-card-title>
                    <v-divider style="width: 100%"></v-divider>
                    <v-card-text id="appt-preview-container">
                        <div id="appt-preview-appt">
                            <p class="info--text">Start:</p>
                            <p>{{selected.start}}</p>
                            <p class="info--text">Duration:</p>
                            <p>{{selected.duration}} min</p>
                        </div>
                        <div id="appt-preview-derm">
                            <v-img src="../assets/placeholder-profile-sq.jpg" height="64px" width="64px"></v-img>
                            <p>{{selected.medicalEmployee.name}}</p>
                            <p><span class="info--text">Rating: </span>{{selected.dermatologistRating}}</p>
                        </div>
                    </v-card-text>
                    <v-divider style="width:100%"></v-divider>
                    <v-card-actions id="appt-preview-actions">
                        <v-btn plain @click="dialog=false">Cancel</v-btn>
                        <v-spacer></v-spacer>
                        <v-btn color="accent" depressed @click="scheduleAppt">Schedule</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            error: '',
            success: false,
            selected: {
                id: '',
                date: '',
                start: '',
                duration: '',
                medicalEmployee: {
                    name: '',
                },
                dermatologistRating: '',
                price: '',
            },
            dialog: false,
            headers: [
                { text: 'Date', value: 'date' },
                { text: 'Start', value: 'start'},
                { text: 'Duration (min.)', value: 'duration'},
                { text: 'Derm.', value: 'medicalEmployee.name'},
                { text: 'Derm. rating', value: 'dermatologistRating' },
                { text: 'Price', value: 'price'}
            ],
            appointments: [],
        }
    },

    methods: {

        getAppointments: function() {
            this.refreshToken()
                .then(rr => {
                    localStorage.jws = rr.data;
                    this.axios.get("api/appointment/dermatology")
                        .then(r => {
                            if (r.data._embedded) {
                                console.log(r.data._embedded);
                                this.appointments = r.data._embedded.appointments;
                            } else {
                                this.appointments = [];
                            }
                        })
                        .catch(() => {
                            this.error = 'Could not get appointments';
                        });
                })
                .catch(() => {
                    this.$router.push("/login");
                });
        },

        scheduleAppt: function() {
            this.error = '';
            this.success = false;
            this.refreshToken()
                .then(rr => {
                    localStorage.jws = rr.data;
                    this.axios.post("api/patient/apptderm",
                    parseInt(this.selected.id),
                    { headers: {
                        "Authorization": "Bearer " + localStorage.jws,
                        "Content-Type" : "application/json",
                    }})
                        .then(() => {
                            this.getAppointments();
                            this.success = true;
                            this.dialog = false;
                        })
                        .catch(() => {
                            this.error = 'Could not schedule the appointment';
                        });

                })
                .catch(() => {
                    this.$router.push("/login");
                });
        },

        selectRow: function(item, event) {
            // eslint-disable-next-line no-unused-vars
            event
            this.selected = item;
            this.dialog = true;
        }
    },

    mounted() {
        this.getAppointments();
    },
}
</script>

<style scoped>

    #appt-main {
        display: grid;
        place-items: center;
        background: #fafafa;
        height: 100vh;
    }

    #appt-container {
        display: flex;
        flex-direction: column;
        text-align: center;
    }

    #appt-container h2{
        font-family: 'Poppins' sans-serif;
        font-weight: 500;
    }

    #appt-form {
        margin-top: 20px;
        background: #fff;
        padding: 20px;
        border: 1px solid #eee;
    }

    #appt-preview-container {
        display: grid;
        grid-template-columns: 1fr 1fr;
        padding: 20px;
    }

    #appt-preview-appt {
        display: flex;
        flex-direction: column;
    }

    #appt-preview-derm {
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    #success-form {
        background: #fff;
        text-align: center;
        height: 100%;
        padding: 20px;
    }

    #success-form p {
        margin: 0px;
    }

    #success-icon {
        font-size: 3rem;
    }


</style>