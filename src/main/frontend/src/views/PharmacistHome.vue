<template>
    <div id="pharm-home-main">
        <v-row>
            <v-col id="graph">
                <v-card
                    class="pharm-home-graph primary secondary--text"
                    dark
                    max-width="90%"
                    >
                    <v-card-text>
                        <v-sheet color="rgba(0, 0, 0, .12)">
                        <v-sparkline
                            :value="value"
                            color="rgba(255, 255, 255, .7)"
                            height="100"
                            padding="24"
                            stroke-linecap="round"
                            smooth
                        >
                            <template v-slot:label="item">
                            {{ item.value }}
                            </template>
                        </v-sparkline>
                        </v-sheet>
                    </v-card-text>
                
                    <v-card-text>
                        <div class="display-1 font-weight-thin">
                        Last 7 days appointment
                        </div>
                    </v-card-text>
                </v-card>
            </v-col>
            <v-col id="pharm-home-timeline">
                <h1>Today activity:</h1>
                <v-timeline
                    :reverse="true"
                    dense>
                    <v-timeline-item
                    v-for="n in 5"
                    :key="n"
                    >
                    <v-card>
                        <v-card-title class="primary secondary--text">
                        Appointment at 2:40pm
                        </v-card-title>
                        <v-card-text>
                            <h4>Radovan Zupunski</h4>
                            <h4>Brufen</h4>
                        </v-card-text>
                    </v-card>
                    </v-timeline-item>
                </v-timeline>
            </v-col>
        </v-row>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                value: [5, 10, 10, 11, 12, 20, 17],
            }
        }
    }
</script>

<style scoped>
    #graph{
        margin: 2%;
        margin-left: 5%;
    }
    #pharm-home-timeline{
        max-width: 30%;
    }
</style>