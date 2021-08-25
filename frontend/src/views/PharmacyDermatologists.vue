<template>
    <div id="dermatologists-main">
        <v-card
        id="dermatologists-card"
        elevation="2">
            <v-card-title>Dermatologists</v-card-title>
            <v-card-text>
                <div id="dermatologists-table">
                    <v-data-table :headers="headers"
                                    :items="dermatologists"
                                    :search="search">
                        <template v-slot:item="row">
                            <tr>
                                <td>{{row.item.name}}</td>
                                <td>{{row.item.surname}}</td>
                                <td>{{row.item.score}}</td>                       
                            </tr>
                        </template>
                    </v-data-table>
                </div>
            </v-card-text>
        </v-card>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                dermatologists: [],
                id: '',
                headers: [
                    { text: "Name" },
                    { text: "Surname" },
                    { text: "Score" },
                ],
            }
        },
        methods: {
            getDermatologists: function() {
                this.id = this.$route.params.id;
                this.axios.get("api/dermatologist/pharmacy/" + this.id)
                    .then(response => {
                        this.dermatologists = response.data._embedded.dermatologists;
                    })
                    .catch(response => {
                        console.log("Failed to get dermatologists", response.data);
                    });
            },
        },
        mounted() {
            this.getDermatologists();
        },
    }
</script>

<style scoped>
    #dermatologists-main {
        display:grid;
        grid-template-columns:auto;
        place-items: center;
        min-height: 92vh;
        background-color: #fbecdd;
    }
</style>