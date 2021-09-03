<template>
    <div>
        <main-navigation>
                <router-link to="/">Home</router-link>
        </main-navigation>
        <div>
            <v-card id="new-card">
                <v-card-title>Complaint</v-card-title>
                <v-card-text>
                    <v-textarea v-model="complaint">
                    </v-textarea>
                    <v-divider></v-divider>
                    <v-btn @click="sendComplaint()">
                        Send Complaint
                    </v-btn>
                </v-card-text>
            </v-card>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            complaint: '',
        }
    },
    computed:{
        currentUser() {
                return this.$store.state.auth.user;
            }
    },
    methods: {
        
        sendComplaint: function() {
            var dto = {
            text: this.complaint
            }
            this.axios.post("api/complaint/complain", dto, {headers:{"Authorization": "Bearer " + this.currentUser}})
            .then(r => {
                console.log(r.data);
                this.$router.push('user');
            })
            .catch(r => {
                console.log(r.data);
            })
        }
    },
    mounted() {

    },
}
</script>

<style scoped>
    #new-card {
        width: 60%;
        margin: 0 auto;
        justify-content: center;
    }
</style>