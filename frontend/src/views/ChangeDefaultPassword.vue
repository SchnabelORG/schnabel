<template>
    <div id="account-main">
        <v-card 
        min-width="50%">
            <v-card-title class="info primary--text">
                <b>Please change your default password</b>
                <v-spacer></v-spacer>
            </v-card-title>
            <v-card-text >
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
                    <v-btn :disabled="!validPassword" id="save-btn" class="accent white--text" @click="save()">
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
                employee: {},
                confirmPassword: '',
                password: '',
                show1: false,
                show2: false,
                validPassword: false,
                rules: {
                    required: value => !!value || 'Required.',
                    min: v => v.length >= 8 || 'Min 8 characters',
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
                let jws = window.localStorage.getItem('jwt');
                if(!jws) {
                    this.$router.push("/");
                }
                return this.axios.get("/api/auth/refresh", {headers: {"Authorization": "Bearer " + jws}});
            },
            getEmployee: function() {
                console.log("Getting employee");
                let jws = window.localStorage.getItem('jwt');
                console.log(jws)
                this.axios.get("api/medical_employee/jwt", {headers:{"Authorization": "Bearer " + jws}})
                    .then(response => {
                        console.log(response.data);
                        this.employee = response.data;
                    })
                    .catch(response => {
                        console.log("Failed to get patient", response.data);
                        this.refreshToken()
                            .then(response => {
                                window.localStorage.jwt = response.data;
                                this.$router.go();
                            })
                            .catch(response => {
                                console.log(response.data);
                                this.$router.push("/");
                            });
                    });
            },
            save: function(){
                let jws = window.localStorage.getItem('jwt');
                let dto = {email: this.employee.email, password: this.password}
                console.log(dto)
                this.axios.put("api/medical_employee/pass", dto, {headers:{"Authorization": "Bearer " + jws}})
                    .then(response =>
                    {
                        console.log(response.data);
                        this.$router.push("/redirect");
                    })
                    .catch(response =>
                    {
                        console.log(response.data);
                    });
            }
        },
        mounted(){
            this.getEmployee();
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