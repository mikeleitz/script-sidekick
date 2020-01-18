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
          <b-form-checkbox v-model="isValueRequired" name="check-button" :disabled="!stringTypeSelected" switch>
            {{ isValueRequired ? 'Required' : 'Not required' }}
          </b-form-checkbox>
        </b-form-group>

        <b-form-group required="true" :disabled="!stringTypeSelected">
          <b-form-radio value="plain-string" v-model="dataSubtype">A plain string</b-form-radio>
          <b-form-radio value="email" v-model="dataSubtype">An email address</b-form-radio>
          <b-form-radio value="url" v-model="dataSubtype">A url</b-form-radio>
          <b-row no-gutters>
            <b-col cols="3">
              <b-form-radio value="regex" v-model="dataSubtype">Specified via RegEx</b-form-radio>
            </b-col>
            <b-col cols="4">
              <b-form-input v-model="regexValue" :disabled="dataSubtype !== 'regex'" />
            </b-col>
            <b-col cols="5">
            </b-col>
          </b-row>
        </b-form-group>

        <b-form-group label="Defaulted to" label-cols="3">
          <b-row no-gutters>
            <b-col cols="5">
              <b-form-input v-model="defaultValue" :disabled="!stringTypeSelected" />
            </b-col>
            <b-col cols="4">
            </b-col>
          </b-row>
        </b-form-group>
      </b-form-group>
    </b-tab>
  </div>
</template>

<script>
import { store } from '../store.js'
import ScriptInputTypeMixin from './ScriptInputTypeMixin.js'

// import { ValidationProvider } from 'vee-validate'

export default {
  name: 'ScriptInputTypeString',
  mixins: [ScriptInputTypeMixin],
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
      regexValue: '',
      defaultValue: '',
      totalValidations: 0
    }
  },
  watch: {
    defaultValue: function (val, oldVal) {
      if (oldVal.length === 0 && val.length > 0) {
        this.totalValidations = this.totalValidations + 1
      } else if (oldVal.length > 0 && val.length === 0) {
        this.totalValidations = this.totalValidations - 1
      }

      console.log('New default value: [' + val + '].')
    },
    isValueRequired: function (val, oldVal) {
      if (val !== oldVal) {
        if (val) {
          this.totalValidations = this.totalValidations + 1
        } else {
          this.totalValidations = this.totalValidations - 1
        }
      }
    },
    dataSubtype: function (val, oldVal) {
      if (val !== oldVal) {
        if (val === 'plain-string') {
          this.totalValidations = this.totalValidations - 1
        } else if (oldVal === 'plain-string') {
          this.totalValidations = this.totalValidations + 1
        }
      }

      console.log('New selected subtype: [' + val + '].')
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
    }
  }
}
</script>

<style scoped>

</style>
