<template>
    <div id="loyalty-main">
        <v-card>
            <v-card-title>
                Loyalty Program
            </v-card-title>
            <v-card-text>
                <div>
                    <v-text-field
                        v-model="l.pharmacyPoints"
                        :rules="[rules.required]"
                        label="Pharmacist points"
                    >
                    </v-text-field>
                    <v-text-field
                        v-model="l.dermatologistPoints"
                        :rules="[rules.required]"
                        label="Dermatologist points"
                    >
                    </v-text-field>
                    <v-text-field
                        v-model="l.pointsForBronze"
                        :rules="[rules.required]"
                        label="Points for bronze"
                    >
                    </v-text-field>
                    <v-text-field
                        v-model="l.bronzeDiscount"
                        :rules="[rules.required]"
                        label="Bronze discount"
                    >
                    </v-text-field>
                    <v-text-field
                        v-model="l.pointsForSilver"
                        :rules="[rules.required]"
                        label="Points for silver"
                    >
                    </v-text-field>
                    <v-text-field
                        v-model="l.silverDiscount"
                        :rules="[rules.required]"
                        label="Silver discount"
                    >
                    </v-text-field>
                    <v-text-field
                        v-model="l.pointsForGold"
                        :rules="[rules.required]"
                        label="Points for gold"
                    >
                    </v-text-field>
                    <v-text-field
                        v-model="l.goldDiscount"
                        :rules="[rules.required]"
                        label="Gold discount"
                    >
                    </v-text-field>

                    <v-divider></v-divider>

                    <v-btn @click="saveProgram()">
                        Save
                    </v-btn>

                </div>
            </v-card-text>
        </v-card>
    </div>
</template>

<script>
export default {
    data() {
        return {
            l: {},
            rules: {
                    required: value => !!value || 'Required.',
                },
        }
    },
    computed:{
        currentUser() {
                return this.$store.state.auth.user;
            }
    },
    methods: {
        getLotalty: function() {
            this.axios.get("api/loyalty", {headers:{"Authorization": "Bearer " + this.currentUser}})
            .then(r => {
                this.l = r.data;
            })
            .catch(r => {
                console.log(r.data);
            })
        },
        saveProgram: function() {
            this.axios.put("api/loyalty/update", this.l, {headers:{"Authorization": "Bearer " + this.currentUser}})
            .then(r => {
                console.log(r.data);
                location.reload();
            })
        }
    },
    mounted() {
        this.getLotalty();
    }

}
</script>