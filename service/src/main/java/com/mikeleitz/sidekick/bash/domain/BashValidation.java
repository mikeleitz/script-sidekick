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
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * The regex values are mostly taken from the community driven: https://regexr.com
 */
@Data
@SuperBuilder
@AllArgsConstructor
@JsonDeserialize(using = BashValidationDeserializer.class)
abstract public class BashValidation {
    public static BashValidation EMAIL = BashValidationRegex.builder().id(10).name("Email").bashRegex(RegexEnum.EMAIL).build();
    public static BashValidation DATE = BashValidationRegex.builder().id(6).name("Date").build();
    public static BashValidation SIGNED_INTEGER = BashValidationRegex.builder().id(1).name("Integer").build();
    public static BashValidation UNSIGNED_INTEGER = BashValidationRegex.builder().id(19).name("Unsigned number").build();
    public static BashValidation SIGNED_REAL = BashValidationRegex.builder().id(3).name("Real number").build();
    public static BashValidation UNSIGNED_REAL = BashValidationRegex.builder().id(18).name("Signed number").build();
    public static BashValidation BOOLEAN = BashValidationRegex.builder().id(2).name("Boolean").build();
    public static BashValidation TIMESTAMP_ISO = BashValidationRegex.builder().id(7).name("Timestamp").build();
//    public static BashValidation TIMESTAMP_ONE_TRUE = BashValidationRegex.builder().id(7).name("Timestamp").build();
    public static BashValidation URL = BashValidationRegex.builder().id(9).name("URL").build();
    public static BashValidation IPV4 = BashValidationRegex.builder().id(11).name("Ipv4 address").build();
    public static BashValidation REQUIRED = BashValidationRegex.builder().id(20).name("Required value").build();
    public static BashValidation ALPHANUMERIC = BashValidationRegex.builder().id(21).name("Alpha-numeric").build();

    // Special cases. Either non-regex validation or take parameters.
/*
    public static BashValidation REGEX = BashValidationRegex.builder().id(13).name("Custom regex").build();

    public static BashValidation GREATER_THAN = BashValidationLogic.builder().id(14).name("Greater than").build();
    public static BashValidation GREATER_THAN_EQUAL = BashValidationLogic.builder().id(15).name("Greater than or equal").build();
    public static BashValidation LESS_THAN = BashValidationLogic.builder().id(16).name("Less than").build();
    public static BashValidation LESS_THAN_EQUAL = BashValidationLogic.builder().id(17).name("Less than or equal").build();
    public static BashValidation CURRENCY = BashValidationLogic.builder().id(5).name("Currency").build();
    public static BashValidation ENUMERATED = BashValidationLogic.builder().id(8).name("Enumerated type").build();
*/

    // Not implemented or supported yet.
/*
    public static BashValidation IPV6 = BashValidationRegex.builder().id(12).name("Ipv6 address").build();
    public static BashValidation STRING = BashValidationRegex.builder().id(4).name("Some string?").build();
*/

    private Integer id;
    private String name;
    private List args;

    public Boolean isSameValidationType(BashValidation bashValidation) {
        Boolean returnValue = null;

        if (this.getId().equals(bashValidation.getId())) {
            returnValue = true;
        } else {
            returnValue = false;
        }

        return returnValue;
    }

    public String getValidationType() {
        return "";
    }
}
