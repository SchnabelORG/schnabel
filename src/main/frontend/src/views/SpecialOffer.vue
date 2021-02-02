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
                <template>
                    <v-menu
                        ref="menu"
                        v-model="menu"
                        :close-on-content-click="false"
                        transition="scale-transition"
                        offset-y
                        min-width="auto"
                        >
                        <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                            v-model="dateRange[0]"
                            label="Special offer valid from"
                            prepend-icon="mdi-calendar"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                        ></v-text-field>
                        </template>
                        <v-date-picker
                        ref="picker"
                        v-model="dateRange[0]"
                        :max="dateRange[1]"
                        :min="new Date().toISOString().substr(0, 10)"
                        @change="save"
                        ></v-date-picker>
                    </v-menu>
                    </template>
                    <template>
                    <v-menu
                        ref="menuto"
                        v-model="menuto"
                        :close-on-content-click="false"
                        transition="scale-transition"
                        offset-y
                        min-width="auto"
                        >
                        <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                            v-model="dateRange[1]"
                            label="Special offer valid to"
                            prepend-icon="mdi-calendar"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                        ></v-text-field>
                        </template>
                        <v-date-picker
                        ref="picker"
                        v-model="dateRange[1]"
                        :min="dateRange[0]"
                        @change="saveto"
                        ></v-date-picker>
                    </v-menu>
                    </template>
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
                dateRange: [],
                menu: false,
                menuto: false,
            }
        },
        methods: {
            add: function() {
                let specialoffer = { id: 5, name: this.name, content: this.content, validFrom: this.dateRange[0], validUntil: this.dateRange[1], pharmacyId: "Jankovic" };
                this.axios.post("specialoffer", specialoffer)
                .then(response => {
                    console.log(response);
                })
                .catch(response => {
                    console.log(response);
                })
            },
            save (date) {
                this.$refs.menu.save(date)
            },
            saveto (date) {
                this.$refs.menuto.saveto(date)
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