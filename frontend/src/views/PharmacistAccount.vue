<template>
    <div id="account-main">
        <v-card 
        min-width="50%">
            <v-card-title class="info primary--text">
                <b>Your information</b>
                <v-spacer></v-spacer>
					<v-btn class="accent white--text" dark @click="editModeChange()">
                        <div v-if="!editMode"><i class="fa fa-pencil fa-fw" aria-hidden="true"></i> Edit</div>
                        <div v-else><i class="fa fa-ban fa-fw"></i>Cancel</div>
                   </v-btn>
            </v-card-title>
            <v-card-text>
                <v-form id="ph-add" v-model="valid">
                    <v-text-field
                    v-model="pharmacist.name"
                    :rules="[rules.required]"
                    label="Name"
                    :disabled="!editMode"
                    ></v-text-field>
                    <v-text-field
                    v-model="pharmacist.surname"
                    :rules="[rules.required]"
                    label="Surname"
                    :disabled="!editMode"
                    ></v-text-field>
                    <v-text-field
                    v-model="pharmacist.email"
                    :rules="[rules.required]"
                    label="Email"
                    :disabled="true"
                    ></v-text-field>
                    <v-text-field
                    v-model="pharmacist.address.city"
                    :rules="[rules.required]"
                    label="City"
                    :disabled="!editMode"
                    ></v-text-field>
                    <v-text-field
                    v-model="pharmacist.address.postcode"
                    :rules="[rules.required]"
                    label="Postcode"
                    :disabled="!editMode"
                    ></v-text-field>
                    <v-text-field
                    v-model="pharmacist.address.street"
                    :rules="[rules.required]"
                    label="Street"
                    :disabled="!editMode"
                    ></v-text-field>
                     <v-text-field
                    v-model="pharmacist.address.streetNo"
                    :rules="[rules.required, rules.isNmb]"
                    label="Street number"
                    :disabled="!editMode"
                    ></v-text-field>
                    <v-text-field
                    v-model="pharmacist.password"
                    :append-icon="show1 ? 'fa-eye' : 'fa-eye-slash'"
                    :rules="[rules.required, rules.min]"
                    :type="show1 ? 'text' : 'password'"
                    label="Password"
                    hint="At least 8 characters"
                    counter
                    :disabled="!editMode"
                    @click:append="show1 = !show1"
                    ></v-text-field>
                    <v-text-field
                    v-if="editMode"
                    v-model="confirmPassword"
                    :append-icon="show2 ? 'fa-eye' : 'fa-eye-slash'"
                    :rules="[rules.required, rules.min, passwordConfirmationRule]"
                    :type="show2 ? 'text' : 'password'"
                    label="Confirm Password"
                    hint="At least 8 characters"
                    counter
                    @click:append="show2 = !show2"
                    ></v-text-field>
                    <v-btn :disabled="!valid" id="save-btn"  v-if="editMode" class="accent white--text" @click="save()">
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
                pharmacist: {},
                pharmacistCopy: {},
                confirmPassword: '',
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
                return () => (this.pharmacist.password === this.confirmPassword) || 'Password must match'
            },
        },
         methods: {
            getPharmacist: function(){
                this.axios.get("api/pharmacist/5")
                    .then(response =>
                    {
                        this.pharmacist = response.data;
                        this.pharmacistCopy = JSON.parse(JSON.stringify(response.data));
                        this.confirmPassword = this.pharmacistCopy.password;
                    })
                    .catch(response =>
                    {
                        console.log(response.data);
                    });
                
            },
            editModeChange: function(){
                this.editMode = !this.editMode;
                this.confirmPassword = this.pharmacistCopy.password;
                this.pharmacist = JSON.parse(JSON.stringify(this.pharmacistCopy));

            },
            save: function(){
                this.axios.put("/pharmacist", this.pharmacist)
                    .then(response =>
                    {
                        this.pharmacistCopy = response.data;
                        this.editModeChange();

                    })
                    .catch(response =>
                    {
                        console.log(response.data);
                    });
            }
        },
        mounted(){
            this.getPharmacist();
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