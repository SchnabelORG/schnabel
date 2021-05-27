<template>
    <div id="main-home">
        <div id="cover-home">
        <div id="cover-text">
            <h2 id="cover-header">Centralized pharmacies information system</h2>
            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Tenetur facilis facere aut expedita delectus ducimus natus, obcaecati nihil sapiente!</p>
            <div>
            <router-link to="" v-slot="{href, navigate}">
                <v-btn color="accent" :href="href" @click="navigate" elevation="1">View reports</v-btn>
            </router-link>
            </div>
        </div>
        <img src="../assets/plaguedoctorcovid.png">
        </div>

        <div id="footer-home">
        <div id="footer-container">

            <div id="footer-main">
            <div id="footer-title">
                <h3 class="primary--text">Schnabel</h3>
                <p class="info--text">Ipsum animi fugiat ab suscipit quidem obcaecati, repellendus ipsa alias, deserunt deleniti voluptas dicta distinctio dolore recusandae rem dignissimos laudantium perferendis. Sunt!</p>
            </div>
            </div>

            <div id="footer-columns">
            <div class="footer-column">
                <h4 class="primary--text">Employees</h4>
                <div class="footer-divider"></div>
                <ul class="footer-column-list unstyled-list">
                <li><a href="/pharmacyadmin/dermatologist">Dermatologists</a></li>
                <li><a href="/pharmacyadmin/pharmacist">Pharmacists</a></li>
                <li><a href="/pharmacyadmin/vacation">Vacations</a></li>
                <li><a href="/pharmacyadmin/defineappointment">Define appointment</a></li>
                </ul>
            </div>
            <div class="footer-column">
                <h4 class="primary--text">Drugs</h4>
                <div class="footer-divider"></div>
                <ul class="footer-column-list unstyled-list">
                <li><a href="/pharmacyadmin/drug">Drugs</a></li>
                <li><a href="/pharmacyadmin/makeorder">New order</a></li>
                <li><a href="/pharmacyadmin/order">Orders</a></li>
                <li><a href="/pharmacyadmin/promotion">Promotion</a></li>
                </ul>
            </div>
            </div>
        </div>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {

            }
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