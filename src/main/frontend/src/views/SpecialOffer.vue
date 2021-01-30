<template>
    <div id="specialoffer-main">
        <v-card
        id="specialoffer-card"
        elevation="2">
            <v-card-title>Special offer</v-card-title>
            <v-card-subtitle v-if="apiKey">
                <b class="green--text">Special offer name:</b> {{apiKey}}
                <div><b class="green--text">Special offer content:</b> {{}}</div>
            </v-card-subtitle>
            <v-card-subtitle v-else-if="failedMsg"><b class="red--text">{{failedMsg}}</b></v-card-subtitle>
            <v-card-text>
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
                @click="add"
                :disabled="!valid"
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
                failedMsg: "",
            }
        },
        methods: {
            add: function() {
                if (!this.valid) return;
                this.failedMsg = "";
                this.apiKey = "";
            },
        },
    }
</script>