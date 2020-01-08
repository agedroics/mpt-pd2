<template>
  <b-table striped hover outlined stacked="md" class="tournament-table"
           :fields="fields" :items="itemsProvider" :busy="busy">
    <template v-slot:cell(wins)="data">
      {{ data.item.wins }} ({{ data.item.overtimeWins }})
    </template>
    <template v-slot:cell(losses)="data">
      {{ data.item.losses }} ({{ data.item.overtimeLosses }})
    </template>
    <template v-slot:table-busy>
      <div class="text-center text-danger my-2">
        <b-spinner class="align-middle mr-2"/>
        <strong>Ielādē...</strong>
      </div>
    </template>
  </b-table>
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
          label: 'Uzvaras (papildl.)'
        }, {
          key: 'losses',
          label: 'Zaudējumi (papildl.)'
        }, {
          key: 'goalsFor',
          label: 'Gūtie vārti'
        }, {
          key: 'goalsAgainst',
          label: 'Zaudētie vārti'
        }, {
          key: 'points',
          label: 'Punkti'
        }],
        busy: false
      }
    },
    methods: {
      itemsProvider(ctx, callback) {
        this.busy = true;
        axios.get('/api/tournament')
            .then(response => {
              this.busy = false;
              callback(response.data)
            })
      }
    }
  }
</script>

<style>
  .tournament-table td:nth-of-type(2),
  .tournament-table td:last-of-type {
    font-weight: bold;
  }
</style>