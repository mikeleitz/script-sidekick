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

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum ValidationLogicEnum {
    GREATER_THAN(14, "Greater than", ""),
    GREATER_THAN_EQUAL(15, "Greater than or equal", ""),
    LESS_THAN(16, "Less than", ""),
    LESS_THAN_EQUAL(17, "Less than or equal", ""),
    CURRENCY(5, "Currency", ""),
    ENUMERATED(8, "Enumerated type", ""),
    ;

    private Integer id;
    private String name;
    // This is the path to the location of the StringTemplate file.
    // TODO replace with Spring Resource?
    private String templateLocation;


    public static Optional<ValidationLogicEnum> getById(Integer id) {
        Optional<ValidationLogicEnum> returnValue = null;

        returnValue = Arrays.stream(ValidationLogicEnum.values()).filter(r -> r.getId() == id).findFirst();

        return returnValue;
    }
}
