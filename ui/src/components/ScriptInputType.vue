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
          <b-tab title="String" active>
            <b-form-group
              label-cols-lg="2"
              label="This value is"
              label-size="lg"
              label-class="pt-0"
              class="mb-0">
              <b-form-group>
                <b-form-checkbox name="check-button" v-model="stringTypeSelected" @change="typeSelected" switch>
                  {{stringTypeSelected ? 'A string' : 'Not a string' }}
                </b-form-checkbox>
              </b-form-group>
              <b-form-group>
                <b-form-checkbox v-model="thisScriptInput.required" name="check-button" :disabled="!stringTypeSelected" switch>
                  {{ thisScriptInput.required ? 'Required' : 'Not required' }}
                </b-form-checkbox>
              </b-form-group>

              <b-form-group required="true" :disabled="!stringTypeSelected">
                <b-form-radio v-model="selected" value="plain-string">A plain string</b-form-radio>
                <b-form-radio v-model="selected" value="email">An email address</b-form-radio>
                <b-form-radio v-model="selected" value="url">A url</b-form-radio>
                <b-form-radio v-model="selected" value="regex">Specified via RegEx</b-form-radio>
              </b-form-group>

              <b-form-group label="Defaulted to" label-cols-sm="2" :disabled="!stringTypeSelected">
                <b-form-input/>
              </b-form-group>
              <b-form-row ><b-col>&nbsp;</b-col></b-form-row>
            </b-form-group>
          </b-tab>
          <b-tab title="Number">
            <b-form-group
              label-cols-lg="2"
              label="This value is"
              label-size="lg"
              label-class="pt-0"
              class="mb-0">
              <b-form-group>
                <b-form-checkbox v-model="numberTypeSelected" name="check-button" @change="typeSelected" switch>
                  {{ numberTypeSelected ? 'A number' : 'Not a number' }}
                </b-form-checkbox>
              </b-form-group>
              <b-form-group>
                <b-form-checkbox v-model="thisScriptInput.required" name="check-button" :disabled="!numberTypeSelected" switch>
                  {{ thisScriptInput.required ? 'Required' : 'Not required' }}
                </b-form-checkbox>
              </b-form-group>
              <b-form-group>
                <b-form-checkbox v-model="thisScriptInput.signed" name="check-button" :disabled="!numberTypeSelected" switch>
                  {{ thisScriptInput.signed ? 'Signed' : 'Unsigned' }}
                </b-form-checkbox>
              </b-form-group>
              <b-form-group label="at least" label-cols-sm="2" :disabled="!numberTypeSelected">
                <b-form-input/>
              </b-form-group>
              <b-form-group label="at most" label-cols-sm="2" :disabled="!numberTypeSelected">
                <b-form-input/>
              </b-form-group>
              <b-form-group label="Defaulted to" label-cols-sm="2" :disabled="!numberTypeSelected">
                <b-form-input/>
              </b-form-group>
            </b-form-group>
          </b-tab>
          <b-tab title="Boolean">
            <b-form-group
              label-cols-lg="2"
              label="This value is"
              label-size="lg"
              label-class="pt-0"
              class="mb-0">
              <b-form-group>
                <b-form-checkbox v-model="booleanTypeSelected" name="check-button" @change="typeSelected" switch>
                  {{ booleanTypeSelected ? 'A boolean' : 'Not a boolean' }}
                </b-form-checkbox>
              </b-form-group>
              <b-form-group>
                <b-form-checkbox v-model="thisScriptInput.required" name="check-button" :disabled="!booleanTypeSelected" switch>
                  {{ thisScriptInput.required ? 'Required' : 'Not required' }}
                </b-form-checkbox>
              </b-form-group>
              <b-form-group label="Defaulted to" label-cols-sm="2" :disabled="!booleanTypeSelected">
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
              label="This value is"
              label-size="lg"
              label-class="pt-0"
              class="mb-0">
              <b-form-group>
                <b-form-group>
                  <b-form-checkbox name="check-button" v-model="otherTypeSelected" @change="typeSelected" switch>
                    {{otherTypeSelected ? 'Any other type' : 'Not any other type' }}
                  </b-form-checkbox>
                </b-form-group>
                <b-form-checkbox v-model="isValueRequired" name="check-button" :disabled="!otherTypeSelected" switch>
                  {{ isValueRequired ? 'Required' : 'Not required' }}
                </b-form-checkbox>
              </b-form-group>
              <b-form-group label="Defaulted to" :disabled="!otherTypeSelected" label-cols-sm="2">
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
      dataType: '',
      dataSubtype: '',
      defaultValue: '',
      isValueRequired: false,
      selected: 'plain-string',
      stringTypeSelected: false,
      numberTypeSelected: false,
      booleanTypeSelected: false,
      otherTypeSelected: false
    }
  },
  methods: {
    typeSelected: function () {
      this.stringTypeSelected = false
      this.numberTypeSelected = false
      this.booleanTypeSelected = false
      this.otherTypeSelected = false
    },
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
