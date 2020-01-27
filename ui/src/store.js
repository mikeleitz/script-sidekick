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

export const store = {
  state: {
    nextTempId: -1,
    verboseCommandId: 0,
    quietCommandId: 0,
    scriptForm: {
      scriptName: '',
      shellType: 'BASH',
      scriptInputs: [
      ]
    }
  },
  getNextScriptId: function () {
    let newScriptId = this.state.nextTempId
    this.state.nextTempId -= 1
    return newScriptId
  },
  getNextValidationId: function () {
    let newValidationId = this.state.nextTempId
    this.state.nextTempId -= 1
    return newValidationId
  },
  addScriptInput: function (scriptInput) {
    let newScriptId = store.getNextScriptId()
    scriptInput.id = newScriptId
    this.state.scriptForm.scriptInputs.unshift(scriptInput)
    return newScriptId
  },
  removeScriptInput: function (scriptInputId) {
    let index = this.state.scriptForm.scriptInputs.findIndex(scriptInput => scriptInput.id === scriptInputId)
    this.state.scriptForm.scriptInputs.splice(index, 1)
  },
  getScriptInput: function (scriptInputId) {
    let scriptInput = this.state.scriptForm.scriptInputs.find(scriptInput => {
      return scriptInput.id === scriptInputId
    })
    return scriptInput
  },
  getAllScriptInputs: function () {

  },
  addValidation: function (scriptInputId, validation) {

  },
  removeValidation: function (scriptInputId, validationId, validation) {

  },
  getValidation: function (validationId) {

  },
  getAllValidations: function (scriptInputId) {

  }
}
