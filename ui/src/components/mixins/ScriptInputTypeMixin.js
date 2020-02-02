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

export default {
  created: function () {
  },
  data () {
    return {
      thisScriptInput: null
    }
  },
  watch: {
    defaultValue: function (val, oldVal) {
      console.log('New default value: [' + val + '].')
    }
  },
  computed: {
    totalValidations: function () {
      this.thisScriptInput.totalValidations()
    },
    isValueRequired: function () {
      let requiredValidation = DomainFactory.createBashValidationFromType(ValidationTypes.REQUIRED)
      let isValueRequired = this.thisScriptInput.hasValidation(requiredValidation)
      return isValueRequired
    }
  },
  methods: {
    changeTypeSelected: function (event, val) {
      let selectedType = val

      console.log('Current type selected: [' + this.thisScriptInput.type + ']. User just selected: [' + selectedType + '].')

      if (event) {
        if (selectedType !== this.thisScriptInput.type) {
          console.log('New type selected. Changing type from [' + this.thisScriptInput.type + '] to [' + selectedType + '].')

          this.thisScriptInput.type = selectedType
        } else {
          // User somehow selected the currently selected type. No changes needed
        }
      } else {
        console.log('Current type [' + this.thisScriptInput.type + '] is now disabled.')

        this.thisScriptInput.type = ''
      }
    },
    changeIsValueRequired: function (event) {
      let requiredValidation = DomainFactory.createBashValidationFromType(ValidationTypes.REQUIRED)

      if (event) {
        if (!this.thisScriptInput.hasValidation(requiredValidation)) {
          this.thisScriptInput.addValidation(requiredValidation)
        }
      } else {
        if (this.thisScriptInput.hasValidation(requiredValidation)) {
          this.thisScriptInput.removeValidation(requiredValidation)
        }
      }

      console.log('isValueRequired: [' + this.isValueRequired() + '].')
    },
    resetType: function () { }
  }
}
