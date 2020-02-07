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
      <b-form-checkbox name="check-button" v-model="storeState.isBooleanSelected" @change="changeTypeSelected($event, 'boolean')" switch>
        {{ thisScriptInput.type === 'boolean' ? 'is a boolean' : 'is not a boolean' }}
      </b-form-checkbox>
    </b-form-group>
    <b-form-group>
      <b-form-checkbox name="check-button" @change="changeIsValueRequired($event)" :disabled="thisScriptInput.type !== 'boolean'" switch>
        {{ isValueRequired ? 'is required' : 'is not required' }}
      </b-form-checkbox>
    </b-form-group>
  </b-form-group>
</template>

<script>
import ScriptInputTypeMixin from './mixins/ScriptInputTypeMixin.js'

export default {
  name: 'ScriptInputTypeBoolean',
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
