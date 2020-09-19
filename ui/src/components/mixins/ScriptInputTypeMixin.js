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
      isStringSelected: false,
      isNumberSelected: false,
      isBooleanSelected: false,
      isOtherSelected: false,
      isValueRequired: false,
      stringSubtype: '-1',
      isRegexValueSelected: false,
      regexValue: '',
      regexValueValidation: undefined,
      isGreaterThan: false,
      isEqualForGreaterCheck: false,
      greaterThanValue: '',
      isLessThan: false,
      isEqualForLessCheck: false,
      lessThanValue: '',
      isNumberReal: false,
      isNumberUnsigned: false,
      greaterThanValidation: undefined,
      lessThanValidation: undefined
    }
  },
  watch: {
    defaultValue: function (val, oldVal) {
      console.log('New default value: [' + val + '].')
    },
    isRegexValueSelected: function (val, oldVal) {
      this.regexValueValidation = undefined

      if (val) {
        let validation = DomainFactory.createBashValidationFromType(ValidationTypes.CUSTOM_REGEX)
        validation.addArgs('value', this.regexValue)

        this.thisScriptInput.addValidation(validation)
        this.regexValueValidation = validation
      }
    },
    regexValue: function (val, olVal) {
      if (typeof this.regexValueValidation !== 'undefined') {
        this.regexValueValidation.removeArg('value')
        this.regexValueValidation.addArgs('value', val)
      }
    },
    greaterThanValue: function (val, oldVal) {
      if (typeof this.greaterThanValidation !== 'undefined') {
        this.greaterThanValidation.removeArg('value')
        this.greaterThanValidation.addArgs('value', val)
      }
    },
    lessThanValue: function (val, oldVal) {
      if (typeof this.lessThanValidation !== 'undefined') {
        this.lessThanValidation.removeArg('value')
        this.lessThanValidation.addArgs('value', val)
      }
    },
    isGreaterThan: function (val, oldVal) {
      this.thisScriptInput.removeValidation(DomainFactory.createBashValidationFromType(ValidationTypes.GREATER_THAN))
      this.thisScriptInput.removeValidation(DomainFactory.createBashValidationFromType(ValidationTypes.GREATER_THAN_EQUAL))
      this.greaterThanValidation = undefined

      if (val) {
        if (this.isEqualForGreaterCheck) {
          let validation = DomainFactory.createBashValidationFromType(ValidationTypes.GREATER_THAN_EQUAL)
          validation.addArgs('value', this.greaterThanValue)

          this.thisScriptInput.addValidation(validation)
          this.greaterThanValidation = validation
        } else {
          let validation = DomainFactory.createBashValidationFromType(ValidationTypes.GREATER_THAN)
          validation.addArgs('value', this.greaterThanValue)

          this.thisScriptInput.addValidation(validation)
          this.greaterThanValidation = validation
        }
      } else {
        this.isEqualForGreaterCheck = false
      }
    },
    isLessThan: function (val, oldVal) {
      this.thisScriptInput.removeValidation(DomainFactory.createBashValidationFromType(ValidationTypes.LESS_THAN))
      this.thisScriptInput.removeValidation(DomainFactory.createBashValidationFromType(ValidationTypes.LESS_THAN_EQUAL))
      this.lessThanValidation = undefined

      if (val) {
        if (this.isEqualForGreaterCheck) {
          let validation = DomainFactory.createBashValidationFromType(ValidationTypes.LESS_THAN_EQUAL)
          validation.addArgs('value', this.lessThanValue)

          this.thisScriptInput.addValidation(validation)
          this.lessThanValidation = validation
        } else {
          let validation = DomainFactory.createBashValidationFromType(ValidationTypes.LESS_THAN)
          validation.addArgs('value', this.lessThanValue)

          this.thisScriptInput.addValidation(validation)
          this.lessThanValidation = validation
        }
      } else {
        this.isEqualForLessCheck = false
      }
    },
    isNumberReal: function (val, oldVal) {
      console.log('New isNumberReal value: [' + val + '].')

      this.clearNumberValidations()

      if (this.isNumberUnsigned) {
        if (val) {
          this.thisScriptInput.addValidation(DomainFactory.createBashValidationFromType(ValidationTypes.UNSIGNED_REAL))
        } else {
          this.thisScriptInput.addValidation(DomainFactory.createBashValidationFromType(ValidationTypes.UNSIGNED_INTEGER))
        }
      } else {
        if (val) {
          this.thisScriptInput.addValidation(DomainFactory.createBashValidationFromType(ValidationTypes.SIGNED_REAL))
        } else {
          this.thisScriptInput.addValidation(DomainFactory.createBashValidationFromType(ValidationTypes.SIGNED_INTEGER))
        }
      }
    },
    isNumberUnsigned: function (val, oldVal) {
      console.log('New isNumberUnsigned value: [' + val + '].')

      this.clearNumberValidations()

      if (this.isNumberReal) {
        if (val) {
          this.thisScriptInput.addValidation(DomainFactory.createBashValidationFromType(ValidationTypes.UNSIGNED_REAL))
        } else {
          this.thisScriptInput.addValidation(DomainFactory.createBashValidationFromType(ValidationTypes.SIGNED_REAL))
        }
      } else {
        if (val) {
          this.thisScriptInput.addValidation(DomainFactory.createBashValidationFromType(ValidationTypes.UNSIGNED_INTEGER))
        } else {
          this.thisScriptInput.addValidation(DomainFactory.createBashValidationFromType(ValidationTypes.SIGNED_INTEGER))
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

          this.resetType()
          this.thisScriptInput.removeAllValidations()

          this.thisScriptInput.type = selectedType
          if (selectedType === 'string') {
            this.storeState.isStringSelected = true
          } else if (selectedType === 'number') {
            this.storeState.isNumberSelected = true
            let unsignedIntegerValidation = DomainFactory.createBashValidationFromType(ValidationTypes.UNSIGNED_INTEGER)
            this.thisScriptInput.addValidation(unsignedIntegerValidation)
          } else if (selectedType === 'boolean') {
            let booleanValidation = DomainFactory.createBashValidationFromType(ValidationTypes.BOOLEAN)
            this.thisScriptInput.addValidation(booleanValidation)
            this.storeState.isBooleanSelected = true
          } else if (selectedType === 'other') {
            this.storeState.isOtherSelected = true
          }
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
    clearNumberValidations: function () {
      this.thisScriptInput.removeValidation(DomainFactory.createBashValidationFromType(ValidationTypes.UNSIGNED_REAL))
      this.thisScriptInput.removeValidation(DomainFactory.createBashValidationFromType(ValidationTypes.SIGNED_REAL))
      this.thisScriptInput.removeValidation(DomainFactory.createBashValidationFromType(ValidationTypes.UNSIGNED_INTEGER))
      this.thisScriptInput.removeValidation(DomainFactory.createBashValidationFromType(ValidationTypes.SIGNED_INTEGER))

      this.thisScriptInput.removeValidation(DomainFactory.createBashValidationFromType(ValidationTypes.GREATER_THAN))
      this.thisScriptInput.removeValidation(DomainFactory.createBashValidationFromType(ValidationTypes.GREATER_THAN_EQUAL))
      this.thisScriptInput.removeValidation(DomainFactory.createBashValidationFromType(ValidationTypes.LESS_THAN))
      this.thisScriptInput.removeValidation(DomainFactory.createBashValidationFromType(ValidationTypes.LESS_THAN_EQUAL))

      this.greaterThanValidation = undefined
      this.lessThanValidation = undefined
    },
    resetType: function () {
      this.storeState.isStringSelected = false
      this.storeState.isNumberSelected = false
      this.storeState.isBooleanSelected = false
      this.storeState.isOtherSelected = false
      this.storeState.isValueRequired = false
      this.isGreaterThan = false
      this.isEqualForGreaterCheck = false
      this.greaterThanValue = ''
      this.isLessThan = false
      this.isEqualForLessCheck = false
      this.lessThanValue = ''
      this.isNumberReal = false
      this.isNumberUnsigned = false
      this.isRegexValueSelected = false
      this.regexValue = ''
      this.regexValueValidation = undefined
      this.stringSubtype = '-1'
    }
  }
}
