<template>
    <div id="consultation-report-main">
       <v-stepper v-model="e1">
            <v-stepper-header>
                <v-stepper-step
                :complete="e1 > 1"
                step="1">
                Choose patient
                </v-stepper-step>
        
                <v-divider></v-divider>
        
                <v-stepper-step
                :complete="e1 > 2"
                step="2">
                Consultation info
                </v-stepper-step>
        
                <v-divider></v-divider>
        
                <v-stepper-step
                :complete="e1 > 3"
                step="3">
                Medication prescription
                </v-stepper-step>

                <v-divider></v-divider>
        
                <v-stepper-step
                :complete="e1 > 4"
                step="4">
                New consultation
                </v-stepper-step>
    
            </v-stepper-header>
        
            <v-stepper-items>
                <v-stepper-content step="1">
                    <v-card
                        elevation="3"
                        class="mb-12"
                        height="200px">
                        <div >
                            <h2></h2>
                        </div>
                        <div v-if="!chosen">
                            <div v-if="todayAppointments.length == 0">
                                <h4>No consultation for today</h4>
                            </div>
                            <div v-else>                       
                                <h2>Choose todays consultation:</h2>
                                <v-chip class="primary single-chip" v-for="item in todayAppointments" :key="item.id" @click="setAppointment(item)"> 
                                    {{item.patient.name}} {{item.patient.surname}}({{item.period.startTime.slice(11)}} ) 
                                </v-chip>
                            </div>
                        </div>
                        
                        <table v-else style="width:100%">
                            <tr>
                                <td><b>Name:</b> </td>
                                <td>{{chosenAppointment.patient.name}}</td>
                            </tr>
                            <tr>
                                <td><b> Surname:</b></td>
                                <td>{{chosenAppointment.patient.surname}}</td>
                            </tr>
                            <tr>
                                <td><b>Email:</b></td>
                                <td>{{chosenAppointment.patient.email}}</td>
                            </tr>
                            <tr>
                                <td><b>Address:</b></td>
                                <td>{{chosenAppointment.patient.address.streetNo}} {{chosenAppointment.patient.address.street}}, {{chosenAppointment.patient.address.city}}</td>
                            </tr>
                             <tr>
                                <td><b>Time:</b></td>
                                <td>{{chosenAppointment.period.startTime.slice(11)}}-{{chosenAppointment.period.endTime.slice(11)}}({{chosenAppointment.period.startTime.slice(0,10)}})</td>
                            </tr>
                        </table>         
                    </v-card>
                     <v-row>
                        <v-col class="text-left">
                            <v-btn
                                color="primary"
                                :disabled="!chosen"
                                @click="e1 = 2">
                                Continue
                            </v-btn>
                        </v-col>
                        <v-col class="text-right">
                            <v-btn text
                                @click="reset()">
                                Cancel
                            </v-btn>
                        </v-col>
                    </v-row>  
            
                </v-stepper-content>
        
                <v-stepper-content step="2">
                    
                    <v-textarea
                            filled
                            shaped
                            v-model="consultationInfo"
                            label="Consultation info">
                    </v-textarea>    

                    <v-row>
                        <v-col class="text-left">
                            <v-btn
                                color="primary"
                                @click="e1 = 3">
                                Continue
                            </v-btn>
                    
                            <v-btn text
                                @click="e1 = 1">
                                Back
                            </v-btn>
                        </v-col>
                        <v-col class="text-right">
                            <v-btn text
                                @click="reset()">
                                Cancel
                            </v-btn>
                        </v-col>
                    </v-row>                    
                </v-stepper-content>
        
                <v-stepper-content step="3">
                    <v-card
                        class="mb-12"
                        
                        height="200px">
                        <v-row>
                            <div id="select-medication">
                                <v-select
                                    ref="med"
                                    v-model="choosenMedication"
                                    :items="medications"
                                    label="Medication name"
                                    :rules="[rules.required]"
                                    :item-disabled="checkIfDisabled">
                                    <template slot="selection" slot-scope="data" >
                                        {{ data.item.name }}
                                    </template>
                                    <template slot="item" slot-scope="data">
                                        {{ data.item.name }}
                                    </template>
                                </v-select>
                            </div>
                            <v-btn id="add-med-btn" :disabled="!choosenMedication" elevation="2" @click="addMedication" class="accent white--text">
                                Add
                            </v-btn>
                        </v-row>
                        
                        <v-data-table :items="prescripedMedication"
                                        :hide-default-footer="true"
                                        :hide-default-header="true">
                            <template v-slot:item="row"> 
                                <tr>
                                    <td>{{row.item.name}}</td>
                                    <td>{{row.item.dosage}}</td>
                                    <td> 
                                        <v-text-field
                                            class="mt-0 pt-0"
                                            hide-details
                                            v-model="dosage[row.item.id].perDay"
                                            :value="1"
                                            :min="1"
                                            :max="12"
                                            single-line
                                            type="number"
                                            style="width: 60px"
                                            @change="$set(range, 1, $event)"
                                        ></v-text-field>
                                        times per day 
                                    </td>
                                    <td> 
                                        <v-text-field
                                            class="mt-0 pt-0"
                                            :value="1"
                                            hide-details
                                            v-model="dosage[row.item.id].howManyDays"
                                            :min="1"
                                            :max="10"
                                            single-line
                                            type="number"
                                            style="width: 60px"
                                            @change="$set(range, 1, $event)"
                                        ></v-text-field>
                                        how many days
                                    </td>
                                    <td> 
                                        <v-text-field
                                            class="mt-0 pt-0"
                                            :value="1"
                                            hide-details
                                            v-model="dosage[row.item.id].quantity"
                                            :min="1"
                                            :max="40"
                                            single-line
                                            type="number"
                                            style="width: 60px"
                                            @change="$set(range, 1, $event)"
                                        ></v-text-field>
                                        quantity
                                    </td>
                                    <td>
                                        <v-btn elevation="2" @click="remove(row.item)" class="check-btn accent white--text">
                                            <i class="fa fa-trash fa-fw" aria-hidden="true"></i>
                                        </v-btn>
                                    </td>
                                </tr>
                            </template>
                        </v-data-table>
                        <div class="check-btn">
                            <v-spacer></v-spacer>
                            <v-btn v-if="prescripedMedication.length !== 0" @click="checkForMedication" class="check-btn accent white--text">
                                Check availability
                            </v-btn>
                            
                        </div>
                        
                        <v-alert
                            id="alert-for-med"
                            dense
                            v-if="haveMedication !== ''"
                            :type="haveMedication">
                                <div v-if="haveMedication === 'success'">We <strong>have medication</strong> in stock</div>
                                <div v-else>We <strong>dont have medication</strong> in stock</div>
                            
                        </v-alert>
                    </v-card>
            
                    <v-row>
                        <v-col class="text-left">
                            <v-btn
                                :disabled="shouldCheck"
                                color="primary"
                                @click="e1 = 4">
                                Continue
                            </v-btn>
                    
                            <v-btn text
                                @click="e1 = 2">
                                Back
                            </v-btn>
                        </v-col>
                        <v-col class="text-right">
                            <v-btn text
                                @click="reset()">
                                Cancel
                            </v-btn>
                        </v-col>
                    </v-row>   
                </v-stepper-content>

                <v-stepper-content step="4">
                    <v-card
                        class="mb-12"
                        height="450px">
                        <v-row>
                            <v-col>
                                <v-date-picker
                                    v-model="date"
                                    :allowed-dates="getAllowedDates"
                                ></v-date-picker>
                            </v-col>
                            <v-col>
                                <!-- <v-radio-group
                                    v-model="time"
                                    column>
                                    <v-radio
                                        v-for="n in 6"
                                        :key="n"
                                        :label="n"
                                        color="primary"
                                        :value="n"
                                    ></v-radio>

                                </v-radio-group> -->
                            </v-col>
                        </v-row>

                        <v-alert
                            dense
                            v-if="(time != null) && (date != null)"
                            type="info">
                            Choosen term is 20.03.2021 at 12:30
                        </v-alert>

                        <v-btn :disabled="(time == null) || (date == null)" id="sumbit-btn" elevation="2" @click="makeNewConsultation" class="accent white--text">
                            Submit
                        </v-btn>

                    </v-card>

                    <v-row>
                        <v-col class="text-left">
                            <v-btn
                                color="primary"
                                @click="finish">
                                Finish
                            </v-btn>
                    
                            <v-btn text
                                @click="e1 = 3">
                                Back
                            </v-btn>
                        </v-col>
                        <v-col class="text-right">
                            <v-btn text
                                @click="reset()">
                                Cancel
                            </v-btn>
                        </v-col>
                    </v-row>   
                </v-stepper-content>
            </v-stepper-items>
        </v-stepper>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                e1: 1,
                medicationName: '',
                allAppointments: [],
                todayAppointments: [],
                pharmacist: {},
                chosen: false,
                chosenAppointment: {},
                consultationInfo: '',
                medications: [],
                prescripedMedication: [],
                choosenMedication: null,
                shouldCheck: true,
                dosage:[],
                haveMedication: '',
                rules: {
                    required: value => !!value || 'Required.',
                },
                range: [1, 12],

            }
        },
        methods:{
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
                this.axios.get("api/pharmacist/jwt", {headers:{"Authorization": "Bearer " + jws}})
                    .then(response => {
                        console.log(response.data);
                        this.pharmacist = response.data;
                        this.getAllAppointments()
                    })
                    .catch(response => {
                        console.log("Failed to get patient", response.data);
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
                console.log(this.pharmacist.id)
                this.axios.get("api/appointment/appbyemployee/" + this.pharmacist.id, {headers:{"Authorization": "Bearer " + jws}})
                    .then(response =>
                    {
                        this.allAppointments = response.data._embedded.appointments;
                        this.getTodaysAppointments();
                        console.log(this.todayAppointments)
                    })
                    .catch(response =>
                    {
                        console.log(response.data);
                    });
            },
            getTodaysAppointments: function(){
                
                var today = new Date();
                today.setHours(0,0,0,0);
                var i = 0;
                while(i < this.allAppointments.length){
                    var appDate = new Date(this.allAppointments[i].period.startTime);
                    appDate.setHours(0,0,0,0);
                    if(today.getTime() === appDate.getTime()){
                        this.todayAppointments = this.allAppointments.splice(i);
                    }
                    else{
                        ++i;
                    }
                }
                console.log(this.previousDayAppointments);
            },
            setAppointment: function(appointment){
                this.chosenAppointment = appointment;
                this.chosen = true
                this.getAllMedications();
            },
            reset: function(){
               this.$router.go();
            },
            getAllMedications: function(){
                let jws = window.localStorage.getItem('jwt');
                console.log(this.pharmacist.id)
                this.axios.get("api/drug", {headers:{"Authorization": "Bearer " + jws}})
                    .then(response =>
                    {
                        this.medications = response.data._embedded.drugs;
                    })
                    .catch(response =>
                    {
                        console.log(response.data);
                    });
            },
            checkIfDisabled: function(item){
                let allergies = this.chosenAppointment.patient.allergies
                for(var i = 0; i < allergies.length; i++){
                    if(item.id === allergies[i].id)
                        return true;
                }
                    return false;

            },
            remove: function(item){
                for(var i = 0; i <  this.prescripedMedication.length; i++){
                    console.log(this.prescripedMedication.length)
                    console.log(this.prescripedMedication[i].id + " " + item.id)
                    if(item.id === this.prescripedMedication[i].id){
                        this.prescripedMedication.splice(i, 1);
                        this.dosage.splice(item.id, 1)
                        this.haveMedication = '';
                        this.shouldCheck = true;
                        console.log(this.prescripedMedication)
                    }  
                }
                
            },
            addMedication: function(){
                this.dosage[this.choosenMedication.id] = {perDay: 1, howManyDays: 1, quantity: 1};
                console.log(this.dosage);
                this.prescripedMedication.push(this.choosenMedication);
                this.choosenMedication = null;
                this.shouldCheck = true;
                this.haveMedication = '';
                this.$refs["med"].reset();

            },
            checkForMedication: function(){
                this.shouldCheck = false;
                console.log(this.prescripedMedication.length)
                let jws = window.localStorage.getItem('jwt');
                for(var i = 0; i < this.prescripedMedication.length; i++){

                    let dto = {drugId: this.prescripedMedication[i].id, quantity: this.dosage[this.prescripedMedication[i].id].quantity, pharmacyId: this.pharmacist.pharmacy.id}
                    console.log(this.prescripedMedication[i])
                    this.axios.post("api/warehouseitem/doeshave", dto, {headers:{"Authorization": "Bearer " + jws}})
                        .then(response =>
                        {
                            console.log(response.data);
                            if(response.data){
                                this.haveMedication = "success";
                            }else{
                                this.haveMedication = "error";
                            }
                        })
                        .catch(response =>
                        {
                            console.log(response.data);
                        });
                    if(this.haveMedication === "error") {
                        this.shouldCheck = true;
                        return;
                    }
                }
                this.haveMedication = "success";
                
            },
            getAllowedDates: function(val){
                if (this.allowedDates.indexOf(val) !== -1) {
                    return true
                } else {
                    return false
                }
            },
        },
        mounted(){
            this.getPharmacist();
        },
    }
</script>

<style scoped>
    #consultation-report-main{
        display:grid;
        grid-template-columns:auto;
        place-items: center;
        min-height: 92vh;
        background-color: #fbecdd;
    }
    #add-med-btn{
        margin: 0;
        margin-top: 1.5%;
        width: 10%;
    }
    #select-medication{
        margin-left: 2%;
        width: 85%;
    }
    #alert-for-med{
        margin-top: 3%;
    }
    #sumbit-btn{
        width: 100%;
    }
    #chips-stepper{
        margin-top: 1%;
        margin-left: 1%;
    }
    .single-chip{
        margin: 1%
    }
    .check-btn{
        margin-top: 1%;
        margin-left: 5%;
        width: 90%;
    }
</style>