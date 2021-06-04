<template>
  <div id="main-home">
    <div id="cover-home">
      <div id="cover-text">
        <h2 id="cover-header">Pharmacy {{this.pharmacyName}}</h2>
      </div>
      <img src="../assets/plaguedoctorcovid.png">
    </div>
    <div id="cards-home">
      <div id="cards-container">
        <div class="card">
          <div class="card-header primary--text">
            <i class="card-icon fa fa-bullhorn" aria-hidden="true"></i>
            <p class="info--text">Subscription</p>
            <h3 class="primary--text">Subscribe and save!</h3>
          </div>
          <div class="card-body">
            <p class="info--text">Subscribe and be one of the first to know about our promotions and discounts.</p>
          </div>
          <div class="card-footer">
            <v-btn class='primary'>Subscribe</v-btn>
          </div>
        </div>
        <div class="card">
          <div class="card-header primary--text">
            <i class="card-icon fa fa-qrcode" aria-hidden="true"></i>
            <p class="info--text">Drug availability</p>
            <h3 class="primary--text">Check availability via ePrescription!</h3>
          </div>
          <div class="card-body">
            <p class="info--text">Check drug availability very easy via your ePrescription.</p>
          </div>
          <div class="card-footer">
            <v-btn class='primary'>Check</v-btn>
          </div>
        </div>
        <div class="card">
          <div class="card-header primary--text">
            <i class="fa fa-clock-o" aria-hidden="true"></i>
            <p class="info--text">Dermatologist appointment</p>
            <h3 class="primary--text">Schedule a visit to a dermatologist</h3>
          </div>
          <div class="card-body">
            <p class="info--text">Find a dermatologist and schedule an appointment with him.</p>
          </div>
          <div class="card-footer">
            <v-btn class="primary" :to="'/dermappointment/' + pharmacyName">Schedule</v-btn>
          </div>
        </div>
      </div>
    </div>

    <div id="footer-home">
      <div id="footer-container">

        <div id="footer-main">
          <div id="footer-title">
            <h3 class="primary--text">Schnabel</h3>
            <p class="info--text">The pharmacy that cares for the wellbeing of you and your family.</p>
          </div>
        </div>

        <div id="footer-columns">
          <div class="footer-column">
            <h4 class="primary--text">Drug</h4>
            <div class="footer-divider"></div>
            <ul class="footer-column-list unstyled-list">
              <li><router-link to="">Reserve drug</router-link></li>
              <li><router-link to="/pharmacy/drug">Our drugs</router-link></li>
            </ul>
          </div>
          <div class="footer-column">
            <h4 class="primary--text">Dermatology</h4>
            <div class="footer-divider"></div>
            <ul class="footer-column-list unstyled-list">
              <li><router-link to="">Make examination</router-link></li>
              <li><router-link to="/pharmacy/dermatologist">Our dermatologists</router-link></li>
            </ul>
          </div>
          <div class="footer-column">
            <h4 class="primary--text">Pharmacology</h4>
            <div class="footer-divider"></div>
            <ul class="footer-column-list unstyled-list">
              <li><router-link to="">Make consulting</router-link></li>
              <li><router-link to="/pharmacy/pharmacist">Our pharmacists</router-link></li>
            </ul>
          </div>
          <div class="footer-column">
            <h4 class="primary--text">General info</h4>
            <div class="footer-divider"></div>
            <div id="contact-info">
                <div class="space-between info--text">
                    <i class="fa fa-diamond" aria-hidden="true"></i>
                    <span>Avarage grade: {{this.avarageGrade}}</span>
                </div>
                <div class="space-between info--text">
                    <i class="fa fa-map-marker" aria-hidden="true"></i>
                    <span>{{this.address}}</span>
                </div>
                <div class="space-between info--text">
                </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div>
    <vl-map :load-tiles-while-animating="true" :load-tiles-while-interacting="true"
             data-projection="EPSG:4326" style="height: 400px">
      <vl-view :zoom.sync="zoom" :center.sync="center" :rotation.sync="rotation"></vl-view>

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

		<vl-overlay id="overlay" :position="overlayCoordinate">
      <template>
        <div class="overlay-content">
          <i class="fa fa-map-marker" aria-hidden="true"></i>
        </div>
      </template>
    </vl-overlay>

      <vl-layer-tile id="osm">
        <vl-source-osm></vl-source-osm>
      </vl-layer-tile>
    </vl-map>
  </div>
  </div>
