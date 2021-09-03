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
                ereceipt: {},
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
                this.axios.post("api/erecipe/uploadFile", this.formData, {headers:{"Authorization": "Bearer " + this.currentUser, "Content-Type":"multipart/form-data"}})
                .then(r=> {
                    console.log(r.data);
                    this.ereceipt = r.data;
                    this.drugs = r.data.drugs;
                })
                .catch(r => {
                    console.log(r.data);
                })
            },
            
        },
        mounted() {
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

        
