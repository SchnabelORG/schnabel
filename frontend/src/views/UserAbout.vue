<template>
    <div>
        <search-navigation>
            <router-link to='/'>Home</router-link>
        </search-navigation>
        <div id="user-main" class="info">
            <div id="user-container">
                <div id="info-container">
                    <v-avatar size="200" id="user-profile">
                        <img src="../assets/placeholder-profile-sq.jpg"/>
                    </v-avatar>
                        <div class="grid-container">
                            <p class="info--text">Penalties: </p>
                            <p>{{penaltyCount}}</p>
                            <p class="info--text">Membership:</p>
                            <p>{{membership}}</p>
                        </div>
                </div>
                <div>
                    <v-tabs
                    v-model="tabs">
                        <v-tabs-slider color="accent"></v-tabs-slider>
                        <v-tab>
                            Overview
                        </v-tab>
                        <v-tab>
                            Allergies
                        </v-tab>
                    </v-tabs>
                    <v-tabs-items
                    v-model="tabs">
                        <v-tab-item>
                            <div id="settings-container">
                                <v-text-field
                                v-model="user.name"
                                label="Name"></v-text-field>
                                <v-text-field
                                v-model="user.surname"
                                label="Surname"></v-text-field>
                                <v-text-field
                                v-model="user.phoneNo"
                                label="Phone No."></v-text-field>
                                <v-text-field
                                v-model="user.address.city"
                                label="City"></v-text-field>
                                <v-text-field
                                v-model="user.address.street"
                                label="Street"></v-text-field>
                                <v-text-field
                                v-model="user.address.streetNo"
                                label="Street No."></v-text-field>

                            </div>
                            <v-spacer></v-spacer>
                            <v-btn plain color="accent" @click="updateInfo">Update info</v-btn>
                        </v-tab-item>
                        <v-tab-item>
                            <div id="allergy-container">
                                <ul>
                                    <li v-for="allergy in allergies" :key="allergy.id">{{allergy.drug.name.split(' ')[0]}}</li>
                                </ul>
                                <v-btn plain @click="allergyDialog = true">+</v-btn>
                                <v-dialog
                                v-model="allergyDialog"
                                width="500">
                                    <v-card>
                                        <v-card-title>Allergies</v-card-title>
                                        <v-card-text>
                                            <v-data-table
                                            :headers="allergyHeaders"
                                            :items="potentialAllergies"
                                            @click:row="selectAllergy"></v-data-table>
                                        </v-card-text>
                                    </v-card>
                                </v-dialog>
                            </div>
                        </v-tab-item>
                    </v-tabs-items>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            allergyDialog: false,
            //
            allergies: [],
            allergyHeaders: [
                {text: 'Allergy', value:'name' }
            ],
            potentialAllergies: [],
            //
            tabs: null,
            //
            isAllergyValid: false,
            rules: {
                min: v => v.length > 0 || "Cannot be empty",
            },
            newAllergy: "",
            user: {
                address: {},
            },
            membership: 'Gold',
            penaltyCount: 0,
        }
    },
    methods: {
        updateInfo: function() {
            this.refreshToken()
                .then(rr => {
                    localStorage.jws = rr.data;
                    this.axios.post('api/patient/update', this.user, {headers: this.getAHeader()})
                        .then(() => {
                            this.getUser();
                        }).catch(r => console.log(r));
                }).catch(() => this.$router.push('/login'));
        },

        getPenaltyCount: function() {
            this.refreshToken()
                .then(rr => {
                    localStorage.jws = rr.data;
                    this.axios.get('api/penalty/patient/count', {headers:this.getAHeader()})
                        .then(r => {
                            this.penaltyCount = r.data;
                        })
                }).catch(() => this.$router.push('/login'));
        },
        getUser: function() {
            this.refreshToken()
                .then(rr => {
                    localStorage.jws = rr.data;
                    this.axios.get('api/patient', {headers: this.getAHeader()})
                        .then(r => {
                            this.user = r.data;
                        })
                }).catch(() => this.$router.push('/login'));
        },

        selectAllergy: function(item, event) {
            // eslint-disable-next-line no-unused-vars
            event
            this.refreshToken()
                .then(rr => {
                    localStorage.jws = rr.data;
                    this.axios.post('api/allergy/patient', parseInt(item.id),
                    { headers: {
                        "Authorization": "Bearer " + localStorage.jws,
                        "Content-Type" : "application/json",
                    }})
                        .then(() => {
                            this.allergyDialog = false;
                            this.getUserAllergies();
                        });
                }).catch(() => this.$router.push('/login'));
        },
        getPotentialAllergies: function() {
            this.refreshToken()
                .then(rr => {
                    localStorage.jws = rr.data;
                    this.axios.get('api/drug/allergy', {headers: this.getAHeader()})
                        .then(r => {
                            this.potentialAllergies = r.data.map(d => ({name: d.name.split(' ')[0], id: d.id}))
                        })
                }).catch(() => this.$router.push('/login'))
        },

        getUserAllergies: function() {
            this.refreshToken()
                .then(rr => {
                    localStorage.jws = rr.data;
                    this.axios.get('api/allergy/patient', {headers: this.getAHeader()})
                        .then(r => {
                            if(r.data._embedded) {
                                this.allergies = r.data._embedded.allergies;
                            } else {
                                this.allergies = [];
                            }
                        })
                }).catch(() => this.$router.push('/login'));
        },
        getProfileImg: function() {
            return require('../assets/placeholder-profile-sq.jpg')
        },

    },
    mounted() {
        this.getPenaltyCount();
        this.getUser();
        this.getPotentialAllergies();
        this.getUserAllergies();
    },
}
</script>

<style scoped>
    #user-main {
        display: grid;
        place-items: center;
        height: 100vh;
        background: #fafafa;
    }

    #user-profile {
        margin: 20px;
        display: flex;
        flex-direction: column;
    }

    #user-container {
        background: #fff;
        display: flex;
        flex-direction: row;
        padding: 20px;
    }

    #allergy-container {
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        text-align: center;
    }

    #user-basic {
        max-width: 20vw;
    }
    

    #user-basic b {
        font-weight: 500;
    }

    #user-basic img {
        width: 100%;
    }

    #user-about {
        flex-grow: 1;
    }

    #user-about h3 {
        font-weight: 400;
    }

    .divider {
        width: 40vw;
        border-bottom: 1px solid #eee;
    }

    #overview-container {
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        padding: 20px;
    }

    .grid-container {
        display: grid;
        grid-template-columns: 1fr 1fr;
    }
</style>