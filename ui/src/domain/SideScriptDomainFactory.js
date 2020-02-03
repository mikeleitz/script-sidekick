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

let BashScript = function (pIid = undefined, pScriptName = '', pShellType = 'BASH') {
  this.domainObjectType = 'bash-script'

  this.id = pIid
  this.scriptName = pScriptName
  this.shellType = pShellType

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

let BashOption = function (pId = undefined) {
  this.domainObjectType = 'bash-option'

  this.id = pId
  this.longName = ''
  this.shortName = ''
  this.type = ''
  this.defaultValue = ''
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
    if (this.validations === undefined) {
      return 0
    } else {
      return this.validations.length
    }
  }
}

let BashValidation = function (pId = undefined, pName = '') {
  this.domainObjectType = 'bash-validation'

  this.id = pId
  this.name = pName
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
  createBashScript: function (pId = undefined, pScriptName = '', pShellType = 'BASH') {
    let bashScript = new BashScript(pId, pScriptName, pShellType)
    this.addCommonMethods(bashScript)

    return bashScript
  },
  createBashOption: function (pId = undefined, pLongName = '', pShortName = '', pHelpText = '') {
    let bashOption = new BashOption(pId)

    bashOption.longName = pLongName
    bashOption.shortName = pShortName
    bashOption.helpText = pHelpText

    this.addCommonMethods(bashOption)

    return bashOption
  },
  createBashValidation: function (pId = undefined, pName = '') {
    let bashValidation = new BashValidation(pId, pName)
    this.addCommonMethods(bashValidation)

    return bashValidation
  },
  createBashValidationFromType: function (validationType) {
    let bashValidation = new BashValidation(validationType.id, validationType.name)
    this.addCommonMethods(bashValidation)

    return bashValidation
  }
}

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
