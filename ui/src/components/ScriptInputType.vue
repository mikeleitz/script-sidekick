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
        <b-tabs :card="true" :noFade="false">
          <ScriptInputTypeString :id="thisScriptInput.id" :bashOption="this.bashOption"/>
          <ScriptInputTypeNumber :id="thisScriptInput.id" :bashOption="this.bashOption" />
          <ScriptInputTypeBoolean :id="thisScriptInput.id" :bashOption="this.bashOption" />
          <ScriptInputTypeOther :id="thisScriptInput.id" :bashOption="this.bashOption" />
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

export default {
  name: 'ScriptInputType',
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

</style>
