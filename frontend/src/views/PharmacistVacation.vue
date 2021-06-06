<template>
    <div id="account-main">
        <v-card 
        min-width="50%">
            <v-card-title class="info primary--text">
                <b>Your vacations</b>
                <v-spacer></v-spacer>
            </v-card-title>
            <v-card-text>
                <v-form id="ph-add" v-model="valid">
                    <v-text-field
                    :rules="[rules.required]"
                    v-model="dateRangeText"
                    readonly
                    label="Date range"
                    ></v-text-field>
                     <v-date-picker
                        v-model="dates"
                        range
                        :min="new Date(Date.now() + 5*8640000).toISOString().substr(0, 10)"
                    ></v-date-picker>
                    <v-btn :disabled="dates.length < 2" id="save-btn" class="accent white--text" @click="makeVacation()">
                        Make vacation request
                    </v-btn>
                </v-form>
            </v-card-text>
        </v-card>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                pharmacist: {},
                dates: [],
                valid: false,
                rules: {
                    required: value => !!value || 'Required.',
                },
            }
        },
        computed: {
            dateRangeText () {
            return this.dates.join(' ~ ')
            },
        },
         methods: {
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
            makeVacation: function(){
                let startTime = {}
                let endTime = {}
                if(new Date(this.dates[0]).getTime() < new Date(this.dates[1]).getTime()){
                    startTime= new Date(this.dates[0])
                    endTime= new Date(this.dates[1])
                }else{
                     startTime= new Date(this.dates[1])
                    endTime= new Date(this.dates[0])
                }
                let vacation = {startTime: startTime,endTime: endTime , employeeId: this.pharmacist.id, pharmacyId: this.pharmacist.pharmacy.id}
                //let jws = window.localStorage.getItem('jwt');
                this.axios.post("api/vacation/makenew", vacation, {headers:{"Authorization": "Bearer " + localStorage.jws}})
                    .then(response => {
                        console.log(response.data);
                        this.$router.go();
                    })
                    .catch(response => {
                        console.log(response.data);
                    });
            }
        },
        mounted(){
            this.getPharmacist();
        }
    }

</script>

<style scoped>
    #account-main{
        display:grid;
        grid-template-columns:auto;
        place-items: center;
        min-height: 92vh;
        background-color: #fbecdd;
    }
    #save-btn{
        margin-top: 5%;
        min-width: 100%;
    }
</style>