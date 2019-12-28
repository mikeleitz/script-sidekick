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
    <b-form-group label="Data type"
                  label-for="input-type-selection">
      <b-card no-body>
        <b-tabs pills card vertical>
          <b-tab title="String" active><b-card-text>Tab contents 1</b-card-text></b-tab>
          <b-tab title="Number"><b-card-text>I'm the second tab</b-card-text></b-tab>
          <b-tab title="Boolean"><b-card-text>I'm a disabled tab!</b-card-text></b-tab>
          <b-tab title="Other"><b-card-text>I'm a disabled tab!</b-card-text></b-tab>
        </b-tabs>
      </b-card>
    </b-form-group>
  </div>
</template>

<script>
import { store } from '../store.js'
// import { ValidationProvider } from 'vee-validate'

export default {
  name: 'ScriptInputType',
  components: {
    // ValidationProvider
  },
  props: {
    // Script input id for this type component.
    id: {
      required: false,
      type: Number,
      default: -1
    }
  },
  created () {
    this.thisScriptInput = store.getScriptInputById(this.id)
  },
  data () {
    return {
      thisScriptInput: {},
      isNumberPushed: false,
      isStringPushed: true,
      isBooleanPushed: false,
      isOtherPushed: false
    }
  },
  methods: {
    numberPressed: function () {
      this.unselectAll()
      this.isNumberPushed = true
    },
    stringPressed: function () {
      this.unselectAll()
      this.isStringPushed = true
    },
    booleanPressed: function () {
      this.unselectAll()
      this.isBooleanPushed = true
    },
    otherPressed: function () {
      this.unselectAll()
      this.isOtherPushed = true
    },
    unselectAll: function () {
      this.isNumberPushed = false
      this.isStringPushed = false
      this.isBooleanPushed = false
      this.isOtherPushed = false
    }
  }
}
</script>

<style scoped>

</style>
