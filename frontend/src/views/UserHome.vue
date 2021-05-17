<template>
    <div id="uhome-main" class="secondary">
        <main-navigation>
            <router-link to="/">Home</router-link>
        </main-navigation>
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
                            <span class="info--text">Penalties: <b class="accent--text">{{user.penalties}}</b></span>
                            <router-link to="/user/about">Profile info</router-link>
                        </div>
                    </div>
                </div>
            </div>
            <div id="uhome-panel">
                <div id="panel-container">
                    <v-card tile>
                        <v-card-title class="primary white--text">
                            Pharmacies
                        </v-card-title>
                        <v-card-text>
                            <v-text-field
                            v-model="pharmacySearch"
                            label="Search"
                            append-icon="fa-search"
                            single-line
                            hide-details
                            />
                            <v-data-table
                            :headers="pharmacyHeaders"
                            :items="pharmacies"
                            :items-per-page="5"
                            :search="pharmacySearch">
                            </v-data-table>
                        </v-card-text>
                    </v-card>
                    <v-card tile>
                        <v-card-title class="primary white--text">Active appointments</v-card-title>
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
                                        mdi-chevron-left
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
                                        mdi-chevron-right
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
                                            mdi-menu-down
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
                                    @change="updateRange"
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
                                        <v-btn icon>
                                          <v-icon>mdi-pencil</v-icon>
                                        </v-btn>
                                        <v-toolbar-title v-html="selectedEvent.name"></v-toolbar-title>
                                        <v-spacer></v-spacer>
                                        <v-btn icon>
                                          <v-icon>mdi-heart</v-icon>
                                        </v-btn>
                                        <v-btn icon>
                                          <v-icon>mdi-dots-vertical</v-icon>
                                        </v-btn>
                                      </v-toolbar>
                                      <v-card-text>
                                        <span v-html="selectedEvent.details"></span>
                                      </v-card-text>
                                      <v-card-actions>
                                        <v-btn
                                          text
                                          color="secondary"
                                          @click="selectedOpen = false"
                                        >
                                          Cancel
                                        </v-btn>
                                      </v-card-actions>
                                    </v-card>
                                  </v-menu>
                                </v-sheet>
                            </div>
                        </v-card-text>
                    </v-card>
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
                    <v-card tile>
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
                            </v-data-table>
                        </v-card-text>
                    </v-card>
                    <v-card tile>
                        <v-card-title>E-Prescription drugs</v-card-title>
                        <v-card-text>Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum illo debitis sunt quos recusandae, culpa ducimus magnam qui dicta voluptatibus aperiam similique amet impedit, nostrum consequatur molestias ea reprehenderit sed!</v-card-text>
                    </v-card>
                    <v-card tile>
                        <v-card-title>Active pharmacy subscriptions</v-card-title>
                        <v-card-text>Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum illo debitis sunt quos recusandae, culpa ducimus magnam qui dicta voluptatibus aperiam similique amet impedit, nostrum consequatur molestias ea reprehenderit sed!</v-card-text>
                    </v-card>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
          drugReservations: [],
          drugReservationSearch: '',
          drugReservationHeaders: [

          ],
          //
          ePresc: [],
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
            { text: 'Price', value: 'price' },
            { text: 'Duration', value: 'duration' },
          ],
          //
          consultHistory: [],
          consultHistorySearch: '',
          consultHistoryHeaders: [
            { text: 'Date', value: 'date' },
            { text: 'Price', value: 'price' },
            { text: 'Duration', value: 'duration' },
          ],
          //
            focus: '',
            type: 'month',
            typeToLabel: {
                month: 'Month',
                week: 'Week',
                day: 'Day',
            },
            selectedEvent: {},
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
                penalties: 1,
            },
        }
    },

    methods: {

      getPharmacies: function() {
        this.axios.get("api/pharmacy")
          .then(r => {
            this.pharmacies = r.data._embedded.pharmacies;
            console.log("Pharmacies", this.pharmacies);
          })
          .catch(r => {
            console.log(r.data);
          });
      },

      refreshToken: async function() {
          let jws = this.$store.state.jws;
          if(!jws) {
              this.$router.push("/");
          }
          return this.axios.get("/api/auth/refresh", {headers: {"Authorization": "Bearer " + jws}});
      },

      getProfileImg: function() {
          return require('../assets/placeholder-profile-sq.jpg')
      },

      getUser: function() {
          console.log("Getting user");
          let jws = this.$store.state.jws;
          this.axios.get("api/patient", {headers:{"Authorization": "Bearer " + jws}})
              .then(r => {
                  this.patient = r.data;
              })
              .catch(r => {
                  console.log("Failed to get patient", r.data);
                  this.refreshToken()
                      .then(r => {
                          this.$store.state.jws = r.data;
                          this.$router.go();
                      })
                      .catch(r => {
                          console.log(r.data);
                          this.$router.push("/");
                      });
              });
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
    },

    mounted() {
        // this.getUser();
        this.$refs.calendar.checkChange()
        this.getPharmacies();
    },
}
</script>

<style scoped>
    #uhome-main {
        /* background: #dedede; */
    }

    #uhome-container {
        display: flex;
        flex-direction: row;
        margin: 20px;
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
</style>