<template>
  <div>
    <search-navigation>
      <router-link to="/">Home</router-link>
    </search-navigation>
    <div id="rating-main">
      <div id="rating-container">
        <p>Ratings</p>
        <h2>Rate pharmacies, employees and drugs</h2>
        <v-card id="ratings">
          <v-card-title>
            <v-tabs 
            v-model="tab" 
            color="primary"
            fixed-tabs>
              <v-tab>Pharmacies</v-tab>
              <v-tab>Employees</v-tab>
              <v-tab>Drugs</v-tab>
            </v-tabs>
          </v-card-title>
          <v-card-text>
            <v-tabs-items v-model="tab">
              <v-tab-item>
                <v-data-table
                  :headers="pharmacyHeaders"
                  :items="pharmacies"
                  @click:row="selectPharmacy"
                >
                </v-data-table>
                <v-dialog v-model="pharmacyDialog" width="500">
                  <div v-if="success" id="success-form">
                    <p id="success-icon"><i class="fa fa-check"></i></p>
                    <p>Thank you for your feedback!</p>
                  </div>
                  <v-card v-else>
                    <v-card-title>{{ selectedPharmacy.name }}</v-card-title>
                    <v-card-text>
                      <h3>Submit your rating</h3>
                      <v-slider
                        v-model="pharmacyRating"
                        step="1"
                        min="0"
                        max="5"
                        thumb-label
                        ticks
                        :append-icon="pharmacyRating.toString()"
                      ></v-slider>
                    </v-card-text>
                    <v-card-actions>
                      <v-btn plain>Cancel</v-btn>
                      <v-spacer></v-spacer>
                      <v-btn
                        :disabled="!pharmacyRating"
                        color="accent"
                        depressed
                        @click="gradePharmacy"
                        >Submit</v-btn
                      >
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </v-tab-item>
              <v-tab-item>
                <v-data-table
                  :headers="employeeHeaders"
                  :items="employees"
                  @click:row="selectEmployee"
                >
                </v-data-table>
                <v-dialog v-model="employeeDialog" width="500">
                  <div v-if="success" id="success-form">
                    <p id="success-icon"><i class="fa fa-check"></i></p>
                    <p>Thank you for your feedback!</p>
                  </div>
                  <v-card v-else>
                    <v-card-title>{{ selectedEmployee.name }}</v-card-title>
                    <v-card-text>
                      <h3>Submit your rating</h3>
                      <v-slider
                        v-model="employeeRating"
                        step="1"
                        min="0"
                        max="5"
                        thumb-label
                        :append-icon="employeeRating.toString()"
                      >
                      </v-slider>
                    </v-card-text>
                    <v-card-actions>
                      <v-btn plain>Cancel</v-btn>
                      <v-spacer></v-spacer>
                      <v-btn
                        :disabled="!employeeRating"
                        color="accent"
                        depressed
                        @click="gradeEmployee"
                        >Submit</v-btn
                      >
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </v-tab-item>
              <v-tab-item>
                <v-data-table
                  :headers="drugHeaders"
                  :items="drugs"
                  @click:row="selectDrug"
                >
                </v-data-table>
                <v-dialog v-model="drugDialog" width="500">
                  <div v-if="success" id="success-form">
                    <p id="success-icon"><i class="fa fa-check"></i></p>
                    <p>Thank you for your feedback!</p>
                  </div>
                  <v-card v-else>
                    <v-card-title>{{ selectedDrug.name }}</v-card-title>
                    <v-card-text>
                      <h3>Submit your rating</h3>
                      <v-slider
                        v-model="drugRating"
                        min="0"
                        max="5"
                        thumb-label
                        :append-icon="drugRating.toString()"
                      >
                      </v-slider>
                    </v-card-text>
                    <v-card-actions>
                      <v-btn plain>Cancel</v-btn>
                      <v-spacer></v-spacer>
                      <v-btn
                        :disabled="!drugRating"
                        color="accent"
                        depressed
                        @click="gradeDrug"
                        >Submit</v-btn
                      >
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </v-tab-item>
            </v-tabs-items>
          </v-card-text>
        </v-card>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      drugDialog: false,
      selectedDrug: {},
      drugRating: 0,
      drugHeaders: [
        { text: "Name", value: "name" },
        { text: "Rating", value: "score" },
      ],
      drugs: [],
      //
      employeeDialog: false,
      selectedEmployee: {},
      employeeRating: 0,
      employeeHeaders: [
        { text: "Name", value: "name" },
        { text: "Surname", value: "surname" },
        { text: "Rating", value: "score" },
      ],
      employees: [],
      //
      pharmacyDialog: false,
      selectedPharmacy: {},
      pharmacyRating: 0,
      pharmacyHeaders: [
        { text: "Name", value: "name" },
        { text: "City", value: "address.city" },
        { text: "Street", value: "address.street" },
        { text: "Rating", value: "score" },
      ],
      pharmacies: [],
      //
      tab: null,
      success: false,
    };
  },

  methods: {
    gradeDrug: function() {
      this.refreshToken()
        .then(rr => {
          localStorage.jws = rr.data;
          let request = {
            targetId: this.selectedDrug.id,
            value: this.drugRating,
          };
          this.axios.post("api/grade/drug", request, { headers: this.getAHeader() })
            .then(() => {
              this.success = true;
              this.drugDialog = false;
              this.getDrugs();
            }).catch(r => console.log(r));
        }).catch(() => this.$router.push('/'));
    },

    selectDrug: function(item, event) {
      // eslint-disable-next-line no-unused-vars
      event;
      this.success = false;
      this.selectedDrug = item;
      this.drugDialog = true;
    },

    getDrugs: function() {
      this.refreshToken()
        .then(rr => {
          localStorage.jws = rr.data;
          this.axios
            .get("api/grade/patient/gradeable_drugs", {
              headers: this.getAHeader(),
            })
            .then(r => {
              if (r.data._embedded) {
                this.drugs = r.data._embedded.drugs;
              } else {
                this.drugs = [];
              }
            });
        })
        .catch(() => this.$router.push("/"));
    },

    gradeEmployee: function() {
      this.refreshToken()
        .then(rr => {
          localStorage.jws = rr.data;
          let request = {
            targetId: this.selectedEmployee.id,
            value: this.employeeRating,
          };
          this.axios
            .post("api/grade/employee", request, { headers: this.getAHeader() })
            .then(() => {
              this.success = true;
              this.employeeDialog = false;
              this.getEmployees();
            });
        })
        .catch(() => this.$router.push("/"));
    },

    selectEmployee: function(item, event) {
      this.success = false;
      // eslint-disable-next-line no-unused-vars
      event;
      this.selectedEmployee = item;
      this.employeeDialog = true;
    },

    getEmployees: function() {
      this.refreshToken()
        .then(rr => {
          localStorage.jws = rr.data;
          this.axios
            .get("api/grade/patient/gradeable_employees", {
              headers: this.getAHeader(),
            })
            .then(r => {
              if (r.data._embedded) {
                this.employees = r.data._embedded.employees;
              } else {
                this.employees = [];
              }
            });
        })
        .catch(() => this.$router.push("/"));
    },
    gradePharmacy: function() {
      this.success = false;
      this.refreshToken()
        .then(rr => {
          localStorage.jws = rr.data;
          let request = {
            targetId: this.selectedPharmacy.id,
            value: this.pharmacyRating,
          };
          this.axios
            .post("api/grade/pharmacy", request, { headers: this.getAHeader() })
            .then(() => {
              this.success = true;
              this.pharmacyDialog = false;
              this.getPharmacies();
            });
        })
        .catch(() => this.$router.push("/"));
    },

    selectPharmacy: function(item, event) {
      this.success = false;
      // eslint-disable-next-line no-unused-vars
      event;
      this.selectedPharmacy = item;
      this.pharmacyDialog = true;
    },

    getPharmacies: function() {
      this.refreshToken()
        .then(rr => {
          localStorage.jws = rr.data;
          this.axios
            .get("api/grade/patient/gradeable_pharmacies", {
              headers: this.getAHeader(),
            })
            .then(r => {
              if (r.data._embedded) {
                this.pharmacies = r.data._embedded.pharmacies;
              } else {
                this.pharmacies = [];
              }
            });
        })
        .catch(() => this.$router.push("/"));
    },
  },

  mounted() {
    this.getPharmacies();
    this.getEmployees();
    this.getDrugs();
  },
};
</script>

<style scoped>
  #rating-main {
    display: grid;
    place-items: center;
    height: 92vh;
    background: #fafafa;
  }

  #rating-container{
    display: flex;
    flex-direction: column;
    justify-content: center;
    text-align: center;
  }

  #ratings {
    margin-top: 20px;
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
