<template>
  <b-table striped hover :fields="fields" :items="itemsProvider"/>
</template>

<script>
  import axios from 'axios'

  export default {
    data() {
      return {
        fields: [{
          key: 'position',
          label: 'Vieta'
        }, {
          key: 'team',
          label: 'Komanda'
        }, {
          key: 'wins',
          label: 'Uzvaras'
        }, {
          key: 'overtimeWins',
          label: 'Uzvaras papildlaikā'
        }, {
          key: 'losses',
          label: 'Zaudējumi'
        }, {
          key: 'overtimeLosses',
          label: 'Zaudējumi papildlaikā'
        }, {
          key: 'goalsFor',
          label: 'Gūtie vārti'
        }, {
          key: 'goalsAgainst',
          label: 'Zaudētie vārti'
        }, {
          key: 'points',
          label: 'Punkti'
        }]
      }
    },
    methods: {
      itemsProvider(ctx, callback) {
        axios.get('/api/tournament')
            .then(response => callback(response.data))
      }
    }
  }
</script>

<style>
  td:nth-of-type(2), td:last-of-type {
    font-weight: bold;
  }
  th, td {
    text-align: center;
  }
  th:nth-of-type(2), td:nth-of-type(2) {
    text-align: start;
  }
</style>