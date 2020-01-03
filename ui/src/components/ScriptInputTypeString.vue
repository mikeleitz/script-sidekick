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
    <b-tab active>
      <template v-slot:title>
        String <b-badge variant="info" v-if="totalValidations > 0">{{ totalValidations }}</b-badge>
      </template>
      <b-form-group
        label-cols-lg="2"
        label="This value is"
        label-size="lg"
        label-class="pt-0"
        class="mb-0">
        <b-form-group>
          <b-form-checkbox name="check-button" v-model="stringTypeSelected" @change="typeSelected" switch>
            {{ stringTypeSelected ? 'A string' : 'Not a string' }}
          </b-form-checkbox>
        </b-form-group>
        <b-form-group>
          <b-form-checkbox v-model="stringRequired" name="check-button" :disabled="!stringTypeSelected" switch>
            {{ stringRequired ? 'Required' : 'Not required' }}
          </b-form-checkbox>
        </b-form-group>

        <b-form-group required="true" :disabled="!stringTypeSelected">
          <b-form-radio v-model="selected" value="plain-string">A plain string</b-form-radio>
          <b-form-radio v-model="selected" value="email">An email address</b-form-radio>
          <b-form-radio v-model="selected" value="url">A url</b-form-radio>
          <b-form-radio v-model="selected" value="regex">Specified via RegEx</b-form-radio>
        </b-form-group>

        <b-form-group label="Defaulted to" v-model="stringDefault" label-cols-sm="2" :disabled="!stringTypeSelected">
          <b-form-input/>
        </b-form-group>
        <b-form-row>
          <b-col>&nbsp;</b-col>
        </b-form-row>
      </b-form-group>
    </b-tab>
  </div>
</template>

<script>
import { store } from '../store.js'

// import { ValidationProvider } from 'vee-validate'

export default {
  name: 'ScriptInputTypeString',
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
      selected: 'plain-string',
      stringTypeSelected: false,
      stringRequired: false,
      stringDefault: '',
      totalValidations: 0
    }
  },
  methods: {
    typeSelected: function () {
      this.unselectAll()
    },
    unselectAll: function () {
      this.stringTypeSelected = false
    }
  }
}
</script>

<style scoped>

</style>
