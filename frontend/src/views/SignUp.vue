<template>
    <div id="register-main">
        <div id="register-container">
            <p>Join Schnabel</p>
            <h2>Create your account</h2>
            <v-stepper v-model="regSteps" id="register-form">
                <v-stepper-header>
                    <v-stepper-step
                    :complete="regSteps > 1"
                    step="1"
                    >
                    Email and user info
                    </v-stepper-step>
                    <v-divider></v-divider>
                    <v-stepper-step
                    :complete="regSteps > 2"
                    step="2"
                    >
                        Address info
                    </v-stepper-step>
                    <v-stepper-step
                    step="3">
                        Confirm
                    </v-stepper-step>
                </v-stepper-header>
                <v-stepper-items>
                    <v-stepper-content
                    step="1">
                    <v-form class="register" v-model="isFormValid.step1">
                        <v-text-field
                        v-model="email"
                        label="Email"
                        :rules="[rules.required, rules.email]"
                        required/>
                        <v-text-field
                        v-model="fName"
                        label="First name"
                        :rules="[rules.required]"
                        required/>
                        <v-text-field
                        v-model="lName"
                        label="Last name"
                        :rules="[rules.required]"
                        required/>
                        <v-text-field
                        v-model="password1"
                        label="Password"
                        hint="At least 8 characters, mixed capitals, a number and a symbol"
                        :rules="[rules.required, rules.min, match]"
                        :type="showPassword1 ? 'text' : 'password'"
                        @click:append="showPassword1 = !showPassword1"
                        :append-icon="showPassword1 ? 'fa-eye' : 'fa-eye-slash'"
                        required
                        />
                        <v-text-field
                        v-model="password2"
                        label="Confirm password"
                        hint="Must match password"
                        :rules="[rules.required, rules.min, match]"
                        :type="showPassword2 ? 'text' : 'password'"
                        @click:append="showPassword2 = !showPassword2"
                        :append-icon="showPassword2 ? 'fa-eye' : 'fa-eye-slash'"
                        required
                        />
                        <v-btn :disabled="!isFormValid.step1" color="primary" @click="regSteps++">Next</v-btn>
                    </v-form>
                    </v-stepper-content>
                    <v-stepper-content
                    step="2">
                        <v-form class="register" v-model="isFormValid.step2">
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
                            <v-btn :disabled="!isFormValid.step2" color="primary" @click="regSteps++">Next</v-btn>
                            <v-btn @click="regSteps--">Back</v-btn>
                        </v-form>
                    </v-stepper-content>
                    <v-stepper-content
                    step="3">
                    <div id="preview">
                        <div id="account-info" class="preview-field">
                            <h3 class="info--text">Account info</h3>
                            <v-text-field
                            v-model="fName"
                            label="First name"
                            readonly/>
                            <v-text-field
                            v-model="lName"
                            label="Last name"
                            readonly/>
                            <v-text-field
                            v-model="email"
                            label="Email"
                            readonly/>
                        </div>
                        <div id="address" class="preview-field">
                            <h3 class="info--text">Address</h3>
                            <div class="street">
                                <v-text-field
                                v-model="street"
                                label="Street"
                                readonly/>
                                <v-text-field
                                v-model="streetNo"
                                label="Street No."
                                readonly/>
                            </div>
                            <div class="street">
                                <v-text-field
                                v-model="city"
                                label="City"
                                readonly/>
                                <v-text-field
                                v-model="zipCode"
                                label="Zip Code"
                                readonly/>
                            </div>
                            <v-text-field
                            v-model="state"
                            label="State"
                            readonly/>
                        </div>
                        <v-btn color="accent">Sign up</v-btn>
                        <v-btn @click="regSteps--">Back</v-btn>
                    </div>
                    </v-stepper-content>
                </v-stepper-items>
            </v-stepper>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            fName: 'Petar',
            lName: 'Petrovic',
            email: 'petar.petrovic@gmail.com',
            password1: '',
            password2: '',
            showPassword1: false,
            showPassword2: false,
            street: 'Balzakova',
            streetNo: '69',
            city: 'Novi Sad',
            zipCode: '21000',
            state: 'Serbia',
            regSteps: 1,
            rules: {
                required: v => !!v || "Required",
                min: v => v.length >= 8 || "Min 8 characters",
                email: v => !v || /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || 'E-mail must be valid',
            },
            isFormValid: {
                step1: false,
                step2: false,
            },
        }
    },

    computed: {
        match: function() {
            return this.password1 == this.password2 || "Passwords must match";
        },
    },
}
</script>

<style scoped>
    #register-main {
        display: grid;
        place-items: center;
        background: #fafafa;
        height: 100vh;
    }

    #register-container {
        display: flex;
        flex-direction: column;
        text-align: center;
    }

    #register-container h2{
        font-family: 'Poppins' sans-serif;
        font-weight: 500;
    }

    #register-form {
        margin-top: 20px;
    }

    .register {
        display: flex;
        flex-direction: column;
        padding: 20px;
        /* border: 1px solid #eee; */
        background: #fff;
        max-width: 30rem;
    }

    .street {
        display: grid;
        grid-template-columns: 1fr 0.5fr;
        gap: 10px;
    }

    #preview {
        display: flex;
        flex-direction: column;
    }

    .preview-field {
        display: flex;
        flex-direction: column;
    }

    .preview-field h3 {
        position: relative;
    }

    .preview-field h3 {
        text-align: left;
        font-weight: 500;
        font-size: 1rem;
        margin-bottom: 10px;
    }

    .preview-field h3:after {
        content: "";
        display: inline-block;
        height: 1px;
        position: relative;
        vertical-align: middle;
        width: 70%;
        background-color: #eee;
        left: 0.5rem;
    }

</style>