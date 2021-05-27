<template>
    <div id="define-main">
        <v-card
        id="define-card"
        elevation="2">
            <v-card-title>New appointment</v-card-title>
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
            <v-datetime-picker label="Datetime" v-model="datetime"> </v-datetime-picker>
            <v-btn class='primary' elevation="1" @click="createAppointment()" :disabled="!dermatologist || !price">
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
                price: '',
                rules: {
                    required: value => !!value || 'Required.',
                },
            }
        },
        methods: {
            getDermatologists: function() {

            },

            refreshToken: async function() {
                let jws = this.$store.state.jws;
                if(!jws) {
                    this.$router.push("/");
                }
                return this.axios.get("/api/auth/refresh", {headers: {"Authorization": "Bearer " + jws}});
            },

            createAppointment: function() {
                
            },
        },
        mounted() {
			this.getDermatologists();
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