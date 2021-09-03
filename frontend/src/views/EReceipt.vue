<template>
    <div id="drugs-main">
        <v-card
        id="drugs-card"
        elevation="2">
            <v-card-title>E Receipt</v-card-title>
            <v-card-text>
                <div>
                    <img :src="previewImage" class="uploading-image" />
                    <input type="file" accept="image/jpeg" @change=uploadImage>
                </div>
                <div>
                <v-btn @click="analyseImage()">Analyse Image</v-btn>
                </div>
                <div id="drugs-table">
                    <v-data-table :headers="headers"
                                    :items="drugs"
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
                </div>
                <div>
                    <v-data-table :headers="headers"
                                    :items="pharmacies"
                                    >
                        <template v-slot:item="row">
                            <tr>
                                <td>{{row.item.name}}</td>                   
                                <td>{{row.item.address}}</td>
                                <td>{{row.item.score}}</td>
                                <td>{{row.item.price}}</td>
                            </tr>
                        </template>
                    </v-data-table>
                </div>
                <div>
                    <v-btn>
                        Accept
                    </v-btn>
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
                pharmacies: [],
                id: '',
                new: false,
                edit: false,
                reader: {},
                search: '',
                formData: null,
                previewImage: null,
                code: '',
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
                    { text: "Name", value: 'name' },
                    { text: "Address", value: 'address' },
                    { text: "Score", value: 'score' },
                    { text: "Price", value: 'price'},
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
            uploadImage(e){
                const image = e.target.files[0];
                this.reader = new FileReader();
                this.reader.readAsDataURL(image);
                this.formData = new FormData();
                this.formData.append("file", image);
                this.reader.onload = e =>{
                    this.previewImage = e.target.result;
                    console.log(this.previewImage);
                };
            },
            onImageUpload: function() {
                let file = this.$refs.uploadImage.files[0];
                this.formData = new FormData();
                this.formData.append("file", file);
            },
            analyseImage: function() {
                console.log(this.formData);
                this.axios.post("api/pharmacy/uploadFile", this.formData, {headers:{"Authorization": "Bearer " + this.currentUser, "Content-Type":"multipart/form-data"}})
                .then(r=> {
                    console.log(r.data);
                })
                .catch(r => {
                    console.log(r.data);
                })
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
    .uploading-image{
     display:flex;
   }
</style>

        
