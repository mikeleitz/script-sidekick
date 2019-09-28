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
  <div>
<!--    Users don't need to see the script input ids.-->

<!--
    <div class="form-group">
      <label for="scriptInputIndex">Id</label>
      <input id="scriptInputIndex"
             type="text"
             class="form-control"
             placeholder="Id"
             v-model="thisScriptInput.id">
    </div>
-->
    <div class="form-group">
      <ValidationProvider name="Long name" rules="required" v-slot="{ errors }">

        <label for="scriptInputLongName">Long name</label>
        <input id="scriptInputLongName"
               type="text"
               class="form-control"
               placeholder="Long name"
               v-model="thisScriptInput.longName">
        <span>{{ errors[0] }}</span>
      </ValidationProvider>
    </div>

    <div class="form-group">
      <ValidationProvider name="Short name" rules="max:1" v-slot="{ errors }">

        <label for="scriptInputShortName">Short name</label>
        <input id="scriptInputShortName"
               type="text"
               class="form-control"
               placeholder="Short name"
               v-model="thisScriptInput.shortName"
               maxlength="1">
        <span>{{ errors[0] }}</span>
      </ValidationProvider>
    </div>

    <div class="form-group">
      <label for="inputRequired">Decree</label>

      <div id="inputRequired" class="input-group mb-3">
        <toggle-button :width="110"
                       :height="35"
                       :margin="10"
                       :font-size="14"
                       :labels="{checked: 'Required', unchecked: 'Optional'}"
                       v-model="thisScriptInput.decree"/>

      </div>
    </div>

    <div class="form-group">
      <label for="scriptInputHelpText">Help text</label>
      <input id="scriptInputHelpText"
             type="text"
             class="form-control"
             placeholder="Help text"
             v-model="thisScriptInput.helpText">
    </div>

  </div>
</template>

<script>
import { store } from '../store.js'

import { ValidationProvider, extend } from 'vee-validate'
import { required } from 'vee-validate/dist/rules'

extend('required', required)
extend('max', {
  validate: (value, { max }) => {
    const length = value && value.length

    return length <= max
  },
  params: ['max'],
  message: '{_field_} length must not be more than {max}'
})

export default {
  name: 'ScriptInput',
  components: {
    ValidationProvider
  },
  props: {
    // These properties include the database id (or -1 if not saved) and the index from the array.
    // This will allow the component to lookup the corresponding data element in the global store and
    // display the rest of the values.
    id: {
      required: false,
      type: Number,
      default: -1
    }
  },
  created () {
    this.thisScriptInput = store.state.scriptInputs.find(scriptInput => {
      return scriptInput.id === this.id
    })
  },
  data () {
    return {
      storeState: store.state,
      thisScriptInput: {}
    }
  },
  methods: {
  }
}
</script>

<style scoped>

</style>
