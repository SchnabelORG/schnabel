<template>
    <div id="pricelist-main">
        <transition name="bounce">
            <v-card v-if="show && !showadd"
            id="pricelist-card"
            elevation="1">
                <v-card-title>Pricelist</v-card-title>
                <v-btn class="deep-orange white--text" elevation="0" @click="add()">
                    Add new drug
                </v-btn>
                <div id="pricelist-table">
                    <v-data-table :headers="headers"
                                    :items="drugs"
                                    :search="search">
                        <template v-slot:item="row">
                            <tr>
                                <td>{{row.item.id}}</td>
                                <td>{{row.item.name}}</td>
                                <td>{{row.item.price}}</td>
                                <td>{{row.item.priceDuration.startTime}}</td>
                                <td>{{row.item.priceDuration.endTime}}</td>
                                <td>
                                    <v-btn class="deep-orange white--text" elevation="1" @click="modify(row.item.id)">
                                        Modify
                                    </v-btn>
                                </td>
                                <td>
                                    <v-btn class="deep-orange white--text" elevation="1" @click="delete(row.item.id)">
                                        Delete
                                    </v-btn>
                                </td>
                            </tr>
                        </template>
                    </v-data-table>
                </div>
            </v-card>
        </transition>
        <transition name="bounce">
            <v-card v-if="!show"
            id="modify-card"
            elevation="1">
                <v-card-title>Modify drug</v-card-title>
                <div id="modify">
                   <v-form >
                       <v-card-subtitle>
                           Id: {{drug.id}}
                       </v-card-subtitle>
                       <v-card-subtitle>
                           Name: {{drug.name}}
                       </v-card-subtitle>
                   </v-form>
                    <v-form>
                        <v-text-field
                        v-model="drug.price"
                        :rules="nameRules"
                        label="Price"
                        ></v-text-field>
                    </v-form>
                    <template>
                    <v-menu
                        ref="menu"
                        v-model="menu"
                        :close-on-content-click="false"
                        transition="scale-transition"
                        offset-y
                        min-width="auto"
                        >
                        <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                            v-model="drug.priceDuration.startTime"
                            label="Price valid from"
                            prepend-icon="mdi-calendar"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                        ></v-text-field>
                        </template>
                        <v-date-picker
                        ref="picker"
                        v-model="drug.priceDuration.startTime"
                        :min="drug.priceDuration.startTime"
                        @change="save"
                        ></v-date-picker>
                    </v-menu>
                    </template>
                    <template>
                    <v-menu
                        ref="menuto"
                        v-model="menuto"
                        :close-on-content-click="false"
                        transition="scale-transition"
                        offset-y
                        min-width="auto"
                        >
                        <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                            v-model="drug.priceDuration.endTime"
                            label="Price valid to"
                            prepend-icon="mdi-calendar"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                        ></v-text-field>
                        </template>
                        <v-date-picker
                        ref="picker"
                        v-model="drug.priceDuration.endTime"
                        :min="drug.priceDuration.startTime"
                        @change="saveto"
                        ></v-date-picker>
                    </v-menu>
                    </template>
                    <v-btn class="deep-orange white--text" elevation="0" @click="update" :disabled="!drug.price">
                            Save
                    </v-btn>
                </div>
            </v-card>
        </transition>
        <transition>
            <v-card v-if="showadd"
            id="add-card"
            elevation="1">
                <v-card-title>Add drug</v-card-title>
                <div id="add">
                   <v-form >
                        <v-text-field
                        v-model="addname"
                        :rules="nameRules"
                        label="Name"
                        ></v-text-field>
                   </v-form>
                   <v-form>
                       <v-text-field
                        v-model="adddescription"
                        :rules="nameRules"
                        label="Description"
                        ></v-text-field>
                   </v-form>
                   <v-form>
                       <v-text-field
                        v-model="addprice"
                        :rules="nameRules"
                        label="Price"
                        ></v-text-field>
                   </v-form>
                    <template>
                    <v-menu
                        ref="menuadd"
                        v-model="menuadd"
                        :close-on-content-click="false"
                        transition="scale-transition"
                        offset-y
                        min-width="auto"
                        >
                        <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                            v-model="addperiodDuration[0]"
                            label="Price valid from"
                            prepend-icon="mdi-calendar"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                        ></v-text-field>
                        </template>
                        <v-date-picker
                        ref="picker"
                        v-model="addperiodDuration[0]"
                        :min="new Date().toISOString().substr(0, 10)"
                        @change="saveadd"
                        ></v-date-picker>
                    </v-menu>
                    </template>
                    <template>
                    <v-menu
                        ref="menuaddto"
                        v-model="menuaddto"
                        :close-on-content-click="false"
                        transition="scale-transition"
                        offset-y
                        min-width="auto"
                        >
                        <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                            v-model="addperiodDuration[1]"
                            label="Price valid to"
                            prepend-icon="mdi-calendar"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                        ></v-text-field>
                        </template>
                        <v-date-picker
                        ref="picker"
                        v-model="addperiodDuration[1]"
                        :min="addperiodDuration[0]"
                        @change="saveaddto"
                        ></v-date-picker>
                    </v-menu>
                    </template>
                    <v-btn class="deep-orange white--text" elevation="0" @click="savedrug" :disabled="!addperiodDuration[0] || !addperiodDuration[1] || !addname || !adddescription || !addprice">
                            Save
                    </v-btn>
                    <v-btn class="deep-orange white--text" elevation="0" @click="back">
                            Back
                    </v-btn>
                </div>
            </v-card>
        </transition>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                drugs: [],
                drug: '',
                show: true,
                showadd: false,
                addname: '',
                adaddescription: '',
                addprice: '',
                addperiodDuration: [],
                menu: false,
                menuto: false,
                menuadd: false,
                menuaddto: false,
                headers: [
					{ text: "Id",  value: "id"},
					{ text: "Name",  value: "name"},
                    { text: "Price",  value: "price"},
                    { text: "Price valid from",  value: "startTime"},
                    { text: "Price valid to",  value: "endTime"},
                    { text: "Modify" },
                    { text: "Delete" },
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
            modify: function(id) {
                this.show = !this.show;
                this.axios.get("/pswapi/drugs/" + id)
					.then(response => {
						this.drug = response.data;
						console.log(response);
					})
					.catch(response => {
						console.log(response);
					})
					.finally(function(){
                    });
                this.getDrugs();

            },
            update: function() {
                this.show = !this.show;
                this.getDrugs();
            },
            delete: function() {
                
            },
            add: function() {
                this.showadd = !this.showadd;
                this.getDrugs();

            },
            savedrug: function() {
                this.showadd = !this.showadd;
                this.getDrugs();
                if(typeof this.addprice === 'number')
                {
                    let adddrug = {name: this.addname, description: this.adddescription, price: this.addprice, priceDuration: this.addperiodDuration }
                    alert(adddrug.name);
                }

                this.addname = '';
                this.adddescription = '';
                this.addprice = '';
                this.addperiodDuration = [];
            },
            back: function() {
                this.showadd = !this.showadd;
                this.getDrugs();
            },
            save (date) {
                this.$refs.menu.save(date)
            },
            saveto (date) {
                this.$refs.menuto.saveto(date)
            },
            saveadd (date) {
                this.$refs.menuadd.saveadd(date)
            },
            saveaddto (date) {
                this.$refs.menuaddto.saveaddto(date)
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
    #modify-card {
        display: flex;
        flex-direction: column;
        margin: 0 auto;
        width: 40%;
    }
     #add-card {
        display: flex;
        flex-direction: column;
        margin: 0 auto;
        width: 40%;
    }

</style>