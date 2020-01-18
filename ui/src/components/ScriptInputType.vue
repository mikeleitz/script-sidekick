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
          <ScriptInputTypeString :id="thisScriptInput.id" />
          <ScriptInputTypeNumber :id="thisScriptInput.id" />
          <ScriptInputTypeBoolean :id="thisScriptInput.id" />
          <ScriptInputTypeOther :id="thisScriptInput.id" />
        </b-tabs>
      </b-card>
    </b-form-group>
  </div>
</template>

<script>
import { store } from '../store.js'

import ScriptInputTypeNumber from '@/components/ScriptInputTypeNumber.vue'
import ScriptInputTypeString from '@/components/ScriptInputTypeString.vue'
import ScriptInputTypeBoolean from '@/components/ScriptInputTypeBoolean.vue'
import ScriptInputTypeOther from '@/components/ScriptInputTypeOther.vue'

// import { ValidationProvider } from 'vee-validate'

export default {
  name: 'ScriptInputType',
  components: {
    ScriptInputTypeNumber,
    ScriptInputTypeBoolean,
    ScriptInputTypeOther,
    ScriptInputTypeString
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
      thisScriptInput: {}
    }
  },
  methods: {
  }
}
</script>

<style scoped>

</style>
