<template>
    <div id="pricelist-main">
        <v-card
        id="pricelist-card"
        elevation="2">
            <v-card-title>Pricelist</v-card-title>
            <div id="pricelist-table">
                <v-data-table :headers="headers"
                                :items="drugs"
                                :search="search">
                    <template v-slot:item="row">
                        <tr>
                            <td>{{row.item.id}}</td>
                            <td>{{row.item.name}}</td>
                            <td>{{row.item.price}}</td>
                            <td>
                                <v-btn class="white red--text" elevation="0" @click="modify(row.item.id)">
                                    <i class="fa fa-trash" aria-hidden="true"></i>
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
                drugs: [],
                headers: [
					{ text: "Id", value: "id", },
					{ text: "Name", value: "name", },
					{ text: "Price", value: "price", },
					{ text: "Remove"},
				],
            }
        },
        methods: {
            getDrugs: function () {
				this.axios.get("/pswapi/drugs")
					.then(response => {
						this.drugs = response.data;
						console.log(response);
					})
					.catch(response => {
						console.log(response);
					})
					.finally(function(){
					})
            },
            modify: function() {

            },
        },
        mounted() {
			this.getDrugs();
		}
    }
</script>

<style scoped>
    #pricelist-main {
        background: linear-gradient(69deg, rgba(63,81,181,1) 5%, rgba(197,202,233,1) 100%); 
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
</style>