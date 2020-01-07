<template>
  <div id="app">
    <b-navbar toggleable="md" type="dark" variant="dark" sticky>
      <b-container>
        <b-navbar-brand to="/">LPL</b-navbar-brand>
        <b-navbar-toggle class="ml-auto" target="nav-collapse"/>
        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item to="/turnirs" active-class="active">Turnīra tabula</b-nav-item>
            <b-nav-item to="/top10" active-class="active">Spēlētāju TOP 10</b-nav-item>
            <b-nav-item to="/ielade" active-class="active">Failu ielāde</b-nav-item>
          </b-navbar-nav>
          <b-navbar-nav class="ml-auto">
            <b-nav-item title="Atvērt DB pārvaldnieku" v-on:click="openDbManager">
              <svg width="1em" height="1em" viewBox="0 0 20 20" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                <path fill-rule="evenodd" d="M13.293 3.293a1 1 0 011.414 0l2 2a1 1 0 010 1.414l-9 9a1 1 0 01-.39.242l-3 1a1 1 0 01-1.266-1.265l1-3a1 1 0 01.242-.391l9-9zM14 4l2 2-9 9-3 1 1-3 9-9z" clip-rule="evenodd"/>
                <path fill-rule="evenodd" d="M14.146 8.354l-2.5-2.5.708-.708 2.5 2.5-.708.708zM5 12v.5a.5.5 0 00.5.5H6v.5a.5.5 0 00.5.5H7v.5a.5.5 0 00.5.5H8v-1.5a.5.5 0 00-.5-.5H7v-.5a.5.5 0 00-.5-.5H5z" clip-rule="evenodd"/>
              </svg>
            </b-nav-item>
            <b-nav-item title="Dzēst spēļu datus" v-on:click="deleteGames">
              <svg width="1em" height="1em" viewBox="0 0 20 20" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                <path d="M7.5 7.5A.5.5 0 018 8v6a.5.5 0 01-1 0V8a.5.5 0 01.5-.5zm2.5 0a.5.5 0 01.5.5v6a.5.5 0 01-1 0V8a.5.5 0 01.5-.5zm3 .5a.5.5 0 00-1 0v6a.5.5 0 001 0V8z"/>
                <path fill-rule="evenodd" d="M16.5 5a1 1 0 01-1 1H15v9a2 2 0 01-2 2H7a2 2 0 01-2-2V6h-.5a1 1 0 01-1-1V4a1 1 0 011-1H8a1 1 0 011-1h2a1 1 0 011 1h3.5a1 1 0 011 1v1zM6.118 6L6 6.059V15a1 1 0 001 1h6a1 1 0 001-1V6.059L13.882 6H6.118zM4.5 5V4h11v1h-11z" clip-rule="evenodd"/>
              </svg>
            </b-nav-item>
            <b-nav-item title="Aizvērt programmu" v-on:click="shutdown">
              <svg width="1em" height="1em" viewBox="0 0 20 20" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                <path fill-rule="evenodd" d="M7.578 6.437a5 5 0 104.922.044l.5-.865a6 6 0 11-5.908-.053l.486.874z" clip-rule="evenodd"/>
                <path fill-rule="evenodd" d="M9.5 10V3h1v7h-1z" clip-rule="evenodd"/>
              </svg>
            </b-nav-item>
          </b-navbar-nav>
        </b-collapse>
      </b-container>
    </b-navbar>
    <b-container class="py-3">
      <router-view/>
    </b-container>
  </div>
</template>

<script>
  import axios from 'axios'

  export default {
    methods: {
      shutdown() {
        axios.post('/api/shutdown');
        close()
      },
      openDbManager() {
        axios.post('/api/database/open-manager')
      },
      deleteGames() {
        axios.delete('/api/games').then(() => this.$router.go())
      }
    }
  }
</script>

<style>
  button:disabled {
    cursor: not-allowed;
    pointer-events: all !important;
  }
</style>