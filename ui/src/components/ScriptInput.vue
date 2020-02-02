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
    <b-form-group label="Long name">
      <ValidationProvider name="Long name" rules="required" v-slot="{ errors }">
        <b-form-input placeholder="Long name" v-model="thisScriptInput.longName" />
        <span>{{ errors[0] }}</span>
      </ValidationProvider>
    </b-form-group>

    <b-form-group label="Short name">
      <ValidationProvider name="Short name" rules="exactly:1" v-slot="{ errors }">
        <b-form-input placeholder="Short name" v-model="thisScriptInput.shortName" />
        <span>{{ errors[0] }}</span>
      </ValidationProvider>
    </b-form-group>

    <ScriptInputType :id="thisScriptInput.id" :bashOption="this.bashOption"/>

    <b-form-group label="Help text">
      <ValidationProvider name="Help text" rules="max:80" v-slot="{ errors }">
        <b-form-input placeholder="Help text" v-model="thisScriptInput.helpText" />
        <span>{{ errors[0] }}</span>
      </ValidationProvider>
    </b-form-group>
  </div>
</template>

<script>
import ScriptInputType from '@/components/ScriptInputType.vue'

import { ValidationProvider, extend } from 'vee-validate'
import { required } from 'vee-validate/dist/rules'

extend('required', required)

extend('exactly', {
  validate: (value, { exactly }) => {
    const length = value && value.length
    let exactLength = Number(exactly)

    return length === exactLength
  },
  params: ['exactly'],
  message: '{_field_}\'s length must be exactly {exactly}'
})

extend('max', {
  validate: (value, { max }) => {
    const length = value && value.length

    return length <= max
  },
  params: ['max'],
  message: '{_field_} length must not be more than {max}'
})

export default {
  name: 'ScriptInput',
  components: {
    ValidationProvider,
    ScriptInputType
  },
  props: {
    // These properties include the database id (or -1 if not saved) and the index from the array.
    // This will allow the component to lookup the corresponding data element in the global store and
    // display the rest of the values.
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
    console.log('creating option ui ' + this.bashOption.toJson())
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
