<template>
    <div id="drugs-main">
        <v-card
        id="drugs-card"
        elevation="2">
            <v-card-title>Drugs</v-card-title>
            <v-card-text>
                <div id="drugs-table">
                    <v-data-table :headers="headers"
                                    :items="warehouseitems"
                                    :search="search">
                        <template v-slot:item="row">
                            <tr v-if="row.item.available > 0">
                                <td>{{row.item.drug.name}}</td>  
                                <td>{{row.item.drug.description}}</td>                   
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
                warehouseitems: [],
                id: '',
                headers: [
                    { text: "Name" },
                    { text: "Description" },
                ],
            }
        },
        methods: {
            getDrugs: function() {
                this.id = this.$route.params.id;
                    this.axios.get("api/warehouseitem/pharmacy/" + this.id)
                        .then(response => {
                            this.warehouseitems = response.data._embedded.warehouseitems;
                        })
                        .catch(response => {
                            console.log("Failed to get drugs", response.data);
                        });
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
    #drugs-card {
        display: flex;
        flex-direction: column;
        margin: 0 auto;
        width: 40%;
    }
    #drugs-main {
        display: flex;
        flex-direction: column;
        justify-content: center;
        width: 100vw;
        min-height: 100vh;
    }
</style>