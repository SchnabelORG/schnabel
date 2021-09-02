<template>
    <div id="systemadmin-main">
        <main-navigation>
            <router-link to="/systemadmin/">Home</router-link>
            <div v-if="currentUser">
                <a href @click.prevent="logOut">Logout</a>
            </div>
            <v-divider vertical></v-divider>

        </main-navigation>
        <div id = "main-contetn">
            <router-view/>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                title: "SystemAdmin panel",
            }
        },
        computed: {
            currentUser() {
                return this.$store.state.auth.user;
            }
        },
        methods: {
            checkActive: function() {
                if(!this.currentUser){
                    this.$router.push("login");
                }
                this.axios.get("api/systemAdmin/active", {headers:{"Authorization": "Bearer " + this.currentUser}})
                .then(r => {
                    console.log(r.data);
                })
                .catch(r => {
                    console.log(r.data);
                    this.$router.push("/systemadmin/changepass") ;
                });
            },
            logOut() {
                this.$store.dispatch('auth/logout');
                this.$router.push('/login');
            }
        },
        mounted() {
            this.checkActive();
        }
    }
</script>

<style scoped>
    #main-content {
        min-height: 100vh;
        background-color: #fafafa;
    }
</style>
