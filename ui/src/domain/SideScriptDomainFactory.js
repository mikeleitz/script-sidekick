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

let BashScript = function () {
  this.domainObjectType = 'bash-script'
  
  this.longName = 'abc'
  this.shortName = 'xyz'
  this.helpText = '123'
}

let BashOption = function (id) {
  this.domainObjectType = 'bash-option'

  this.id = id
  this.type = ''
  this.default = ''
  this.validations = []
}

let BashValidation = function (id) {
  this.domainObjectType = 'bash-validation'

  this.id = id
  this.name = ''
  this.args = []
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
