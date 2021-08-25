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
                    v-model="dermatologist.name"
                    :rules="[rules.required]"
                    label="Name"
                    :disabled="!editMode"
                    ></v-text-field>
                    <v-text-field
                    v-model="dermatologist.surname"
                    :rules="[rules.required]"
                    label="Surname"
                    :disabled="!editMode"
                    ></v-text-field>
                    <v-text-field
                    v-model="dermatologist.email"
                    :rules="[rules.required]"
                    label="Email"
                    :disabled="true"
                    ></v-text-field>
                    <v-text-field
                    v-model="dermatologist.address.city"
                    :rules="[rules.required]"
                    label="City"
                    :disabled="!editMode"
                    ></v-text-field>
                    <v-text-field
                    v-model="dermatologist.address.postcode"
                    :rules="[rules.required]"
                    label="Postcode"
                    :disabled="!editMode"
                    ></v-text-field>
                    <v-text-field
                    v-model="dermatologist.address.street"
                    :rules="[rules.required]"
                    label="Street"
                    :disabled="!editMode"
                    ></v-text-field>
                     <v-text-field
                    v-model="dermatologist.address.streetNo"
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
                dermatologist: {},
                dermatologistCopy: {},
                confirmPassword: '',
                password: '',
                changePasswordForm: false,
                show1: false,
                show2: false,
                valid: false,
                validPassword: false,
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
             refreshToken: async function() {
                //let jws = window.localStorage.getItem('jwt');
                if(!localStorage.jws) {
                    this.$router.push("/");
                }
                return this.axios.get("/api/auth/refresh", {headers: {"Authorization": "Bearer " + localStorage.jws}});
            },
            getDermatologist: function() {
                console.log("Getting dermatologist");
                //let jws = window.localStorage.getItem('jwt');
                console.log(localStorage.jws)
                this.axios.get("api/dermatologist/jwt", {headers:{"Authorization": "Bearer " + localStorage.jws}})
                    .then(response => {
                        console.log(response.data);
                        this.dermatologist = response.data;
                        this.dermatologistCopy = JSON.parse(JSON.stringify(response.data));
                    })
                    .catch(response => {
                        console.log("Failed to get patient", response.data);
                        this.refreshToken()
                            .then(response => {
                                localStorage.jws = response.data;
                                this.$router.go();
                            })
                            .catch(response => {
                                console.log(response.data);
                                this.$router.push("/");
                            });
                    });
            },
            editModeChange: function(){
                this.editMode = !this.editMode;
                this.changePasswordForm = false;
                this.confirmPassword = '';
                this.password = '';
                this.dermatologist = JSON.parse(JSON.stringify(this.dermatologistCopy));

            },
            save: function(){
                if(this.changePasswordForm){
                    //let jws = window.localStorage.getItem('jwt');
                    let dto = {email: this.dermatologist.email, password: this.password}
                    this.axios.put("api/dermatologist/pass", dto, {headers:{"Authorization": "Bearer " + localStorage.jws}})
                        .then(response =>
                        {
                            this.dermatologistCopy = response.data;
                            this.editModeChange();
    
                        })
                        .catch(response =>
                        {
                            console.log(response.data);
                        });
                }
                else{
                    //let jws = window.localStorage.getItem('jwt');
                    this.axios.put("api/dermatologist", this.dermatologist, {headers:{"Authorization": "Bearer " + localStorage.jws}})
                        .then(response =>
                        {
                            this.dermatologistCopy = response.data;
                            this.editModeChange();
    
                        })
                        .catch(response =>
                        {
                            console.log(response.data);
                        });
                }
            }
        },
        mounted(){
            this.getDermatologist();
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