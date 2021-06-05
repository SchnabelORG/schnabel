<template>
    <div id="ps-main">
    <v-toolbar
        id="search-toolbar"
        compact
        floating>
            <v-text-field
            placeholder="Name surname"
            v-model="nameSurname"
            hide-details
            />
            <v-dialog max-width="600px" v-model="showFiltersDialog"
            transition="dialog-bottom-transition">
            <template v-slot:activator="{ on, attrs }">
                <v-btn icon v-bind="attrs" v-on="on">
                <v-icon>
                    fa-sliders
                </v-icon>
                </v-btn>
            </template>
            <v-card>
                <v-card-title>
                <v-btn icon plain @click="showFiltersDialog=false">
                    <svg viewBox="0 0 32 32" xmlns="http://www.w3.org/2000/svg" style="display: block; fill: none; height: 16px; width: 16px; stroke: currentcolor; stroke-width: 3px; overflow: visible;" aria-hidden="true" role="presentation" focusable="false"><path d="m6 6 20 20"></path><path d="m26 6-20 20"></path></svg>
                </v-btn>
                <v-spacer></v-spacer>
                Filters
                <v-spacer></v-spacer>
                <div></div>
                </v-card-title>
                <v-divider style="width:100%"></v-divider>
                <v-card-text>
                <div id="score-filters" class="filter-container">
                    <h3>Score</h3>
                    <v-text-field
                    label="Minimum score"
                    v-model="filters[0].value"
                    />
                </div>
                </v-card-text>
                <v-divider style="width:100%"></v-divider>
                <v-card-actions>
                <v-btn
                color="primary"
                plain
                v-ripple="false"
                style="text-decoration:underline"
                @click="clearFilters()"
                >
                    Clear all
                </v-btn>
                <v-spacer></v-spacer>
                <v-btn depressed color="black white--text" @click="applyFilters()">Apply filters</v-btn>
                </v-card-actions>
            </v-card>
            </v-dialog>
            <v-divider vertical></v-divider>
            <v-btn icon color="accent" @click="getSearchResults()">
            <v-icon>fa-search</v-icon>
            </v-btn>
        </v-toolbar>
        <div id="dermatologists-main">
            <v-card
                id="dermatologists-card"
                elevation="2">
                <v-card-title>Dermatologists</v-card-title>
                <v-card-text>
                    <div id="dermatologists-table">
                        <v-data-table :headers="headers"
                                        :items="dermatologists"
                                        :search="search">
                            <template v-slot:item="row">
                                <tr>
                                    <td>{{row.item.name}}</td>
                                    <td>{{row.item.surname}}</td>
                                    <td>{{row.item.score}}</td>
                                </tr>
                            </template>
                        </v-data-table>
                    </div>
                </v-card-text>
            </v-card>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            showFiltersDialog: false,
            nameSurname: '',
            name: '',
            surname: '',
            filters: [
              {
                id: 0,
                name: 'Min. Score',
                on: false,
                value: '',
                query: 'score',
              },
            ],
            dermatologists: [],
            headers: [
                    { text: "Name" },
                    { text: "Surname" },
                    { text: "Score" },
            ],
        }
    },

  methods: {
    applyFilters: function() {
      this.filters.forEach(f => {
        if(f.value) {
          f.on = true;
        } else {
          f.on = false;
        }
      });
      this.showFiltersDialog = false;
    },

    clearFilters: function() {
      this.filters.forEach(f => {
        f.value = '';
        f.on = false;
      });
    },

    getPharmacyAdmin: function() {
        this.refreshToken().then(response => {
            localStorage.jws = response.data;
            this.axios.get("api/pharmacyadmin", {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                .then(response => {
                    this.pharmacyId = response.data.pharmacy.id;
                    console.log(this.pharmacyId);
                    this.getSearchResults();
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

    getSearchResults: function() {
      let queryString = '?';
      let first = true;

      if(this.nameSurname) {
        this.name = this.nameSurname.substr(0,this.nameSurname.indexOf(' '));
        this.surname = this.nameSurname.substr(this.nameSurname.indexOf(' ')+1);
        queryString = queryString.concat('name=' + this.name + '&');
        queryString = queryString.concat('surname=' + this.surname);
        first = false;
      }
      this.filters.forEach(f => {
        if(f.on) {
          if(first) {
            queryString = queryString.concat(f.query + '=' + f.value)
            first = false;
          }
          queryString = queryString.concat('&' + f.query + '=' + f.value);
        }
      });

        this.refreshToken().then(response => {
            localStorage.jws = response.data;
            this.axios.get("api/pharmacyadmin/searchdermatologist" + queryString, {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                .then(r => {
                if(r.data._embedded) {
                    this.dermatologists = r.data._embedded.dermatologists;
                } else {
                    this.dermatologists = [];
                }
                console.log(r.data._embedded.dermatologists);
                })
                .catch(r => {
                console.log(r);
                });
            })
            .catch(response => {
            console.log(response.data);
            this.$router.push("/");
        });
    },
  },

  computed: {
    activeFilters: function() {
      return this.filters.filter(f => f.on);
    }
  },

    mounted() {
      this.getPharmacyAdmin();
    },
}
</script>

<style scoped>
  #ps-main {
    min-height: 100vh;
    background: #fafafa;
  }
    #dermatologists-main {
        display:grid;
        grid-template-columns:auto;
        place-items: center;
        min-height: 92vh;
        background-color: #fbecdd;
    }
  #ps-container {
    display: flex;
    flex-direction: column;
    margin: 20px;
    padding: 10px;
    background: #fff;
  }

  #search-container {
    background: #fff;
    padding: 10px;
  }

  #results-and-map {
    display: grid;
    grid-template-columns: auto 1fr;
  }

  #results-container {
    display: flex;
    flex-direction: column;
    max-height: 100vh;
    overflow: auto;
    width: 600px;
  }

  #results-header {
    padding: 10px;
  }

  #results-header h2 {
    font-family: 'Poppins' sans-serif;
    font-weight: 600;
  }

  .result {
    width: 600px;
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    padding: 0 0 20px 20px;
  }

  .result img {
    border-radius: 10px;
  }

  #map-container {
    position: relative;
  }

  #search-toolbar {
        display:grid;
        place-items: center;
    top: 5px;
    left: 45px;
    border-radius: 15px;
  }

  .filter-container {
    padding: 20px;
  }

  .filter-container h3 {
    font-weight: 500;
    font-size: 1.5rem;
  }

</style>