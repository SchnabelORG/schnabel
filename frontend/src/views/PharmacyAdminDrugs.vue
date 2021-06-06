<template>
    <div id="drugs-main">
        <v-card
        id="drugs-card"
        elevation="2">
            <v-card-title>Drugs</v-card-title>
            <v-card-text>
                <v-btn @click="newDrug()">
                    New
                </v-btn>
                <div id="drugs-table">
                    <v-data-table :headers="headers"
                                    :items="warehouseitems"
                                    :search="search">
                        <template v-slot:item="row">
                            <tr>
                                <td>{{row.item.drug.name}}</td>                     
                                <td>
                                    <v-btn @click="editDrug(row.item)">
                                        Edit
                                    </v-btn>
                                </td>
                                <td>
                                    <v-btn @click="deleteDrug(row.item.id)">
                                        Delete
                                    </v-btn>
                                </td>
                            </tr>
                        </template>
                    </v-data-table>
                </div>
            </v-card-text>
        </v-card>
        <v-dialog v-model="this.new" persistent>
            <v-card id="new-card">
                <v-card-title>Drug</v-card-title>
                <v-spacer></v-spacer>
                 <v-text-field
                    v-model="name"
                    :rules="[rules.required]"
                    label="Name"
                    ></v-text-field>
                <v-text-field
                    v-model="description"
                    :rules="[rules.required]"
                    label="Description"
                    ></v-text-field>
                <v-spacer></v-spacer>
                <v-btn class="primary" @click="addDrug()" :disabled="!name || !description">
                    Add
                </v-btn>
                <v-btn class="accent" @click="cancelNew()">
                    Cancel
                </v-btn>
            </v-card>
        </v-dialog>
        <v-dialog v-model="this.edit" persistent>
            <v-card id="edit-card">
                <v-card-title>Edit drug</v-card-title>
                <v-spacer></v-spacer>
                 <v-text-field
                        v-model="drug.name"
                        :rules="[rules.required]"
                        label="Name"
                        ></v-text-field>
                    <v-text-field
                        v-model="drug.description"
                        :rules="[rules.required]"
                        label="Description"
                        ></v-text-field>
                <v-spacer></v-spacer>
                <v-btn class="primary" @click="saveDrug()" :disabled="!drug.name || !drug.description">
                    Save
                </v-btn>
                <v-btn class="accent" @click="cancelEdit()">
                    Cancel
                </v-btn>
            </v-card>
        </v-dialog>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                drugs: [],
                warehouseitems: [],
                new: false,
                edit: false,
                name: '',
                description: '',
                drug: '',
                warehouseitem: '',
                pharmacyId: '',
                headers: [
                    { text: "Name" },
                    { text: "Edit" },
                    { text: "Delete" },
                ],
                rules: {
                    required: value => !!value || 'Required.',
                },
            }
        },
        methods: {
            getPharmacyAdmin: function() {
                this.refreshToken().then(response => {
                    localStorage.jws = response.data;
                    this.axios.get("api/pharmacyadmin", {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                        .then(response => {
                            this.pharmacyId = response.data.pharmacy.id;
                            this.getDrugs();
                        })
                        .catch(response => {
                            console.log("Failed to get pharmacy admin", response.data);
                        });
                   })
                    .catch(response => {
                    console.log(response.data);
                    this.$router.push("/");
                });
            },
            getDrugs: function() {
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
            editDrug: function(wareHouseItem) {
                this.drug = wareHouseItem.drug;
                this.warehouseitem = wareHouseItem;
                this.edit = true;
            },
            deleteDrug: function(id) {
                this.refreshToken().then(response => {
                    localStorage.jws = response.data;
                    this.axios.delete("api/warehouseitem/" + id, {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                        .then(response => {
                            console.log("Successfully deleted", response.data);
                            this.getDrugs();
                        })
                        .catch(response => {
                            console.log("Failed to delete", response.data);
                        });
                   })
                    .catch(response => {
                    console.log(response.data);
                    this.$router.push("/");
                });
            },
            newDrug: function() {
                this.new = true;
            },
            addDrug: function() {
                this.refreshToken().then(response => {
                    localStorage.jws = response.data;
                    let newDrug = { name: this.name, description: this.description, pharmacyId: this.pharmacyId };
                    this.axios.post("api/warehouseitem", newDrug, {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                        .then(response => {
                            console.log("Successfully added", response.data);
                            alert("Successfully added!");
                            this.getDrugs();
                        })
                        .catch(response => {
                            console.log("Failed to add", response.data);
                        });
                   })
                    .catch(response => {
                    console.log(response.data);
                    this.$router.push("/");
                });
                this.new = false;
            },
            saveDrug: function() {
                this.refreshToken().then(response => {
                    localStorage.jws = response.data;
                    let updateDrug = { id: this.warehouseitem.id, drugId: this.drug.id, name: this.drug.name, description: this.drug.description };
                    this.axios.put("api/warehouseitem", updateDrug, {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                        .then(response => {
                            console.log("Successfully updated", response.data);
                            alert("Successfully updated!");
                            this.getDrugs();
                        })
                        .catch(response => {
                            console.log("Failed to update", response.data);
                        });
                   })
                    .catch(response => {
                    console.log(response.data);
                    this.$router.push("/");
                });
                this.edit = false;
            },
            cancelEdit: function() {
                this.edit = false;
            },
            cancelNew: function() {
                this.new = false;
            }
        },
        mounted() {
            this.getPharmacyAdmin();
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
    #edit-card, #new-card {
        display:grid;
        grid-template-columns:auto;
        margin: 0 auto;
        width: 40%;
    }
</style>