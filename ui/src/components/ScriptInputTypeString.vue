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
          <b-form-checkbox v-model="isValueRequired" name="check-button" :disabled="!stringTypeSelected" @change="requiredSelected" switch>
            {{ isValueRequired ? 'Required' : 'Not required' }}
          </b-form-checkbox>
        </b-form-group>

        <b-form-group required="true" :disabled="!stringTypeSelected">
          <b-form-radio value="plain-string" v-model="dataSubtype" @change="subtypeSelected">A plain string</b-form-radio>
          <b-form-radio value="email" v-model="dataSubtype" @change="subtypeSelected">An email address</b-form-radio>
          <b-form-radio value="url" v-model="dataSubtype" @change="subtypeSelected">A url</b-form-radio>
          <b-form-radio value="regex" v-model="dataSubtype" @change="subtypeSelected">Specified via RegEx</b-form-radio>
        </b-form-group>

        <b-form-group label="Defaulted to" v-model="defaultValue" label-cols-sm="2" :disabled="!stringTypeSelected">
          <b-form-input/>
        </b-form-group>
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
      dataType: 'string',
      dataSubtype: 'plain-string',
      stringTypeSelected: false,
      isValueRequired: false,
      defaultValue: '',
      totalValidations: 0
    }
  },
  methods: {
    typeSelected: function (stringTypeCheckboxValue) {
      if (stringTypeCheckboxValue) {
        this.dataType = 'String'
        this.totalValidations = 1
      } else {
        this.dataType = ''
        this.totalValidations = 0
      }
    },
    requiredSelected: function (requiredCheckboxValue) {
      if ((this.isValueRequired === true && requiredCheckboxValue) ||
        (this.isValueRequired === false && !requiredCheckboxValue)
      ) {
        // No change.
      } else if (this.isValueRequired === false && requiredCheckboxValue) {
        this.isValueRequired = true
        this.totalValidations = this.totalValidations + 1
      } else {
        // this.isValueRequired === true && !requiredCheckboxValue
        this.isValueRequired = false
        this.totalValidations = this.totalValidations - 1
      }
    },
    subtypeSelected: function (selectedSubtype) {
      if ((this.dataSubtype === 'plain-string') && (selectedSubtype !== 'plain-string')) {
        // Change from plain-string to something else.
        this.totalValidations = this.totalValidations + 1
      } else if ((this.dataSubtype !== 'plain-string') && (selectedSubtype === 'plain-string')) {
        // Change from non-plain-string to plain-string.
        this.totalValidations = this.totalValidations - 1
      } else {
        // No change to the number of validations.
      }

      this.dataSubtype = selectedSubtype
      console.log(selectedSubtype)
    },
    defaultEntered: function () {
    }
  }
}
</script>

<style scoped>

</style>
