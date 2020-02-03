<!--
  -  Copyright (c) 2020, Michael Leitz
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
        String <b-badge variant="info" v-if="totalValidations > 0 && thisScriptInput.type === 'string'">{{ totalValidations }}</b-badge>
      </template>
      <b-form-group
        label-cols-lg="2"
        label="This value is"
        label-size="lg"
        label-class="pt-0"
        class="mb-0">
        <b-form-group>
          <b-form-checkbox name="check-button" v-model="storeState.isStringSelected" @change="changeTypeSelected($event, 'string')" switch>
            {{ thisScriptInput.type === 'string' ? 'A string' : 'Not a string' }}
          </b-form-checkbox>
        </b-form-group>
        <b-form-group>
          <b-form-checkbox name="check-button" @change="changeIsValueRequired($event)" :disabled="thisScriptInput.type !== 'string'" switch>
            {{ isValueRequired ? 'Required' : 'Not required' }}
          </b-form-checkbox>
        </b-form-group>
        <b-form-group required="true" :disabled="thisScriptInput.type !== 'string'">
          <b-form-radio value="plain-string" v-model="stringSubtype">A plain string</b-form-radio>
          <b-form-radio value="email" v-model="stringSubtype">An email address</b-form-radio>
          <b-form-radio value="url" v-model="stringSubtype">A url</b-form-radio>
          <b-row no-gutters>
            <b-col cols="3">
              <b-form-radio value="regex" v-model="stringSubtype">Specified via RegEx</b-form-radio>
            </b-col>
            <b-col cols="4">
              <b-form-input v-model="regexValue" :disabled="stringSubtype !== 'regex'" />
            </b-col>
            <b-col cols="5">
            </b-col>
          </b-row>
        </b-form-group>
        <b-form-group label="Defaulted to" label-cols="3">
          <b-row no-gutters>
            <b-col cols="5">
              <b-form-input v-model="thisScriptInput.defaultValue" :disabled="thisScriptInput.type !== 'string'" />
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
import ScriptInputTypeMixin from './mixins/ScriptInputTypeMixin.js'
import { DomainFactory, ValidationTypes } from '../domain/SideScriptDomainFactory'

/*
The fields in the html above invoke local methods and data. These then delegate
to the mixins to create the validations.
 */
export default {
  name: 'ScriptInputTypeString',
  mixins: [ScriptInputTypeMixin],
  components: {
  },
  props: {
    // Script input id for this type component.
    id: {
      required: false,
      type: Number,
      default: -1
    },
    bashOption: {
      required: false,
      type: Object
    }
  },
  data () {
    return {
      stringSubtype: 'plain-string',
      regexValue: ''
    }
  },
  created () {
    this.thisScriptInput = this.bashOption
  },
  watch: {
    stringSubtype: function (val, oldVal) {
      console.log('stringSubtype changed from [' + oldVal + '] to [' + val + '].')
      if (val !== oldVal) {
        // Changed. Need to update validations.
        let oldValidation
        let newValidation

        if (oldVal === 'email') {
          oldValidation = DomainFactory.createBashValidationFromType(ValidationTypes.EMAIL)
        } else if (oldVal === 'url') {
          oldValidation = DomainFactory.createBashValidationFromType(ValidationTypes.URL)
        } else if (oldVal === 'regex') {
          oldValidation = DomainFactory.createBashValidationFromType(ValidationTypes.CUSTOM_REGEX)
        } else if (oldVal === 'plain-string') {
          // I don't think we need to do anything with this.
        }

        if (val === 'email') {
          newValidation = DomainFactory.createBashValidationFromType(ValidationTypes.EMAIL)
        } else if (val === 'url') {
          newValidation = DomainFactory.createBashValidationFromType(ValidationTypes.URL)
        } else if (val === 'regex') {
          newValidation = DomainFactory.createBashValidationFromType(ValidationTypes.CUSTOM_REGEX)
          newValidation.addArgs('regex', this.regexValue)
        } else if (val === 'plain-string') {
          // I don't think we need to do anything with this.
        }

        if (oldValidation !== undefined) {
          this.thisScriptInput.removeValidation(oldValidation)
        }

        if (newValidation !== undefined) {
          this.thisScriptInput.addValidation(newValidation)
        }
      }
    }
  },
  methods: { }
}
</script>

<style scoped>

</style>
