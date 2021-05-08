<template>
    <div id="user-main">
        <main-navigation>
            <router-link to="/">Home</router-link>
        </main-navigation>
        <div id="user-container">
            <div id="user-basic">
                <img :src="getProfileImg()" alt="">
                <div id="user-info">
                    <h2 class="primary--text">{{user.name}} {{user.surname}}</h2>
                    <b class="info--text">{{user.email}}</b>
                </div>
            </div>
            <div id="user-about">
                <h3 class="info--text">About</h3>
                <div class="divider"></div>
                <div id="allergies-container">
                    <b>Allergies</b>
                    <ul>
                        <li v-for="a in user.allergies" :key="a">{{a}}</li>
                    </ul>
                    <v-form v-model="isAllergyValid">
                        <v-text-field 
                        v-model="newAllergy"
                        label="New allergy"
                        :rules="[rules.min]"
                        />
                        <v-btn :disabled="!isAllergyValid" @click="addAllergy" color='primary'>Add allergy</v-btn>
                    </v-form>
                </div>

                <div id="loyalty-container">
                    <b>Loyalty</b>
                    <p>
                        Points: {{user.loyalty.points}}
                    </p>
                    <p>
                        Category: {{user.loyalty.category}}
                    </p>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            isAllergyValid: false,
            rules: {
                min: v => v.length > 0 || "Cannot be empty",
            },
            newAllergy: "",
            user: {
                name: "Petar",
                surname: "Petrovic",
                email: "petar.petrovic@email.com",
                allergies: [
                    'eggs',
                    'pollen',
                ],
                loyalty: {
                    points: 3,
                    category: "Gold",
                },
            },
        }
    },
    methods: {
        getProfileImg: function() {
            return require('../assets/placeholder-profile-sq.jpg')
        },

        addAllergy: function() {

        },
    },
}
</script>

<style scoped>
    #user-main {
        height: 100vh;
        background: #fafafa;
    }

    #user-container {
        display: flex;
        flex-direction: row;
    }

    #user-basic {
        max-width: 20vw;
    }

    #user-basic b {
        font-weight: 500;
    }

    #user-basic img {
        width: 100%;
    }

    #user-about {
        flex-grow: 1;
    }

    #user-about h3 {
        font-weight: 400;
    }

    .divider {
        width: 40vw;
        border-bottom: 1px solid #eee;
    }
</style>