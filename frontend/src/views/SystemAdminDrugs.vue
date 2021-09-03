<template>
    <div id="drugs-main">
        <v-card
        id="drugs-card"
        elevation="2">
            <v-card-title>All drugs</v-card-title>
            <v-card-text>
                <v-btn @click="newDrug()">
                    New
                </v-btn>
                <div id="drugs-table">
                    <v-data-table :headers="headers"
                                    :items="drugs"
                                    item-key="name"
                                    :expanded.sync="expanded"
                                    >
                        <template v-slot:item="row">
                            <tr>
                                <td>{{row.item.code}}</td>  
                                <td>{{row.item.name}}</td>                   
                                <td>{{row.item.dosage}}</td>
                                <td>{{row.item.producer}}</td>
                                <td>{{row.item.drugType}}</td>
                                <td>{{row.item.description}}</td>
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
                        <template v-slot:expanded-item="{headers, item}">
                            <td :colspan="headers.length">
                                {{item.description}}
                            </td>
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
                    v-model="code"
                    :rules="[rules.required]"
                    label="Code"
                ></v-text-field>
                 <v-text-field
                    v-model="name"
                    :rules="[rules.required]"
                    label="Name"
                ></v-text-field>
                <v-text-field
                    v-model="dosage"
                    :rules="[rules.required]"
                    label="Dosage"
                ></v-text-field>
                <v-text-field
                    v-model="producer"
                    :rules="[rules.required]"
                    label="Producer"
                    ></v-text-field>
                <v-combobox
                    v-model="drugtype"
                    :items="types"
                    return-object
                    label="Drug Type"
                >
                </v-combobox>
                <v-combobox
                    v-model="drugorigin"
                    :items="origins"
                    return-object
                    label="Drug Origin"
                >
                </v-combobox>
                <v-combobox
                    v-model="drugstate"
                    :items="states"
                    return-object
                    label="Drug State"
                >
                </v-combobox>
                <v-radio-group
                    v-model="issuingType"
                    row
                >
                    <v-radio
                        label="ON PRESCRIPTION"
                        value="ON_PRESCRIPTION"
                    ></v-radio>   
                    <v-radio
                        label="WITHOUT PRESCRIPTION"
                        value="WITHOUT_PRESCRIPTION"
                    ></v-radio>  
                </v-radio-group>
                <v-textarea
                    v-model="description"
                    label="Description"
                    ></v-textarea>
                <v-text-field
                    v-model="points"
                    :rules="[rules.required]"
                    label="Points"
                ></v-text-field>
                <v-spacer></v-spacer>
                <v-card-subtitle>Substitute Drugs</v-card-subtitle>
                <v-combobox
                    v-model="substituedrug"
                    :items="drugs"
                    return-object
                    label="Drugs"
                >
                    <template slot="selection" slot-scope="data" >
                        {{ data.item.name }}
                    </template>
                    <template slot="item" slot-scope="data">
                        {{ data.item.name }}
                    </template>
                </v-combobox>
                <v-btn elevation="2" @click="addToSubstitue()" class="accent" :disabled="!substituedrug">
                        Add to substitue drugs
                </v-btn>
                <v-data-table :headers="headers2"
                                    :items="substitue"
                                    >
                        <template v-slot:item="row">
                            <tr>
                                <td>{{row.item.code}}</td>  
                                <td>{{row.item.name}}</td>                   
                                <td>{{row.item.dosage}}</td>
                                <td>{{row.item.producer}}</td>
                                <td>{{row.item.drugType}}</td>
                                <td>{{row.item.description}}</td>
                            </tr>
                        </template>
                    </v-data-table>
                
                <v-btn class="primary" @click="addDrug()" :disabled="!name || !description">
                    Add
                </v-btn>
                <v-btn class="accent" @click="cancelNew()">
                    Cancel
                </v-btn>
            </v-card>
        </v-dialog>
        <v-dialog v-model="this.edit" persistent>
            <v-card id="new-card">
                <v-card-title>Drug</v-card-title>
                <v-spacer></v-spacer>
                <v-text-field
                    v-model="drug.code"
                    :rules="[rules.required]"
                    label="Code"
                ></v-text-field>
                 <v-text-field
                    v-model="drug.name"
                    :rules="[rules.required]"
                    label="Name"
                ></v-text-field>
                <v-text-field
                    v-model="drug.dosage"
                    :rules="[rules.required]"
                    label="Dosage"
                ></v-text-field>
                <v-text-field
                    v-model="drug.producer"
                    :rules="[rules.required]"
                    label="Producer"
                    ></v-text-field>
                <v-combobox
                    v-model="drug.drugtype"
                    :items="types"
                    return-object
                    label="Drug Type"
                >
                </v-combobox>
                <v-combobox
                    v-model="drug.drugorigin"
                    :items="origins"
                    return-object
                    label="Drug Origin"
                >
                </v-combobox>
                <v-combobox
                    v-model="drug.drugstate"
                    :items="states"
                    return-object
                    label="Drug State"
                >
                </v-combobox>
                <v-radio-group
                    v-model="drug.issuingType"
                    row
                >
                    <v-radio
                        label="ON PRESCRIPTION"
                        value="ON_PRESCRIPTION"
                    ></v-radio>   
                    <v-radio
                        label="WITHOUT PRESCRIPTION"
                        value="WITHOUT_PRESCRIPTION"
                    ></v-radio>  
                </v-radio-group>
                <v-textarea
                    v-model="drug.description"
                    label="Description"
                    ></v-textarea>
                <v-spacer></v-spacer>
                <v-card-subtitle>Substitute Drugs</v-card-subtitle>
                <v-combobox
                    v-model="substituedrug"
                    :items="drugs"
                    return-object
                    label="Drugs"
                >
                    <template slot="selection" slot-scope="data" >
                        {{ data.item.name }}
                    </template>
                    <template slot="item" slot-scope="data">
                        {{ data.item.name }}
                    </template>
                </v-combobox>
                <v-btn elevation="2" @click="addToSubstitueEdit()" class="accent" :disabled="!substituedrug">
                        Add to substitue drugs
                </v-btn>
                <v-data-table :headers="headers2"
                                    :items="drugsubstitue"
                                    >
                        <template v-slot:item="row">
                            <tr>
                                <td>{{row.item.code}}</td>  
                                <td>{{row.item.name}}</td>                   
                                <td>{{row.item.dosage}}</td>
                                <td>{{row.item.producer}}</td>
                                <td>{{row.item.drugType}}</td>
                                <td>{{row.item.description}}</td>
                            </tr>
                        </template>
                    </v-data-table>
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
                id: '',
                new: false,
                edit: false,
                search: '',
                code: '',
                name: '',
                dosage: '',
                producer: '',
                drugtype: '',
                drugorigin: '',
                drugstate: '',
                description: '',
                points: '',
                issuingType: '',
                substitue: [],
                drugsubstitue: [],
                substituedrug: {},
                expanded: [],
                types: [],
                states: [],
                origins: [],
                drug: {},
                headers: [
                    { text: "Code", value: 'code' },
                    { text: "Name", value: 'name' },
                    { text: "Dosage", value: 'dosage' },
                    { text: "Producer", value: 'producer' },
                    { text: "Drug Type", value: 'drugtype'},
                    { text: "Description"},
                    {text: "Edit"},
                    { text: "Delete"},
                ],
                headers2: [
                    { text: "Code", value: 'code' },
                    { text: "Name", value: 'name' },
                    { text: "Dosage", value: 'dosage' },
                    { text: "Producer", value: 'producer' },
                    { text: "Drug Type", value: 'drugtype'},
                    { text: "Description"},
                ],
                rules: {
                    required: value => !!value || 'Required.',
                },
            }
        },
        computed: {
            currentUser() {
                return this.$store.state.auth.user;
            }
        },
        methods: {
            getDrugs: function() {
                this.axios.get("api/drug/")
                    .then(response => {
                        this.drugs = response.data._embedded.drugs;
                    })
                    .catch(response => {
                        console.log("Failed to get drugs", response.data);
                    });
            },
            getTypes: function() {
                this.axios.get("api/drug/types")
                .then(response => {
                        this.types = response.data;
                    })
                    .catch(response => {
                        console.log("Failed to get drugs types", response.data);
                    });
            },
            getOrigins: function() {
                this.axios.get("api/drug/origins")
                .then(response => {
                        this.origins = response.data;
                    })
                    .catch(response => {
                        console.log("Failed to get drugs origins", response.data);
                    });
            },
            getStates: function() {
                this.axios.get("api/drug/states")
                .then(response => {
                        this.states = response.data;
                    })
                    .catch(response => {
                        console.log("Failed to get drugs states", response.data);
                    });
            },
            editDrug: function(item) {
                this.drug = item;
                this.edit = true;
                this.axios.get("api/drug/substitute/" + item.id)
                .then(r => {
                    this.drugsubstitue = r.data;
                })
                .catch(r => {
                    console.log(r.data);
                })
                
            },
            deleteDrug: function(id) {
                this.axios.delete("api/drug/remove/"+ id)
                .then(r => 
                {
                    console.log("Successfully deleted", r.data);
                    this.getDrugs();
                })
                .catch(r =>
                {
                    console.log("Failed to delete", r.data);
                })
            },
            newDrug: function() {
                this.new = true;
            },
            addDrug: function() {
                var s = [];
                for(var sd of this.drugsubstitue){
                    s.push(sd.id)
                }
                var d = {
                    code: this.code,
                    name: this.name,
                    description: this.description,
                    drugState: this.drugstate,
                    drugOrigin: this.drugorigin,
                    drugType: this.drugtype,
                    producer: this.producer,
                    dosage: this.dosage,
                    issuingType: this.issuingtype,
                    substituteDrugs: s,
                    points: this.points,
                };
                console.log(d);
                this.axios.post("api/drug", d,  {headers:{"Authorization": "Bearer " + this.currentUser}})
                .then(r => 
                {
                    console.log("Successfully added", r.data);
                    alert("Successfully added!");
                    this.getDrugs();
                    this.new = false;
                })
                .catch(r => {
                    console.log("Failed to add", r.data);
                });

            }, 
            saveDrug: function() {
                var s = [];
                for(var sd of this.substitue){
                    s.push(sd.id)
                }
                var d = {
                    id: this.drug.id,
                    code: this.drug.code,
                    name: this.drug.name,
                    description: this.drug.description,
                    drugState: this.drug.drugstate,
                    drugOrigin: this.drug.drugorigin,
                    drugType: this.drug.drugtype,
                    producer: this.drug.producer,
                    dosage: this.drug.dosage,
                    issuingType: this.drug.issuingtype,
                    substituteDrugs: s,
                };
                this.axios.put("api/drug/", d,  {headers:{"Authorization": "Bearer " + this.currentUser}})
                .then(r => {
                    console.log("Successfully updated", r.data);
                    alert("Successfully updated!");
                    this.getDrugs();
                })
                .catch(r => {
                    console.log("Failed to update", r.data);
                });
            },
            cancelEdit: function() {
                this.edit = false;
            },
            cancelNew: function() {
                this.new = false;
            },
            addToSubstitue: function() {
                this.substitue.push(this.substituedrug);
                this.substituedrug = {};
            },
            addToSubstitueEdit: function() {
                this.drugsubstitue.push(this.substituedrug);
                this.substituedrug = {};
            },
        },
        mounted() {
            this.getDrugs();
            this.getTypes();
            this.getOrigins();
            this.getStates();
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
        width: 80%;
    }
    #new-card {
        display: flex;
        flex-direction: column;
        margin: 0 auto;
        width: 50%;
    }
    #drugs-main {
        display: flex;
        flex-direction: column;
        justify-content: center;
        width: 100vw;
        min-height: 100vh;
    }
</style>