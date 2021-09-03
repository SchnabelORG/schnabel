<template>
    <div id="systemadmin-home-main">
        <v-container id="container">
            <v-row>
                <v-col>
                    <v-card color="#385F73"
                    @click="$router.push('/systemadmin/pharmacies')"
                    dark>
                        <v-card-title class="text-h6">
                            Pharmacies
                        </v-card-title>
                        <v-card-subtitle>
                            See all pharmacies and register new ones
                        </v-card-subtitle>
                        <v-card-actions>
                            <v-btn text>
                                . . .
                            </v-btn>
                        </v-card-actions>
                    </v-card>
                </v-col>
                <v-col>
                    <v-card color="#385F73"
                    dark>
                        <v-card-title class="text-h6">
                            PharmacyAdmins
                        </v-card-title>
                        <v-card-actions>
                            <v-btn text>
                                See more
                            </v-btn>
                        </v-card-actions>
                    </v-card>
                </v-col>
                <v-col>
                     <v-card color="#385F73"
                     @click="$router.push('/systemadmin/dermatologists')"
                    dark>
                        <v-card-title class="text-h6">
                            Dermatologits
                        </v-card-title>
                        <v-card-actions>
                            <v-btn text>
                                See more
                            </v-btn>
                        </v-card-actions>
                    </v-card>
                </v-col>
                <v-col>
                     <v-card color="#385F73"
                     @click="$router.push('/systemadmin/suppliers')"
                    dark>
                        <v-card-title class="text-h6">
                            Suppliers
                        </v-card-title>
                        <v-card-actions>
                            <v-btn text>
                                See more
                            </v-btn>
                        </v-card-actions>
                    </v-card>
                </v-col>
            </v-row>
            <v-row>
                <v-col>
                     <v-card color="#385F73"
                     @click="$router.push('/systemadmin/drugs')"
                    dark>
                        <v-card-title class="text-h6">
                            Drugs
                        </v-card-title>
                        <v-card-actions>
                            <v-btn text>
                                See more
                            </v-btn>
                        </v-card-actions>
                    </v-card>
                </v-col>
                <v-col>
                     <v-card color="#385F73"
                    dark>
                        <v-card-title class="text-h6">
                            Loyalti Program
                        </v-card-title>
                        <v-card-actions>
                            <v-btn text>
                                See more
                            </v-btn>
                        </v-card-actions>
                    </v-card>
                </v-col>
                <v-col>
                     <v-card color="#385F73"
                    dark>
                        <v-card-title class="text-h6">
                            Complaints
                        </v-card-title>
                        <v-card-actions>
                            <v-btn text
                            @click="$router.push('/systemadmin/complaints')"
                            >
                                See more
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
    }
    #container{
        margin: 5%;
    }
</style>