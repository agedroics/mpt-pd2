<template>
  <b-card>
    <b-card-text>
      <b-form-file v-model="files"
                   multiple
                   placeholder="Izvēlieties failu(-s)"
                   browse-text="Izvēlēties"
                   drop-placeholder="Velciet failu(-s) šeit"
                   accept="text/xml, application/xml, application/json"
                   :state="state"/>
      <b-form-valid-feedback :state="state">Fail(-i) veiksmīgi ielādēti</b-form-valid-feedback>
      <b-form-invalid-feedback :state="state">{{ error }}</b-form-invalid-feedback>
    </b-card-text>

    <b-button variant="primary"
              :disabled="!files || !files.length"
              v-on:click="upload">
      Ielādēt
    </b-button>
  </b-card>
</template>

<script>
  import axios from 'axios'

  export default {
    data() {
      return {
        files: [],
        state: null,
        error: null
      }
    },
    methods: {
      upload() {
        this.state = null;
        const formData = new FormData();
        this.files.forEach(file => formData.append('file', file));
        axios.post('/api/games', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }).then(() => {
          this.files = [];
          this.state = true;
        }).catch(error => {
          this.error = error.response.data;
          this.state = false;
        });
      }
    }
  }
</script>