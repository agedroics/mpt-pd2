<template>
  <b-card no-body>
    <b-card-header header-tag="nav">
      <b-nav card-header pills justified>
        <b-nav-item v-for="team of teams"
                    :key="team"
                    :to="`/komandas/${team}`"
                    active-class="active">
          {{ team }}
        </b-nav-item>
      </b-nav>
    </b-card-header>
    <b-card-body class="p-0">
      <router-view/>
    </b-card-body>
  </b-card>
</template>

<script>
  import axios from 'axios'

  export default {
    data() {
      return {
        teams: []
      }
    },
    beforeCreate() {
      axios.get('/api/teams').then(response => {
        this.teams = response.data;
        if (this.teams.length !== 0) {
          this.$router.push(`/komandas/${this.teams[0]}`)
        }
      });
    }
  }
</script>