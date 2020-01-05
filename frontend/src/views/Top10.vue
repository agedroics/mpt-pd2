<template>
  <b-row>
    <b-col md="6" v-for="(player, index) of players" class="mb-3">
      <b-card :title="`#${index + 1} ${player.firstName} ${player.lastName}`">
        <b-container>
          <b-row tag="dl" class="mb-0">
            <dt>Gūtie vārti</dt>
            <dd class="ml-auto">{{ player.goals }}</dd>
          </b-row>
          <b-row tag="dl" class="mb-0">
            <dt>Rezultatīvās piespēles</dt>
            <dd class="ml-auto mb-0">{{ player.assists }}</dd>
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
        players: []
      }
    },
    beforeCreate() {
      axios.get('/api/players/top10')
        .then(response => this.players = response.data)
    }
  }
</script>

<style scoped>

</style>