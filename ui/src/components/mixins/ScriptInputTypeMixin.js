/*
 *  Copyright (c) 2020, Michael Leitz
 *  <p/>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p/>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p/>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/*
This mixin creates a new instance of 'data' for each component. To share values across instances, it's using state.
 */
export const ValidationTypes = Object.freeze({
  INTEGER: { id: 1, name: 'Integer' },
  BOOLEAN: { id: 2, name: 'Boolean' },
  REAL: { id: 3, name: 'Real' },
  STRING: { id: 4, name: 'String' },
  CURRENCY: { id: 5, name: 'Currency' },
  DATE: { id: 6, name: 'Date' },
  TIMESTAMP: { id: 7, name: 'Timestamp' },
  ENUMERATED: { id: 8, name: 'Enumerated Type' },
  URL: { id: 9, name: 'URL' },
  EMAIL: { id: 10, name: 'Email' },
  IPV4: { id: 11, name: 'Ipv4' },
  IPV6: { id: 12, name: 'Ipv6' },
  CUSTOM_REGEX: { id: 13, name: 'Regex' },
  GREATER_THAN: { id: 14, name: 'Greater than' },
  GREATER_THAN_EQUAL: { id: 16, name: 'Greater than or equal' },
  LESS_THAN: { id: 17, name: 'Less than' },
  LESS_THAN_EQUAL: { id: 18, name: 'Less than or equal' },
  SIGNED: { id: 19, name: 'Signed' },
  UNSIGNED: { id: 20, name: 'Unsigned' },
  REQUIRED: { id: 21, name: 'Required' }
})

export default {
  created: function () {
  },
  data () {
    return {
      isValueRequired: false,
      typeSelected: '',
      defaultValue: '',
      regexValue: '',
      dataSubtype: '',
      thisScriptInput: {},
      validations: [ ]
    }
  },
  watch: {
    defaultValue: function (val, oldVal) {
      console.log('New default value: [' + val + '].')
    },
    isValueRequired: function (val, oldVal) {
      console.log('isValueRequired: [' + val + '].')
    }
  },
  computed: {
    totalValidations: function () {
      if (this.validations !== null) {
        return 0
      } else {
        return this.validations.length
      }
    }
  },
  methods: {
    changeTypeSelected: function (event, val) {
      console.log('Current type selected: [' + this.typeSelected + ']. User just selected: [' + val + '].')

      if (event) {
        let selectedType = val
        if (selectedType !== this.typeSelected) {
          console.log('New type selected. Changing type from [' + this.typeSelected + '] to [' + val + '].')
          this.resetType()
          this.typeSelected = selectedType
        } else {
          // User somehow selected the currently selected type. No changes needed
        }
      } else {
        console.log('Current type [' + this.typeSelected + '] is now disabled.')

        this.typeSelected = null
        this.resetType()
      }
    },
    changeIsValueRequired: function (event) {
      if (event) {
        this.isValueRequired = event
      } else {
        this.isValueRequired = false
      }

      console.log('isValueRequired: [' + this.isValueRequired + '].')
    },
    resetType: function () {
      this.validations.splice(0, this.validations.length)
    }
  }
}
