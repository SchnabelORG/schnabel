<template>
    <div id="define-main">
        <v-card
        id="define-card"
        elevation="2">
        <b class="err">{{error}}</b>
            <v-card-title>New free appointment</v-card-title>
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
            <v-btn class='primary' elevation="1" @click="createAppointment()" :disabled="!dermatologist || !price || !startTime || !endTime">
                Create
            </v-btn>
        </v-card>
    </div>
</template>
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
                pharmacyId: '',
                error: '',
                rules: {
                    required: value => !!value || 'Required.',
                },
            }
        },
        methods: {
            getPharmacyAdmin: function() {
                this.refreshToken().then(response => {
                    localStorage.jws = response.data;
                    this.axios.get("api/pharmacyadmin", {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                        .then(response => {
                            this.pharmacyAdmin = response.data;
                            this.pharmacyId = this.pharmacyAdmin.pharmacy.id;
                            this.getDermatologists();
                        })
                        .catch(response => {
                            console.log("Failed to get pharmacy admin", response.data);
                        });
                   })
                    .catch(response => {
                    console.log(response.data);
                    this.$router.push("/");
                });
            },

            getDermatologists: function() {
                this.axios.get("api/dermatologist/pharmacy/" + this.pharmacyId)
                    .then(response => {
                        this.dermatologists = response.data._embedded.dermatologists;
                    })
                    .catch(response => {
                        console.log("Failed to get dermatologists", response.data);
                    });
            },

            createAppointment: function() {
                if(this.startTime < Date.now() || this.endTime < Date.now() || this.startTime > this.endTime)
                {
                    alert('Time interval is not set properly');
                    return;
                }
                this.refreshToken().then(response => {
                    console.log(response.data);
                    localStorage.jws = response.data;
                    let appointmentRequest = { startTime: this.startTime, endTime: this.endTime, price: this.price, dermatologistId: this.dermatologist.id };
                    this.axios.post("/api/appointment", appointmentRequest, {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                        .then(response =>
                        {
                            console.log(response);
                            this.dermatologist = '';
                            this.price = '';
                            this.startTime = '';
                            this.endTime = '';
                            this.error = 'Successfully added';
                        })
                        .catch(response =>
                        {
                            this.axios.get("/api/shift/medicalemployeepharmacy/" + this.dermatologist.id + "/" + this.pharmacyId)
                                .then(response =>
                                {
                                    console.log(response.data);
                                    let shift = response.data;
                                    alert("Shift -> start: " + shift.startTime + " end: " + shift.endTime);
                                }).catch(response => {
                                    console.log(response.data);
                                });

                            this.error = "Defining new free appointment unsuccessfull";
                            console.log(response.data);
                        });
                })
               .catch(response => {
                    console.log(response.data);
                    this.$router.push("/");
                });
            },
        },
        mounted() {
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