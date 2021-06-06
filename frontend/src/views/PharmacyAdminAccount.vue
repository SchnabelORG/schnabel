<template>
    <div id="account-main">
        <v-card 
        min-width="50%">
            <v-card-title class="info primary--text">
                <b>Your information</b>
                <v-spacer></v-spacer>
					<v-btn v-if="editMode" class="accent white--text" dark @click="changePasswordForm = !changePasswordForm">
                        Change password
                   </v-btn>
					<v-btn class="accent white--text" dark @click="editModeChange()">
                        <div v-if="!editMode"><i class="fa fa-pencil fa-fw" aria-hidden="true"></i> Edit</div>
                        <div v-else><i class="fa fa-ban fa-fw"></i>Cancel</div>
                   </v-btn>
            </v-card-title>
            <v-card-text v-if="!changePasswordForm">
                <v-form id="ph-add" v-model="valid">
                    <v-text-field
                    v-model="pharmacyAdmin.name"
                    :rules="[rules.required]"
                    label="Name"
                    :disabled="!editMode"
                    ></v-text-field>
                    <v-text-field
                    v-model="pharmacyAdmin.surname"
                    :rules="[rules.required]"
                    label="Surname"
                    :disabled="!editMode"
                    ></v-text-field>
                    <v-text-field
                    v-model="pharmacyAdmin.email"
                    :rules="[rules.required]"
                    label="Email"
                    :disabled="true"
                    ></v-text-field>
                    <v-text-field
                    v-model="pharmacyAdmin.address.city"
                    :rules="[rules.required]"
                    label="City"
                    :disabled="!editMode"
                    ></v-text-field>
                    <v-text-field
                    v-model="pharmacyAdmin.address.postcode"
                    :rules="[rules.required]"
                    label="Postcode"
                    :disabled="!editMode"
                    ></v-text-field>
                    <v-text-field
                    v-model="pharmacyAdmin.address.street"
                    :rules="[rules.required]"
                    label="Street"
                    :disabled="!editMode"
                    ></v-text-field>
                     <v-text-field
                    v-model="pharmacyAdmin.address.streetNo"
                    :rules="[rules.required, rules.isNmb]"
                    label="Street number"
                    :disabled="!editMode"
                    ></v-text-field>
                    <v-btn :disabled="!valid" id="save-btn"  v-if="editMode" class="accent white--text" @click="save()">
                        Save changes
                    </v-btn>
                </v-form>
            </v-card-text>
            <v-card-text v-else>
                <v-form id="ph-add" v-model="validPassword">
                   <v-text-field
                    v-model="password"
                    :append-icon="show1 ? 'fa-eye' : 'fa-eye-slash'"
                    :rules="[rules.required, rules.min]"
                    :type="show1 ? 'text' : 'password'"
                    label="Change your Password"
                    hint="At least 8 characters"
                    counter
                    @click:append="show1 = !show1"
                    ></v-text-field>
                    <v-text-field
                    v-model="confirmPassword"
                    :append-icon="show2 ? 'fa-eye' : 'fa-eye-slash'"
                    :rules="[rules.required, rules.min, passwordConfirmationRule]"
                    :type="show2 ? 'text' : 'password'"
                    label="Confirm Password"
                    hint="At least 8 characters"
                    counter
                    @click:append="show2 = !show2"
                    ></v-text-field>
                    <v-btn :disabled="!validPassword" id="save-btn"  v-if="editMode" class="accent white--text" @click="save()">
                        Save changes
                    </v-btn>
                </v-form>
            </v-card-text>
        </v-card>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                editMode: false,
                pharmacyAdmin: {},
                pharmacyAdminCopy: {},
                confirmPassword: '',
                password:'',
                changePasswordForm: false,
                validPassword: false,
                show1: false,
                show2: false,
                valid: false,
                rules: {
                    required: value => !!value || 'Required.',
                    min: v => v.length >= 8 || 'Min 8 characters',
                    isNmb: v => /^\d+$/.test(v) || 'Must be a number'
                },
            }
        },
        computed:{
            passwordConfirmationRule: function() {
                return () => (this.password === this.confirmPassword) || 'Password must match'
            },
        },
        methods: {
            getPharmacyAdmin: function() {
                this.refreshToken().then(response => {
                    localStorage.jws = response.data;
                    this.axios.get("api/pharmacyadmin", {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                        .then(response => {
                            this.pharmacyAdmin = response.data;
                            this.pharmacyAdminCopy = JSON.parse(JSON.stringify(response.data));
                        })
                        .catch(response => {
                            console.log("Failed to get pharmacy admin", response.data);
                        });
                   })
                    .catch(response => {
                    console.log(response.data);
                    this.$router.push("/");
                });
            },
            editModeChange: function(){
                this.editMode = !this.editMode;
                this.changePasswordForm = false;
                this.confirmPassword = '';
                this.password = '';
                this.pharmacyAdmin = JSON.parse(JSON.stringify(this.pharmacistCopy));
            },
            save: function(){
                 if(this.changePasswordForm){
                    this.refreshToken().then(response => {
                        localStorage.jws = response.data;
                        let dto = {email: this.pharmacyAdmin.email, password: this.password}
                        this.axios.put("api/pharmacyadmin/pass", dto, {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                            .then(response =>
                            {
                                this.pharmacyAdminCopy = response.data;
                                this.editModeChange();

                            })
                            .catch(response =>
                            {
                                console.log(response.data);
                            });
                        })
                        .catch(response => {
                        console.log(response.data);
                        this.$router.push("/");
                });
                 } else {
                     this.refreshToken().then(response => {
                        localStorage.jws = response.data;
                        this.axios.put("api/pharmacyadmin", this.pharmacyAdmin, {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                            .then(response =>
                            {
                                this.pharmacyAdminCopy = response.data;
                                this.editModeChange();

                            })
                            .catch(response =>
                            {
                                console.log(response.data);
                            });
                        })
                        .catch(response => {
                        console.log(response.data);
                        this.$router.push("/");
                });
                 }
            },
        },
        mounted() {
            this.getPharmacyAdmin();
        }
    }

</script>

<style scoped>
    #account-main{
        display:grid;
        grid-template-columns:auto;
        place-items: center;
        min-height: 92vh;
        background-color: #fbecdd;
    }
    #save-btn{
        margin-top: 5%;
        min-width: 100%;
    }
</style>