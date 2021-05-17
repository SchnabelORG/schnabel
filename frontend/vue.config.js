module.exports = {
  "outputDir": "../../../target/frontend/public",
  "transpileDependencies": [
    "vuetify"
  ],
  "devServer": {
    "proxy": 'http://localhost:8082/'
  }
}