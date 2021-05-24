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
                    { text: "Name" },
                    { text: "Surname" },
                ],
            }
        },
        methods: {
            getDermatologists: function() {
                

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