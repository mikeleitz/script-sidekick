<!--
  -  Copyright (c) 2019, Michael Leitz
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
        <b-tabs card
                active-nav-item-class=""
                active-tab-class="">
          <b-tab title="String">
            <b-form-group
              label-cols-lg="2"
              label="Value is"
              label-size="lg"
              label-class="pt-0"
              class="mb-0">
              <b-form-group>
                <b-form-checkbox v-model="thisScriptInput.aNumber" name="check-button" switch>
                  {{ thisScriptInput.aNumber ? 'A string' : 'Not a string' }}
                </b-form-checkbox>
              </b-form-group>
              <b-form-group>
                <b-form-checkbox v-model="thisScriptInput.required" name="check-button" switch>
                  {{ thisScriptInput.required ? 'Required' : 'Not required' }}
                </b-form-checkbox>
              </b-form-group>
              <b-form-group>
                <b-form-checkbox v-model="thisScriptInput.email" name="check-button" switch>
                  {{ thisScriptInput.email ? 'An email address' : 'Not an email address' }}
                </b-form-checkbox>
              </b-form-group>
              <b-form-group>
                <b-form-checkbox v-model="thisScriptInput.url" name="check-button" switch>
                  {{ thisScriptInput.url ? 'A url' : 'Not a url' }}
                </b-form-checkbox>
              </b-form-group>
              <b-form-group>
                <b-form-checkbox v-model="thisScriptInput.regex" name="check-button" switch>
                  {{ thisScriptInput.regex ? 'A regex' : 'Not a regex' }}
                </b-form-checkbox>
              </b-form-group>
              <b-form-group label="defaulted to" label-cols-sm="2">
                <b-form-input/>
              </b-form-group>
              <b-form-row ><b-col>&nbsp;</b-col></b-form-row>
            </b-form-group>
          </b-tab>
          <b-tab title="Number" active>
            <b-form-group
              label-cols-lg="2"
              label="Value is"
              label-size="lg"
              label-class="pt-0"
              class="mb-0">
              <b-form-group>
                <b-form-checkbox v-model="thisScriptInput.aNumber" name="check-button" switch>
                  {{ thisScriptInput.aNumber ? 'A number' : 'Not a number' }}
                </b-form-checkbox>
              </b-form-group>
              <b-form-group>
                <b-form-checkbox v-model="thisScriptInput.required" name="check-button" switch>
                  {{ thisScriptInput.required ? 'Required' : 'Not required' }}
                </b-form-checkbox>
              </b-form-group>
              <b-form-group>
                <b-form-checkbox v-model="thisScriptInput.signed" name="check-button" switch>
                  {{ thisScriptInput.signed ? 'Signed' : 'Unsigned' }}
                </b-form-checkbox>
              </b-form-group>
              <b-form-group label="at least" label-cols-sm="2">
                <b-form-input/>
              </b-form-group>
              <b-form-group label="at most" label-cols-sm="2">
                <b-form-input/>
              </b-form-group>
              <b-form-group label="defaulted to" label-cols-sm="2">
                <b-form-input/>
              </b-form-group>
            </b-form-group>
          </b-tab>
          <b-tab title="Boolean">
            <b-form-group
              label-cols-lg="2"
              label="Value is"
              label-size="lg"
              label-class="pt-0"
              class="mb-0">
              <b-form-group>
                <b-form-checkbox v-model="thisScriptInput.aNumber" name="check-button" switch>
                  {{ thisScriptInput.aNumber ? 'A boolean' : 'Not a boolean' }}
                </b-form-checkbox>
              </b-form-group>
              <b-form-group>
                <b-form-checkbox v-model="thisScriptInput.required" name="check-button" switch>
                  {{ thisScriptInput.required ? 'Required' : 'Not required' }}
                </b-form-checkbox>
              </b-form-group>
              <b-form-group label="defaulted to" label-cols-sm="2">
                <b-form-input/>
              </b-form-group>
              <b-form-row ><b-col>&nbsp;</b-col></b-form-row>
              <b-form-row ><b-col>&nbsp;</b-col></b-form-row>
              <b-form-row ><b-col>&nbsp;</b-col></b-form-row>
              <b-form-row ><b-col>&nbsp;</b-col></b-form-row>
              <b-form-row ><b-col>&nbsp;</b-col></b-form-row>
              <b-form-row ><b-col>&nbsp;</b-col></b-form-row>
            </b-form-group>
          </b-tab>
          <b-tab title="Other">
            <b-form-group
              label-cols-lg="2"
              label="Value is"
              label-size="lg"
              label-class="pt-0"
              class="mb-0">
              <b-form-group>
                <b-form-checkbox v-model="thisScriptInput.required" name="check-button" switch>
                  {{ thisScriptInput.required ? 'Required' : 'Not required' }}
                </b-form-checkbox>
              </b-form-group>
              <b-form-group label="defaulted to" label-cols-sm="2">
                <b-form-input/>
              </b-form-group>
              <b-form-row ><b-col>&nbsp;</b-col></b-form-row>
              <b-form-row ><b-col>&nbsp;</b-col></b-form-row>
              <b-form-row ><b-col>&nbsp;</b-col></b-form-row>
              <b-form-row ><b-col>&nbsp;</b-col></b-form-row>
              <b-form-row ><b-col>&nbsp;</b-col></b-form-row>
              <b-form-row ><b-col>&nbsp;</b-col></b-form-row>
              <b-form-row ><b-col>&nbsp;</b-col></b-form-row>
              <b-form-row ><b-col>&nbsp;</b-col></b-form-row>
            </b-form-group>
          </b-tab>
        </b-tabs>
      </b-card>
    </b-form-group>
  </div>
</template>

<script>
import { store } from '../store.js'
// import { ValidationProvider } from 'vee-validate'

export default {
  name: 'ScriptInputType',
  components: {
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
      thisScriptInput: {},
      isNumberPushed: false,
      isStringPushed: true,
      isBooleanPushed: false,
      isOtherPushed: false,
      signed: true,
      required: true,
      aNumber: true,
      email: false,
      url: false,
      regex: false
    }
  },
  methods: {
    numberPressed: function () {
      this.unselectAll()
      this.isNumberPushed = true
    },
    stringPressed: function () {
      this.unselectAll()
      this.isStringPushed = true
    },
    booleanPressed: function () {
      this.unselectAll()
      this.isBooleanPushed = true
    },
    otherPressed: function () {
      this.unselectAll()
      this.isOtherPushed = true
    },
    unselectAll: function () {
      this.isNumberPushed = false
      this.isStringPushed = false
      this.isBooleanPushed = false
      this.isOtherPushed = false
    }
  }
}
</script>

<style scoped>

</style>
