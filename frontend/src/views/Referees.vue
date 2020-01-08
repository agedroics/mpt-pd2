<template>
  <b-row>
    <b-col md="6" v-for="(referee, index) of referees"
           :key="[referee.firstName, referee.lastName]" class="mb-3">
      <b-card :title="`#${index + 1} ${referee.firstName} ${referee.lastName}`">
        <b-container>
          <b-row tag="dl" class="mb-0">
            <dt>Tiesātās spēles</dt>
            <dd class="ml-auto">{{ referee.games }}</dd>
          </b-row>
          <b-row tag="dl" class="mb-0">
            <dt>Piešķirtie sodi</dt>
            <dd class="ml-auto">{{ referee.fouls }}</dd>
          </b-row>
          <b-row tag="dl" class="mb-0">
            <dt>Vidēji piešķirtie sodi</dt>
            <dd class="ml-auto mb-0">{{ referee.averageFouls.toFixed(1) }}</dd>
          </b-row>
        </b-container>
      </b-card>
    </b-col>
  </b-row>
</template>

<script>
  import axios from 'axios'

  export default {
    data() {
      return {
        referees: []
      }
    },
    beforeCreate() {
      axios.get('/api/referees/statistics')
        .then(response => this.referees = response.data);
    }
  }
</script>