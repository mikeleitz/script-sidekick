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
  <b-form-group
    label-cols-lg="2"
    label="This value is"
    label-size="lg"
    label-class="pt-0"
    class="mb-0">
    <b-form-group>
      <b-form-checkbox v-model="numberTypeSelected" name="check-button" @change="typeSelected" switch>
        {{ numberTypeSelected ? 'A number' : 'Not a number' }}
      </b-form-checkbox>
    </b-form-group>
    <b-form-group>
      <b-form-checkbox v-model="numericRequired" name="check-button" :disabled="!numberTypeSelected" switch>
        {{ numericRequired ? 'Required' : 'Not required' }}
      </b-form-checkbox>
    </b-form-group>
    <b-form-group>
      <b-form-checkbox v-model="signed" name="check-button" :disabled="!numberTypeSelected" switch>
        {{ signed ? 'Signed' : 'Unsigned' }}
      </b-form-checkbox>
    </b-form-group>
    <b-form-group label="at least" v-model="numericAtLeast" label-cols-sm="2" :disabled="!numberTypeSelected">
      <b-form-input/>
    </b-form-group>
    <b-form-group label="at most" v-model="numericAtMost" label-cols-sm="2" :disabled="!numberTypeSelected">
      <b-form-input/>
    </b-form-group>
    <b-form-group label="Defaulted to" v-model="numericDefault" label-cols-sm="2" :disabled="!numberTypeSelected">
      <b-form-input/>
    </b-form-group>
  </b-form-group>
</div>
</template>

<script>
import { store } from '../store.js'
// import { ValidationProvider } from 'vee-validate'

export default {
  name: 'ScriptInputTypeNumber',
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
      dataType: '',
      dataSubtype: '',
      defaultValue: '',
      isValueRequired: false,
      numberTypeSelected: false,
      signed: false,
      numericRequired: false,
      numericDefault: '',
      numericAtMost: '',
      numericAtLeast: ''
    }
  },
  methods: {
    typeSelected: function () {
      this.unselectAll()
    },
    unselectAll: function () {
      this.numberTypeSelected = false
    }
  }
}
</script>

<style scoped>

</style>
