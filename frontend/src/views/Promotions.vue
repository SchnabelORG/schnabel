<template>
    <div id="promotion-main">
        <v-card
        id="promotion-card"
        elevation="2">
            <v-card-title>New promotion</v-card-title>
            <b class="err">{{error}}</b>
            <div v-if="success" id="success-form">
                    <p id="success-icon"><i class="fa fa-check"></i></p>
                    <p>Promotion created!</p>
                </div>
            <v-textarea
              v-model="description"
              color="teal"
              :rules="[rules.required]"
              id="descripton-t"
            >
              <template v-slot:label>
                <div>
                  Description
                </div>
              </template>
            </v-textarea>
            <v-menu
                ref="menu"
                v-model="menu"
                :close-on-content-click="false"
                :return-value.sync="date"
                transition="scale-transition"
                offset-y
                min-width="auto"
            >
                <template v-slot:activator="{ on, attrs }">
                <v-text-field
                    v-model="validFrom"
                    label="Valid from"
                    prepend-icon="mdi-calendar"
                    readonly
                    v-bind="attrs"
                    v-on="on"
                ></v-text-field>
                </template>
                <v-date-picker
                    v-model="validFrom"
                    no-title
                    scrollable
                    :min="new Date().toISOString().substr(0, 10)"
                    :max="this.validUntil"
                >
                    <v-spacer></v-spacer>
                    <v-btn
                        text
                        color="primary"
                        @click="menu = false"
                    >
                        Cancel
                    </v-btn>
                    <v-btn
                        text
                        color="primary"
                        @click="$refs.menu.save(date)"
                    >
                        OK
                    </v-btn>
                </v-date-picker>
            </v-menu>
            <v-menu
                    ref="menu2"
                    v-model="menu2"
                    :close-on-content-click="false"
                    :return-value.sync="date"
                    transition="scale-transition"
                    offset-y
                    min-width="auto"
                >
                    <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                        v-model="validUntil"
                        label="Valid until"
                        prepend-icon="mdi-calendar"
                        readonly
                        v-bind="attrs"
                        v-on="on"
                    ></v-text-field>
                    </template>
                    <v-date-picker
                        v-model="validUntil"
                        no-title
                        scrollable
                        :min="this.validFrom"
                    >
                        <v-spacer></v-spacer>
                        <v-btn
                            text
                            color="primary"
                            @click="menu2 = false"
                        >
                            Cancel
                        </v-btn>
                        <v-btn
                            text
                            color="primary"
                            @click="$refs.menu2.save(date)"
                        >
                            OK
                        </v-btn>
                    </v-date-picker>
            </v-menu>
            <v-btn class='primary' elevation="1" @click="createPromotion()" :disabled="!validFrom || !validUntil ||!description">
                Create
            </v-btn>
        </v-card>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                validFrom: '',
                validUntil: '',
                description: '',
                error: '',
                success: false,
                rules: {
                    required: value => !!value || 'Required.',
                },
            }
        },
        methods: {
            createPromotion: function() {
                if(this.validUntil < this.validFrom)
                {
                    this.error = 'Invalid time interval';
                    return;
                }
                let promotionRequest = { description: this.description, startTime: new Date(this.validFrom).toISOString(), endTime: new Date(this.validUntil).toISOString() };
                this.refreshToken()
                .then(rr => {
                    localStorage.jws = rr.data;
                    this.axios.post("api/promotion",
                    promotionRequest,
                    { headers: {
                        "Authorization": "Bearer " + localStorage.jws,
                        "Content-Type" : "application/json",
                    }})
                        .then(() => {
                            this.success = true;
                            this.description = '';
                            this.validFrom = '';
                            this.validUntil = '';
                        })
                        .catch(() => {
                            this.error = 'Could not create the promotion';
                        });
                })
                .catch(() => {
                    this.$router.push("/");
                });
            },
        },
    }
</script>

<style scoped>
    #promotion-main {
        display:grid;
        grid-template-columns:auto;
        place-items: center;
        min-height: 92vh;
        background-color: #fbecdd;
    }
    #promotion-card {
        display: flex;
        flex-direction: column;
        margin: 0 auto;
        width: 40%;
    }
    #promotion-main {
        display: flex;
        flex-direction: column;
        justify-content: center;
        width: 100vw;
        min-height: 100vh;
    }
    #description-t {
        width: 80%;
    }
</style>