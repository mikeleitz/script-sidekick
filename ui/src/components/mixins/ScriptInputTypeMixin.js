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

import { DomainFactory, ValidationTypes } from '../../domain/SideScriptDomainFactory'
import { store } from '../../store'

export default {
  created: function () {
  },
  data () {
    return {
      thisScriptInput: null,
      storeState: store.state,
      stringSubtype: store.stringSubtype,
      isBooleanSelected: store.isBooleanSelected,
      isGreaterThan: false,
      isEqualForGreaterCheck: false,
      greaterThanValue: '',
      isLessThan: false,
      isEqualForLessCheck: false,
      lessThanValue: '',
      isNumberReal: false,
      isNumberUnsigned: false
    }
  },
  watch: {
    defaultValue: function (val, oldVal) {
      console.log('New default value: [' + val + '].')
    },
    isNumberReal: function (val, oldVal) {
      console.log('New isNumberReal value: [' + val + '].')

      let unsignedRealValidation = DomainFactory.createBashValidationFromType(ValidationTypes.UNSIGNED_REAL)
      let signedRealValidation = DomainFactory.createBashValidationFromType(ValidationTypes.SIGNED_REAL)
      let unsignedIntegerValidation = DomainFactory.createBashValidationFromType(ValidationTypes.UNSIGNED_INTEGER)
      let signedIntegerValidation = DomainFactory.createBashValidationFromType(ValidationTypes.SIGNED_INTEGER)

      this.clearNumberValidations();

      if (this.isNumberUnsigned()) {
        if (val) {
          this.thisScriptInput.addValidation(unsignedRealValidation)
        } else {
          this.thisScriptInput.addValidation(unsignedIntegerValidation)
        }
      } else {
        if (val) {
          this.thisScriptInput.addValidation(signedRealValidation)
        } else {
          this.thisScriptInput.addValidation(signedIntegerValidation)
        }
      }
    },
    isNumberUnsigned: function (val, oldVal) {
      console.log('New isNumberUnsigned value: [' + val + '].')

      let unsignedRealValidation = DomainFactory.createBashValidationFromType(ValidationTypes.UNSIGNED_REAL)
      let signedRealValidation = DomainFactory.createBashValidationFromType(ValidationTypes.SIGNED_REAL)
      let unsignedIntegerValidation = DomainFactory.createBashValidationFromType(ValidationTypes.UNSIGNED_INTEGER)
      let signedIntegerValidation = DomainFactory.createBashValidationFromType(ValidationTypes.SIGNED_INTEGER)

      this.clearNumberValidations();

      if (this.isNumberReal) {
        if (val) {
          this.thisScriptInput.addValidation(unsignedRealValidation)
        } else {
          this.thisScriptInput.addValidation(signedRealValidation)
        }
      } else {
        if (val) {
          this.thisScriptInput.addValidation(unsignedIntegerValidation)
        } else {
          this.thisScriptInput.addValidation(signedIntegerValidation)
        }
      }
    },
    stringSubtype: function (val, oldVal) {
      console.log('New stringSubtype validation id: [' + val + '].')

      let previousValidation
      let newValidation

      if (oldVal !== undefined && parseInt(oldVal) !== -1) {
        previousValidation = DomainFactory.createBashValidationFromId(parseInt(oldVal))
        this.thisScriptInput.removeValidation(previousValidation)
      }

      if (val !== undefined && parseInt(val) !== -1) {
        newValidation = DomainFactory.createBashValidationFromId(parseInt(val))
        this.thisScriptInput.addValidation(newValidation)
      }
    }
  },
  computed: {
    totalValidations: function () {
      let totalVal = this.thisScriptInput.totalValidations()
      return totalVal
    }
  },
  methods: {
    changeTypeSelected: function (event, val) {
      let selectedType = val

      console.log('Current type selected: [' + this.thisScriptInput.type + ']. User just selected: [' + selectedType + '].')

      if (event) {
        if (selectedType !== this.thisScriptInput.type) {
          console.log('New type selected. Changing type from [' + this.thisScriptInput.type + '] to [' + selectedType + '].')

          this.thisScriptInput.removeAllValidations()

          this.thisScriptInput.type = selectedType
          if (selectedType === 'string') {
            this.storeState.isStringSelected = true
            this.storeState.isNumberSelected = false
            this.storeState.isBooleanSelected = false
            this.storeState.isOtherSelected = false
          } else if (selectedType === 'number') {
            this.storeState.isNumberSelected = true
            let unsignedIntegerValidation = DomainFactory.createBashValidationFromType(ValidationTypes.BOOLEAN)
            this.thisScriptInput.addValidation(unsignedIntegerValidation)

            this.storeState.isStringSelected = false
            this.storeState.isBooleanSelected = false
            this.storeState.isOtherSelected = false
          } else if (selectedType === 'boolean') {
            let booleanValidation = DomainFactory.createBashValidationFromType(ValidationTypes.BOOLEAN)
            this.thisScriptInput.addValidation(booleanValidation)

            this.storeState.isBooleanSelected = true
            this.storeState.isStringSelected = false
            this.storeState.isNumberSelected = false
            this.storeState.isOtherSelected = false
          } else if (selectedType === 'other') {
            this.storeState.isOtherSelected = true
            this.storeState.isNumberSelected = false
            this.storeState.isStringSelected = false
            this.storeState.isBooleanSelected = false
          }

          this.storeState.isValueRequired = false
        } else {
          // User somehow selected the currently selected type. No changes needed
        }
      } else {
        console.log('Current type [' + this.thisScriptInput.type + '] is now disabled.')

        this.thisScriptInput.type = ''
      }
    },
    changeIsValueRequired: function (event) {
      let requiredValidation = DomainFactory.createBashValidationFromType(ValidationTypes.VALUE_REQUIRED)

      if (event) {
        if (!this.thisScriptInput.hasValidation(requiredValidation)) {
          this.thisScriptInput.addValidation(requiredValidation)
        }
      } else {
        if (this.thisScriptInput.hasValidation(requiredValidation)) {
          this.thisScriptInput.removeValidation(requiredValidation)
        }
      }

      console.log('isValueRequired: [' + this.isValueRequired + '].')
    },
    clearNumberValidations: function() {
      let unsignedRealValidation = DomainFactory.createBashValidationFromType(ValidationTypes.UNSIGNED_REAL)
      let signedRealValidation = DomainFactory.createBashValidationFromType(ValidationTypes.SIGNED_REAL)
      let unsignedIntegerValidation = DomainFactory.createBashValidationFromType(ValidationTypes.UNSIGNED_INTEGER)
      let signedIntegerValidation = DomainFactory.createBashValidationFromType(ValidationTypes.SIGNED_INTEGER)

      this.thisScriptInput.removeValidation(unsignedRealValidation)
      this.thisScriptInput.removeValidation(signedRealValidation)
      this.thisScriptInput.removeValidation(unsignedIntegerValidation)
      this.thisScriptInput.removeValidation(signedIntegerValidation)
    },
    resetType: function () { }
  }
}
