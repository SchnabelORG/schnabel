<template>
    <div id="login-main">
        <div id="login-container">
            <div>
                <img id="logo" src="../assets/logo.png" alt="Logo">
                <h2>Log in to Schnabel</h2>
            </div>
            <v-form
            id="login-form"
            v-model="isFormValid"
            @submit="login">
                <b class="err">{{err}}</b>
                <v-text-field 
                v-model="email"
                label="Email"
                :rules="[rules.required]"
                required/>
                <v-text-field 
                v-model="password"
                label="Password"
                :rules="[rules.required]"
                :append-icon="showPassword ? 'fa-eye' : 'fa-eye-slash'"
                :type="showPassword ? 'text' : 'password'"
                @click:append="showPassword = !showPassword"
                required/>
                <v-btn :disabled="!isFormValid" color="accent" type="submit">Log in</v-btn>
            </v-form>
            <div id="register-container">
                New?
                <router-link to="/signup">Create an account.</router-link>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            error: '',
            isFormValid: false,
            showPassword: false,
            email: '',
            password: '',
            rules: {
                required: v => !!v || "Required",
            },
        }
    },
    
    methods: {
        login: function(e) {
            e.preventDefault();
            this.error = '';
            let dto = {
                email: this.email,
                password: this.password,
            };
            this.axios.post("api/auth/login", dto)
                .then(r => {
                    this.$store.state.jws = r.data;
                    localStorage.jws = r.data; // TODO(Jovan): TEMP!
                    this.$router.push("redirect");
                })
                .catch(() => {
                    this.error = "Bad credentials";
                });
        },
    },
}
</script>

<style scoped>
    #login-main {
        background: #fafafa;
        height: 100vh;
    }

    #login-container {
        display: flex;
        flex-direction: column;
        justify-content: space-around;
        height: 60vh;
        text-align: center;
        padding: 4rem;
        width: 20vw;
        margin: 0 auto;
    }

    #login-container h2 {
        font-family: 'Poppins', sans-serif;
        font-weight: 500;
    }

    #login-form {
        display: flex;
        flex-direction: column;
        justify-content: space-around;
        padding: 20px 10px;
        background: #fff;
        border: 1px solid #eee;
        border-radius: 5px;
    }

    #logo {
        height: 42px;
        width: 42px;
        text-align:center;
        margin-left: auto;
        margin-right: auto;
    }

    #register-container {
        background: #fff;
        border: 1px solid #eee;
        padding: 15px;
        border-radius: 5px;
    }

    .err {
        color: #f00;
        font-weight: 500;
    }

</style>