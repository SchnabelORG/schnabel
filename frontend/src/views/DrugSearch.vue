<template>
    <div>
        <main-navigation>
            <router-link to="/">Home</router-link>
        </main-navigation>
    <div id="search-main" class="info">
        <div id="search-container">
            <b class="err">{{error}}</b>
            <!-- <p>Buy meds</p>
            <h2>Find your drug and reserve it</h2> -->
            <div v-if="success" id="success-form">
                <p id="success-icon"><i class="fa fa-check"></i></p>
                <p>Drug reserved!</p>
                <v-btn plain @click="steps = 1; success=false" color="accent">Reserve another drug</v-btn>
            </div>
            <v-stepper
            v-else
            v-model="steps"
            id="search-form">
                <v-stepper-header>
                    <v-stepper-step
                    :complete="steps > 1"
                    step="1">
                        Search drugs
                    </v-stepper-step>
                    <v-stepper-step
                    :complete="steps > 2"
                    step="2">
                        Pick a pharmacy
                    </v-stepper-step>
                    <v-stepper-step
                    :complete="steps > 3"
                    step="3">
                        Pickup date and quantity
                    </v-stepper-step>
                    <v-stepper-step
                    step="4">
                        Confirm reservation
                    </v-stepper-step>
                </v-stepper-header>
                <v-stepper-items>
                    <v-stepper-content
                    step="1">
                        <v-text-field 
                        v-model="search"
                        placeholder="Search drugs..."
                        @keydown.enter="searchDrugs"
                        dense/>
                        <b class="err">{{error}}</b>
                        <div id="results-container">
                            <div id="results">
                                <v-card
                                class='result'
                                v-for="drug in drugs" :key="drug.id"
                                @click="selectDrug(drug)">
                                    <v-card-title>{{drug.name}}</v-card-title>
                                    <v-card-text>
                                        <p>{{drug.description}}</p>
                                    </v-card-text>
                                    <v-card-actions>
                                        <v-btn plain>&#62;</v-btn>
                                    </v-card-actions>
                                </v-card>
                            </div>
                        </div>
                    </v-stepper-content>
                    <v-stepper-content
                    step="2">
                    <div id="pharmacies">
                        <v-data-table
                        :headers="pharmacyHeaders"
                        :items="pharmacies"
                        @click:row="selectPharmacy">
                        </v-data-table>
                        <div class='d-flex flex-row'>
                            <v-btn plain @click="--steps">&#60; Search for different drug</v-btn>
                        </div>
                    </div>
                    </v-stepper-content>
                    <v-stepper-content
                    step="3">
                        <v-form v-model="isFormValid.step3">
                            <div id="date-and-amount">
                                <div>
                                <v-date-picker
                                full-width
                                v-model="resDate"
                                :min="new Date().toISOString().substr(0, 10)">
                                </v-date-picker>
                            </div>
                            <div id="amount-container">
                                <div class="d-flex flex-row">
                                    <div class="d-flex flex-column">
                                        <p class="info--text">In stock:</p>
                                        <p>{{stock}}</p>
                                    </div>
                                    <div class="d-flex flex-column">
                                        <p class="info--text">Enter amount: </p>
                                        <v-text-field
                                        v-model="amount"
                                        label="Quantity"
                                        placeholder="Enter quantity"
                                        type="number"
                                        :rules="[min, max]"
                                        single-line
                                        />
                                    </div>
                                </div>
                            </div>
                            </div>
                            <div class="d-flex flex-row">
                                <v-btn plain @click="--steps">&#60; Pick different pharmacy</v-btn>
                                <v-spacer></v-spacer>
                                <v-btn :disabled="!resDate || !isFormValid.step3" color="primary" depressed @click="++steps">Next</v-btn>
                            </div>
                        </v-form>
                    </v-stepper-content>
                    <v-stepper-content
                    step="4">
                        <div id="preview-container">
                            <div id="reserve-preview">
                                <p class="info--text">Drug</p>
                                <p class="info--text">Pharmacy</p>
                                <p>{{selectedDrug.name}}</p>
                                <p>{{selectedPharmacy.name}}</p>
                                <p class="info--text">Pickup date</p>
                                <p class="info--text">Amount</p>
                                <p>{{resDate}}</p>
                                <p>{{amount}}</p>
                            </div>
                        </div>
                        <div class="d-flex flex-row">
                            <v-btn plain @click="--steps">&#60; Change date or quantity</v-btn>
                            <v-spacer></v-spacer>
                            <v-btn color="accent" @click="reserveDrug" :disabled="penaltyCount >= 3" depressed>Reserve drug</v-btn>
                        </div>
                    </v-stepper-content>
                </v-stepper-items>
            </v-stepper>
        </div>
    </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            penaltyCount: 0,
            //
            amount: 0,
            stock: 0,
            resDate: null,
            //
            pharmacyHeaders: [
                { text: 'Name', value: 'name' },
                { text: 'City', value: 'address.city' },
                { text: 'Street', value: 'address.street' },
            ],
            pharmacies: [],
            selectedPharmacy: {},
            //
            isFormValid: {
                step3: false,
            },
            steps: 1,
            error: '',
            success: false,
            //
            search: '',
            drugs: [],
            selectedDrug: {},
        }
    },

    computed: {
        min: function() {return this.amount > 0 || 'Must be greater than 0!';},
        max: function() {return this.amount <= this.stock || 'Exceeds stock!';},
    },

    methods: {
        reserveDrug: function() {
            this.error = '';
            if(this.penaltyCount >= 3) {
                return;
            }
            let currDate = new Date();
            let request = {
                pharmacyId: this.selectedPharmacy.id,
                drugId: this.selectedDrug.id,
                quantity: this.amount,
                period: {
                    startTime: currDate.getTime(),
                    endTime: this.getDateTimeFromString(this.resDate, currDate.toISOString().substr(11, 5)).getTime(),
                },
            };
            this.refreshToken()
                .then(rr => {
                    localStorage.jws = rr.data;
                    this.axios.post('api/patient/resdrug', request, {headers: this.getAHeader()})
                        .then(() => this.success = true)
                        .catch(() => this.error = 'Failed to reserve drug');
                }).catch(() => this.$router.push('/login'));
        },

        selectPharmacy: function(item, event) {
            // eslint-disable-next-line no-unused-vars
            event
            this.selectedPharmacy = item;
            this.refreshToken()
                .then(rr => {
                    localStorage.jws = rr.data;
                    this.axios.get('api/warehouseitem/stock?pharmacy_id=' + item.id + '&drug_id=' + this.selectedDrug.id, {headers: this.getAHeader()})
                        .then(r => this.stock = r.data);
                    ++this.steps;
                }).catch(() => this.$router.push('/login'));
        },

        selectDrug: function(drug) {
            this.error = '';
            if(this.penaltyCount >= 3) {
                return;
            }
            this.selectedDrug = drug;
            this.refreshToken()
                .then(rr => {
                    localStorage.jws = rr.data;
                    this.axios.get('api/pharmacy/drug/' + drug.id, {headers: this.getAHeader()})
                        .then(r => {
                            if(r.data._embedded) {
                                this.pharmacies = r.data._embedded.pharmacies;
                                ++this.steps;
                            } else {
                                this.pharmacies = [];
                                this.error = 'No pharmacies found with drug in stock.';
                            }
                        })
                        .catch(() => this.error = 'No pharmacies found with drug in stock.');
                })
                .catch(() => this.$router.push('/login'));
        },

        searchDrugs: function() {
            this.axios.get('api/drug/search?name=' + this.search)
                .then(r => {
                    if(r.data._embedded) {
                        this.drugs = r.data._embedded.drugs;
                    } else {
                        this.drugs = [];
                    }
                });
        },
    },

    mounted() {
        this.searchDrugs();
        this.refreshToken()
            .then(rr => {
                localStorage.jws = rr.data;
                this.axios.get('api/penalty/patient/count', {headers:this.getAHeader()})
                    .then(r => {
                        this.penaltyCount = r.data
                        if(this.penaltyCount >= 3) {
                            this.error = "3+ penalties, functionality disabled";
                        }
                    });
            }).catch(() => console.log('Not logged in'));
    },
}
</script>

<style scoped>

    #search-main {
        display: grid;
        place-items: center;
        height: 92vh;
        background: #fafafa;
    }

    #search-container {
        display: flex;
        flex-direction: column;
        justify-content: center;
        text-align: center;
    }

    #search-form {
        background: #fff;
        margin-top: 20px;
    }

    #results {
        max-height: 40vh;
        overflow: auto;
    }

    .result {
        display: grid;
        grid-template-columns: auto 1fr auto;
        place-items: center;
    }

    #date-and-amount {
        display: grid;
        grid-template-columns: 1fr 1fr;
    }

    #amount-container {
        display: flex;
        flex-direction: column;
        justify-content: flex-end;
    }

    #reserve-preview {
        display: grid;
        grid-template-columns: 1fr 1fr;
    }

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