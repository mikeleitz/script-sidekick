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

package com.mikeleitz.sidekick.bash.domain;

import java.util.List;
import java.util.Set;

public class BashValidationFactory {
    private static final Set<Integer> BASH_LOGIC_VALIDATIONS = Set.of(14, 15, 16, 17, 5, 8);
    private static final Set<Integer> BASH_REGEX_VALIDATIONS = Set.of(10, 6, 1, 19, 3, 18, 2, 7, 9, 11, 20, 21);

    public BashValidation createBashValidation(Integer id, String name, List args) {
        BashValidation returnValue = null;

        if (BASH_LOGIC_VALIDATIONS.contains(id)) {
            returnValue = BashValidationLogic.builder().id(id).name(name).args(args).build();
        } else if (BASH_REGEX_VALIDATIONS.contains(id)) {
            returnValue = BashValidationRegex.builder().id(id).name(name).args(args).build();
        } else {
            throw new IllegalArgumentException(String.format("BashValidation id %s not supported.", id));
        }

        return returnValue;
    }
}
