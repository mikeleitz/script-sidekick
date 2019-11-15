<!--
  -  Copyright (c) 2019, Michael Leitz
  -  <p/>
  -  Licensed under the Apache License, Version 2.0 (the "License");
  -  you may not use this file except in compliance with the License.
  -  You may obtain a copy of the License at
  -  <p/>
  -  http://www.apache.org/licenses/LICENSE-2.0
  -  <p/>
  -  Unless required by applicable law or agreed to in writing, software
  -  distributed under the License is distributed on an "AS IS" BASIS,
  -  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  -  See the License for the specific language governing permissions and
  -  limitations under the License.
  -->

<template>

  <div class="script-form">
    <div v-if="loading" class="loading">
      Loading...
    </div>

    <div v-if="generatorStatus !== 'Ready'" class="error">
      The generator is not ready. Can not generate scripts. Status : {{ generatorStatus }}
    </div>

    <main v-if="generatorStatus === 'Ready'" role="main" class="container">
      <ValidationObserver v-slot="{ invalid }">
        <b-form @submit.prevent="onSubmit" autocomplete="off">
          <fieldset class="scheduler-border">
            <legend class="scheduler-border">Script Details</legend>

            <b-form-group label="Name">
              <ValidationProvider name="Name" rules="required|lengthBetween:3,50" v-slot="{ errors }">

                <b-form-input placeholder="Script Name" v-model="scriptForm.scriptName"/>
                <span>{{ errors[0] }}</span>
              </ValidationProvider>
            </b-form-group>

            <b-form-group label="Shell">
              <b-form-radio v-model="scriptForm.shellType" name="scriptShell" value="BASH" checked>Bash</b-form-radio>
            </b-form-group>
          </fieldset>

          <fieldset class="scheduler-border">
            <legend class="scheduler-border">Script Inputs</legend>

            <b-form-group>
              <b-button-group class="mr-5">
                <b-button size="sm" variant="outline-primary" @click="addScriptInput">Add input</b-button>
              </b-button-group>

              <b-button-group>
                <b-button size="sm" variant="outline-secondary" @click="quickAddVerbose"
                          :pressed="isVerboseCommandPushed">Verbose
                </b-button>
                <b-button size="sm" variant="outline-secondary" @click="quickAddQuiet" :pressed="isQuietCommandPushed">
                  Quiet
                </b-button>
              </b-button-group>
            </b-form-group>

            <div>
              <hr>
            </div>

            <div v-for="(scriptInput, index) in scriptForm.scriptInputs"
                 v-bind:item="scriptInput"
                 v-bind:index="index"
                 :key="scriptInput.id">

              <ScriptInput :id="scriptInput.id"/>

              <b-form-group>
                <b-button variant="outline-danger" size="sm" @click="removeScriptInputById(scriptInput.id)">Remove this
                  input
                </b-button>
              </b-form-group>

              <div>
                <hr>
              </div>

            </div>
          </fieldset>

          <b-form-group>
            <b-button type="submit" variant="primary" :disabled="invalid">Create Script</b-button>
          </b-form-group>
        </b-form>
      </ValidationObserver>
    </main>
  </div>
</template>

<script>
// @ is an alias to /src
import ScriptInput from '@/components/ScriptInput.vue'
import axios from 'axios'

import { store } from '../store.js'

import { ValidationObserver, ValidationProvider, extend } from 'vee-validate'
import { required } from 'vee-validate/dist/rules'

extend('required', {
  ...required,
  message: '{_field_} is required'
})

extend('lengthBetween', {
  validate: (value, { min, max }) => {
    const length = value && value.length

    return length >= min && length <= max
  },
  params: ['min', 'max'],
  message: '{_field_}\'s length must be between {min} and {max}'
})

export default {
  name: 'configure-script',
  components: {
    ValidationProvider,
    ValidationObserver,
    ScriptInput
  },
  data () {
    return {
      generatorStatus: 'Not Ready',
      loading: false,
      isVerboseCommandPushed: false,
      verboseCommandId: store.state.verboseCommandId,
      isQuietCommandPushed: false,
      quietCommandId: store.state.quietCommandId,
      scriptForm: store.state.scriptForm
    }
  },
  created () {
    this.getStatus()
  },
  watch: {
    // call again the method if the route changes
    '$route': 'getStatus'
  },
  methods: {
    getStatus: function () {
      this.loading = true
      axios({
        url: 'http://localhost:8080/status',
        method: 'GET'
      }).then(result => {
        this.generatorStatus = result.data.status
        this.loading = false
      }, error => {
        console.error(error)
        this.loading = false
      })
    },
    onSubmit: function () {
      axios({
        url: `http://localhost:8080/`,
        method: 'POST',
        data: this.scriptForm,
        responseType: 'blob'
      }).then(response => {
        const url = window.URL.createObjectURL(new Blob([response.data]))
        const link = document.createElement('a')
        link.href = url
        link.setAttribute('download', this.scriptForm.scriptName + '.sh')
        document.body.appendChild(link)
        link.click()
      }).catch(e => { this.errors.push(e) })
    },
    quickAddVerbose: function () {
      if (!this.isVerboseCommandPushed) {
        let newScriptId = store.getNextScriptId()
        this.verboseCommandId = newScriptId
        this.scriptForm.scriptInputs.unshift({
          id: newScriptId,
          longName: 'verbose',
          shortName: 'v',
          decree: false,
          helpText: 'Maximum script output.'
        })
        this.isVerboseCommandPushed = true
      } else {
        this.removeScriptInputById(this.verboseCommandId)
        this.isVerboseCommandPushed = false
      }
    },
    quickAddQuiet: function () {
      if (!this.isQuietCommandPushed) {
        let newScriptId = store.getNextScriptId()
        this.quietCommandId = newScriptId
        this.scriptForm.scriptInputs.unshift({
          id: newScriptId,
          longName: 'quiet',
          shortName: 'q',
          decree: false,
          helpText: 'Quiet mode.  No output.'
        })
        this.isQuietCommandPushed = true
      } else {
        this.removeScriptInputById(this.quietCommandId)
        this.isQuietCommandPushed = false
      }
    },
    addScriptInput: function () {
      let newScriptId = store.getNextScriptId()
      this.scriptForm.scriptInputs.unshift({ id: newScriptId, longName: '', shortName: '', decree: false, helpText: '' })
    },
    removeScriptInputById: function (id) {
      let index = this.scriptForm.scriptInputs.findIndex(scriptInput => scriptInput.id === id)
      this.$delete(this.scriptForm.scriptInputs, index)

      if (id === this.quietCommandId) {
        this.isQuietCommandPushed = false
      } else if (id === this.verboseCommandId) {
        this.isVerboseCommandPushed = false
      }
    }
  }
}
</script>

<style>
  .script-form {
    text-align: left;
  }

  fieldset.scheduler-border {
    border: 1px groove #ddd !important;
    padding: 0 1.4em 1.4em 1.4em !important;
    margin: 0 0 1.5em 0 !important;
    -webkit-box-shadow: 0px 0px 0px 0px #000;
    box-shadow: 0px 0px 0px 0px #000;
  }

  legend.scheduler-border {
    font-size: 1.2em !important;
    font-weight: bold !important;
    text-align: left !important;
    width: auto;
    padding: 0 10px;
    border-bottom: none;
  }
</style>
