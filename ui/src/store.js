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

import { DomainFactory } from './domain/SideScriptDomainFactory.js'

export const store = {
  state: {
    nextTempId: -1,
    verboseCommandId: 0,
    quietCommandId: 0,
    isStringSelected: false,
    isNumberSelected: false,
    isBooleanSelected: false,
    isOtherSelected: false,
    isValueRequired: false,
    scriptForm: {
      scriptName: '',
      shellType: 'BASH',
      scriptInputs: [
      ]
    },
    scriptInProgress: DomainFactory.createBashScript()
  },
  getNextScriptId: function () {
    let newScriptId = this.state.nextTempId
    this.state.nextTempId -= 1
    return newScriptId
  }
}
