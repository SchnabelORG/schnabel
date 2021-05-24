<template>
    <div id="ps-main">
        <main-navigation>
            <router-link to="/">Home</router-link>
        </main-navigation>
        <div id="ps-container">
          <v-card id="results-and-map">
            <div id="results-container">
              <div id="results-header">
                <small>{{results.length}} pharmacies</small>
                <h2>Search results:</h2>
                <div id="search-filters">
                  <small class="info--text" v-if="activeFilters.length">Active filters:</small>

                  <v-chip-group>
                    <v-chip
                    outlined
                    v-for="filter in activeFilters"
                    :key="filter.id"
                    color="primary"
                    @click:close="filter.on=false; filter.value=''"
                    close-icon="fa-times"
                    close
                    >
                      {{filter.name}}: {{filter.value}}
                    </v-chip>
                  </v-chip-group>
                </div>
                <v-divider></v-divider>
              </div>
              <div v-for="result in results" :key="result.id">
                <v-card class="result" 
                flat>
                    <v-img src="../assets/pharmacy-placeholder.jpg" max-height="210px" max-width="280px" ></v-img>
                    <!-- <v-spacer></v-spacer> -->
                    <div>
                      <v-card-title>
                        {{result.name}}
                      </v-card-title>
                      <v-card-subtitle>{{result.address.street}} {{result.address.streetNo}}, {{result.address.city}}</v-card-subtitle>
                    </div>
                </v-card>
                <v-divider></v-divider>
              </div>
            </div>

            <div id="map-container">
              <vl-map
              :load-tiles-while-animating="true"
              :load-tiles-while-interacting="true"
              data-projection="EPSG:4326"
              height="100vh"
              >
                <vl-view :zoom.sync="zoom"
                :center.sync="center"
                :rotation.sync="rotation">
                </vl-view>
                
                <vl-geoloc @update:position="geolocPosition = $event">
                  <template slot-scope="geoloc">
                    <vl-feature v-if="geoloc.position" id="position-feature">
                      <vl-geom-point :coordinates="geoloc.position"></vl-geom-point>
                      <vl-style-box>
                        <vl-style-icon src="_media/marker.png" :scale="0.4" :anchor="[0.5, 1]"></vl-style-icon>
                      </vl-style-box>
                    </vl-feature>
                  </template>
                </vl-geoloc>

                <vl-layer-tile id="osm">
                  <vl-source-osm></vl-source-osm>
                </vl-layer-tile>

                <v-toolbar
                id="search-toolbar"
                compact
                floating>
                  <v-text-field
                  placeholder="Pharmacy name"
                  v-model="pharmacyName"
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
                        <div id="location-filters" class="filter-container">
                          <h3>Location</h3>
                          <v-text-field
                          label="City"
                          v-model="filters[0].value"
                          />
                        </div>
                        <v-divider></v-divider>
                        <div id="score-filters" class="filter-container">
                          <h3>Score</h3>
                          <v-text-field
                          label="Minimum score"
                          v-model="filters[1].value"
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
              </vl-map>
              
            </div>
          </v-card>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
        overlayCoordinate: [19, 45],
            zoom: 14,
            center: [19.842550, 45.254410],
            rotation: 0,
            geolocPosition: undefined,
            //
            showFiltersDialog: false,
            pharmacyName: '',
            filters: [
              {
                id: 0,
                name: 'City',
                on: false,
                value: '',
                query: 'city'
              },
              {
                id: 1,
                name: 'Min. Score',
                on: false,
                value: '',
                query: 'score',
              },
            ],
            //
            results: [
                {
                    id: 0,
                    name: 'Pharmacy 1',
                    address: {
                        city: 'City',
                        street: 'Street',
                        streetNo: '42A',
                    },
                    image: '../assets/pharmacy-placeholder.jpg'
                },
                {
                    id: 1,
                    name: 'Pharmacy 2',
                    address: {
                        city: 'City',
                        street: 'Street',
                        streetNo: '5F',
                    },
                    image: '../assets/pharmacy-placeholder.jpg'
                },
                {
                    id: 2,
                    name: 'Pharmacy 3',
                    address: {
                        city: 'City',
                        street: 'Street',
                        streetNo: '32A',
                    },
                    image: '../assets/pharmacy-placeholder.jpg'
                },
                {
                    id: 3,
                    name: 'Pharmacy 4',
                    address: {
                        city: 'City',
                        street: 'Street',
                        streetNo: '12',
                    },
                    image: '../assets/pharmacy-placeholder.jpg'
                },
                {
                    id: 4,
                    name: 'Pharmacy 5',
                    address: {
                        city: 'City',
                        street: 'Street',
                        streetNo: '122',
                    },
                    image: '../assets/pharmacy-placeholder.jpg'
                },
                {
                    id: 5,
                    name: 'Pharmacy 6',
                    address: {
                        city: 'City',
                        street: 'Street',
                        streetNo: '122',
                    },
                    image: '../assets/pharmacy-placeholder.jpg'
                },
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

    getSearchResults: function() {
      let queryString = '?';
      let first = true;
      if(this.pharmacyName) {
        queryString = queryString.concat('name=' + this.pharmacyName);
        this.first = false;
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

      this.axios.get("api/pharmacy/search" + queryString)
        .then(r => {
          if(r.data._embedded) {
            this.results = r.data._embedded.pharmacies;
          } else {
            this.results = [];
          }
          console.log(r.data._embedded.pharmacies);
        })
        .catch(r => {
          console.log(r);
        });
    },
  },

  computed: {
    activeFilters: function() {
      return this.filters.filter(f => f.on);
    }
  },

    mounted() {
      console.log('loading geoloc');
      navigator.geolocation.getCurrentPosition(pos => {
        this.center = [pos.coords.longitude, pos.coords.latitude]
      }, err => {
        console.log('Err getting location:', err);
      });

      this.getSearchResults();
    },
}
</script>

<style scoped>
  #ps-main {
    min-height: 100vh;
    background: #fafafa;
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
    position: absolute;
    z-index: 1;
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