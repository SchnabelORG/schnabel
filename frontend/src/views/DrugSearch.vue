<template>
    <div id="search-main">
        <div id="search-container">
            <v-text-field 
            placeholder="Search drugs..."/>
            <div id="results-container">
                <div id="results">
                    <v-card v-for="result in results" :key="result">
                        <v-card-title>{{result.drug.name}}</v-card-title>
                        <v-card-text>
                            <p>{{result.drug.prescription}}</p>
                        </v-card-text>
                    </v-card>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            drugs: [],
        }
    },
}
</script>