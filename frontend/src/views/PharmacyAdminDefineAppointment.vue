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
            <div>
                <v-datetime-picker v-model="startTime" :min="new Date().toISOString().substr(0, 10)" label="From"/>
            </div>
            <div>
                <v-datetime-picker v-model="endTime" :min="startTime" label="To"/>
            </div>
            <v-btn class='primary' elevation="1" @click="createAppointment()" :disabled="/*!dermatologist ||*/ !price || !startTime || !endTime">
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
                startTime: '',
                endTime: '',
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
                if(this.startTime < Date.now() || this.endTime < Date.now() || this.startTime > this.endTime)
                {
                    alert('Time interval is not set properly');
                    return;
                }

                //let appointmentRequest = { startTime: this.startTime, endTime: this.endTime, price: this.price, dermatologistId: dermatologist.id };
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