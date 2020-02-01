<!--
  -  Copyright (c) 2020, Michael Leitz
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

                <b-form-input placeholder="Script Name" v-model="scriptInProgress.scriptName"/>
                <span>{{ errors[0] }}</span>
              </ValidationProvider>
            </b-form-group>

            <b-form-group label="Shell">
              <b-form-radio v-model="scriptInProgress.shellType" name="scriptShell" value="BASH" checked>Bash</b-form-radio>
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

            <div v-for="(bashOption, index) in scriptInProgress.bashOptions"
                 v-bind:item="bashOption"
                 v-bind:index="index"
                 v-bind:key="bashOption.id" >

              <ScriptInput :id="bashOption.id" :bashOption="bashOption"/>

              <b-form-group>
                <b-button variant="outline-danger" size="sm" @click="removeScriptInputById(bashOption.id)">Remove this
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
import { DomainFactory } from '../domain/SideScriptDomainFactory.js'

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
      scriptInProgress: store.state.scriptInProgress
    }
  },
  created () {
    this.getStatus()
    let scriptId = store.getNextScriptId()
    // AFAICT we have to create the initial BashScript domain object in the store.js
    // We set the id when the page is initially created.
    this.scriptInProgress.id = scriptId
  },
  watch: {
    // call the status method again if the route changes
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
        data: this.scriptInProgress.toJson(),
        responseType: 'blob'
      }).then(response => {
        const url = window.URL.createObjectURL(new Blob([response.data]))
        const link = document.createElement('a')
        link.href = url
        link.setAttribute('download', this.scriptInProgress.scriptName + '.sh')
        document.body.appendChild(link)
        link.click()
      }).catch(e => { this.errors.push(e) })
    },
    quickAddVerbose: function () {
      if (!this.isVerboseCommandPushed) {
        this.verboseCommandId = store.getNextScriptId()

        let verboseOption = DomainFactory.createBashOption(this.verboseCommandId, 'verbose', 'v', 'Maximum script logging.')

        this.scriptInProgress.addOption(verboseOption)
        this.isVerboseCommandPushed = true
      } else {
        let verboseOption = this.scriptInProgress.getOptionById(this.verboseCommandId)
        this.scriptInProgress.removeOption(verboseOption)

        this.isVerboseCommandPushed = false
      }
    },
    quickAddQuiet: function () {
      if (!this.isQuietCommandPushed) {
        this.quietCommandId = store.getNextScriptId()

        let quietOption = DomainFactory.createBashOption(this.quietCommandId, 'quiet', 'q', 'Quiet mode.  No output.')

        this.scriptInProgress.addOption(quietOption)

        this.isQuietCommandPushed = true
      } else {
        let quietOption = this.scriptInProgress.getOptionById(this.quietCommandId)
        this.scriptInProgress.removeOption(quietOption)

        this.isQuietCommandPushed = false
      }
    },
    addScriptInput: function () {
      let newScriptId = store.getNextScriptId()
      let newBashOption = DomainFactory.createBashOption(newScriptId)

      this.scriptInProgress.addOption(newBashOption)
    },
    removeScriptInputById: function (id) {
      let optionToRemove = this.scriptInProgress.getOptionById(id)
      this.scriptInProgress.removeOption(optionToRemove)

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
