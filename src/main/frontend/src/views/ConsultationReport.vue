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
        
                <v-divider></v-divider>

                <v-stepper-step
                :complete="e1 > 5"
                step="5">
                Finish
                </v-stepper-step>
            </v-stepper-header>
        
            <v-stepper-items>
                <v-stepper-content step="1">
                    <v-card
                        class="mb-12"
                        color="grey lighten-1"
                        height="200px"
                    ></v-card>

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
                    <v-card
                        class="mb-12"
                        color="grey lighten-1"
                        height="200px"></v-card>
                    
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
                        color="grey lighten-1"
                        height="200px"></v-card>
            
                    <v-row>
                        <v-col class="text-left">
                            <v-btn
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
                        color="grey lighten-1"
                        height="200px"></v-card>

                    <v-row>
                        <v-col class="text-left">
                            <v-btn
                                color="primary"
                                @click="e1 = 5">
                                Continue
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

                <v-stepper-content step="5">
                    <v-card
                        class="mb-12"
                        color="grey lighten-1"
                        height="200px"></v-card>
                    
                    <v-row>
                        <v-col class="text-left">
                            <v-btn
                                color="primary"
                                @click="finish">
                                Finish
                            </v-btn>
                    
                            <v-btn text
                                @click="e1 = 4">
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
            }
        },
        methods:{
            getAllPatients: function(){
                console.log("aa");
            }
        },
        mounted(){
            this.getAllPatients();
        },
    }
</script>

<style scoped>
    #consultation-report-main{
        display:grid;
        grid-template-columns:auto;
        place-items: center;
        min-height: 100%;
    }
</style>