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
    <main role="main" class="container">
      <b-form @submit.prevent="onSubmit" autocomplete="off">
        <fieldset class="scheduler-border">
          <legend class="scheduler-border">Script Details</legend>

          <b-form-group label="Name">
            <ValidationProvider name="Name" rules="required|lengthBetween:3,50" v-slot="{ errors }">

              <b-form-input placeholder="Script Name" v-model="scriptForm.scriptName" />
              <span>{{ errors[0] }}</span>
            </ValidationProvider>
          </b-form-group>

          <b-form-group label="Shell">
            <b-form-radio v-model="scriptForm.shellType" name="scriptShell" value="bash" checked>Bash</b-form-radio>
          </b-form-group>
        </fieldset>

        <fieldset class="scheduler-border">
          <legend class="scheduler-border">Script Inputs</legend>

          <b-form-group>
              <b-button-group class="mr-5">
                <b-button size="sm" variant="outline-primary" @click="addScriptInput">Add input</b-button>
              </b-button-group>

              <b-button-group>
                <b-button size="sm" variant="outline-secondary" @click="quickAddVerbose" :pressed="isVerboseCommandPushed">Verbose</b-button>
                <b-button size="sm" variant="outline-secondary" @click="quickAddQuiet" :pressed="isQuietCommandPushed">Quiet</b-button>
              </b-button-group>
          </b-form-group>

          <div>
            <hr>
          </div>

          <div v-for="(scriptInput, index) in scriptForm.scriptInputs"
               v-bind:item="scriptInput"
               v-bind:index="index"
               :key="scriptInput.id">

            <ScriptInput :id="scriptInput.id" />

            <b-form-group>
              <b-button variant="outline-danger" size="sm" @click="removeScriptInputById(scriptInput.id)">Remove this input</b-button>
            </b-form-group>

            <div>
              <hr>
            </div>

          </div>
        </fieldset>

        <fieldset class="scheduler-border">
          <legend class="scheduler-border">Two-way binding example</legend>

          <b-form-group>
            <p>Script Name: {{ scriptForm.scriptName }}</p>
            <p>Shell Type: {{ scriptForm.shellType }}</p>
            <p>All Inputs:
              {{ scriptForm.scriptInputs }}
            </p>
            <p>Server Message: {{ serverMessage }}</p>
          </b-form-group>
        </fieldset>

        <b-form-group>
          <b-button type="submit" variant="primary">Create Script</b-button>
        </b-form-group>
      </b-form>
    </main>
  </div>
</template>

<script>
// @ is an alias to /src
import ScriptInput from '@/components/ScriptInput.vue'
import axios from 'axios'

import { store } from '../store.js'

import { ValidationProvider, extend } from 'vee-validate'
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
    ScriptInput
  },
  data () {
    return {
      serverMessage: '-1',
      isVerboseCommandPushed: false,
      isQuietCommandPushed: false,
      storeState: store.state,
      scriptForm: store.state.scriptForm
    }
  },
  mounted () {
    axios({ method: 'GET', 'url': 'http://localhost:8080/' }).then(result => {
      this.serverMessage = result.data.message
    }, error => {
      console.error(error)
    })
  },
  methods: {
    onSubmit: function () {
      alert('Submitting!')
    },
    quickAddVerbose: function () {
      if (!this.isVerboseCommandPushed) {
        let newScriptId = this.getNextScriptId()
        this.storeState.verboseCommandId = newScriptId
        this.scriptForm.scriptInputs.unshift({
          id: newScriptId,
          longName: 'verbose',
          shortName: 'v',
          decree: false,
          helpText: 'Maximum script output.'
        })
        this.isVerboseCommandPushed = true
      } else {
        this.removeScriptInputById(this.storeState.verboseCommandId)
        this.isVerboseCommandPushed = false
      }
    },
    quickAddQuiet: function () {
      if (!this.isQuietCommandPushed) {
        let newScriptId = this.getNextScriptId()
        this.storeState.quietCommandId = newScriptId
        this.scriptForm.scriptInputs.unshift({
          id: newScriptId,
          longName: 'quiet',
          shortName: 'q',
          decree: false,
          helpText: 'Quiet mode.  No output.'
        })
        this.isQuietCommandPushed = true
      } else {
        this.removeScriptInputById(this.storeState.quietCommandId)
        this.isQuietCommandPushed = false
      }
    },
    getNextScriptId: function () {
      let newScriptId = this.storeState.nextTempId
      this.storeState.nextTempId -= 1
      return newScriptId
    },
    addScriptInput: function () {
      let newScriptId = this.getNextScriptId()
      this.scriptForm.scriptInputs.unshift({ id: newScriptId, longName: '', shortName: '', decree: false, helpText: '' })
    },
    removeScriptInputById: function (id) {
      let index = this.scriptForm.scriptInputs.findIndex(scriptInput => scriptInput.id === id)
      this.$delete(this.scriptForm.scriptInputs, index)

      if (id === this.storeState.quietCommandId) {
        this.isQuietCommandPushed = false
      } else if (id === this.storeState.verboseCommandId) {
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
