<template>
    <div id="main-home">
        <div id="cover-home">
        <div id="cover-text">
            <h2 id="cover-header">Pharmacy {{this.pharmacy.name}}</h2>
        </div>
        <img src="../assets/plaguedoctorcovid.png">
        </div>

        <div id="footer-home">
        <div id="footer-container">

            <div id="footer-main">
            <div id="footer-title">
                <h3 class="primary--text">Schnabel</h3>
                <p class="info--text">The pharmacy that cares for the wellbeing of you and your family.</p>
                <spacer></spacer>
                <h4 class="primary--text">Avarage grade: {{this.pharmacy.score}}</h4>
                <h4  class="primary--text">Address: {{this.pharmacy.address.street}} {{this.pharmacy.address.streetNo}}, {{this.pharmacy.address.city}}</h4>
            </div>
            </div>

            <div id="footer-columns">
            <div class="footer-column">
                <h4 class="primary--text">Employees</h4>
                <div class="footer-divider"></div>
                <ul class="footer-column-list unstyled-list">
                <li><router-link to="/pharmacyadmin/pharmacistsearch">Search pharmacists</router-link></li>
                <li><router-link to="/pharmacyadmin/pharmacist">Pharmacists</router-link></li>
                <li><router-link to="/pharmacyadmin/dermatologistsearch">Search dermatologists</router-link></li>
                <li><router-link to="/pharmacyadmin/dermatologist">Dermatologists</router-link></li>
                <li><router-link to="/pharmacyadmin/vacation">Vacations</router-link></li>
                </ul>
            </div>
            <div class="footer-column">
                <h4 class="primary--text">Drugs</h4>
                <div class="footer-divider"></div>
                <ul class="footer-column-list unstyled-list">
                <li><router-link to="/pharmacyadmin/drug">Drugs</router-link></li>
                <li><router-link to="/pharmacyadmin/pricelist">Pricelist</router-link></li>
                <li><router-link to="/pharmacyadmin/order">Orders</router-link></li>
                <li><router-link to="/pharmacyadmin/makeorder">New order</router-link></li>
                <li><router-link to="/pharmacyadmin/promotion">New promotion</router-link></li>
                <li><router-link to="/pharmacyadmin/availabilityrequests">Availability requests</router-link></li>

                </ul>
            </div>
            <div class="footer-column">
                <h4 class="primary--text">Appointments</h4>
                <div class="footer-divider"></div>
                <ul class="footer-column-list unstyled-list">
                <li><router-link to="/pharmacyadmin/defineappointment">Define appointment</router-link></li>
                <li><router-link to="/pharmacyadmin/freedermatologistappointment">Free dermatologist appointments</router-link></li>
                </ul>
            </div>
             <div class="footer-column">
                <h4 class="primary--text">Reports</h4>
                <div class="footer-divider"></div>
                <ul class="footer-column-list unstyled-list">
                <li><router-link to="/pharmacyadmin/appointmentsreport">Appointment report</router-link></li>
                <li><router-link to="/pharmacyadmin/drugusage">Drug usage report</router-link></li>
                <li><router-link to="/pharmacyadmin/pharmacyincome">Income report</router-link></li>
                </ul>
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
              pharmacy: '',
              pharmacyId: '',
              avarageGrade: '',
              pharmacyName: '',
              zoom: 12,
              center: [19.882, 45.254],
              rotation: 0,
              geolocPosition: undefined,
              overlayCoordinate: [19.882, 45.254],
            }
        },
        methods: {
          getPharmacyAdmin: function() {
                this.refreshToken().then(response => {
                    localStorage.jws = response.data;
                    this.axios.get("api/pharmacyadmin", {headers:{"Authorization": "Bearer " + localStorage.jws, "Content-Type" : "application/json",}})
                        .then(response => {
                            this.pharmacyAdmin = response.data;
                            this.pharmacyId = this.pharmacyAdmin.pharmacy.id;
                            this.getPharmacy();
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
            getPharmacy: function() {
              this.axios.get("api/pharmacy/" + this.pharmacyId)
                  .then(response => {
                      this.pharmacy = response.data;
                      this.overlayCoordinate = [this.pharmacy.address.longitude, this.pharmacy.address.latitude];
                  })
                  .catch(response => {
                      console.log("Failed to get pharmacy", response.data);
                  });
            },
        },
        mounted() {
          this.getPharmacyAdmin();
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