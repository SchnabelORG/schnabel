<template>
    <div id="define-main">
        <v-card
        id="define-card"
        elevation="2">
            <v-card-title>New appointment</v-card-title>
            <v-combobox v-model="dermatologist"
                        :items="dermatologists"
                        return-object="true"
                        label="Dermatologist"
                        :rules="[rules.required]">
                <template slot="selection" slot-scope="data" >
                    {{ data.item.name }} {{ data.item.surname }}
                </template>
                <template slot="item" slot-scope="data">
                    {{ data.item.name }} {{ data.item.surname }}
                </template>
            </v-combobox>
             <v-text-field 
                v-model="price"
                label="Price"
                type="number"
                :rules="[rules.required]"
                required/>
            <div>
                <v-datetime-picker v-model="startTime" date-format="dd.MM.yyyy." time-format="HH:mm" :min="new Date().toISOString().substr(0, 10)" label="From"/>
            </div>
            <div>
                <v-datetime-picker v-model="endTime" date-format="dd.MM.yyyy." time-format="HH:mm" :min="startTime" label="To"/>
            </div>
            <v-btn class='primary' elevation="1" @click="createAppointment()" >
                Create
            </v-btn>
        </v-card>
    </div>
</template>
<!-- :disabled="!dermatologist || !price || !startTime || !endTime"-->
<script>
    export default {
        data() {
            return {
                dermatologist: '',
                dermatologists: [],
                pharmacyAdmin: '',
                startTime: '',
                endTime: '',
                price: '',
                rules: {
                    required: value => !!value || 'Required.',
                },
            }
        },
        methods: {
            getDermatologists: function() {

            },

            getPharmacyAdmin: function() {
                let jws = this.$store.state.jws;
                console.log(jws)
                this.axios.get("api/pharmacyadmin", {headers:{"Authorization": "Bearer " + this.jws}})
                    .then(response => {
                        console.log(response.data);
                        this.pharmacyadmin = response.data;
                    })
                    .catch(response => {
                        console.log("Failed to get pharmacy admin", response.data);
                        this.refreshToken()
                            .then(response => {
                                this.$store.state.jws = response.data;
                                this.$router.go();
                            })
                            .catch(response => {
                                console.log(response.data);
                                this.$router.push("/");
                            });
                    });
            },  

            refreshToken: async function() {
                let jws = this.$store.state.jws;
                if(!jws) {
                    this.$router.push("/");
                }
                return this.axios.get("/api/auth/refresh", {headers: {"Authorization": "Bearer " + jws}});
            },

            createAppointment: function() {
                if(this.startTime < Date.now() || this.endTime < Date.now() || this.startTime > this.endTime)
                {
                    alert('Time interval is not set properly');
                    return;
                }
                // this.refreshToken().then(response => {
                    
                    let appointmentRequest = { startTime: this.startTime, endTime: this.endTime, price: this.price, dermatologistId: '2' };
                    this.axios.post("/api/appointment", appointmentRequest, {headers:{"Authorization":"Bearer " + this.$store.state.jws}})
                        .then(response =>
                        {
                            console.log(response);
                            this.dermatologist = '';
                            this.price = '';
                            this.startTime = '';
                            this.endTime = '';
                        })
                        .catch(response =>
                        {
                            console.log(response.data);
                        });
               /* })
                .catch(response => {
                    console.log(response.data);
                    this.$router.push("/");
                });*/
            },
        },
        mounted() {
			this.getDermatologists();
            this.getPharmacyAdmin();
		}
    }
</script>

<style scoped>
    #define-main {
        display:grid;
        grid-template-columns:auto;
        place-items: center;
        min-height: 92vh;
        background-color: #fbecdd;
    }
    #define-card {
        display: flex;
        flex-direction: column;
        margin: 0 auto;
        width: 40%;
    }
    #define-main {
        display: flex;
        flex-direction: column;
        justify-content: center;
        width: 100vw;
        min-height: 100vh;
    }
</style>