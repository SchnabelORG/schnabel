<template>
  <div id="header">
    <div id="header-title">
      <h1 id="title">Schnabel</h1>
      <p id="header-desc">Centralized pharmaceutical information system</p>
    </div>
    <div id="header-img">
      <img src="img/flask.svg" alt="Header image">
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
  #header {
    display: flex;
    flex-direction: row;
    justify-content: flex-start;

    height: 80vh;
    background: none;
    background-color: #324A5E;
  }

  #header-title {
    
  }

  #title {
    font-size: 4.5rem;
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
