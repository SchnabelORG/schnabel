<template>
    <div id="appt-main">
        <div id="appt-container">
            <p>Dermatologist appointments</p>
            <h2>Schedule an appointment</h2>
            <div id="appt-form">
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
                <v-card>
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
                            <p>{{selected.dermatologist}}</p>
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
            selected: {},
            dialog: false,
            headers: [
                { text: 'Date', value: 'date' },
                { text: 'Start', value: 'start'},
                { text: 'Duration (min.)', value: 'duration'},
                { text: 'Derm.', value: 'dermatologist'},
                { text: 'Derm. rating', value: 'dermatologistRating' },
            ],
            appointments: [
                {
                    date: '06.06.2021.',
                    start: '12:33',
                    duration: 12,
                    dermatologist: "Petar Petrovic",
                    dermatologistRating: 3,
                },
            ],
        }
    },

    methods: {

        scheduleAppt: function() {

        },

        selectRow: function(item, event) {
            // eslint-disable-next-line no-unused-vars
            event
            this.selected = item;
            this.dialog = true;
        }
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

</style>