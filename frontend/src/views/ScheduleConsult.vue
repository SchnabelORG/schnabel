<template>
    <div>
        <search-navigation>
            <router-link to="/">Home</router-link>
        </search-navigation>
        <div id="appt-main">
            <div>
                <div id="appt-container">
                    <p>Get advice</p>
                    <h2>Schedule a pharmacist consult</h2>
                    <b class="err">{{penaltyError}}</b>
                </div>
                <div v-if="success" id="success-form">
                        <p id="success-icon"><i class="fa fa-check"></i></p>
                        <p>Consult scheduled!</p>
                </div>
                <v-stepper
                v-else 
                v-model="steps" id="appt-form">
                    <v-stepper-header>
                        <v-stepper-step
                        :complete="steps > 1"
                        step="1">
                            Select date and time
                        </v-stepper-step>
                        <v-stepper-step
                        :complete="steps > 2"
                        step="2">
                            Pick a pharmacy
                        </v-stepper-step>
                        <v-stepper-step
                        :complete="steps > 3"
                        step="3">
                            Choose your pharmacist
                        </v-stepper-step>
                        <v-stepper-step
                        step="4">
                            Confirm details
                            <!-- Could confirm email? -->
                        </v-stepper-step>
                    </v-stepper-header>
                    <v-stepper-items>
                        <v-stepper-content
                        step="1">
                            <v-form
                            class="appt"
                            v-model="step1"
                            @submit="findPharmacies">
                                <div id="date-and-time-form">
                                    <div>
                                        <v-tabs
                                        v-model="dateTimeTab"
                                        class="date-and-time"
                                        fixed-tabs>
                                            <v-tab><i class="fa fa-calendar-o"></i></v-tab>
                                            <v-tab><i class="fa fa-clock-o"></i></v-tab>
                                        </v-tabs>
                                        <v-tabs-items v-model="dateTimeTab" class="date-and-time">
                                            <v-tab-item class="picker">
                                                <v-date-picker
                                                v-model="apptDate"
                                                :min="new Date().toISOString().substr(0, 10)"
                                                full-width></v-date-picker>
                                            </v-tab-item>
                                            <v-tab-item class="picker">
                                                <v-time-picker v-model="apptTime" full-width format="24hr" :allowed-minutes="allowedMinutes"></v-time-picker>
                                            </v-tab-item>
                                        </v-tabs-items>
                                    </div>
                                    <div id="appt-info">
                                        <h3>Find an appointment appointment for</h3>
                                        <div class="appt-info-date-and-time">
                                            <p class="info--text">Date</p>
                                            <p class="info--text">Time</p>
                                            <p v-if="apptDate">{{apptDate}}</p>
                                            <p v-else class="accent--text" style="font-weight:500">Pick a date</p>
                                            <p v-if="apptTime">{{apptTime}}</p>
                                            <p v-else class="accent--text" style="font-weight:500">Pick a time</p>
                                        </div>
                                        <v-spacer></v-spacer>
                                        <b class="err">{{error}}</b>
                                        <v-btn :disabled="!step1 || penaltyCount >= 3" color="primary" type="submit">Find Pharmacies</v-btn>
                                        <small class="info--text">Appointments last a maximum of 15 minutes!</small>
                                    </div>
                                </div>
                            </v-form>
                        </v-stepper-content>
                        <v-stepper-content
                        step="2">
                            <div id="appt-pharmacies">
                                <h3>Available pharmacies</h3>
                                <v-data-table
                                :items="pharmacies"
                                :headers="pharmacyHeaders"
                                @click:row="selectAppointment">

                                </v-data-table>
                                <v-btn @click='--steps' plain>&#60; Choose a different date</v-btn>
                            </div>
                        </v-stepper-content>
                        <v-stepper-content
                        step="3">
                            <div id="appt-pharmacists">
                                <h3>Available pharmacists</h3>
                                <v-data-table
                                :items="pharmacists"
                                :headers="pharmacistHeaders"
                                @click:row="selectPharmacist">
                                </v-data-table>
                                <v-btn @click='--steps' plain>&#60; Choose a different pharmacy</v-btn>
                            </div>
                        </v-stepper-content>
                        <v-stepper-content
                        step="4">
                            <div id="appt-preview">
                                <div id="appt-preview-container">
                                    <div class="appt-info-date-and-time">
                                        <p class="info--text">Date</p>
                                        <p class="info--text">Time</p>
                                        <p>{{apptDate}}</p>
                                        <p>{{apptTime}}</p>
                                        <p class="info--text">Duration</p>
                                        <p class="info--text">Cost</p>
                                        <p>15 minutes</p>
                                        <p v-if="selectedPharmacy">{{selectedPharmacy.consultPrice}}</p>
                                    </div>
                                    <div id="appt-pharmacist-pr">
                                        <v-img src="../assets/placeholder-profile-sq.jpg" height="64px" width="64px"></v-img>
                                        <div id="appt-pharmacist-info">
                                            <p class="info--text">Name</p>
                                            <p v-if="selectedPharmacist">{{selectedPharmacist.name}} {{selectedPharmacist.surname}}</p>
                                            <p class="info--text">Rating</p>
                                            <p v-if="selectedPharmacist">{{selectedPharmacist.rating}}</p>
                                        </div>
                                    </div>
                                </div>
                                <div id="appt-btns-pr">
                                    <v-btn plain @click="--steps">&#60; Choose a different pharmacist</v-btn>
                                    <v-spacer></v-spacer>
                                    <v-btn color="accent" @click="scheduleAppt">Schedule appointment</v-btn>
                                </div>
                            </div>
                        </v-stepper-content>
                    </v-stepper-items>
                </v-stepper>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            //
            success: false,
            penaltyError: '',
            penaltyCount: 3,
            //
            selectedPharmacist: null,
            pharmacists: [],
            pharmacistHeaders: [
                { text: 'Name', value: 'name' },
                { text: 'Surname', value: 'surname' },
                { text: 'Rating', value: 'rating' },
            ],

            //
            selectedPharmacy: null,
            pharmacies: [],
            pharmacyHeaders: [
                { text: 'Name', value: 'name' },
                { text: 'Price', value: 'consultPrice' },
                { text: 'Score', value: 'score' },
                { text: 'City', value: 'address.city'},
            ],

            //
            error: '',

            //
            apptDate: new Date().toISOString().substr(0, 10),
            apptTime: null,
            dateTimeTab: null,

            //
            steps: 1,
            isFormValid: {
                step2: false,
                step3: false,
            },
        }
    },
    
    methods: {

        scheduleAppt: function() {
            if(this.penaltyCount >= 3) {
                return;
            }
            this.refreshToken()
                .then(rr => {
                    localStorage.jws = rr.data;
                    let consultRequest = {
                        pharmacistId: this.selectedPharmacist.id,
                        start: this.getDateTimeFromString(this.apptDate, this.apptTime).getTime(),
                    };
                    this.axios.post('api/patient/consult', consultRequest, { headers: this.getAHeader()})
                        .then(() => this.success = true)
                        .catch(() => this.error = 'Failed to schedule appointment');
                })
        },

        selectPharmacist: function(item, event) {
            // eslint-disable-next-line no-unused-vars
            event
            this.selectedPharmacist = item;
            ++this.steps;
        },

        selectAppointment: function(item, event) {
            // eslint-disable-next-line no-unused-vars
            event
            this.selectedPharmacy = item;
            this.refreshToken()
                .then(rr => {
                    localStorage.jws = rr.data;
                    let lookupRequest = {
                        pharmacyId: this.selectedPharmacy.id,
                        start: this.getDateTimeFromString(this.apptDate, this.apptTime).getTime(),
                    };
                    this.axios.post('api/pharmacist/free_pharmacy_appt', lookupRequest, { headers: this.aHeader})
                        .then(r => {
                            this.pharmacists = r.data._embedded.pharmacists;
                            ++this.steps;
                        })
                        .catch(r => {
                            console.log(r);
                        });
                })
                .catch(() => {
                    this.$router.push("/");
                });
        },

        findPharmacies: function(e) {
            e.preventDefault();
            this.pharmacies = [];
            this.error = '';
            this.refreshToken()
                .then(rr => {
                    localStorage.jws = rr.data;
                    let timestamp = this.getDateTimeFromString(this.apptDate, this.apptTime).getTime();
                    this.axios.get('api/pharmacy/phappt/' + timestamp, { headers: this.aHeader })
                        .then(r => {
                            console.log(r);
                            if (r.data._embedded) {
                                ++this.steps;
                                this.pharmacies = r.data._embedded.pharmacies;
                            } else {
                                this.error = 'No pharmacies found';
                            }
                        })
                        .catch(r => {
                            console.log(r);
                            this.error = 'No pharmacies found';
                        });
                })
                .catch(() => {
                    // this.$router.push("/");
                });
        },

        allowedMinutes: m => m % 5 === 0,
    },

    computed: {
        step1: {
            get: function() {
                return !!this.apptDate && !!this.apptTime;
            },
            
            set: function() {
                this.apptDate = new Date().toISOString().substr(0, 10);
                this.apptTime = null;
            }
        }
    },

    mounted() {
        this.refreshToken()
            .then(rr => {
                localStorage.jws = rr.data;
                this.axios.get('api/penalty/patient/count', {headers:this.getAHeader()})
                    .then(r => {
                        this.penaltyCount = r.data;
                        if(this.penaltyCount >= 3) {
                            this.penaltyError = "3+ Penalties, functionality disabled";
                        }
                    });
            }).catch(() => this.$router.push('/login'));
    },
}
</script>