</template>

<script>
    export default {
        data() {
            return {
                address: 'Balzakova 48, Novi Sad',
                avarageGrade: '4.5',
                pharmacyName: 'Schnabel Liman',
                zoom: 12,
                center: [19.882, 45.254],
                rotation: 0,
                lat: '',
                att: '',
                geolocPosition: undefined,
                overlayCoordinate: [19.882, 45.254],
            }
        },
        methods: {
          getPharmacy: function() {
            
          },
        },
        mounted() {
          this.getPharmacy();
        },
    }
</script>

<style scoped>
  #main-home {
    background-color: #fafafa;
  }

  #cover-home {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-content: center;
    width: 100vw;
    height: 50vh;
    background-color: #fbecdd;
    padding: 0 10rem;
  }

  #cover-text {
    display: flex;
    flex-direction: column;
    justify-content: center;
    padding-right: 30rem;
  }

  #cover-header {
    font-family: "Poppins", sans-serif;
    color: #311403;
    font-weight: 600;
    font-size: 3rem;
  }

  #cover-home img {
    height: 100%;
    /* padding-right: 20vw; */
    /* padding-top: 10rem; */
  }

  #cards-home {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-content: flex-end;
    max-width: 100vw;
    margin: auto;
    height: 60vh;
    background: #fff;
  }

  #cards-container {
    margin-top: -2rem;
    --w: 1000px;
    --n: 3;
    width: 60vw;
    display: grid;
    background: none;
    grid-template-columns: repeat(auto-fit, minmax(clamp(100% / (var(--n) + 1) + 0.1%, (var(--w) - 100vw) * 1000, 100%), 1fr));
  }

  .card{
    display: flex;
    flex-grow: 1;
    flex-shrink: 1;
    flex-basis: 0px;
    flex-direction: column;
    justify-content: space-around;
    max-width: 20vw;
    border-radius: 15px 15px 15px 15px;
    box-shadow: 0px 0px 30px 0px rgba(0, 42, 106, 0.1);
    padding: 2rem;
    margin: 0rem 1rem;
    background-color: #fff;
    height: 400px;
  }

  .card-header h3 {
    font-size: 1.5rem;
    font-weight: 600;
  }

  .card-header p {
    margin: 0;
    margin-top: 2rem;
  }

  .card-header i {
    font-size: 3.5rem;
  }

  .unstyled-list {
    padding: 0;
    list-style: none;
  }

  .w-hours li {
    display: flex;
    justify-content: space-between;
    border-bottom: 1px solid #eee;
    margin-bottom: 8px;
  }

  #footer-home {
    display: grid;
    place-items: center;
    background: #f4f9fc;
    height: 40vh;
  }

  #footer-main {
    width: 40%;
    margin: 2rem;
  }

  #footer-container {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    max-width: 80vw;
    margin: auto;
  }

  #map-container {
    position: relative;
  }

  #footer-main h3 {
    font-size: 3rem;
    font-weight: 600;
    margin-bottom: 1rem;
  }

  #footer-columns {
    display: flex;
    flex-direction: row;
  }

  .footer-column {
    margin: 2rem;
  }

  .footer-column h4 {
    font-size: 1.5rem;
    font-weight: 600;
    margin-bottom: 1rem;
  }

  .footer-divider {
    height: 3px;
    background: #e12454;
    width: 40px;
    margin-bottom: 2rem;
  }

  .footer-column-list li {
    padding: 6px 0px;
  }

  .footer-column-list > li > a  {
    color: #8692a8;
  }

  .footer-column-list > li > a:hover  {
    transition: 400ms;
    color: #e12454;
  }

  #contact-info {
    display: flex;
    flex-direction: column;
  }

  #contact-info a {
    font-size: 1.5rem;
    font-weight: 500;
    color: #000;
    text-decoration: underline;
  }

  #contact-info a:hover {
    transition: 400ms;
    color: #e12454;
    text-decoration: none;
  }

  .space-between {
    display: flex;
    justify-content: space-between;
  }
</style>