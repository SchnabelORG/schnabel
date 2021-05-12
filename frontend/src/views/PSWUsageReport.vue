<template>
    <div id="reports-main">
        <v-card :loading="loadingNotifications ? 'accent' : 'null'">
            <v-card-title id="notifications-title" class="primary secondary--text">
                Notifications
                <v-btn icon color=accent elevation="0" @click="getNotifications">
                    <i class="fa fa-refresh"></i>
                </v-btn>
            </v-card-title>
            <v-card-text>
                <div id="notifications-table">
                    <v-data-table :headers="headers"
                                :items="notifications">
                        <template v-slot:item="row">
                            <tr>
                                <td>{{row.item.endpoint}}</td>
                                <td>{{row.item.message}}</td>
                                <td>{{row.item.filename}}</td>
                                <td>
                                    <v-btn elevation="0" class="white black--text" @click="getReport(row.item)">Get report</v-btn>
                                </td>
                            </tr>
                        </template>
                    </v-data-table>
                </div>
            </v-card-text>
        </v-card>
    </div>
</template>

<script>
export default {
    data: function() {
        return {
            notifications: [],
            headers: [
                { text: "Message" },
                { text: "Endpoint" },
                { text: "Filename" },
                { text: "Get report" },
            ],
            loadingNotifications: false,
        }
    },

    methods: {

        getReport: function(notification) {
            let fileInfo = {
                url: "http://schnabel.herokuapp.com/pswupload",
                filename: notification.filename
            };
            this.axios.post(notification.endpoint, fileInfo)
                .then(response => {
                    console.log(response);
                })
                .catch(response => {
                    console.log(response);
                });
        },

        getNotifications: function() {
            this.loadingNotifications = true;
            this.axios.get("pswapi/usagereportnotifications")
                .then(response => {
                    console.log(response);
                    this.notifications = response.data;
                })
                .catch(response => {
                    console.log(response);
                })
                .finally(function(){
                    this.loadingNotifications = false;
                });
        },
    },

    mounted() {
        this.getNotifications();
    },
}
</script>

<style scoped>

    #reports-main {
        display: grid;
        grid-template-columns: auto;
        place-items: center;
        min-height: 100%;
    }

    #notifications-title {
        display: grid;
        grid-template-columns: 1fr auto;
    }

</style>