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
<!--
    <div class="form-group">
      <label for="scriptInputNumbers">Numbers</label>
      <input id="scriptInputNumbers" type="text" class="form-control" placeholder="Numbers from store"
             v-model="storeState.name">
    </div>
-->

    <div class="form-group">
      <label for="scriptInputIndex">Index</label>
      <input id="scriptInputIndex" type="text" class="form-control" placeholder="Index" v-model="thisScriptInput.index">
    </div>

    <div class="form-group">
      <label for="scriptInputLongName">Long name</label>
      <input id="scriptInputLongName" type="text" class="form-control" placeholder="Long name"
             v-model="thisScriptInput.longName">
    </div>

    <div class="form-group">
      <label for="scriptInputShortName">Short name</label>
      <input id="scriptInputShortName" type="text" class="form-control" placeholder="Short name"
             v-model="thisScriptInput.shortName"
             maxlength="1">
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
      <input id="scriptInputHelpText" type="text" class="form-control" placeholder="Help text"
             v-model="thisScriptInput.helpText">
    </div>

    <div class="form-group">
      <button class="btn btn-outline-secondary btn-sm" @click="()=>removeInput(thisScriptInput.index)">Remove this input</button>
    </div>

  </div>
</template>

<script>
import { store } from '../store.js'

export default {
  name: 'ScriptInput',
  props: {
    // These properties include the database id (or -1 if not saved) and the index from the array.
    // This will allow the component to lookup the corresponding data element in the global store and
    // display the rest of the values.
    id: {
      required: false,
      type: Number,
      default: -1
    },
    index: {
      required: true,
      type: Number
    }
  },
  created () {
    const valu = store.state.scriptInputs.find(scriptInput => {
      if (typeof scriptInput.index === 'undefined') {
        return { index: this.index }
      } else {
        return scriptInput.index === this.index
      }
    })
    this.thisScriptInput = valu
  },
  data () {
    return {
      storeState: store.state,
      thisScriptInput: {}
    }
  },
  watch: {
    inputVal (val) {
      this.$emit('input', val)
    }
  },
  methods: {
    addInput: function (event) {
    },
    removeInput: function (index) {
      // alert('removing index: ' + this.index)
      // I don't think this is going to work.  Will need to move tracking the index out of the model, or we'll have to update it on every refresh.
      this.storeState.scriptInputs.splice(this.index, 1)
    },
    onLongNameChanged () {
      // this.$store.commit('SET_MEETING_VALUE', this.value)
    }
  }
}
</script>

<style scoped>

</style>
