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
                    v-model="supplier.name"
                    :rules="[rules.required]"
                    label="Name"
                    :disabled="!editMode"
                    ></v-text-field>
                    <v-text-field
                    v-model="supplier.surname"
                    :rules="[rules.required]"
                    label="Surname"
                    :disabled="!editMode"
                    ></v-text-field>
                    <v-text-field
                    v-model="supplier.email"
                    :rules="[rules.required]"
                    label="Email"
                    :disabled="true"
                    ></v-text-field>
                    <v-text-field
                    v-model="supplier.firm"
                    :rules="[rules.required]"
                    label="Firm"
                    :disabled="true"
                    ></v-text-field>
                    <v-text-field
                    v-model="supplier.address.city"
                    :rules="[rules.required]"
                    label="City"
                    :disabled="!editMode"
                    ></v-text-field>
                    <v-text-field
                    v-model="supplier.address.postcode"
                    :rules="[rules.required]"
                    label="Postcode"
                    :disabled="!editMode"
                    ></v-text-field>
                    <v-text-field
                    v-model="supplier.address.street"
                    :rules="[rules.required]"
                    label="Street"
                    :disabled="!editMode"
                    ></v-text-field>
                     <v-text-field
                    v-model="supplier.address.streetNo"
                    :rules="[rules.required, rules.isNmb]"
                    label="Street number"
                    :disabled="!editMode"
                    ></v-text-field>
                    <v-btn :disabled="!this.supplier.name || !this.supplier.firm" id="save-btn"  v-if="editMode" class="accent white--text" @click="save()">
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
                supplier: {},
                supplierCopy: {},
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
            currentUser() {
                return this.$store.state.auth.user;
            },
        },
         methods: {
             getSupplier: function() {
                this.axios.get("api/supplier/get", {headers:{"Authorization": "Bearer " + this.currentUser}})
                    .then(response => {
                        console.log(response.data);
                        this.supplier = response.data;
                        this.supplierCopy = JSON.parse(JSON.stringify(response.data));
                    })
                    .catch(response => {
                        console.log("Failed to get supplier", response.data);
                    });
            },
            editModeChange: function(){
                this.editMode = !this.editMode;
                this.changePasswordForm = false;
                this.confirmPassword = '';
                this.password = '';
                this.supplier = JSON.parse(JSON.stringify(this.pharmacistCopy));

            },
            save: function(){
                if(this.changePasswordForm){
                    let dto = {newPassword: this.password, repeatedPassword: this.confirmPassword }
                    this.axios.post("api/supplier/pass", dto, {headers:{"Authorization": "Bearer " + this.currentUser}})
                        .then(response =>
                        {
                            console.log(response.data);
                            this.editModeChange();
                        })
                        .catch(response =>
                        {
                            console.log(response.data);
                        });
                }else{
                    this.axios.put("api/supplier", this.supplier, {headers:{"Authorization": "Bearer " + this.currentUser}})
                        .then(response =>
                        {
                            this.pharmacistCopy = response.data;
                            this.editModeChange();
                            this.getSupplier();

                        })
                        .catch(response =>
                        {
                            console.log(response.data);
                        });
                }
            }
        },
        mounted(){
            this.getSupplier();
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