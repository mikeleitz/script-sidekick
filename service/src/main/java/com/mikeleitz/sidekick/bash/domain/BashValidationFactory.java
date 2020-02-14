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

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BashValidationFactory {

    public BashValidation createBashValidation(Integer id, List args) {
        BashValidation returnValue = null;

        Optional<ValidationRegexEnum> regexEnumOptional = ValidationRegexEnum.getById(id);
        Optional<ValidationLogicEnum> logicEnumOptional = ValidationLogicEnum.getById(id);

        if (regexEnumOptional.isPresent()) {
            returnValue = toBashValidation(regexEnumOptional.get(), args);
        } else if (logicEnumOptional.isPresent()) {
            returnValue = toBashValidation(logicEnumOptional.get(), args);
        } else {
            throw new IllegalArgumentException(String.format("BashValidation id %s not supported.", id));
        }


        return returnValue;
    }

    public BashValidationRegex toBashValidation(ValidationRegexEnum regexEnum, List<Pair<String, String>> args) {
        BashValidationRegex returnValue = null;

        returnValue = BashValidationRegex.builder()
                .id(regexEnum.getId())
                .name(regexEnum.getRegexName())
                .args(CollectionUtils.isNotEmpty(args) ? args : Collections.emptyList())
                .bashRegexEnum(regexEnum)
                .build();

        return returnValue;
    }

    public BashValidationLogic toBashValidation(ValidationLogicEnum logicEnum, List<Pair<String, String>> args) {
        BashValidationLogic returnValue = null;

        returnValue = BashValidationLogic.builder()
                .id(logicEnum.getId())
                .name(logicEnum.getName())
                .args(CollectionUtils.isNotEmpty(args) ? args : Collections.emptyList())
                .templateLocation(logicEnum.getTemplateLocation())
                .validationLogicEnum(logicEnum)
                .build();

        return returnValue;
    }
}
