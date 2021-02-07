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
                dermatologists: [],
                headers: [
                    { text: "Name",  value: "name"},
                    { text: "Surname",  value: "surname"},
                    { text: "Delete" },
                ],
            }
        },
        methods: {
            getDermatologists: function() {
                // SAMO ONI DERMATOLOZI KOJI RADE U ISTOJ APOTECI GDE I ADMIN


            },
            delete: function() {
                // USLOV POSTOJI

            },
        },
        mounted() {
            this.getDermatologists();
        },
    }
</script>

<style scoped>
    #dermatologists-main {
        background: linear-gradient(69deg, rgba(63,81,181,1) 5%, rgba(197,202,233,1) 100%); 
    }
    #dermatologists-card {
        display: flex;
        flex-direction: column;
        margin: 0 auto;
        width: 40%;
    }
    #dermatologists-main {
        display: flex;
        flex-direction: column;
        justify-content: center;
        width: 100vw;
        min-height: 100vh;
    }
</style>