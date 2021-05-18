<template>
    <div id="drugs-main">
        <v-card
        id="drugs-card"
        elevation="2">
            <v-card-title>Drugs</v-card-title>
            <v-card-text>
                <div id="drugs-table">
                    <v-data-table :headers="headers"
                                    :items="drugs"
                                    :search="search">
                        <template v-slot:item="row">
                            <tr>
                                <td>{{row.item.name}}</td>
                                <td>{{row.item.price}}</td>                    
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
                drugs: [],
                headers: [
                    { text: "Name" },
                    { text: "Price"}
                ],
            }
        },
        methods: {
            getDrugs: function() {
                

            },
        },
        mounted() {
            this.getDrugs();
        },
    }
</script>

<style scoped>
    #drugs-main {
        display:grid;
        grid-template-columns:auto;
        place-items: center;
        min-height: 92vh;
        background-color: #fbecdd;
    }
</style>