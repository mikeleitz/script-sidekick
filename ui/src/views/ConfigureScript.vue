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
      <form @submit.prevent="onSubmit">
        <fieldset class="scheduler-border">
          <legend class="scheduler-border">Script Details</legend>

          <div class="form-group">
            <label for="scriptName">Name</label>

            <input type="text" class="form-control" id="scriptName" placeholder="Script Name">
          </div>

          <div class="form-group">
            <label for="scriptShell">Shell</label>

            <div id="scriptShell">
              <div class="form-check">
                <input class="form-check-input" type="radio" name="scriptShell" id="bashShell"
                       value="bash" checked>
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
                <b-button :pressed.sync="verboseCommandAdded" variant="outline-primary" >Verbose</b-button>
<!--                <p>Verbose State: <strong>{{ verboseCommandAdded }}</strong></p>-->
                <b-button :pressed.sync="quietCommandAdded" variant="outline-primary">Quiet</b-button>
<!--                <p>Quiet State: <strong>{{ quietCommandAdded }}</strong></p>-->
              </div>
            </div>
          </div>

          <div>
            <hr>
          </div>

          <div v-for="(scriptInput, index) in storeState.scriptInputs" :key="index">
            <ScriptInput :id="-1" :index="index" />
            <div>
              <hr>
            </div>
          </div>

          <div class="form-group">
            <button class="btn btn-outline-secondary btn-sm" @click="addScriptInput">Add another input</button>
            <button class="btn btn-outline-secondary btn-sm" @click="removeScriptInput">Remove last input</button>
            <button class="btn btn-outline-secondary btn-sm" @click="removeAllScriptInputs">Remove all inputs</button>
          </div>

        </fieldset>

        <fieldset class="scheduler-border">
          <legend class="scheduler-border">Two-way binding example</legend>
<!--

          <div class="form-group">
            <label for="scriptInputNumbers">Numbers from component</label>
            <input id="scriptInputNumbers" type="text" class="form-control" placeholder="Numbers from component" v-model="storeState.name">
          </div>
-->

          <div class="form-group">
            <p>
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

export default {
  name: 'configure-script',
  data () {
    return {
      verboseCommandAdded: false,
      quietCommandAdded: false,
      storeState: store.state
    }
  },
  components: {
    ScriptInput
  },
  methods: {
    onSubmit: function () {

    },
    addScriptInput: function () {
      let newIndex = this.storeState.scriptInputs.length
      this.storeState.scriptInputs.push({ index: newIndex, longName: '', shortName: '', decree: false, helpText: '' })
    },
    removeScriptInput: function () {
      this.storeState.scriptInputs.pop()
    },
    removeAllScriptInputs: function () {
      this.storeState.scriptInputs.splice(0, this.storeState.scriptInputs.length)
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