<template>
    <div id="pricelist-main">
        <v-card
        id="pricelist-card"
        elevation="1">
            <v-card-title>Pricelist</v-card-title>
            <div id="pricelist-table">
                <v-data-table :headers="headers"
                                :items="warehouseitems"
                                :search="search">
                    <template v-slot:item="row">
                        <tr>
                            <td>{{row.item.drug.name}}</td>
                            <td>{{row.item.drug.description}}</td>
                            <td>{{row.item.drugPrice.price}}</td>
                            <td>
                                <v-btn @click="define()">
                                    Define
                                </v-btn>
                            </td>
                        </tr>
                    </template>
                </v-data-table>
            </div>
        </v-card>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                drug: '',
                warehouseitems: [],
                dialog: false,
                headers: [
					{ text: "Name" },
                    { text: "Description" },
                    { text: "Price" },
                    { text: "Define" },
                ],
            }
        },
        methods: {
            getDrugs: function () {
				this.refreshToken().then(response => {
                    localStorage.jws = response.data;
                    this.axios.get("api/pharmacyadmin/drug", {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                        .then(response => {
                            this.warehouseitems = response.data._embedded.warehouseitems;
                        })
                        .catch(response => {
                            console.log("Failed to get drugs", response.data);
                        });
                   })
                    .catch(response => {
                    console.log(response.data);
                    this.$router.push("/");
                });
            },
            define: function() {
               this.dialog = true;

            },
        },
        mounted() {
			this.getDrugs();
		}
    }
</script>

<style scoped>
    #pricelist-main {
        display:grid;
        grid-template-columns:auto;
        place-items: center;
        min-height: 92vh;
        background-color: #fbecdd;
    }
    #pricelist-card {
        display: flex;
        flex-direction: column;
        margin: 0 auto;
        width: 40%;
    }
    #pricelist-main {
        display: flex;
        flex-direction: column;
        justify-content: center;
        width: 100vw;
        min-height: 100vh;
    }
    #edit-card {
        display:grid;
        grid-template-columns:auto;
        margin: 0 auto;
        width: 40%;
    }
</style>