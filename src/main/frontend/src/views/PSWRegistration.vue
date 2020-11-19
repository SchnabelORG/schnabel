<template>
    <div id="pswreg-main">
        <v-card
        id="pswreg-card"
        elevation="2">
            <v-card-title>Hospital API registration</v-card-title>
            <v-card-subtitle v-if="apiKey"><b class="red--text">Your api key:</b> {{apiKey}}</v-card-subtitle>
            <v-card-text>
                <!-- TODO(Jovan): Registration form -->
                <v-form v-model="valid">
                    <v-text-field
                    v-model="id"
                    :rules="idRules"
                    label="Hospital ID"
                    ></v-text-field>
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
                id: '',
                idRules: [
                    v => !!v || "Id is required",
                ],
                name: '',
                nameRules: [
                    v => !!v || "Name is required",
                ],

                apiKey: "",
            }
        },
        methods: {
            register: function() {
                if (!this.valid) return;
                this.apiKey = "";
                this.axios.get("pswapi/" + this.id)
                .then(response => {
                    this.apiKey = response.data;
                    console.log(response);
                })
                .catch(response => {
                    this.apiKey = "Failed to load";
                    console.log(response);
                })
            },
        },
    }
</script>

<style scoped>
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
