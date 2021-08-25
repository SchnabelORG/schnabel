<template>
    <div id="pharmacists-main">
        <v-card
        id="pharmacists-card"
        elevation="2">
            <v-card-title>Pharmacists</v-card-title>
            <v-card-text>
                <div id="pharmacists-table">
                    <v-data-table :headers="headers"
                                    :items="pharmacists"
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
                pharmacists: [],
                id: '',
                headers: [
                    { text: "Name" },
                    { text: "Surname" },
                    { text: "Score" },
                ],
            }
        },
        methods: {
            getPharmacists: function() {
                this.id = this.$route.params.id;
                    this.axios.get("api/pharmacist/pharmacy/" + this.id)
                        .then(response => {
                            this.pharmacists = response.data._embedded.pharmacists;
                        })
                        .catch(response => {
                            console.log("Failed to get pharmacists", response.data);
                        });
            },
        },
        mounted() {
            this.getPharmacists();
        },
    }
</script>

<style scoped>
    #pharmacists-main {
        display:grid;
        grid-template-columns:auto;
        place-items: center;
        min-height: 92vh;
        background-color: #fbecdd;
    }
</style>