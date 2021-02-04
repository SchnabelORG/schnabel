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
                                <td>
                                    {{numbers}}
                                </td>
                                <!--
                                    TREBA JOS OCENA I APOTEKA U KOJOJ RADI

                                    -->
                                <td>
                                    <v-btn class="deep-orange white--text" elevation="1" @click="delete(row.item.id)">
                                        Delete
                                    </v-btn>
                                </td>
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
                numbers: [1, 2, 3, 4, 5, 6, 7],
                headers: [
					{ text: "Name",  value: "name"},
                    { text: "Surname",  value: "surname"},
                    { text: "Delete" },
                ],
            }
        },
        methods: {
            getPharmacists: function() {
                // SAMO ONI FARMACEUTI KOJI RADE U ISTOJ APOTECI GDE I ADMIN


            },
            delete: function() {
                // USLOV POSTOJI

            },
        },
        mounted() {
            this.getPharmacists();
        },
    }
</script>

<style scoped>
    #pharmacists-main {
        background: linear-gradient(69deg, rgba(63,81,181,1) 5%, rgba(197,202,233,1) 100%); 
    }
    #pharmacists-card {
        display: flex;
        flex-direction: column;
        margin: 0 auto;
        width: 40%;
    }
    #pharmacists-main {
        display: flex;
        flex-direction: column;
        justify-content: center;
        width: 100vw;
        min-height: 100vh;
    }
</style>
