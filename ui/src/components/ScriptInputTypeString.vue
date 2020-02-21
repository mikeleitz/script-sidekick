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
    <div v-show="storeState.isStringSelected">
      <!-- All validations. Show only if the user selected this type. -->
      <b-form-group>
        <b-form-checkbox name="check-button" v-model="storeState.isValueRequired" @change="changeIsValueRequired($event)" :disabled="thisScriptInput.type !== 'string'" switch>
          {{ storeState.isValueRequired ? 'is required' : 'is not required' }}
        </b-form-checkbox>
      </b-form-group>
      <b-form-group required="true" :disabled="thisScriptInput.type !== 'string'">
        <b-form-radio value="-1" v-model="stringSubtype">has no restrictions</b-form-radio>
        <b-form-radio value="21" v-model="stringSubtype">is an alpha-numeric</b-form-radio>
        <b-form-radio value="10" v-model="stringSubtype">is an email address</b-form-radio>
        <b-form-radio value="9" v-model="stringSubtype">is a url</b-form-radio>
      </b-form-group>
    </div>

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
    }
  },
  created () {
    this.thisScriptInput = this.bashOption
  },
  watch: { },
  methods: { }
}
</script>

<style scoped>

</style>
