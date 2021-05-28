<template>
    <div id="uhome-main" class="secondary">
        <main-navigation>
            <router-link to="/">Home</router-link>
        </main-navigation>
        <div id="uhome-container">
            <div id="uhome-info">
                <div id="info-container">
                    <div id="profile" class="info-card">
                        <v-avatar>
                            <img :src="getProfileImg()" alt="Profile img">
                        </v-avatar>
                        <div id="profile-info">
                            <h3 class="primary--text">{{user.name}} {{user.surname}}</h3>
                            <span class="info--text">Membership: <b class="accent--text">{{user.membershipType}}</b></span>
                            <span class="info--text">Penalties: <b class="accent--text">{{user.penalties}}</b></span>
                            <router-link to="/user/about">Profile info</router-link>
                        </div>
                    </div>
                </div>
            </div>
            <div id="uhome-panel">
                <div id="panel-container">
                    <v-card tile>
                        <v-card-title>
                            Pharmacies
                            <v-spacer></v-spacer>
                            <v-text-field
                            v-model="pharmacySearch"
                            label="Search"
                            append-icon="fa-search"
                            single-line
                            hide-details
                            />
                        </v-card-title>
                        <v-card-text>
                            <v-data-table
                            :headers="pharmacyHeaders"
                            :items="pharmacies"
                            :items-per-page="5"
                            :search="pharmacySearch">
                            </v-data-table>
                        </v-card-text>
                    </v-card>
                    <v-card tile>
                        <v-card-title>Active appointments</v-card-title>
                        <v-card-text>
                            <div id="active-app-container">
                                
                            </div>
                        </v-card-text>
                    </v-card>
                    <v-card tile>
                        <v-card-title>Pharmacist consult history</v-card-title>
                        <v-card-text>Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum illo debitis sunt quos recusandae, culpa ducimus magnam qui dicta voluptatibus aperiam similique amet impedit, nostrum consequatur molestias ea reprehenderit sed!</v-card-text>
                    </v-card>
                    <v-card tile>
                        <v-card-title>Dermatologist appointment history</v-card-title>
                        <v-card-text>Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum illo debitis sunt quos recusandae, culpa ducimus magnam qui dicta voluptatibus aperiam similique amet impedit, nostrum consequatur molestias ea reprehenderit sed!</v-card-text>
                    </v-card>
                    <v-card tile>
                        <v-card-title>E-Prescriptions</v-card-title>
                        <v-card-text>Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum illo debitis sunt quos recusandae, culpa ducimus magnam qui dicta voluptatibus aperiam similique amet impedit, nostrum consequatur molestias ea reprehenderit sed!</v-card-text>
                    </v-card>
                    <v-card tile>
                        <v-card-title>Drug reservations</v-card-title>
                        <v-card-text>Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum illo debitis sunt quos recusandae, culpa ducimus magnam qui dicta voluptatibus aperiam similique amet impedit, nostrum consequatur molestias ea reprehenderit sed!</v-card-text>
                    </v-card>
                    <v-card tile>
                        <v-card-title>E-Prescription drugs</v-card-title>
                        <v-card-text>Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum illo debitis sunt quos recusandae, culpa ducimus magnam qui dicta voluptatibus aperiam similique amet impedit, nostrum consequatur molestias ea reprehenderit sed!</v-card-text>
                    </v-card>
                    <v-card tile>
                        <v-card-title>Active pharmacy subscriptions</v-card-title>
                        <v-card-text>Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum illo debitis sunt quos recusandae, culpa ducimus magnam qui dicta voluptatibus aperiam similique amet impedit, nostrum consequatur molestias ea reprehenderit sed!</v-card-text>
                    </v-card>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            pharmacySearch: '',
            pharmacyHeaders: [
                { text: 'Name', value: 'name' },
                { text: 'City', value: 'city' },
                { text: 'Avg. score', value: 'avgScore' },
            ],
            pharmacies: [],
            user: {
                name: "Petar",
                surname: "Petrovic",
                membershipType: "gold",
                penalties: 1,
            },
        }
    },

    methods: {

        refreshToken: async function() {
            let jws = this.$store.state.jws;
            if(!jws) {
                this.$router.push("/");
            }
            return this.axios.get("/api/auth/refresh", {headers: {"Authorization": "Bearer " + jws}});
        },

        getProfileImg: function() {
            return require('../assets/placeholder-profile-sq.jpg')
        },

        getUser: function() {
            console.log("Getting user");
            let jws = this.$store.state.jws;
            this.axios.get("api/patient", {headers:{"Authorization": "Bearer " + jws}})
                .then(r => {
                    console.log(r.data);
                })
                .catch(r => {
                    console.log("Failed to get patient", r.data);
                    this.refreshToken()
                        .then(r => {
                            this.$store.state.jws = r.data;
                            this.$router.go();
                        })
                        .catch(r => {
                            console.log(r.data);
                            this.$router.push("/");
                        });
                });
        }
    },

    mounted() {
        this.getUser();
        this.refreshToken().then(r => {
            console.log(r);
        })
        .catch(r => {
            console.log(r);
        })
    },
}
</script>

<style scoped>
    #uhome-main {
        /* background: #dedede; */
    }

    #uhome-container {
        display: flex;
        flex-direction: row;
        margin: 20px;
    }

    #uhome-info {
        background: #f8f8f8;
        min-width: 20vw;
    }

    #profile {
        display: flex;
        flex-direction: row;
        justify-content: space-around;
        align-items: center;
    }

    #profile h3 {
        font-family: 'Poppins', sans-serif;
        font-weight: 500;
        text-transform: uppercase;
    }

    #profile-info {
        display: flex;
        flex-direction: column;
    }

    #profile-info span {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
    }

    #profile-info b {
        font-weight: 500;
    }
    
    .info-card {
        background: #fbfbfb;
        border-bottom: 1px solid #eee;
        padding: 10px;
    }
    
</style>
