<template>
    <div id="uhome-main" class="info">
        <search-navigation>
            <router-link to="/">Home</router-link>
        </search-navigation>
        <div id="uhome-container">
            <div id="uhome-info">
                <div id="info-container">
                    <div id="profile" class="info-card">
                        <v-avatar>
                            <img :src="getProfileImg()" alt="Profile img">
                        </v-avatar>
                        <div id="profile-info">
                            <h3 class="primary--text">{{patient.name}} {{patient.surname}}</h3>
                            <span class="info--text">Membership: <b class="accent--text">{{user.membershipType}}</b></span>
                            <span class="info--text">Penalties: <b class="accent--text">{{penaltyCount}}</b></span>
                            <router-link to="/user/about">Profile info</router-link>
                        </div>
                    </div>
                    <div class="info-card">
                      <v-tooltip top v-if="penaltyCount >= 3">
                        <template v-slot:activator="{ on, attrs }">
                          <h3 :class="penaltyCount >= 3 ? 'err' : 'info--text'" v-bind="attrs" v-on="on">Appointments</h3>
                        </template>
                        <span>Too many penalties, disabled</span>
                      </v-tooltip>
                      <h3 v-else class="info--text">Appointments</h3>
                      <div class="d-flex flex-column align-start">
                        <v-btn :disabled="penaltyCount >= 3" plain to="/consult">&#62; Schedule a consult</v-btn>
                        <v-btn :disabled="penaltyCount >= 3" plain to="/pharmacysearch">&#62; Schedule a derm. appt.</v-btn>
                      </div>
                    </div>
                    <div class="info-card">
                      <v-tooltip top v-if="penaltyCount >= 3">
                        <template v-slot:activator="{ on, attrs }">
                          <h3 :class="penaltyCount >= 3 ? 'err' : 'info--text'" v-bind="attrs" v-on="on">Drugs</h3>
                        </template>
                        <span>Too many penalties, disabled</span>
                      </v-tooltip>
                      <h3 v-else class="info--text">Drugs</h3>
                        <v-btn :disabled="penaltyCount >= 3" plain to="/drugsearch">&#62; Make drug reservation</v-btn>
                    </div>
                    <div class="info-card">
                      <h3 class="info--text">Feedback</h3>
                      <div class="d-flex flex-column align-start">
                        <v-btn plain to="/rating">&#62; Leave a rating</v-btn>
                        <v-btn plain to="/">&#62; Submit a complaint</v-btn>
                      </div>
                    </div>
                    <div v-if="penalties" class="info-card">
                      <h3 class="info--text">Penalties</h3>
                      <div class="d-flex flex-column align-start">
                        <div v-for="p in penalties" :key="p.id" class="penalty red--text">
                          <div>{{p.reason}}</div>
                          <div>{{p.date}}</div>
                        </div>
                      </div>
                    </div>
                </div>
            </div>
            <div id="uhome-panel">
                <div id="panel-container">
                  <v-tabs
                  grow
                  slider-color="accent"
                  v-model="tabs">
                    <v-tab>Overview</v-tab>
                    <v-tab>Appointments</v-tab>
                    <v-tab>Drugs</v-tab>
                    <v-tab>Subscriptions & e-prescriptions</v-tab>
                  </v-tabs>
                  <v-tabs-items
                  v-model="tabs">
                    <v-tab-item>
                      <v-card tile>
                          <v-card-title class="primary white--text">Appointments and consults</v-card-title>
                          <v-card-text>
                              <div id="active-app-container">
                                  <v-sheet height="64">
                                    <v-toolbar
                                      flat
                                    >
                                      <v-btn
                                        outlined
                                        class="mr-4"
                                        color="grey darken-2"
                                        @click="setToday"
                                      >
                                        Today
                                      </v-btn>
                                      <v-btn
                                        fab
                                        text
                                        small
                                        color="grey darken-2"
                                        @click="prev"
                                      >
                                        <v-icon small>
                                          fa-angle-left
                                        </v-icon>
                                      </v-btn>
                                      <v-btn
                                        fab
                                        text
                                        small
                                        color="grey darken-2"
                                        @click="next"
                                      >
                                        <v-icon small>
                                          fa-angle-right
                                        </v-icon>
                                      </v-btn>
                                      <v-toolbar-title v-if="$refs.calendar">
                                        {{ $refs.calendar.title }}
                                      </v-toolbar-title>
                                      <v-spacer></v-spacer>
                                      <v-menu
                                        bottom
                                        right
                                      >
                                        <template v-slot:activator="{ on, attrs }">
                                          <v-btn
                                            outlined
                                            color="grey darken-2"
                                            v-bind="attrs"
                                            v-on="on"
                                          >
                                            <span>{{ typeToLabel[type] }}</span>
                                            <v-icon right>
                                              fa-angle-down
                                            </v-icon>
                                          </v-btn>
                                        </template>
                                        <v-list>
                                          <v-list-item @click="type = 'day'">
                                            <v-list-item-title>Day</v-list-item-title>
                                          </v-list-item>
                                          <v-list-item @click="type = 'week'">
                                            <v-list-item-title>Week</v-list-item-title>
                                          </v-list-item>
                                          <v-list-item @click="type = 'month'">
                                            <v-list-item-title>Month</v-list-item-title>
                                          </v-list-item>
                                        </v-list>
                                      </v-menu>
                                    </v-toolbar>
                                  </v-sheet>
                                  <v-sheet height="600">
                                    <v-calendar
                                      ref="calendar"
                                      v-model="focus"
                                      color="primary"
                                      :events="events"
                                      :event-color="getEventColor"
                                      :type="type"
                                      @click:event="showEvent"
                                      @click:more="viewDay"
                                      @click:date="viewDay"
                                    ></v-calendar>
                                    <v-menu
                                      v-model="selectedOpen"
                                      :close-on-content-click="false"
                                      :activator="selectedElement"
                                      offset-x
                                    >
                                      <v-card
                                        color="grey lighten-4"
                                        min-width="350px"
                                        flat
                                      >
                                        <v-toolbar
                                          :color="selectedEvent.color"
                                          dark
                                        >
                                          <v-toolbar-title v-html="selectedEvent.name"></v-toolbar-title>
                                        </v-toolbar>
                                        <v-card-text id="appt-preview-container">
                                          <div id="appt-preview">
                                              <p class="info--text">Start:</p>
                                              <p>{{selectedEvent.appt.start}}</p>
                                              <p class="info--text">Duration:</p>
                                              <p>{{selectedEvent.appt.duration}} min</p>
                                          </div>
                                          <div id="appt-preview-emp">
                                              <v-img src="../assets/placeholder-profile-sq.jpg" height="64px" width="64px"></v-img>
                                              <p>{{selectedEvent.appt.medicalEmployee.name}}</p>
                                              <p><span class="info--text">Rating: </span>{{selectedEvent.appt.dermatologistRating}}</p>
                                          </div>
                                        </v-card-text>
                                        <v-card-actions>
                                          <v-btn
                                            text
                                            color="accent"
                                            @click="cancelAppt"
                                            :disabled="!isCancellable(selectedEvent.start)"
                                          >
                                            Cancel appointment
                                          </v-btn>
                                        </v-card-actions>
                                      </v-card>
                                    </v-menu>
                                  </v-sheet>
                              </div>
                          </v-card-text>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item>
                      <v-card tile>
                          <v-card-title class="primary white--text">Pharmacist consult history</v-card-title>
                          <v-card-text>
                              <v-text-field
                              v-model="consultHistorySearch"
                              label="Search"
                              append-icon="fa-search"
                              single-line
                              hide-details
                              />
                              <v-data-table
                              :headers="consultHistoryHeaders"
                              :items="consultHistory"
                              :items-per-page="5"
                              :search="consultHistorySearch">
                              </v-data-table>
                          </v-card-text>
                      </v-card>
                      <v-card tile>
                          <v-card-title class="primary white--text">Dermatologist appointment history</v-card-title>
                          <v-card-text>
                              <v-text-field
                              v-model="dermApptHistorySearch"
                              label="Search"
                              append-icon="fa-search"
                              single-line
                              hide-details
                              />
                              <v-data-table
                              :headers="dermApptHistoryHeaders"
                              :items="dermApptHistory"
                              :items-per-page="5"
                              :search="dermApptHistorySearch">
                              </v-data-table>
                          </v-card-text>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item>
                      <v-card tile>
                          <v-card-title class="primary white--text">Drug reservations</v-card-title>
                          <v-card-text>
                              <v-text-field
                              v-model="drugReservationSearch"
                              label="Search"
                              append-icon="fa-search"
                              single-line
                              hide-details
                              />
                              <v-data-table
                              :headers="drugReservationHeaders"
                              :items="drugReservations"
                              :items-per-page="5"
                              :search="drugReservationSearch">
                              <template v-slot:item.cancel="props">
                                <v-btn :disabled="!isCancellable(new Date(props.item.endOfReservation))" plain @click="cancelDrugReservation(props.item)">Cancel</v-btn>
                              </template>
                              </v-data-table>
                          </v-card-text>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item>
                      <div class="d-flex flex-row justify-space-between">
                        <v-card tile class="flex-grow-1">
                            <v-card-title class="primary white--text">E-Prescriptions</v-card-title>
                            <v-card-text>
                                <v-text-field
                                v-model="ePrescSearch"
                                label="Search"
                                append-icon="fa-search"
                                single-line
                                hide-details
                                />
                                <v-data-table
                                :headers="ePrescHeaders"
                                :items="ePresc"
                                :items-per-page="5"
                                :search="ePrescSearch">
                                </v-data-table>
                            </v-card-text>
                        </v-card>
                        <v-card tile>
                            <v-card-title class="primary white--text">E-Prescription drugs</v-card-title>
                            <v-card-text>
                              <v-data-table
                              :headers="eDrugHeaders"
                              :items="eDrugs">

                              </v-data-table>
                            </v-card-text>
                        </v-card>
                      </div>
                    <v-card tile>
                        <v-card-title class="primary white--text">Active pharmacy subscriptions</v-card-title>
                        <v-card-text>
                          <v-data-table
                          :headers="subHeaders"
                          :items="subs">

                          </v-data-table>
                        </v-card-text>
                    </v-card>
                    </v-tab-item>
                  </v-tabs-items>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
          penaltyCount: 0,
          penalties: [
          ],
          //
          tabs: null,
          //
          subHeaders: [
            { text: 'Pharmacy', value: 'pharmacyName' },
            { text: 'Valid until', value: 'endTime' },
          ],
          subs: [
            { pharmacyName: 'Liman Schnabel', endTime: '2021-07-02' },
          ],
          //
          eDrugHeaders: [
            { text: 'Name', value: 'name' },
            { text: 'Picked up', value: 'pickupDate' },
          ],
          eDrugs: [
            { name: 'Vicodin 5 mg/500 mg', pickupDate: '2021-01-13' },
            { name: 'Vicodin 5 mg/500 mg', pickupDate: '2021-02-13' },
            { name: 'Vicodin 5 mg/500 mg', pickupDate: '2021-03-08' },
          ],
          //
          drugReservations: [],
          drugReservationSearch: '',
          drugReservationHeaders: [
            { text: 'Reserved date', value: 'reservationDate' },
            { text: 'Pickup date', value: 'endOfReservation' },
            { text: 'Status', value: 'status' },
            { text: '', value: 'cancel'},
          ],
          //
          ePresc: [
            { med: 'Cyclopentanoperhydrophenanthrene' ,date: '2021-05-10', status: 'Parsed' },
            { med: 'Vicodin' ,date: '2021-05-29', status: 'Rejected' },
            { med: 'Vicodin' ,date: '2021-06-1', status: 'Rejected' },
            { med: 'Cyclomicin' ,date: '2021-06-3', status: 'New' },
            { med: 'Vicodin' ,date: '2021-06-4', status: 'New' },
          ],
          ePrescSearch: '',
          ePrescHeaders: [
            { text: 'Date', value: 'date' },
            { text: 'Status', value: 'status' },
          ],
          //
          dermApptHistory: [],
          dermApptHistorySearch: '',
          dermApptHistoryHeaders: [
            { text: 'Date', value: 'date' },
            { text: 'Start', value: 'start' }, 
            { text: 'Price', value: 'price' },
            { text: 'Duration (min.)', value: 'duration' },
            { text: 'Derm.', value: 'medicalEmployee.name' },
          ],
          //
          consultHistory: [],
          consultHistorySearch: '',
          consultHistoryHeaders: [
            { text: 'Date', value: 'date' },
            { text: 'Start', value: 'start' },
            { text: 'Price', value: 'price' },
            { text: 'Duration (min.)', value: 'duration' },
            { text: 'Pharmacist', value: 'medicalEmployee.name' },
          ],
          //
            focus: '',
            type: 'month',
            typeToLabel: {
                month: 'Month',
                week: 'Week',
                day: 'Day',
            },
            selectedEvent: {
                appt: {
                    medicalEmployee: {},
                },
            },
            selectedElement: null,
            selectedOpen: false,
            events: [],
            colors: ['green', 'indigo',],
            names: ['Pharmacist appt.', 'Dermatological appt.',],
            //
            patient: {},
            pharmacySearch: '',
            pharmacyHeaders: [
                { text: 'Name', value: 'name' },
                { text: 'City', value: 'address.city' },
                { text: 'Avg. score', value: 'avgScore' },
            ],
            pharmacies: [],
            user: {
                name: "Petar",
                surname: "Petrovic",
                membershipType: "gold",
            },
        }
    },

    methods: {
        getPenalties: function() {
          this.refreshToken()
            .then(rr => {
              localStorage.jws = rr.data;
              this.axios.get('api/penalty/patient', {headers: this.getAHeader()})
                .then(r => {
                  if(r.data._embedded) {
                    this.penalties = r.data._embedded.penalties;
                  } else {
                    this.penalties = [];
                  }
                })
            })
        },

        cancelDrugReservation: function(item) {
          this.refreshToken()
            .then(rr => {
              localStorage.jws = rr.data;
              this.axios.delete('api/dreservation/' + item.id, {headers: this.getAHeader()})
                .then(() => this.getDrugHistory())
            }).catch(() => this.$router.push('/'));
        },

        getConsultAppts: function() {
          this.refreshToken()
            .then(rr => {
              localStorage.jws = rr.data;
              this.axios.get('api/patient/consult', {headers: this.getAHeader()})
                .then(r => {
                  if(r.data._embedded) {
                    r.data._embedded.appointments.forEach(a => {
                        let startDate = this.getDateTimeFromString(a.date, a.start);
                        this.events.push({
                            name: 'Pharm. consult.',
                            start: startDate,
                            end: new Date(startDate.getTime() + a.duration * 60000),
                            color: this.colors[0],
                            timed: true,
                            appt: a,
                        });
                    });
                    this.$refs.calendar.checkChange()
                  }
                })
            })
        },

        getDrugHistory: function() {
          this.refreshToken()
            .then(rr => {
              localStorage.jws = rr.data;
              this.axios.get('api/dreservation/patient', {headers: this.getAHeader()})
                .then(r => {
                  if(r.data._embedded) {
                    this.drugReservations = r.data._embedded.drugs_reservations;
                    this.drugReservations.forEach(dr => {
                      dr.reservationDate = dr.reservationDate.substr(0, 10);
                      dr.endOfReservation = dr.endOfReservation.substr(0, 10);
                      dr.status = dr.taken ? 'Picked up' : 'Not picked up';
                    });
                  } else {
                    this.drugReservations = [];
                  }
                })
            })
        },

        getConsultHistory: function() {
          this.refreshToken()
            .then(rr => {
              localStorage.jws = rr.data;
              this.axios.get('api/appointment/patient/consult', {headers: this.getAHeader()})
                .then(r => {
                  if(r.data._embedded) {
                    this.consultHistory = r.data._embedded.appointments;
                  } else {
                    this.consultHistory = [];
                  }
                })
            }).catch(() => this.$router.push('/'));
        },

        getDermHistory: function() {
          this.refreshToken()
            .then(rr => {
              localStorage.jws = rr.data;
              this.axios.get('api/appointment/patient/dermatology', {headers: this.getAHeader()})
                .then(r => {
                  if(r.data._embedded) {
                    this.dermApptHistory = r.data._embedded.appointments;
                  } else {
                    this.dermApptHistory = [];
                  }
                })
            }).catch(() => this.$router.push('/'));
        },

        getAppointments: function() {
            this.events = [];
            this.getDermAppts();
            this.getConsultAppts();
        },

        addDaysToDate: function(date, days) {
            let result = new Date(date);
            result.setDate(result.getDate() + days);
            return result;
        },

        cancelAppt: function() {
            this.selectedOpen = false;
            this.refreshToken()
                .then(rr => {
                    localStorage.jws = rr.data;
                    this.axios.post('api/patient/appointemnt/cancel',
                        this.selectedEvent.appt.id,
                        { headers : {
                            'Content-Type' : 'application/json',
                            'Authorization' : 'Bearer ' + localStorage.jws,
                        }})
                        .then(() => {
                            this.getAppointments();
                        });
                })
                .catch(() => {
                    this.$router.push("/");
                })
        },

        getDermAppts: function() {
            this.refreshToken()
                .then(rr => {
                    localStorage.jws = rr.data;
                    this.axios.get("api/patient/apptderm", { headers: { 'Authorization' : 'Bearer ' + localStorage.jws } })
                        .then(r => {
                            if(r.data._embedded) {
                                r.data._embedded.appointments.forEach(a => {
                                    let startDate = this.getDateTimeFromString(a.date, a.start);
                                    this.events.push({
                                        name: 'Derm. appt.',
                                        start: startDate,
                                        end: new Date(startDate.getTime() + a.duration * 60000),
                                        color: this.colors[1],
                                        timed: true,
                                        appt: a,
                                    });
                                });
                                this.$refs.calendar.checkChange()
                            }
                        })
                })
                .catch(() => {
                    this.$router.push("/");
                })
        },

        getPharmacies: function() {
        this.axios.get("api/pharmacy")
            .then(r => {
                this.pharmacies = r.data._embedded.pharmacies;
            })
        },

      getProfileImg: function() {
          return require('../assets/placeholder-profile-sq.jpg')
      },

      getUser: function() {
          this.refreshToken()
            .then(rr => {
                localStorage.jws = rr.data;
                this.axios.get("api/patient", {headers:{"Authorization": "Bearer " + localStorage.jws}})
                    .then(r => {
                        this.patient = r.data;
                    })
                    .catch(() => {
                        this.$router.push("/");
                    });
            })
            .catch(() => {
                this.$router.push("/");
            });
        //   let jws = this.$store.state.jws;
      },
        //
        viewDay ({ date }) {
        this.focus = date
        this.type = 'day'
      },
      getEventColor (event) {
        return event.color
      },
      setToday () {
        this.focus = ''
      },
      prev () {
        this.$refs.calendar.prev()
      },
      next () {
        this.$refs.calendar.next()
      },
      showEvent ({ nativeEvent, event }) {
        const open = () => {
          this.selectedEvent = event
          this.selectedElement = nativeEvent.target
          requestAnimationFrame(() => requestAnimationFrame(() => this.selectedOpen = true))
        }

        if (this.selectedOpen) {
          this.selectedOpen = false
          requestAnimationFrame(() => requestAnimationFrame(() => open()))
        } else {
          open()
        }

        nativeEvent.stopPropagation()
      },
      // updateRange ({ start, end }) {
      //   const events = []

      //   const min = new Date(`${start.date}T00:00:00`)
      //   const max = new Date(`${end.date}T23:59:59`)
      //   const days = (max.getTime() - min.getTime()) / 86400000
      //   const eventCount = this.rnd(days, days + 20)

      //   for (let i = 0; i < eventCount; i++) {
      //     const allDay = this.rnd(0, 3) === 0
      //     const firstTimestamp = this.rnd(min.getTime(), max.getTime())
      //     const first = new Date(firstTimestamp - (firstTimestamp % 900000))
      //     const secondTimestamp = this.rnd(2, allDay ? 288 : 8) * 900000
      //     const second = new Date(first.getTime() + secondTimestamp)

      //     events.push({
      //       name: this.names[this.rnd(0, this.names.length - 1)],
      //       start: first,
      //       end: second,
      //       color: this.colors[this.rnd(0, this.colors.length - 1)],
      //       timed: !allDay,
      //     })
      //   }

      //   this.events = events
      // },
      rnd (a, b) {
        return Math.floor((b - a + 1) * Math.random()) + a
      },
        isCancellable: function(start) {
            return start >= this.addDaysToDate(new Date(), 1);
        }
    },

    computed: {
    },

    mounted() {
        this.refreshToken()
          .then(rr => {
            localStorage.jws = rr.data;
            this.axios.get('api/penalty/patient/count', {headers: this.getAHeader()})
              .then(r => {
                this.penaltyCount = r.data;
              })
          }).catch(() => this.$router.push('/login'));
        this.getPenalties();
        this.getUser();
        this.getPharmacies();
        this.getAppointments();
        this.getDermHistory();
        this.getConsultHistory();
        this.getDrugHistory();
        this.$refs.calendar.checkChange()
    },
}
</script>

<style scoped>

  #uhome-main {
    /* background: #eee */
  }

    #uhome-container {
        display: flex;
        flex-direction: row;
        margin: 20px;
        min-height: 100vh;
    }

    #uhome-panel {
      flex-grow: 1;
    }

    #uhome-info {
        background: #f8f8f8;
        min-width: 20vw;
    }

    #profile {
        display: flex;
        flex-direction: row;
        justify-content: space-around;
        align-items: center;
    }

    #profile h3 {
        font-family: 'Poppins', sans-serif;
        font-weight: 500;
        text-transform: uppercase;
    }

    #profile-info {
        display: flex;
        flex-direction: column;
    }

    #profile-info span {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
    }

    #profile-info b {
        font-weight: 500;
    }
    
    .info-card {
        background: #fbfbfb;
        border-bottom: 1px solid #eee;
        padding: 10px;
    }

    #appt-preview-container {
        display: grid;
        grid-template-columns: 1fr 1fr;
    }

    .penalty {
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      width: 100%;
    }
</style>
