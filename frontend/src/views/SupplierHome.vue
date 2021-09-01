<template>
    <div id="systemadmin-home-main">
        <v-container id="container">
            <v-row>
                <v-col>
                    <v-card color="#385F73"
                    @click="$router.push('/supplier/orders')"
                    dark>
                        <v-card-title class="text-h6">
                            Orders
                        </v-card-title>
                        <v-card-subtitle>
                            See all oreders and make offers
                        </v-card-subtitle>
                        <v-card-actions>
                            <v-btn text >
                                . . .
                            </v-btn>
                        </v-card-actions>
                    </v-card>
                </v-col>
                <v-col>
                    <v-card color="#385F73"
                    @click="$router.push('/supplier/offers')"
                    dark>
                        <v-card-title class="text-h6">
                            Offers
                        </v-card-title>
                        <v-card-subtitle>
                            See all offers
                        </v-card-subtitle>
                        <v-card-actions>
                            <v-btn text>
                                . . .
                            </v-btn>
                        </v-card-actions>
                    </v-card>
                </v-col>
            </v-row>
        </v-container>
    </div>
</template>



<style scoped>
    #systemadmin-home-main{
        background-color: #fbecdd;
        min-height: 100vh;
    }
    #container{
        margin: 5%;
    }
</style>