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
This file contains all the code to create the relevant javascript domain objects to the application.

It uses the factory pattern to create BashScript, BashOptions, and BashValidations.

A BashScript has zero or more BashOptions.
A BashOption has zero or more BashValidations.

These objects will be deserialized to JSON and sent to the relevant service.
*/

// import { ValidationTypes } from '../components/mixins/ScriptInputTypeMixin'

let BashScript = function () {
  this.domainObjectType = 'bash-script'

  this.id = null
  this.scriptName = ''
  this.shellType = 'BASH'

  this.bashOptions = [] // BashOption objects

  this.addOption = function (bashOption) {
    if (this.hasOption(bashOption)) {
      console.log('Option id [' + bashOption.id + '] already exists for BashScript name [' + this.longName + ']. Not adding it.')
    } else {
      this.bashOptions.unshift(bashOption)

      console.log('Added option id [' + bashOption.id + '] BashScript name [' + this.longName + '] now has total options: [' + this.totalOptions() + '].')
    }
  }

  this.getOptionIndex = function (bashOption) {
    let existingOptionIndex = this.bashOptions.findIndex(arrayOption => arrayOption.id === bashOption.id)

    return existingOptionIndex
  }

  this.getOptionById = function (id) {
    let optionIndex = this.bashOptions.findIndex(arrayOption => arrayOption.id === id)
    let option = this.bashOptions[optionIndex]

    return option
  }

  this.hasOption = function (bashOption) {
    let existingOptionIndex = this.getOptionIndex(bashOption)

    if (existingOptionIndex >= 0) {
      return true
    } else {
      return false
    }
  }

  this.removeOption = function (bashOption) {
    if (this.hasOption(bashOption)) {
      let optionIndex = this.getOptionIndex(bashOption)
      this.bashOptions.splice(optionIndex, 1)
    }
  }

  this.totalOptions = function () {
    return this.bashOptions.length
  }
}

let BashOption = function (id) {
  this.domainObjectType = 'bash-option'

  this.id = id
  this.longName = ''
  this.shortName = ''
  this.type = ''
  this.default = ''
  this.helpText = ''
  this.validations = [] // BashValidation objects

  this.addValidation = function (bashValidation) {
    if (this.hasValidation(bashValidation)) {
      console.log('Validation id [' + bashValidation.id + '] already exists for BashOption id [' + this.id + ']. Not adding it.')
    } else {
      this.validations.unshift(bashValidation)

      console.log('Added validation id [' + bashValidation.id + '] BashOption id [' + this.id + '] now has total validations: [' + this.totalValidations() + '].')
    }
  }

  this.getValidationIndex = function (bashValidation) {
    let existingValidationIndex = this.validations.findIndex(arrayValidation => arrayValidation.id === bashValidation.id)

    return existingValidationIndex
  }

  this.hasValidation = function (bashValidation) {
    let existingValidationIndex = this.getValidationIndex(bashValidation)

    if (existingValidationIndex >= 0) {
      return true
    } else {
      return false
    }
  }

  this.removeValidation = function (bashValidation) {
    if (this.hasValidation(bashValidation)) {
      let validationIndex = this.getValidationIndex(bashValidation)
      this.validations.splice(validationIndex, 1)
    }
  }

  this.totalValidations = function () {
    return this.validations.length
  }
}

let BashValidation = function (id) {
  this.domainObjectType = 'bash-validation'

  this.id = id
  this.name = ''
  this.args = [] // Key value array

  this.addArgs = function (key, value) {
    if (this.hasArg(key)) {
      console.log('Arg key [' + key + '] already exists for BashValidation id [' + this.id + ']. Not adding it.')
    } else {
      this.args.unshift({ 'key': key, 'value': value })

      console.log('Added arg key [' + key + '] BashValidation id [' + this.id + '] now has total args: [' + this.totalArgs() + '].')
    }
  }

  this.getArgIndex = function (key) {
    let existingArgIndex = this.args.findIndex(arrayArg => arrayArg.key === key)

    return existingArgIndex
  }

  this.hasArg = function (key) {
    let existingArgIndex = this.getArgIndex(key)

    if (existingArgIndex >= 0) {
      return true
    } else {
      return false
    }
  }

  this.removeArg = function (key) {
    if (this.hasArg(key)) {
      let argIndex = this.getArgIndex(key)
      this.args.splice(argIndex, 1)
    }
  }

  this.totalArgs = function () {
    return this.args.length
  }
}

export const DomainFactory = {
  addCommonMethods: function (sideScriptDomainObject) {
    sideScriptDomainObject.hello = function () {
      alert('hello')
    }
    sideScriptDomainObject.toJson = function () {
      return JSON.stringify(this)
    }
  },
  createBashScript: function () {
    let bashScript = new BashScript()
    this.addCommonMethods(bashScript)
    return bashScript
  },
  createBashOption: function (id) {
    let bashOption = new BashOption(id)
    this.addCommonMethods(bashOption)
    return bashOption
  },
  createBashValidation: function (id) {
    let bashValidation = new BashValidation(id)
    this.addCommonMethods(bashValidation)
    return bashValidation
  }
}
