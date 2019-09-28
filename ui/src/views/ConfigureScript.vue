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
      <form @submit.prevent="onSubmit" autocomplete="off">
        <fieldset class="scheduler-border">
          <legend class="scheduler-border">Script Details</legend>

          <div class="form-group">
            <ValidationProvider name="Name" rules="required|lengthBetween:3,50" v-slot="{ errors }">

              <label for="scriptName">Name</label>

              <input type="text"
                     class="form-control"
                     id="scriptName"
                     placeholder="Script Name"
                     v-model="storeState.scriptName">
              <span>{{ errors[0] }}</span>
            </ValidationProvider>
          </div>

          <div class="form-group">
            <label for="scriptShell">Shell</label>

            <div id="scriptShell">
              <div class="form-check">
                <input class="form-check-input"
                       type="radio"
                       name="scriptShell"
                       id="bashShell"
                       value="bash"
                       v-model="storeState.shellType"
                       checked>
                <label class="form-check-label" for="bashShell">
                  Bash
                </label>
              </div>
            </div>
          </div>
        </fieldset>

        <fieldset class="scheduler-border">
          <legend class="scheduler-border">Script Inputs</legend>

          <div class="form-group">
            <label for="addCommonInputButtons">Common Inputs</label>

            <div id="addCommonInputButtons" class="input-group mb-3">
              <div class="btn-group btn-group-toggle" data-toggle="buttons">
                <b-button variant="btn btn-outline-secondary btn-sm" @click="quickAddVerbose" :pressed="isVerboseCommandPushed">Verbose</b-button>
                <b-button variant="btn btn-outline-secondary btn-sm" @click="quickAddQuiet" :pressed="isQuietCommandPushed">Quiet</b-button>
              </div>
            </div>
          </div>

          <div>
            <hr>
          </div>

          <div class="form-group">
            <button class="btn btn-outline-secondary btn-sm" @click="addScriptInput">Add input</button>
          </div>

          <div v-for="(scriptInput, index) in storeState.scriptInputs"
               v-bind:item="scriptInput"
               v-bind:index="index"
               :key="scriptInput.id">
            <ScriptInput :id="scriptInput.id" />

            <div class="form-group">
              <button class="btn btn-outline-secondary btn-sm" @click="removeScriptInputById(scriptInput.id)">Remove this input</button>
            </div>

            <div>
              <hr>
            </div>
          </div>

        </fieldset>

        <fieldset class="scheduler-border">
          <legend class="scheduler-border">Two-way binding example</legend>

          <div class="form-group">
            <p>Script Name: {{ storeState.scriptName }}</p>
            <p>Shell Type: {{ storeState.shellType }}</p>
            <p>All Inputs:
              {{ storeState.scriptInputs }}
            </p>
          </div>
        </fieldset>
      </form>
    </main>
  </div>
</template>

<script>
// @ is an alias to /src
import ScriptInput from '@/components/ScriptInput.vue'

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
      isVerboseCommandPushed: false,
      isQuietCommandPushed: false,
      storeState: store.state
    }
  },
  methods: {
    onSubmit: function () {
    },
    quickAddVerbose: function () {
      if (!this.isVerboseCommandPushed) {
        let newScriptId = this.getNextScriptId()
        this.storeState.verboseCommandId = newScriptId
        this.storeState.scriptInputs.unshift({
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
        this.storeState.scriptInputs.unshift({
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
      this.storeState.scriptInputs.unshift({ id: newScriptId, longName: '', shortName: '', decree: false, helpText: '' })
    },
    removeScriptInputById: function (id) {
      let index = this.storeState.scriptInputs.findIndex(scriptInput => scriptInput.id === id)
      this.$delete(this.storeState.scriptInputs, index)

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
