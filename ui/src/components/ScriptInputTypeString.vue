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
  <b-form-group
    label-cols-lg="2"
    label="This value"
    label-size="lg"
    label-class="pt-0"
    class="mb-0">
    <b-form-group>
      <b-form-checkbox name="check-button" v-model="storeState.isStringSelected" @change="changeTypeSelected($event, 'string')" switch>
        {{ thisScriptInput.type === 'string' ? 'is a string' : 'is not a string' }}
      </b-form-checkbox>
    </b-form-group>
    <b-form-group>
      <b-form-checkbox name="check-button" v-model="storeState.isValueRequired" @change="changeIsValueRequired($event)" :disabled="thisScriptInput.type !== 'string'" switch>
        {{ storeState.isValueRequired ? 'is required' : 'is not required' }}
      </b-form-checkbox>
    </b-form-group>
    <b-form-group required="true" :disabled="thisScriptInput.type !== 'string'">
      <b-form-radio value="plain-string" v-model="stringSubtype">has no restrictions</b-form-radio>
      <b-form-radio value="alpha-numeric" v-model="stringSubtype">is an alpha-numeric</b-form-radio>
      <b-form-radio value="email" v-model="stringSubtype">is an email address</b-form-radio>
      <b-form-radio value="url" v-model="stringSubtype">is a url</b-form-radio>
    </b-form-group>
  </b-form-group>
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
        } else if (oldVal === 'alpha-numeric') {
          oldValidation = DomainFactory.createBashValidationFromType(ValidationTypes.ALPHA_NUMERIC)
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
        } else if (val === 'alpha-numeric') {
          newValidation = DomainFactory.createBashValidationFromType(ValidationTypes.ALPHA_NUMERIC)
        } else if (val === 'plain-string') {
          // I don't think we need to do anything with this.
        }

        if (oldValidation !== undefined) {
          console.log('Since the subtype has changed, removing the old validation [' + oldValidation.toJson() + '].')
          this.thisScriptInput.removeValidation(oldValidation)
        } else {
          console.log('Not removing the old validation for subtype [' + oldVal + '] since there isn\'t a corresponding validation for it.')
        }

        if (newValidation !== undefined) {
          console.log('Since the subtype has changed, adding the new validation [' + newValidation.toJson() + '].')
          this.thisScriptInput.addValidation(newValidation)
        } else {
          console.log('Not adding the new validation for subtype [' + oldVal + '] since there isn\'t a corresponding validation for it.')
        }
      }
    }
  },
  methods: { }
}
</script>

<style scoped>

</style>
