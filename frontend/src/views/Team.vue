<template>
  <b-tabs card end>
    <b-tab title="Spēlētāji" class="p-0" lazy>
      <b-table stacked="lg" hover striped class="mb-0"
               :fields="playerFields" :items="players" :busy="busy">
        <template v-slot:cell(games)="data">
          {{ data.item.games }} ({{ data.item.gamesAsStarter }})
        </template>
        <template v-slot:table-busy>
          <div class="text-center text-danger my-2">
            <b-spinner class="align-middle mr-2"/>
            <strong>Ielādē...</strong>
          </div>
        </template>
      </b-table>
    </b-tab>
    <b-tab title="Vārtsargi" class="p-0" lazy>
      <b-table stacked="lg" hover striped class="mb-0"
               :fields="goalkeeperFields" :items="goalkeepers" :busy="busy">
        <template v-slot:cell(games)="data">
          {{ data.item.games }} ({{ data.item.gamesAsStarter }})
        </template>
        <template v-slot:cell(averageGoalsConceded)="data">
          {{ data.item.averageGoalsConceded.toFixed(1) }}
        </template>
        <template v-slot:table-busy>
          <div class="text-center text-danger my-2">
            <b-spinner class="align-middle mr-2"/>
            <strong>Ielādē...</strong>
          </div>
        </template>
      </b-table>
    </b-tab>
  </b-tabs>
</template>

<script>
  import axios from 'axios'

  const commonFields = [{
    key: 'firstName',
    label: 'Vārds'
  }, {
    key: 'lastName',
    label: 'Uzvārds'
  }, {
    key: 'number',
    label: 'Numurs'
  }, {
    key: 'games',
    label: 'Spēles (pamatsast.)'
  }, {
    key: 'timePlayed',
    label: 'Nospēlētās min.'
  }, {
    key: 'yellowCards',
    label: 'Dz. kartiņas'
  }, {
    key: 'redCards',
    label: 'Sark. kartiņas'
  }];

  export default {
    data() {
      return {
        playerFields: [...commonFields, {
          key: 'goals',
          label: 'Gūtie vārti'
        }, {
          key: 'assists',
          label: 'Rezultatīvās piespēles'
        }],
        goalkeeperFields: [...commonFields, {
          key: 'goalsConceded',
          label: 'Ielaistie vārti'
        }, {
          key: 'averageGoalsConceded',
          label: 'Vidēji ielaistie vārti'
        }],
        players: [],
        goalkeepers: [],
        busy: false
      }
    },
    methods: {
      updateStatistics(team = this.$route.params.team) {
        this.busy = true;
        axios.get(`/api/teams/${team}/statistics`).then(response => {
          this.busy = false;
          this.players = response.data.players;
          this.goalkeepers = response.data.goalkeepers;
        });
      }
    },
    beforeMount() {
      this.updateStatistics();
    },
    beforeRouteUpdate(to, from, next) {
      this.updateStatistics(to.params.team);
      next();
    }
  }
</script>