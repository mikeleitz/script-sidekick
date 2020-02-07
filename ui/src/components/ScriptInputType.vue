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
    <b-form-group label="Data type"
                  label-for="input-type-selection">
      <b-card no-body>
        <b-tabs class="same-height-tab" :card="true" :noFade="true" >
          <b-tab active>
            <template v-slot:title>
              String <b-badge variant="info" v-if="totalValidations > 0 && thisScriptInput.type === 'string'">{{ totalValidations }}</b-badge>
            </template>
            <b-card-body>
              <ScriptInputTypeString :id="thisScriptInput.id" :bashOption="this.bashOption"/>
            </b-card-body>
          </b-tab>
          <b-tab>
            <template v-slot:title>
              Number <b-badge variant="info" v-if="totalValidations > 0 && thisScriptInput.type === 'number'">{{ totalValidations }}</b-badge>
            </template>
            <b-card-body>
              <ScriptInputTypeNumber :id="thisScriptInput.id" :bashOption="this.bashOption" />
            </b-card-body>
          </b-tab>
          <b-tab>
            <template v-slot:title>
              Boolean <b-badge variant="info" v-if="totalValidations > 0 && thisScriptInput.type === 'boolean'">{{ totalValidations }}</b-badge>
            </template>
            <b-card-body>
              <ScriptInputTypeBoolean :id="thisScriptInput.id" :bashOption="this.bashOption" />
            </b-card-body>
          </b-tab>
          <b-tab>
            <template v-slot:title>
              Other <b-badge variant="info" v-if="totalValidations > 0 && thisScriptInput.type === 'other'">{{ totalValidations }}</b-badge>
            </template>
            <b-card-body>
              <ScriptInputTypeOther :id="thisScriptInput.id" :bashOption="this.bashOption" />
            </b-card-body>
          </b-tab>
        </b-tabs>
      </b-card>
    </b-form-group>
  </div>
</template>

<script>
import ScriptInputTypeNumber from '@/components/ScriptInputTypeNumber.vue'
import ScriptInputTypeString from '@/components/ScriptInputTypeString.vue'
import ScriptInputTypeBoolean from '@/components/ScriptInputTypeBoolean.vue'
import ScriptInputTypeOther from '@/components/ScriptInputTypeOther.vue'
import ScriptInputTypeMixin from './mixins/ScriptInputTypeMixin.js'

export default {
  name: 'ScriptInputType',
  mixins: [ScriptInputTypeMixin],
  components: {
    ScriptInputTypeNumber,
    ScriptInputTypeBoolean,
    ScriptInputTypeOther,
    ScriptInputTypeString
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
  },
  data () {
    return {
      thisScriptInput: this.bashOption
    }
  },
  methods: {
  }
}
</script>

<style scoped>
  .same-height-tab {
    height: 360px;
    overflow-y: auto;
  }
</style>
