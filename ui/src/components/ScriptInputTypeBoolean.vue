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
  <b-tab>
    <template v-slot:title>
      Boolean <b-badge variant="info" v-if="totalValidations > 0">{{ totalValidations }}</b-badge>
    </template>
    <b-form-group
      label-cols-lg="2"
      label="This value is"
      label-size="lg"
      label-class="pt-0"
      class="mb-0">
      <b-form-group>
        <b-form-checkbox v-model="booleanTypeSelected" name="check-button" @change="typeSelected" switch>
          {{ booleanTypeSelected ? 'A boolean' : 'Not a boolean' }}
        </b-form-checkbox>
      </b-form-group>
      <b-form-group>
        <b-form-checkbox v-model="isValueRequired" name="check-button" :disabled="!booleanTypeSelected" switch>
          {{ isValueRequired ? 'Required' : 'Not required' }}
        </b-form-checkbox>
      </b-form-group>
      <b-form-group label="Defaulted to" label-cols-sm="2" >
        <b-form-input v-model="defaultValue" :disabled="!booleanTypeSelected"/>
      </b-form-group>
    </b-form-group>
  </b-tab>
</div>
</template>

<script>
import { store } from '../store.js'

// import { ValidationProvider } from 'vee-validate'

export default {
  name: 'ScriptInputTypeBoolean',
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
      dataType: 'boolean',
      dataSubtype: '',
      booleanTypeSelected: false,
      isValueRequired: false,
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
    }
  },
  methods: {
    typeSelected: function (booleanTypeCheckboxValue) {
      if (booleanTypeCheckboxValue) {
        this.dataType = 'boolean'
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
