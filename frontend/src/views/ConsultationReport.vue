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

                        <v-text-field
                            v-model="patientName"
                            label="Search patient">
                        </v-text-field>

                        <table style="width:100%">
                            <tr>
                                <td><b>Name:</b> </td>
                                <td>Radovan</td>
                            </tr>
                            <tr>
                                <td><b> Surname:</b></td>
                                <td>Zupunski</td>
                            </tr>
                            <tr>
                                <td><b>Email:</b></td>
                                <td>radovan.zupunski@gmail.com</td>
                            </tr>
                            <tr>
                                <td><b>Address:</b></td>
                                <td>Slobodana Bajica 17, Novi Sad</td>
                            </tr>
                        </table>         
                    </v-card>

                    <v-row>
                        <v-col class="text-left">
                            <v-btn
                                color="primary"
                                @click="e1 = 2">
                                Continue
                            </v-btn>
                        </v-col>
                    </v-row>
            
                </v-stepper-content>
        
                <v-stepper-content step="2">
                    
                    <v-textarea
                            filled
                            shaped
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
                                @click="e1 = 1">
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
                                        {{ data.item.name }}  {{ data.item.dosage }}
                                    </template>
                                    <template slot="item" slot-scope="data">
                                        {{ data.item.name }}  {{ data.item.dosage }}
                                    </template>
                                </v-select>
                            </div>
                            <v-btn id="add-med-btn" :disabled="choosenMedication === ''" elevation="2" @click="addMedication" class="accent white--text">
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
                                            :min="1"
                                            :max="10"
                                            single-line
                                            type="number"
                                            style="width: 60px"
                                            @change="$set(range, 1, $event)"
                                        ></v-text-field>
                                        quantity
                                    </td>
                                    <td>
                                         <v-btn elevation="2" @click="checkForMedication" class="accent white--text">
                                            Check
                                        </v-btn>
                                    </td>
                                </tr>
                            </template>
                        </v-data-table>
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
                                :disabled="haveMedication !== 'success'"
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
                                @click="e1 = 1">
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
                                <v-radio-group
                                    v-model="time"
                                    column>
                                    <v-radio
                                        v-for="n in 6"
                                        :key="n"
                                        :label="n"
                                        color="primary"
                                        :value="n"
                                    ></v-radio>

                                </v-radio-group>
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
                                @click="e1 = 1">
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
                patientName: '',
                medicationName: '',
                medications: [],
                prescripedMedication: [],
                choosenMedication: '',
                haveMedication: '',
                rules: {
                    required: value => !!value || 'Required.',
                },
                range: [1, 12],
                date: null,
                time: null,
                allowedDates: ["2021-02-03", "2021-02-05", "2021-03-05"],

            }
        },
        methods:{
            getAllPatients: function(){
                console.log("aa");
            },
            getAllMedications: function(){
                this.medications.push({name: 'Brufen', dosage: '100mg', company: 'Hemofarm'});
                this.medications.push({name: 'Aspirin', dosage: '200mg', company: 'Hemofarm'});
                this.medications.push({name: 'Morfijum', dosage: '300mg', company: 'Hemofarm'});
                this.medications.push({name: 'Bensedin', dosage: '400mg', company: 'Hemofarm'});
                this.medications.push({name: 'Amigren', dosage: '1000mg', company: 'Hemofarm'});
            },
            checkIfDisabled: function(item){
                if(item.name === 'Morfijum')
                    return true;
                else
                    return false;

            },
            addMedication: function(){
                this.prescripedMedication = [];
                this.prescripedMedication.push(this.choosenMedication);
                this.haveMedication = '';
                this.$refs["med"].reset();

            },
            checkForMedication: function(){
                this.haveMedication = 'error';
                this.haveMedication = 'success';
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
            this.getAllPatients();
            this.getAllMedications();
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
</style>