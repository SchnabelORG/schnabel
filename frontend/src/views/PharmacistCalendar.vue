<template>
    <div id="calendar-main">
        <v-row class="fill-height">
            <v-col>
                <v-sheet height="64">
                    <v-toolbar
                        flat>
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
                            <i class="fa fa-arrow-left"></i>
                        </v-btn>
                        <v-btn
                            fab
                            text
                            small
                            color="grey darken-2"
                            @click="next"
                            >
                            <i class="fa fa-arrow-right"></i>
                        </v-btn>
                        <v-spacer></v-spacer>
                        <v-toolbar-title v-if="$refs.calendar">
                            <v-menu v-if="type == 'month'"
                                ref="menu"
                                v-model="menu"
                                :close-on-content-click="false"
                                transition="scale-transition"
                                offset-y
                                min-width="auto"
                                >
                                <template v-slot:activator="{ on, attrs }">
                                    <v-btn
                                        outlined
                                        class="mr-4"
                                        color="grey darken-2"
                                        center
                                        v-bind="attrs"
                                        v-on="on"
                                        >
                                        {{ $refs.calendar.title }}
                                    </v-btn>
                                </template>
                                <v-date-picker
                                    v-model="picker"
                                    type="month"
                                    min="1950-01-01"
                                    @change="moveView()"
                                ></v-date-picker>
                            </v-menu>
                            <div v-else>
                                 {{ $refs.calendar.title }}
                            </div>
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
                                    <i class="fa fa-arrow-down fa-fw"></i>
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
                                <v-list-item @click="type = '4day'">
                                    <v-list-item-title>4 days</v-list-item-title>
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
                                <v-toolbar-title v-html="selectedEvent.name"></v-toolbar-title>
                                <v-spacer></v-spacer>
                                <!--<v-btn icon>
                                     <i class="fa fa-arrow-down fa-fw"></i>
                                </v-btn>-->
                            </v-toolbar>
                            <v-card-text v-if="selectedOpen">
                                <h3>{{selectedEvent.details.period.startTime.slice(0,10)}} ({{selectedEvent.details.period.startTime.slice(11,16)}}-{{selectedEvent.details.period.endTime.slice(11,16)}})</h3>
                                <h4>{{selectedEvent.details.patient.name}} {{selectedEvent.details.patient.surname}}</h4>
                                <span>{{selectedEvent.details.patient.email}}</span>
                            </v-card-text>
                            <v-card-actions>
                                <v-btn
                                    text
                                    color="primary"
                                    @click="selectedOpen = false"
                                    >
                                    Close
                                </v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-menu>
                </v-sheet>
            </v-col>
        </v-row>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                focus: '',
                type: 'month',
                typeToLabel: {
                    month: 'Month',
                    week: 'Week',
                    day: 'Day',
                    '4day': '4 Days',
                },
                pharmacist: {},
                selectedEvent: {},
                selectedElement: null,
                selectedOpen: false,
                allAppointments: [],
                events: [],
                currentDate: new Date(),
                picker: new Date().toISOString().substr(0, 10),
                menu: false,
            }
        },
        mounted () {
            this.getPharmacist();
        },
        methods: {
            refreshToken: async function() {
                let jws = window.localStorage.getItem('jwt');
                if(!jws) {
                    this.$router.push("/");
                }
                return this.axios.get("/api/auth/refresh", {headers: {"Authorization": "Bearer " + jws}});
            },
             getPharmacist: function() {
                console.log("Getting pharmacist");
                let jws = window.localStorage.getItem('jwt');
                console.log(jws)
                this.axios.get("api/pharmacist", {headers:{"Authorization": "Bearer " + jws}})
                    .then(response => {
                        console.log(response.data);
                        this.pharmacist = response.data;
                        this.getAllAppointments()
                    })
                    .catch(response => {
                        console.log("Failed to get pahrmacist", response.data);
                        this.refreshToken()
                            .then(response => {
                                window.localStorage.jwt = response.data;
                                this.$router.go();
                            })
                            .catch(response => {
                                console.log(response.data);
                                this.$router.push("/");
                            });
                    });
            },
            getAllAppointments: function(){
                let jws = window.localStorage.getItem('jwt');
                this.axios.get("api/appointment/appbyemployee/" + this.pharmacist.id, {headers:{"Authorization": "Bearer " + jws}})
                    .then(response =>
                    {
                        this.allAppointments = response.data._embedded.appointments;
                        this.updateRange();
                    })
                    .catch(response =>
                    {
                        console.log(response.data);
                    });
            },
            moveView: function(){
                this.type = 'month';
                var moveFor = 12*(this.currentDate.getFullYear()-parseInt(this.picker.slice(0,4))) + (this.currentDate.getMonth() + 1 - parseInt(this.picker.slice(5)));
                this.currentDate.setMonth(this.currentDate.getMonth() - moveFor);
                this.menu = false;
                
                this.$refs.calendar.move(-moveFor)
            },
            viewDay: function({ date }) {
                this.focus = date
                this.type = 'day'
            },
            getEventColor: function(event) {
                return event.color
            },
            setToday: function() {
                this.currentDate = new Date();
                this.picker = new Date().toISOString().substr(0, 10);
                this.focus = ''
            },
            prev: function() {
                this.$refs.calendar.prev()
                var month = this.focus.slice(5,7) - 1
                var year = this.focus.slice(0,4)
                this.currentDate.setMonth(month);
                this.currentDate.setFullYear(year);
                this.picker = new Date(this.currentDate).toISOString().substr(0, 10);
            },
            next: function() {
                this.$refs.calendar.next()
                var month = this.focus.slice(5,7) - 1 
                var year = this.focus.slice(0,4)
                this.currentDate.setMonth(month);
                this.currentDate.setFullYear(year);
                this.picker = new Date(this.currentDate).toISOString().substr(0, 10);
            },
            showEvent: function({ nativeEvent, event }) {
                const open = () => {
                    this.selectedEvent = event
                    this.selectedElement = nativeEvent.target
                    setTimeout(() => {
                    this.selectedOpen = true
                    }, 10)
                }

                if (this.selectedOpen) {
                    this.selectedOpen = false
                    setTimeout(open, 10)
                } else {
                    open()
                }

                nativeEvent.stopPropagation()
            },
            updateRange: function() {
                const events = []
                for (let i = 0; i < this.allAppointments.length; i++) {
                    const start = new Date(this.allAppointments[i].period.startTime)
                    const end = new Date(this.allAppointments[i].period.endTime)

                    events.push({
                    name: "Consultation",
                    details: this.allAppointments[i],
                    start: start,
                    end: end,
                    color: 'blue',
                    timed: true,
                    })
                }

                this.events = events
            },
        }
    }
    
</script>

<style scoped>
    #calendar-main{
        min-height: 92vh;
        background-color: #fbecdd;
    }
</style>