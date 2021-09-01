<template>
    <div id="main-home">
        <main-navigation>
            <router-link to="/supplier">Home</router-link>
            <router-link to="/supplier/account">Account</router-link>
            <div v-if="currentUser">
                <a href @click.prevent="logOut">Logout</a>
            </div>
        </main-navigation>
        <div id="main-content">
            <router-view/>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                title: "Supplier panel",
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
                this.axios.get("api/supplier/active", {headers:{"Authorization": "Bearer " + this.currentUser}})
                .then(r => {
                    console.log(r.data);
                })
                .catch(r => {
                    console.log(r.data);
                    this.$router.push("/supplier/changepass") ;
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