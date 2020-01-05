<template>
  <div id="app">
    <b-navbar toggleable="md" type="dark" variant="dark" sticky>
      <b-container>
        <b-navbar-brand to="/">LPL</b-navbar-brand>
        <b-navbar-toggle class="ml-auto" target="nav-collapse"/>
        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item to="/tabula" active-class="active">Turnīra tabula</b-nav-item>
            <b-nav-item to="/ielade" active-class="active">Failu ielāde</b-nav-item>
          </b-navbar-nav>
        </b-collapse>
      </b-container>
    </b-navbar>
    <b-container class="py-3">
      <router-view/>
    </b-container>
  </div>
</template>

<style>
  button:disabled {
    cursor: not-allowed;
    pointer-events: all !important;
  }
</style>