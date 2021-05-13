<template>
    <div id="pswreg-main">
        <v-card
        id="pswreg-card"
        elevation="2">
            <v-card-title>Hospital API registration</v-card-title>
            <v-card-subtitle v-if="apiKey">
                <b class="green--text">Your API key:</b> {{apiKey}}
                <div><b class="green--text">API Endpoint:</b> /pswapi</div>
            </v-card-subtitle>
            <v-card-subtitle v-else-if="failedMsg"><b class="red--text">{{failedMsg}}</b></v-card-subtitle>
            <v-card-text>
                <!-- TODO(Jovan): Registration form -->
                <v-form v-model="valid">
                    <v-text-field
                    v-model="name"
                    :rules="nameRules"
                    label="Hospital Name"
                    ></v-text-field>
                </v-form>
            </v-card-text>
            <v-card-actions>
                <v-btn 
                color="primary"
                elevation="1"
                @click="register"
                :disabled="!valid"
                >Get API key</v-btn>
            </v-card-actions>
        </v-card>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                valid: false,
                name: '',
                nameRules: [
                    v => !!v || "Name is required",
                ],

                apiKey: "",
                failedMsg: "",
            }
        },
        methods: {
            register: function() {
                if (!this.valid) return;
                this.failedMsg = "";
                this.apiKey = "";
                let hospital = {
                    id: "",
                    apiKey: "",
                    name: this.name,
                };
                this.axios.post("pswapi", hospital)
                .then(response => {
                    this.apiKey = response.data;
                    console.log(response);
                })
                .catch(response => {
                    this.failed = "Failed to get API key";
                    console.log(response);
                })
            },
        },
    }
</script>

<style scoped>
    #pswreg-main {
        background: linear-gradient(69deg, rgba(63,81,181,1) 5%, rgba(197,202,233,1) 100%); 
    }
    #pswreg-card {
        display: flex;
        flex-direction: column;
        margin: 0 auto;
        width: 40%;
    }
    #pswreg-main {
        display: flex;
        flex-direction: column;
        justify-content: center;
        width: 100vw;
        min-height: 100vh;
    }
</style>
