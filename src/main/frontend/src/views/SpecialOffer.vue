<template>
    <div id="specialoffer-main">
        <v-card
        id="specialoffer-card"
        elevation="2">
            <v-card-title>Special offer</v-card-title>
            <v-card-text>
                <v-form v-model="valid">
                    <v-text-field
                    v-model="name"
                    :rules="nameRules"
                    label="Name"
                    ></v-text-field>
                <v-form v-model="valid">
                    <v-text-field
                    v-model="content"
                    :rules="nameRules"
                    label="Content"
                    ></v-text-field>
                </v-form>
                <v-form>
                    <v-text>
                        Period
                    </v-text>
                </v-form>
                <v-form>
                    <v-date-picker v-model="dateRange"
                                range 
                                :min="new Date().toISOString().substr(0, 10)">
                    </v-date-picker>
                </v-form>
            </v-card-text>
            <v-card-actions>
                <v-btn 
                color="primary"
                elevation="1"
                @click="add" 
                :disabled="!dateRange[0] || !dateRange[1] || !name || !content"
                >Add</v-btn>
            </v-card-actions>
        </v-card>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                name: '',
                content: '',
                dateRange: "",
            }
        },
        methods: {
            add: function() {
            },
        },
    }
</script>

<style scoped>
    #specialoffer-main {
        background: linear-gradient(69deg, rgba(63,81,181,1) 5%, rgba(197,202,233,1) 100%); 
    }
    #specialoffer-card {
        display: flex;
        flex-direction: column;
        margin: 0 auto;
        width: 40%;
    }
    #specialoffer-main {
        display: flex;
        flex-direction: column;
        justify-content: center;
        width: 100vw;
        min-height: 100vh;
    }
</style>