<style scoped>

    #appt-main {
        display: grid;
        place-items: center;
        background: #fafafa;
        height: 92vh;
    }

    #appt-container {
        display: flex;
        flex-direction: column;
        justify-content: center;
        text-align: center;
    }

    #appt-form {
        margin-top: 20px;
    }

    .appt {
        display: flex;
        flex-direction: column;
        background: #fff;
    }

    #date-and-time-form {
        display: grid;
        grid-template-columns: 1fr 1fr;
    }

    .date-and-time {
        max-width: 30rem;
    }

    #appt-info {
        display: flex;
        flex-direction: column;
        padding: 20px;
    }

    #appt-info h3 {
        font-weight: 500;
    }

    .appt-info-date-and-time {
        display: grid;
        grid-template-columns: 1fr 1fr;
        padding: 5px;
    }

    #appt-preview {
        display: flex;
        flex-direction: column;
    }

    #appt-preview-container {
        display: grid;
        grid-template-columns: 1fr 1fr;
    }

    #appt-pharmacist-pr {
        display: flex;
        flex-direction: column;
        justify-content: flex-end;
        align-items: center;
    }

    #appt-pharmacist-info {
        display: grid;
        grid-template-columns: 1fr 1fr;
    }

    #appt-btns-pr {
        display: flex;
        flex-direction: row;
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