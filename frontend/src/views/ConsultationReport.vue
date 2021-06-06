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
                            <tr>
                                <td>
                                    <v-btn text
                                    class="primary"
                                    @click="patientDidntCome()">
                                    Didn't come
                                    </v-btn>
                                </td>
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
                        class="step-3-card mb-12"
                        
                        height="600px">
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
                        <div class="check-btn">
                            
                            <v-btn v-if="prescripedMedication.length !== 0" @click="deleteFromTable" class="del-btn accent white--text">
                                <i class="fa fa-trash">clear all</i>
                            </v-btn>
                        </div>
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
                                :disabled="haveMedication !=='success'"
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
                        height="650px">
                        <v-row>
                            <v-col>
                                <v-row v-for="item in freeTerms" :key="item">
                                    <v-chip class="primary date-chip" @click="setDateForTerms(item)"> 
                                        {{item[0].startTime.slice(0,10)}}
                                    </v-chip>
                                </v-row>
                            </v-col>
                            <v-col>
                                <v-radio-group
                                v-if="termsToShow.length > 0"
                                    v-model="time"
                                    column>
                                    <!-- :label="`${new Date(item.start).getHours()}:${new Date(item.start).getMinutes()}-${item.end.toISOString().substr(11, 5)} (${item.start.toISOString().substr(0, 10)})`" -->
                                     <v-radio
                                        :label="`none`"
                                        color="primary"
                                    ></v-radio>
                                    <v-radio
                                        v-for="item in termsToShow"
                                        :key="item"
                                        :label="getLabel(item)"
                                        color="primary"
                                    ></v-radio>

                                </v-radio-group>
                            </v-col>
                        </v-row>
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
                futureAppointments: [],
                freeTerms: [],
                pharmacist: {},
                chosen: false,
                chosenAppointment: {},
                consultationInfo: '',
                medications: [],
                shift: {},
                prescripedMedication: [],
                termsToShow: [],
                choosenMedication: null,
                dosage:[],
                haveMedication: '',
                rules: {
                    required: value => !!value || 'Required.',
                },
                range: [1, 12],
                time: 0

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
             getPharmacist: function() {
                console.log("Getting pharmacist");
                //let jws = window.localStorage.getItem('jwt');
                console.log(localStorage.jws)
                this.axios.get("api/pharmacist/jwt", {headers:{"Authorization": "Bearer " + localStorage.jws}})
                    .then(response => {
                        console.log(response.data);
                        this.pharmacist = response.data;
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
                console.log(this.pharmacist.id)
                this.axios.get("api/appointment/appbyemployee/" + this.pharmacist.id, {headers:{"Authorization": "Bearer " + localStorage.jws}})
                    .then(response =>
                    {
                        this.allAppointments = response.data._embedded.appointments;
                        this.getTodaysAppointments();
                        this.getShift();
                        console.log(this.todayAppointments)
                    })
                    .catch(response =>
                    {
                        console.log(response.data);
                    });
            },
            getShift: function(){
                //let jws = window.localStorage.getItem('jwt');
                this.axios.get("api/shift/medicalemployeepharmacy/" + this.pharmacist.id + "/" + this.pharmacist.pharmacy.id, {headers:{"Authorization": "Bearer " + localStorage.jws}})
                    .then(response =>
                    {
                        this.shift = response.data;
                        console.log(response.data);
                        this.getFreeTerms();
                    })
                    .catch(response =>
                    {
                        console.log(response.data);
                    });
            },
            getFreeTerms: function(){
                let start = parseInt(this.shift.startTime.slice(0,2))
                let end = parseInt(this.shift.endTime.slice(0,2))

                var today = new Date();

                for(var i = 0; i < 15; i++){
                    var dates = [];
                    start = parseInt(this.shift.startTime.slice(0,2))
                    for(start; start < end; start++){
                        dates.push({startTime:  new Date(today.setHours(start, 0, 0, 0)), endTime: new Date(today.setHours(start, 30, 0, 0))});
                        dates.push({startTime:  new Date(today.setHours(start, 30, 0, 0)), endTime: new Date(today.setHours(start+1, 0, 0, 0))});
                    }
                    for(var j = 0; j < dates.length; j++){
                        dates[j].startTime.setTime( dates[j].startTime.getTime() + (i+1) *86400000);
                        dates[j].endTime.setTime( dates[j].endTime.getTime() + (i+1) *86400000);
                        for(var k = 0; k < this.futureAppointments.length; k++){
                            let startApp = new Date(this.futureAppointments[k].period.startTime).getTime()
                            let endApp = new Date(this.futureAppointments[k].period.endTime).getTime()
                            if((dates[j].startTime.getTime() > startApp && dates[j].startTime.getTime() < endApp)||
                               (dates[j].endTime.getTime() > startApp && dates[j].endTime.getTime() < endApp)||
                               (dates[j].startTime.getTime() < startApp && dates[j].endTime.getTime() > endApp)){
                                    dates.splice(j,1);
                                    j--;
                               }
                        }
                        
                    }
                    this.freeTerms.push(JSON.parse(JSON.stringify(dates)));
                }
            },
            getTodaysAppointments: function(){
                
                var today = new Date();
                today.setHours(0,0,0,0);
                var i = 0;
                while(i < this.allAppointments.length){
                    var appDate = new Date(this.allAppointments[i].period.startTime);
                    appDate.setHours(0,0,0,0);
                    if(today.getTime() === appDate.getTime() && !this.allAppointments[i].isFinished && !this.allAppointments[i].missed){
                        this.todayAppointments.push(this.allAppointments[i]);
                        
                    }
                    else if (today.getTime() < appDate.getTime()){
                        this.futureAppointments.push(this.allAppointments[i]);
                    }
                    ++i;
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
                //let jws = window.localStorage.getItem('jwt');
                this.axios.get("api/drug", {headers:{"Authorization": "Bearer " + localStorage.jws}})
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
            deleteFromTable: function(){
                this.prescripedMedication.splice(0);
                this.haveMedication = '';
            },
            addMedication: function(){
                this.dosage[this.choosenMedication.id] = {perDay: 1, howManyDays: 1, quantity: 1};
                this.prescripedMedication.push(this.choosenMedication);
                this.choosenMedication = null;
                this.haveMedication = '';
                this.$refs["med"].reset();

            },
            checkForMedication: function(){
                //let jws = window.localStorage.getItem('jwt');
                for(var i = 0; i < this.prescripedMedication.length; i++){
                    let dto = {drugId: this.prescripedMedication[i].id, quantity: this.dosage[this.prescripedMedication[i].id].quantity, pharmacyId: this.pharmacist.pharmacy.id}
                    console.log(this.prescripedMedication[i])
                    this.axios.post("api/warehouseitem/doeshave", dto, {headers:{"Authorization": "Bearer " + localStorage.jws}})
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
                }
                
                console.log(this.haveMedication);
                
            },
            setDateForTerms: function(item){
                this.termsToShow = [];
                for(var i = 0; i < item.length; i++){
                    this.termsToShow.push({start: new Date(item[i].startTime), end: new Date(item[i].endTime)});
                    console.log(this.termsToShow)
                }
            },
            finish: function(){
                //let jws = window.localStorage.getItem('jwt');
                if (!this.consultationInfo){
                    this.consultationInfo = 'No description'
                }
                this.axios.post("api/reportappointment/addreport/" + this.chosenAppointment.id, this.consultationInfo,{headers:{"Authorization": "Bearer " + localStorage.jws}})
                    .then(response =>
                    {
                        console.log(response.data);
                        if(response.data != -1){
                            this.addMedicationsToReport(response.data);
                        }
                        this.scheduleNewAppointment();
                        //this.reset();
                    })
                    .catch(response =>
                    {
                        console.log(response.data);
                    });
            },
            addMedicationsToReport: function(reportId){
                var meds = [];
                for(var i = 0; i < this.prescripedMedication.length; i++){
                    meds.push({durationInDays: this.dosage[this.prescripedMedication[i].id].howManyDays, 
                    takePerDay: this.dosage[this.prescripedMedication[i].id].howManyDays, 
                    quantity: this.dosage[this.prescripedMedication[i].id].howManyDays, 
                    drug: this.prescripedMedication[i]});
                }
                console.log(meds)
                //let jws = window.localStorage.getItem('jwt');
                this.axios.post("api/recommendedmed/addmed/" + reportId, meds,{headers:{"Authorization": "Bearer " + localStorage.jws}})
                    .then(response =>
                    {
                        console.log("aaa");
                        console.log(response.data);
                    })
                    .catch(response =>
                    {
                        console.log(response.data);
                    });

            },
            scheduleNewAppointment: function(){
                if(this.time === 0){
                    console.log("Nista")
                }else{
                    //let jws = window.localStorage.getItem('jwt');
                    let newApp= {pharmacistId: this.pharmacist.id, patientId:this.chosenAppointment.patient.id, 
                                pharmacyId: this.pharmacist.pharmacy.id, startTime: new Date(this.termsToShow[this.time - 1].start), endTime: new Date(this.termsToShow[this.time - 1].end)}
                    console.log(newApp)
                    this.axios.post("api/appointment/pharmacist/newapp", newApp,{headers:{"Authorization": "Bearer " + localStorage.jws}})
                    .then(response =>
                    {
                        console.log(response.data);
                    })
                    .catch(response =>
                    {
                        console.log(response.data);
                    });

                }
            },
            patientDidntCome: function(){
                //let jws = window.localStorage.getItem('jwt');
                this.axios.get("api/appointment/missed/"+ this.chosenAppointment.id, {headers:{"Authorization": "Bearer " + localStorage.jws}})
                    .then(response =>
                    {
                        console.log(response.data);
                        this.reset()
                    })
                    .catch(response =>
                    {
                        console.log(response.data);
                    });
            },
            getLabel: function(item){
                var label = '';
                if(new Date(item.start).getHours()< 10)
                    label += "0" + new Date(item.start).getHours();
                else
                    label +=  new Date(item.start).getHours();

                label += ":"

                if(new Date(item.start).getMinutes()< 10)
                    label += "0" + new Date(item.start).getMinutes();
                else
                    label +=  new Date(item.start).getMinutes();

                label += "-"

                if(new Date(item.end).getHours()< 10)
                    label += "0" + new Date(item.end).getHours();
                else
                    label +=  new Date(item.end).getHours();

                label += ":"

                if(new Date(item.end).getMinutes()< 10)
                    label += "0" + new Date(item.end).getMinutes();
                else
                    label +=  new Date(item.end).getMinutes();

                label += " ("+item.start.toISOString().substr(0, 10)+")"
                return label;
            }
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
    .del-btn{
        margin-top: 1%;
        margin-left: 80%;
        width: 20%;
    }
    #medication-and-intake{
        min-height: 50vh;
    }
    .date-chip{
        margin-left: 5%;
        margin-top: 3%;
    }
</style>