<template>
    <div id="pharmacists-main">
        <v-card
        id="pharmacists-card"
        elevation="2">
            <v-card-title>New System Admin</v-card-title>
            <v-card-text>
                <v-text-field
                        v-model="name"
                        :rules="[rules.required]"
                        label="Name"
                        ></v-text-field>
                <v-text-field
                        v-model="surname"
                        :rules="[rules.required]"
                        label="Surname"
                        ></v-text-field>
                <v-text-field
                        v-model="email"
                        :rules="[rules.required]"
                        label="Email"
                        ></v-text-field>
                <v-text-field
                        v-model="password"
                        :rules="[rules.required]"
                        label="Password"
                        ></v-text-field>
                <v-text-field
                        v-model="postcode"
                        :rules="[rules.required]"
                        label="Postcode"
                        ></v-text-field>
                <v-text-field
                        v-model="city"
                        :rules="[rules.required]"
                        label="City"
                        ></v-text-field>
                <v-text-field
                        v-model="street"
                        :rules="[rules.required]"
                        label="Street"
                        ></v-text-field>
                <v-text-field
                        v-model="streetNo"
                        :rules="[rules.required]"
                        label="StreetNo"
                        ></v-text-field>
                <v-spacer></v-spacer>
           
            <v-btn class="primary" @click="createSystemAdmin()" :disabled="!password || !name || !email">
                Add
            </v-btn>
            <v-btn class="accent" @click="cancel()">
                Cancel
            </v-btn>
            </v-card-text>
        </v-card>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                systemAdmin: {},
                name: '',
                surname: '',
                email: '',
                password: '',
                postcode: '',
                city: '',
                street: '',
                streetNo: '',
                id: '',
                search: '',
                rules: {
                    required: value => !!value || 'Required.',
                },
            }
        },
        computed:{
            currentUser() {
                return this.$store.state.auth.user;
            }
        },
        methods: {
            getSystemAdmin: function() {
                this.axios.get("api/systemAdmin/get", {headers:{"Authorization": "Bearer " + this.currentUser}})
                .then(r =>
                {
                    this.systemAdmin = r.data;
                })
                .catch(r => 
                {
                    this.$router.push('/redirect');
                    console.log(r.data);
                })
            },
            getAddress: function(address) {
                var a = address.street + ' ' + address.streetNo + ', ' + address.city + ', ' + address.postcode; 
                return a;
            },
            createSystemAdmin: function() {
                var adr = {postcode: this.postcode, city: this.city, street: this.street, streetNo: this.streetNo}
                let request = { name: this.name, surname: this.surname, email: this.email, password: this.password, address: adr };
                this.axios.post("api/systemAdmin/", request, {headers:{"Authorization": "Bearer " + this.currentUser}})
                .then(response => {
                    console.log("Successfully regirested system admin", response.data);
                    this.getSuppliers();
                })
                .catch(response => {
                    console.log("Failed to add system admin", response.data);
                });
                
                this.new = false;
                this.name = '';
                this.surname = '';
                this.email = '';
                this.validFrom = '';
                this.validUntil = '';
            },
            cancel: function() {
                this.new = false;
                this.name = '';
                this.surname = '';
                this.email = '';
                this.validFrom = '';
                this.validUntil = '';
                this.firm = '';
            },
        },
        mounted() {
        },
    }
</script>


<style scoped>
    #pharmacists-main {
        display:grid;
        grid-template-columns:auto;
        place-items: center;
        min-height: 92vh;
        background-color: #fbecdd;
    }
    #new-card {
        width: 60%;
        margin: 0 auto;
        justify-content: center;
    }
</style>