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
                <path fill-rule="evenodd" d="M2 3l1-1 3.081 2.2a1 1 0 01.419.815v.07a1 1 0 00.293.708L12.5 11.5l.914-.305a1 1 0 011.023.242l3.356 3.356a1 1 0 010 1.414l-1.586 1.586a1 1 0 01-1.414 0l-3.356-3.356a1 1 0 01-.242-1.023l.305-.914-5.707-5.707a1 1 0 00-.707-.293h-.071a1 1 0 01-.814-.419L2 3zm11.354 9.646a.5.5 0 00-.708.708l3 3a.5.5 0 00.708-.708l-3-3z" clip-rule="evenodd"/>
                <path fill-rule="evenodd" d="M17.898 4.223a3.003 3.003 0 01-3.679 3.674L7.878 14.15a3 3 0 11-2.027-2.027l6.252-6.341a3 3 0 013.675-3.68l-2.142 2.142L14 6l1.757.364 2.141-2.141zm-13.37 9.019L5 13l.471.242.529.026.287.445.445.287.026.529L7 15l-.242.471-.026.529-.445.287-.287.445-.529.026L5 17l-.471-.242L4 16.732l-.287-.445L3.268 16l-.026-.529L3 15l.242-.471.026-.529.445-.287.287-.445.529-.026z" clip-rule="evenodd"/>
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
        axios.post('/api/open-db-manager')
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