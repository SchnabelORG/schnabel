<template>
    <div id="pharm-home-main">
        <v-row>
            <v-col id="container">
                <v-row id="pharm-home-table">
					<v-data-table :headers="headers"
									:items="allAppointments"
									:search="search"
                                    :footer-props="{
                                    'items-per-page-options': [5, 10, 20, 30]}"
                                    :items-per-page="5"
                                    class="elevation-3">
                        <template v-slot:top>
                            <v-toolbar flat class="pharm-home-table-toolbar info">
                                <v-spacer></v-spacer>
                                <v-toolbar-title class="primary--text"><b>All your patients</b></v-toolbar-title>
                                <v-spacer></v-spacer>
                            </v-toolbar>
                            <v-text-field v-model="search"
									label="Search your patients"
									hide-details />
                        </template>

						<template v-slot:item="row">
							<tr>
								
								<td>{{row.item.patient.name}}</td>
								<td>{{row.item.patient.surname}}</td>
                                <td>{{row.item.period.startTime.slice(0,10)}}</td>
                                <td>{{row.item.period.startTime.slice(11)}}</td>
							</tr>
						</template>
					</v-data-table>
                </v-row>
                <v-row>
                    <v-card v-if="sparkLineShow"
                        class="pharm-home-graph info primary--text"
                        dark>
                        <v-card-title class="justify-center">
                            <div class="display-1">
                            Last 7 days consultation
                            </div>
                        </v-card-title>
                        <v-card-text>
                            <v-sheet color="#f4f9fc">
                            <v-sparkline
                                :value="sparkLineValues"
                                :auto-draw="true"
                                :auto-draw-duration="2000"
                                color="primary"
                                height="100"
                                padding="24"
                                stroke-linecap="round"
                                smooth>
                                <template v-slot:label="item">
                                {{ item.value }}
                                </template>
                            </v-sparkline>
                            </v-sheet>
                        </v-card-text>
                    
                        
                    </v-card>
                </v-row>
            </v-col>
            <v-col id="pharm-home-timeline">
                <v-timeline
                    :reverse="true"
                    dense>
                    <v-timeline-item>
                        <v-card>
                            <v-card-title class="justify-center accent white--text">
                                <div>Todays consultation</div>
                            </v-card-title>
                        </v-card>
                    </v-timeline-item>
                    <v-timeline-item  v-for="item in todayAppointments" v-bind:key="item.id">
                        <v-card>
                            <v-card-title class="info primary--text">
                            <b>Consultation at {{item.period.startTime.slice(11)}} ({{item.period.endTime.slice(11)}})</b>
                            </v-card-title>
                            <v-card-text>
                                <h3>{{item.patient.name}} {{item.patient.surname}}</h3>
                                <h3>{{item.patient.email}}</h3>
                            </v-card-text>
                        </v-card>
                    </v-timeline-item>
                    <v-timeline-item>
                        <v-card>
                            <v-card-title class="justify-center accent white--text">
                                <div v-if="this.perviousWorkingDay === ''">Nothing here</div>
                                <div v-else>Previous working day ({{this.perviousWorkingDay.slice(0,10)}})</div>
                            </v-card-title>
                        </v-card>
                    </v-timeline-item>
                    <v-timeline-item  v-for="item in previousDayAppointments" v-bind:key="item.id">
                        <v-card>
                            <v-card-title class="info primary--text">
                            <b>Consultation at {{item.period.startTime.slice(11)}} ({{item.period.endTime.slice(11)}})</b>
                            </v-card-title>
                            <v-card-text>
                                <h3>{{item.patient.name}} {{item.patient.surname}}</h3>
                                <h3>{{item.patient.email}}</h3>
                            </v-card-text>
                        </v-card> 
                    </v-timeline-item>
                </v-timeline>
            </v-col>
        </v-row>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                sparkLineValues: [0,0,0,0,0,0,0],
                todayAppointments: [],
                previousDayAppointments: [],
                allAppointments: [],
                perviousWorkingDay: '',
                sparkLineShow: false,
                search: "",
                dermatologist: {},
                headers: [
                    { text: "Patient's name", value: "patient.name", },
                    { text: "Patient's surname", value: "patient.surname", },
                    { text: "Consultation date", value: "period.startTime", },
                    { text: "Consultation time"},
				],
            }
        },
        methods:{
            refreshToken: async function() {
                //let jws = window.localStorage.getItem('jwt');
                if(!localStorage.jws) {
                    this.$router.push("/");
                }
                return this.axios.get("/api/auth/refresh", {headers: {"Authorization": "Bearer " + localStorage.jws}});
            },
            getDermatologist: function() {
                console.log("Getting dermatologist");
               // let jws = window.localStorage.getItem('jwt');
                this.axios.get("api/dermatologist/jwt", {headers:{"Authorization": "Bearer " + localStorage.jws}})
                    .then(response => {
                        console.log(response.data);
                        this.dermatologist = response.data;
                        if(!this.dermatologist.defaultPasswordChanged){
                            this.$router.push("/defaultpass");
                        }
                        this.getAllAppointments()
                    })
                    .catch(response => {
                        console.log("Failed to get patient", response.data);
                        this.refreshToken()
                            .then(response => {
                                localStorage.jws = response.data;
                                this.$router.go();
                            })
                            .catch(response => {
                                console.log(response.data);
                                this.$router.push("/");
                            });
                    });
            },
            getAllAppointments: function(){
                //let jws = window.localStorage.getItem('jwt');
                this.axios.get("api/appointment/appbyemployee/" + this.dermatologist.id, {headers:{"Authorization": "Bearer " + localStorage.jws}})
                    .then(response =>
                    {
                        this.allAppointments = response.data._embedded.appointments;
                        this.getSparkLineValues();
                        this.sparkLineShow = true;
                        this.getTodaysAppointments();
                    })
                    .catch(response =>
                    {
                        console.log(response.data);
                    });
            },
            getTodaysAppointments: function(){
                var today = new Date();
                today.setHours(0,0,0,0);
                var date = new Date(this.getPerviousWorkingDay());
                date.setHours(0,0,0,0);
                var i = 0;
                var j = 0;
                while(i < this.allAppointments.length){
                    var appDate = new Date(this.allAppointments[i].period.startTime);
                    appDate.setHours(0,0,0,0);
                    if(today.getTime() === appDate.getTime()){
                        this.todayAppointments.push(this.allAppointments[i]);
                    }
                    else if(appDate.getTime() === date.getTime()){
                        this.previousDayAppointments[j++] = this.allAppointments[i];
                    }
                    ++i;
                    
                }
            },
            getPerviousWorkingDay: function(){
                var yesterday = new Date();
                yesterday.setDate(yesterday.getDate() - 1);
                yesterday.setHours(0,0,0,0);
                var tempI;
                var temp = 10000000000;//Infinity
                for (var i = 0; i < this.allAppointments.length; i++) {
                    var appDate = new Date(this.allAppointments[i].period.startTime);
                    appDate.setHours(0,0,0,0);
                    if(appDate.getTime()-yesterday.getTime() == 0){
                        tempI = i;
                    }else if(appDate.getTime()-yesterday.getTime() < temp){
                        temp = appDate.getTime()-yesterday.getTime();
                        tempI = i;
                    }
                }
                if(this.allAppointments.length === 0){
                    this.perviousWorkingDay = '';
                    return null;
                }else{
                    this.perviousWorkingDay = this.allAppointments[tempI].period.startTime;
                    return this.allAppointments[tempI].period.startTime;
                }
            },
            getSparkLineValues: function(){
                var today = new Date();
                today.setHours(0,0,0,0);
                var lastSevenDays = [today.setDate(today.getDate() - 1), today.setDate(today.getDate() - 1), today.setDate(today.getDate() - 1), 
                                   today.setDate(today.getDate() - 1), today.setDate(today.getDate() - 1), today.setDate(today.getDate() - 1), today.setDate(today.getDate() - 1)];
                for (var i = 0; i < 7; i++) {
                    this.sparkLineValues[6-i] = this.calculateNumberOfAppointments(new Date(lastSevenDays[i]));
                }
            },
            calculateNumberOfAppointments: function(date){
                var counter = 0;
                for (var i = 0; i < this.allAppointments.length; i++) {
                    var appDate = new Date(this.allAppointments[i].period.startTime);
                    appDate.setHours(0,0,0,0);
                    if(appDate.getTime() === date.getTime()){
                        counter++;
                    }
                }
                return counter;
            }
        },
        mounted(){
            this.getDermatologist();
        },
    }
</script>

<style scoped>
    #pharm-home-main{
        background-color: #fbecdd;
    }
    #container{
        margin: 5%;
    }
    #pharm-home-timeline{
        max-width: 33%;
        flex-direction: column;
    }
    #pharm-home-graph{
        flex-direction: column;
    }
    #pharm-home-table{
        margin-top: -6%;
        margin-bottom: 2%;
		flex-direction: column;
    }
    .center-text{
        text-align: center;
    }
   .v-text-field input{
       font-size: 1.9em !important;
   }
</style>