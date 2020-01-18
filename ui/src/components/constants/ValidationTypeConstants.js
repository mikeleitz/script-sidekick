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
