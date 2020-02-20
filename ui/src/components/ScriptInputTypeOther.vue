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
      <b-form-group>
        <b-form-checkbox name="check-button" v-model="storeState.isOtherSelected" @change="changeTypeSelected($event, 'other')" switch>
          {{ thisScriptInput.type === 'other' ? 'is some other type' : 'is not some other type' }}
        </b-form-checkbox>
      </b-form-group>
      <b-form-checkbox name="check-button" v-model="storeState.isValueRequired" @change="changeIsValueRequired($event)" :disabled="thisScriptInput.type !== 'other'" switch>
        {{ storeState.isValueRequired ? 'is required' : 'is not required' }}
      </b-form-checkbox>
    </b-form-group>
    <b-form-group>
      <b-row align-v="center">
        <b-col cols="4">
          <b-form-checkbox name="check-button" v-model="storeState.isRegexValueSelected" :disabled="thisScriptInput.type !== 'other'" switch>
            {{ storeState.isRegexValueSelected ? 'is validated by regex' : 'is not validated by regex' }}
          </b-form-checkbox>
        </b-col>
        <b-col cols="4">
          <b-form-input placeholder="Regex value" v-model="storeState.regexValue" :disabled="thisScriptInput.type !== 'other' || !storeState.isRegexValueSelected" />
        </b-col>
      </b-row>
    </b-form-group>
  </b-form-group>
</template>

<script>
import ScriptInputTypeMixin from './mixins/ScriptInputTypeMixin.js'

export default {
  name: 'ScriptInputTypeOther',
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
  created () {
    this.thisScriptInput = this.bashOption
  },
  watch: { },
  methods: { }
}
</script>

<style scoped>

</style>
