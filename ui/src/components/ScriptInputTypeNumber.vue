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
      label="This value is"
      label-size="lg"
      label-class="pt-0"
      class="mb-0">
      <b-form-group>
        <b-form-checkbox @change="changeTypeSelected($event, 'number')" switch>
          {{ thisScriptInput.type === 'number' ? 'A number' : 'Not a number' }}
        </b-form-checkbox>
      </b-form-group>
      <b-form-group>
        <b-form-checkbox name="check-button" @change="changeIsValueRequired($event)" :disabled="thisScriptInput.type !== 'number'" switch>
          {{ isValueRequired ? 'Required' : 'Not required' }}
        </b-form-checkbox>
      </b-form-group>
      <b-form-group label="Defaulted to" label-cols-sm="2">
        <b-form-input v-model="thisScriptInput.type.defaultValue" :disabled="thisScriptInput.type !== 'numeric'" />
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
