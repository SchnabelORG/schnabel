<template>
    <div id="pharm-home-main">
        <v-row>
            <v-col id="container">
                <v-row id="pharm-home-table">
					<v-data-table :headers="headers"
									:items="patients"
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
								
								<td>{{row.item.name}}</td>
								<td>{{row.item.surname}}</td>
                                <td>{{row.item.date}}</td>
                                <td>{{row.item.time}}</td>
							</tr>
						</template>
					</v-data-table>
                </v-row>
                <v-row>
                    <v-card
                        class="pharm-home-graph info primary--text"
                        dark>
                        <v-card-title class="justify-center">
                            <div class="display-1">
                            Last 7 days appointment
                            </div>
                        </v-card-title>
                        <v-card-text>
                            <v-sheet color="#f4f9fc">
                            <v-sparkline
                                :value="value"
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
                    <v-timeline-item
                    v-for="n in 10"
                    :key="n"
                    >
                    <v-card v-if="n == 1 || n == 6">
                        <v-card-title class="justify-center accent white--text">
                            <div v-if="n == 1">Todays consultation</div>
                            <div v-else>Previous working day</div>
                        </v-card-title>
                    </v-card>
                    <v-card v-else>
                        <v-card-title class="info primary--text">
                        <b>Consultation at {{n}}:00pm</b>
                        </v-card-title>
                        <v-card-text>
                            <h3>Radovan Zupunski</h3>
                            <h3>Brufen</h3>
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
                value: [5, 10, 0, 11, 12, 20, 17],
                patients: [],
                search: "",
                headers: [
                    { text: "Patient's name", value: "name", },
                    { text: "Patient's surname", value: "surname", },
                    { text: "Consultation date", value: "date", },
                    { text: "Consultation time", value: "time", },
				],
            }
        },
        methods:{
            getAllPatients: function(){
                this.patients.push({name: 'Radovan', surname: 'Zupunski', date: '20.01.2021', time: '12:30'});
                this.patients.push({name: 'Radovan', surname: 'Zupunski', date: '20.01.2021', time: '12:30'});
                this.patients.push({name: 'Aadovan', surname: 'Zupunski', date: '20.01.2021', time: '12:30'});
                this.patients.push({name: 'Radovan', surname: 'Gupunski', date: '20.01.2020', time: '11:30'});
                this.patients.push({name: 'Radovan', surname: 'Zupunski', date: '20.01.2021', time: '12:30'});
                this.patients.push({name: 'Radovan', surname: 'Zupunski', date: '20.01.2021', time: '12:30'});
            }
        },
        mounted(){
            this.getAllPatients();
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