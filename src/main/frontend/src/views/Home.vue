<template>
  <div id="home">
    <main-navigation />
    <div id="header">
      <div id="header-text">
        <div id="header-title">
          <h1 id="title">Schnabel</h1>
          <p id="header-desc">Centralized pharmaceutical information system</p>
          <router-link to="/pswreg" v-slot="{href, navigate}">
            <v-btn :href="href" @click="navigate" id="mainbtn" elevation="1">Get your API key</v-btn>
          </router-link>
        </div>
      </div>
      <div id="header-img">
        <img src="img/flask.svg" alt="Header image">
      </div>
    </div>
  </div>
</template>

<script>

export default {
  data() {
    return {
    }
  }
}
</script>

<style scoped>
  #mainbtn {
    color: #fff;
    background-color: #ff5722
  }
  #header {
    display: flex;
    flex-direction: row;
    justify-content: flex-start;

    height: 100vh;
    background: none;
    /*background-color: #324A5E;*/
    background: rgb(50,74,94);
    background: linear-gradient(69deg, rgba(63,81,181,1) 5%, rgba(197,202,233,1) 100%); 
  }

  #header-text {
    display: flex;
    flex-direction: column;
    justify-content: center;
    padding-left: 10vw;
  }

  #title {
    font-weight: 500;
    font-size: 3.5rem;
    color: #fff;
  }

  #header-desc {
    font-size: 1.5rem;
    color: #fff;
  }

  #header-img {
    margin-left: auto;
    height: 100%;
    width: 50%;
  }

  #header-img img {
    width: 100%;
    height: 100%;
  }
  
</style>
