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
                    <v-radio-group
                        v-model="pharmNum"
                        class="date-chip"
                        row>
                        <v-radio
                            v-for="item in dermatologist.pharmacies"
                            :key="item.id"
                            :label="item.name"
                            color="primary"
                        ></v-radio>
                    </v-radio-group>
                     <v-date-picker
                        v-model="dates"
                        range
                        :min="new Date(Date.now() + 5*8640000).toISOString().substr(0, 10)"
                    ></v-date-picker>
                    <v-btn :disabled="dates.length < 2 || pharmNum === ''" id="save-btn" class="accent white--text" @click="makeVacation()">
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
                dermatologist: {},
                dates: [],
                valid: false,
                rules: {
                    required: value => !!value || 'Required.',
                },
                pharmNum: '',
            }
        },
        computed: {
            dateRangeText () {
            return this.dates.join(' ~ ')
            },
        },
         methods: {
             refreshToken: async function() {
                let jws = window.localStorage.getItem('jwt');
                if(!jws) {
                    this.$router.push("/");
                }
                return this.axios.get("/api/auth/refresh", {headers: {"Authorization": "Bearer " + jws}});
            },
             getDermatologist: function() {
                console.log("Getting dermatologist");
                let jws = window.localStorage.getItem('jwt');
                console.log(jws)
                this.axios.get("api/dermatologist/jwt", {headers:{"Authorization": "Bearer " + jws}})
                    .then(response => {
                        console.log(response.data);
                        this.dermatologist = response.data;
                    })
                    .catch(response => {
                        console.log("Failed to get dermatologist", response.data);
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
                let vacation = {startTime: startTime,endTime: endTime , employeeId: this.dermatologist.id, pharmacyId: this.dermatologist.pharmacies[this.pharmNum].id}
                let jws = window.localStorage.getItem('jwt');
                this.axios.post("api/vacation/makenew", vacation, {headers:{"Authorization": "Bearer " + jws}})
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
            this.getDermatologist();
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
    .date-chip{
        margin-left: 6%;
        margin-top: 3%;
    }
</style>