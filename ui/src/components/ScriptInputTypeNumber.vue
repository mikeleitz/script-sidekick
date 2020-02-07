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
  <b-tab>
    <template v-slot:title>
      Number <b-badge variant="info" v-if="totalValidations > 0 && thisScriptInput.type === 'number'">{{ totalValidations }}</b-badge>
    </template>
    <b-form-group
      label-cols-lg="2"
      label="This value"
      label-size="lg"
      label-class="pt-0"
      class="mb-0">
      <b-form-group>
        <b-form-checkbox v-model="storeState.isNumberSelected" @change="changeTypeSelected($event, 'number')" switch>
          {{ thisScriptInput.type === 'number' ? 'is a number' : 'is not a number' }}
        </b-form-checkbox>
      </b-form-group>
      <b-form-group>
        <b-form-checkbox name="check-button" @change="changeIsValueRequired($event)" :disabled="thisScriptInput.type !== 'number'" switch>
          {{ isValueRequired ? 'is required' : 'is not required' }}
        </b-form-checkbox>
      </b-form-group>
      <b-form-group>
        <b-row align-h="start" align-v="end">
          <b-col cols="3">
            <b-form-checkbox name="check-button" v-model="isLessThan" :disabled="thisScriptInput.type !== 'number'" switch>
              {{ isLessThan ? 'is less than' : 'has no upper bound' }}
            </b-form-checkbox>
            <b-form-checkbox name="check-button" v-model="isEqualForLessCheck" :disabled="thisScriptInput.type !== 'number'" switch>
              {{ isEqualForLessCheck ? 'and equal' : 'but not equal' }}
            </b-form-checkbox>
          </b-col>
          <b-col cols="3">
            <b-form-input :placeholder="isEqualForLessCheck ? 'Less than or equal' : 'Less than'" v-model="lessThanValue" :disabled="thisScriptInput.type !== 'number' || !isLessThan"/>
          </b-col>
        </b-row>
      </b-form-group>
      <b-form-group>
        <b-row align-h="start" align-v="end">
          <b-col cols="3">
            <b-form-checkbox name="check-button" v-model="isGreaterThan" :disabled="thisScriptInput.type !== 'number'" switch>
              {{ isGreaterThan ? 'is greater than' : 'has no lower bound' }}
            </b-form-checkbox>
            <b-form-checkbox name="check-button" v-model="isEqualForGreaterCheck" :disabled="thisScriptInput.type !== 'number'" switch>
              {{ isEqualForGreaterCheck ? 'and equal' : 'but not equal' }}
            </b-form-checkbox>
          </b-col>
          <b-col cols="3">
            <b-form-input :placeholder="isEqualForGreaterCheck ? 'Greater than or equal' : 'Greater than'" v-model="greaterThanValue" :disabled="thisScriptInput.type !== 'number' || !isGreaterThan" />
          </b-col>
        </b-row>
      </b-form-group>
    </b-form-group>
  </b-tab>
</div>
</template>

<script>
import ScriptInputTypeMixin from './mixins/ScriptInputTypeMixin.js'

export default {
  name: 'ScriptInputTypeNumber',
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
