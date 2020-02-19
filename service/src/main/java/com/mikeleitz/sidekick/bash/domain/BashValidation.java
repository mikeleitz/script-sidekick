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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * The regex values are mostly taken from the community driven: https://regexr.com
 */
@Data
@SuperBuilder
@AllArgsConstructor
@JsonDeserialize(using = BashValidationDeserializer.class)
public class BashValidation {
    private @NonNull Integer id;
    private @NonNull String name;
    private @NonNull List args;
    private @NonNull ValidationEnum validationEnum;

    public Boolean isSameValidationType(BashValidation bashValidation) {
        Boolean returnValue = null;

        if (this.getId().equals(bashValidation.getId())) {
            returnValue = true;
        } else {
            returnValue = false;
        }

        return returnValue;
    }
}
