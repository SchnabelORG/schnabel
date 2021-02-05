<template>
    <div id="account-main">
        <v-card 
        min-width="50%">
            <v-card-title class="primary secondary--text">
                Your information
                <v-spacer></v-spacer>
					<v-btn class="deep-orange white--text" dark @click="editMode = !editMode">
                        <div v-if="!editMode"><v-icon left>mdi-pencil</v-icon>Edit</div>
                        <div v-else><v-icon left>mdi-cancel</v-icon>Cancel</div>
                        
                   </v-btn>
            </v-card-title>
            <v-card-text>
                <v-form id="ph-add" v-model="valid">
                    <v-text-field
                    v-model="name"
                    :rules="[rules.required]"
                    label="Name"
                    :disabled="!editMode"
                    ></v-text-field>
                    <v-text-field
                    v-model="surname"
                    :rules="[rules.required]"
                    label="Surname"
                    :disabled="!editMode"
                    ></v-text-field>
                    <v-text-field
                    v-model="email"
                    :rules="[rules.required]"
                    label="Email"
                    :disabled="true"
                    ></v-text-field>
                    <v-text-field
                    v-model="city"
                    :rules="[rules.required]"
                    label="City"
                    :disabled="!editMode"
                    ></v-text-field>
                    <v-text-field
                    v-model="postcode"
                    :rules="[rules.required]"
                    label="Postcode"
                    :disabled="!editMode"
                    ></v-text-field>
                    <v-text-field
                    v-model="street"
                    :rules="[rules.required]"
                    label="Street"
                    :disabled="!editMode"
                    ></v-text-field>
                     <v-text-field
                    v-model="number"
                    :rules="[rules.required, rules.isNmb]"
                    label="Street number"
                    :disabled="!editMode"
                    ></v-text-field>
                    <v-text-field
                    v-model="password"
                    :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
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
                    :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'"
                    :rules="[rules.required, rules.min, passwordConfirmationRule]"
                    :type="show2 ? 'text' : 'password'"
                    label="Confirm Password"
                    hint="At least 8 characters"
                    counter
                    @click:append="show2 = !show2"
                    ></v-text-field>
                    <v-btn :disabled="!valid" id="save-btn"  v-if="editMode" class="deep-orange white--text" @click="save">
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
                password: 'blablabla',
                name: 'Petar',
                surname: 'Petrovic',
                city: 'Novi Sad',
                postcode: '21000',
                street: 'Slobodana Bajica',
                number: 17,
                email: 'pera@gmail.com',
                confirmPassword: '',
                show1: false,
                show2: false,
                valid: false,
                rules: {
                    required: value => !!value || 'Required.',
                    min: v => v.length >= 8 || 'Min 8 characters',
                    isNmb: v => /^\d+$/.test(v) || 'Must be a number',
                },
            }
        },
        computed:{
            passwordConfirmationRule: function() {
                return () => (this.password === this.confirmPassword) || 'Password must match'
            },
        }
    }

</script>

<style scoped>
    #account-main{
        display:grid;
        grid-template-columns:auto;
        place-items: center;
        min-height: 100%;
    }
    #save-btn{
        margin-top: 5%;
        min-width: 100%;
    }
</style>