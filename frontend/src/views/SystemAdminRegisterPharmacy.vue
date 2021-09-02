<template>
    <div id="register-pharmacy">
        <v-card
            id="register-card"
            elevation="2"
        >
            <v-card-title>
                Register Pharmacy
            </v-card-title>
            <b class="err">{{error}}</b>
            <div v-if="success" id="success-form">
                <p id="success-icon"><i class="fa fa-check"></i></p>
                <p>Pharmacy registered!</p>
            </div>
            <v-card-text id="pharmacy-info">
                <v-form>
                   <v-text-field
                        v-model="name"
                        label="Name"
                        :rules="[rules.required]"
                        required/>
                    <div class="street">
                        <v-text-field
                        v-model="street"
                        label="Street"
                        :rules="[rules.required]"
                        required/>
                        <v-text-field
                        v-model="streetNo"
                        label="Street No."
                        :rules="[rules.required]"
                        required/>
                    </div>
                    <div class="street">
                        <v-text-field
                        v-model="city"
                        label="City"
                        :rules="[rules.required]"
                        required/>
                        <v-text-field
                        v-model="zipCode"
                        label="Zip Code"
                        :rules="[rules.required]"
                        required/>
                    </div>
                        <v-text-field
                        v-model="state"
                        label="State"
                        :rules="[rules.required]"
                        required/>
                    <v-divider></v-divider>
                    <v-card-title>Assign pharmacy admin</v-card-title>
                    <div>
                        <v-combobox
                            v-model="pharmacyAdmin"
                            :items="pharmacyadmins"
                            return-object
                            label="Pharmacy Admins"
                            clearable
                        >
                           <template slot="selection" slot-scope="data"> 
                                 {{data.item.fullname}}
                            </template>
                            <template slot="item" slot-scope="data">
                                {{data.item.fullname}}
                            </template>
                        </v-combobox>
                    </div>
                    <v-btn color="accent" @click="register" :disabled="!name || !street || !streetNo || !city || !state || !zipCode">Register</v-btn>
                    <v-btn @click="$router.go(-1)" >Back</v-btn>
                </v-form>
            </v-card-text>
        </v-card>
    </div>
</template>

<script>
export default {
    data() {
        return {
            name: '',
            street: '',
            streetNo: '',
            city: '',
            zipCode: '',
            state: '',
            pharmacyadmins: [],
            pharmacyAdmin: [],
            success: false,
            error: '',
            rules: {
                required: v => !!v || "Required",
            },
        }
    },
    computed: {
        currentUser() {
            return this.$store.state.auth.user;
        }
    },
    methods: {
        getPharmacyAdmins: function() {
            this.axios.get("api/pharmacyadmin/all")
            .then(r => 
            {
                //this.pharmacyadmins = r.data._embedded.pharmacyadmis;
                for(var pha of r.data._embedded.pharmacyadmins) {
                        var ful = pha.name + '  ' + pha.surname + ',  ' + pha.email;
                        var p = {id: pha.id, fullname: ful};
                        this.pharmacyadmins.push(p);
                        console.log(p)
                    }
                console.log(this.pharmacyadmins)
            })
            .catch(r => 
            {
                console.log(r.data);
            })
        },
        register: function() {
            let request = {
                name: this.name,
                address: {
                    postcode: this.zipCode,
                    city: this.city,
                    street: this.street,
                    streetNo: this.streetNo,
                },
            }
            this.axios.post("api/pharmacy/register", request, {headers:{"Authorization": "Bearer " + this.currentUser}})
            .then(r => {
                console.log(r.data)
                
                this.success = true;
            })
            .catch(r => {
                console.log(r.data)
                this.success = false;
            })
        }

    },
    mounted() {
        this.getPharmacyAdmins();
    }
}
</script>

<style scoped>
    .street {
        display: grid;
        grid-template-columns: 1fr 0.5fr;
        gap: 10px;
    }
    #register-card {
        display: flex;
        flex-direction: column;
        margin: 0 auto;
        width: 40%;
    }
    #register-pharmacy {
        display: flex;
        flex-direction: column;
        justify-content: center;
        width: 100vw;
        min-height: 100vh;
    }
</style>