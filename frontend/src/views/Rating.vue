<template>
    <div id="rating-main">
        <div id="rating-container">
            <v-card id="ratings">
                <v-card-title>
                    <v-tabs
                    v-model="tab"
                    color="primary">
                        <v-tab>Pharmacies</v-tab>
                        <v-tab>Employees</v-tab>
                        <v-tab>Drugs</v-tab>
                    </v-tabs>
                </v-card-title>
                <v-card-text>
                    <v-tabs-items
                    v-model="tab">
                        <v-tab-item>
                            <v-data-table
                            :headers="pharmacyHeaders"
                            :items="pharmacies"
                            @click:row="selectPharmacy">

                            </v-data-table>
                            <v-dialog
                            v-model="pharmacyDialog"
                            width="500">
                            <div v-if="success" id="success-form">
                                <p id="success-icon"><i class="fa fa-check"></i></p>
                                <p>Thank you for your feedback!</p>
                            </div>
                            <v-card v-else>
                                <v-card-title>{{selectedPharmacy.name}}</v-card-title>
                                <v-card-text>
                                    <h3>Submit your rating</h3>
                                    <v-slider
                                    v-model="pharmacyRating"
                                    step="1"
                                    min="0"
                                    max="5"
                                    thumb-label
                                    ticks></v-slider>
                                </v-card-text>
                                <v-card-actions>
                                    <v-btn plain>Cancel</v-btn>
                                    <v-spacer></v-spacer>
                                    <v-btn color="accent" depressed @click="gradePharmacy">Submit</v-btn>
                                </v-card-actions>
                            </v-card>
                            </v-dialog>
                        </v-tab-item>
                    </v-tabs-items>
                </v-card-text>
            </v-card>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            pharmacyDialog: false,
            selectedPharmacy: {},
            pharmacyRating: 0,
            pharmacyHeaders: [
                { text: 'Name', value: 'name' },
                { text: 'City', value: 'address.city' },
                { text: 'Street', value: 'address.street' },
                { text: 'Rating', value: 'score' },
            ],
            pharmacies: [],
            //
            tab: null,
            success: false,
        }
    },

    methods: {
        gradePharmacy: function() {
            this.success = false;
            this.refreshToken()
                .then(rr => {
                    localStorage.jws = rr.data;
                    let request = {
                        targetId: this.selectedPharmacy.id,
                        value: this.pharmacyRating,
                    };
                    this.axios.post('api/grade/pharmacy', request, {headers: this.getAHeader()})
                        .then(() => {
                            this.success = true;
                            this.pharmacyDialog = false;
                        });
                }).catch(() => this.$router.push('/'));
        },

        selectPharmacy: function(item, event) {
            this.success = false;
            // eslint-disable-next-line no-unused-vars
            event
            this.selectedPharmacy = item;
            this.pharmacyDialog = true;
        },

        getPharmacies: function() {
            this.refreshToken()
                .then(rr => {
                    localStorage.jws = rr.data;
                    this.axios.get('api/grade/patient/gradeable_pharmacies', {headers: this.getAHeader()})
                        .then(r => {
                            if(r.data._embedded) {
                                this.pharmacies = r.data._embedded.pharmacies;
                            } else {
                                this.pharmacies = [];
                            }
                        })
                }).catch(() => this.$router.push('/'));
        },
    },

    mounted() {
        this.getPharmacies();
    },
}
</script>

<style scoped>

    #success-form {
        background: #fff;
        text-align: center;
        height: 100%;
        padding: 20px;
    }

    #success-form p {
        margin: 0px;
    }

    #success-icon {
        font-size: 3rem;
    }
</style>