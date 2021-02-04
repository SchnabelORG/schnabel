<template>
    <div id="create-main">
        <v-card
        id="create-card"
        elevation="2">
            <v-card-title>Create appointment</v-card-title>
            <v-card-text>
                <v-form id="create-form">
                   <v-combobox v-model="dermatologist"
                                :items="dermatologists"
                                return-object="true"
                                label="Dermatologist"
                                :rules="rule">
                        <template slot="selection" slot-scope="data" >
                            {{ data.item.name - data.item.surname }}
                        </template>
                        <template slot="item" slot-scope="data">
                            {{ data.item.name - data.item.surname }}
                        </template>
                    </v-combobox>
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
                            v-model="date"
                            label="Date"
                            prepend-icon="mdi-calendar"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                        ></v-text-field>
                        </template>
                        <v-date-picker
                        ref="picker"
                        v-model="date"
                        :min="new Date().toISOString().substr(0, 10)"
                        @change="save"
                        ></v-date-picker>
                    </v-menu>
                    </template>
                    <v-combobox v-model="duration"
                                :items="durations"
                                return-object="true"
                                label="Duration"
                                :rules="rule">
                        <template slot="selection" slot-scope="data" >
                            {{ data.item }}
                        </template>
                        <template slot="item" slot-scope="data">
                            {{ data.item }}
                        </template>
                    </v-combobox>
                    <v-text-field v-model="price"
                            label="Price"
                            :rules="rule">
                </v-text-field>
                </v-form>
            </v-card-text>
            <v-btn id="add-btn" class="deep-orange white--text" elevation="0" @click="create" :disabled="!dermatologist || !price">
                Create Appointment
            </v-btn>
        </v-card>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                dermatologists: [],
                dermatologist: '',
                date: '',
                duratioin: '',
                price: '',
                menu: false,
                durations: [15, 30],
                rule: [
                    v => !!v || "Must not be empty!",
                ],
            }
        },
        methods: {
            create: function() {

            },
            getDermatologists: function() {

            },
            save (date) {
                this.$refs.menu.save(date)
            },  
        },
        mounted() {
            this.getDermatologists();
        },
    }
</script>

<style scoped>
    #create-main {
        background: linear-gradient(69deg, rgba(63,81,181,1) 5%, rgba(197,202,233,1) 100%); 
    }
    #create-card {
        display: flex;
        flex-direction: column;
        margin: 0 auto;
        width: 40%;
    }
    #create-main {
        display: flex;
        flex-direction: column;
        justify-content: center;
        width: 100vw;
        min-height: 100vh;
    }
</style>