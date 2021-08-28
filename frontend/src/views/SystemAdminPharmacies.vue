<template>
    <div id="pharmacies-main">
        <v-container id="container">
            <v-row>
                <v-btn @click="$router.push('/systemadmin/pharmacies/register')">
                    Register new pharmacy

                </v-btn>
            </v-row>
            &nbsp;
            &nbsp;
            <v-row>
                <v-card>
                    <v-card-title>
                        All pharmacies
                        <v-spacer></v-spacer>
                        <v-text-field
                            v-model="search"
                            append-icon="mdi-magnfy"
                            label="Search"
                            single-line
                            hide-details
                        >
                        </v-text-field>
                    </v-card-title>
                    <v-data-table
                        :headers="headers"
                        :items="pharmacies"
                        :search="search"
                    >
                        <template v-slot:[`item.score`]="{ item }">
                            <v-chip
                                :color="getColor(item.score)"
                                :small=true
                            >
                                {{ item.score }}
                            </v-chip>
                        </template>
                        <template v-slot:[`item.warning`]="{ item }">
                            <v-chip
                            v-if="!checkPharmacyAdmin(item.id)">
                                <v-icon
                                    color="yellow"
                                    
                                >
                                    alert-outline
                                </v-icon>
                            </v-chip>
                        </template>
                        
                    </v-data-table>
                </v-card>
            </v-row>
        </v-container>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                headers:[
                    { text: 'Id', value: 'id' },
                    { text: 'Name', value: 'name' },
                    { text: 'Address', value: 'address' },
                    { text: 'Score', value: 'score' },
                    { text: '', value: 'warning' },
                ],
                search: '',
                pharmacies: [],
            }        
        },
        methods: {
            getAllPharmacies: function() {
                this.axios.get("api/pharmacy/")
                .then(response => 
                {
                    console.log(response.data._embedded.pharmacies)
                    for(var pharmacy of response.data._embedded.pharmacies) {
                        let adrs = pharmacy.address.street + ' ' + pharmacy.address.streetNo + ', ' + pharmacy.address.city + ', ' + pharmacy.address.postcode;  
                        var p = {id: pharmacy.id, name: pharmacy.name, address:adrs, score: pharmacy.score};
                        this.pharmacies.push(p);
                    }
                })
                .catch(response => 
                {
                    console.log(response.data)
                })
            },
            getColor (score) {
                if (score >= 4.5 ) return 'green'
                else if (score >= 3.5 ) return 'yellow'
                else if (score >= 2.5 ) return 'orange'
                else return 'red'
            },
            checkPharmacyAdmin (id) {
                this.axios.get("api/pharmacyadmin/bypharmacy/"+id)
                .then(r =>
                {
                    var phAdmins = r.data;
                   
                    if( Object.prototype.hasOwnProperty.call(phAdmins, '_embedded')) {
                        return true;
                    }
                    else return false;
                })
                
            }
        },
        mounted() {
            this.getAllPharmacies();
        }
    }
</script>
