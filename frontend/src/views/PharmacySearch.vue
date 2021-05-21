<template>
    <div id="pharmacy-main" class="secondary">
        <main-navigation>
            <router-link to="/">Home</router-link>
        </main-navigation>
        <div id="search-container">
            <v-text-field
            append-outer-icon='fa-sliders'
            @click:append-outer="showFilters=!showFilters"
            placeholder="Search by name"
            outlined/>
        </div>
        <div id="pharmacy-container">
            <div id="results-container">
                <v-card class="result" v-for="result in results" :key="result.id"
                elevation="2"
                outlined>
                    <!-- NOTE(Jovan): For development -->
                    <div>
                      <v-card-title>{{result.name}}</v-card-title>
                      <v-card-subtitle>{{result.address.street}} {{result.address.streetNo}}, {{result.address.city}}</v-card-subtitle>
                    </div>
                    <v-img src="../assets/pharmacy-placeholder.jpg" height="120px" contain ></v-img>
                </v-card>
            </div>
          <div id="map-container">
            <vl-map
            :load-tiles-while-animating="true"
            :load-tiles-while-interacting="true"
            data-projection="EPSG:4326"
            >
              <vl-view :zoom.sync="zoom"
              :center.sync="center"
              :rotation.sync="rotation"></vl-view>
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

            </vl-map>
          </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {

            zoom: 14,
            center: [19.842550, 45.254410],
            rotation: 0,
            geolocPosition: undefined,
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
            ],
        }
    },
    mounted() {
      console.log('loading geoloc');
      navigator.geolocation.getCurrentPosition(pos => {
        console.log("aaaa", pos);
        this.center = [pos.coords.longitude, pos.coords.latitude]
      }, err => {
        console.log('errr', err);
      });
      console.log('loaded');
    },
}
</script>

<style scoped>
 #pharmacy-main {
     min-height: 100vh;
 }

 #search-container {
  display: grid;
  place-items: center;
  margin-top: 20px;
 }

 #pharmacy-container {
     display: grid;
     grid-template-columns: auto 1fr;
     margin: 20px;
     background: #fff;
 }

 #results-container {
    display: flex;
    flex-direction: column;
    background: #fff;
    justify-content: flex-start;
    align-items: center;
    padding: 20px;
 }

 .result {
   display: flex;
   flex-direction: row;
   justify-content: space-between;
   width: 400px;
 }

</style>