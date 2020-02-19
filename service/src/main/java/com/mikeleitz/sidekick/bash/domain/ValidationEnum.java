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
import lombok.NonNull;

import java.util.Arrays;
import java.util.Optional;

/**
 * This enum is responsible for tracking all the validations available in the generator.
 *
 * Each validation has an id value that the client needs to pass in for the appropriate input option. Every validation
 * also tracks its template that generates it.
 *
 * Every validation goes into the script as a utility function even if it's not used by any of the input options.
 *
 * In the resulting script, regex validations use a common regex validator function and their regex value is
 * defined as a constant. The also have a function that can be called with a value argument that validates the value.
 *
 * e.g. readonly UNSIGNED_INTEGER="^[0-9]{1,10}$"
 *
 * function validateUnsignedInteger() {
 *     // calls (delegates to) validateRegex with UNSIGNED_INTEGER and $1
 *     // returns the value from the validateRegex function.
 * }
 *
 * Regex validations are one type of validation. The other type uses a bash function, which
 * will be defined in the specified string template.
 *
 * @author leitz@mikeleitz.com
 */
@AllArgsConstructor
@Getter
public enum ValidationEnum {
    EMAIL(10, "Email validation", "", "com/mikeleitz/sidekick/bash/validation/bash-validation-email-regex.stg"),
    DATE(6, "Date validation dd/mm/yyy", "", "com/mikeleitz/sidekick/bash/validation/bash-validation-date-regex.stg"),
    SIGNED_INTEGER(1, "Signed integer", "", "com/mikeleitz/sidekick/bash/validation/bash-validation-integer-signed-regex.stg"),
    UNSIGNED_INTEGER(19, "Unsigned integer", "", "com/mikeleitz/sidekick/bash/validation/bash-validation-integer-unsigned-regex.stg"),
    SIGNED_REAL(3, "Signed real number", "", "com/mikeleitz/sidekick/bash/validation/bash-validation-real-signed-regex.stg"),
    UNSIGNED_REAL(18, "Unsigned real number", "", "com/mikeleitz/sidekick/bash/validation/bash-validation-real-unsigned-regex.stg"),
    BOOLEAN(2, "Boolean", "", "com/mikeleitz/sidekick/bash/validation/bash-validation-boolean-regex.stg"),
    TIMESTAMP_ISO(7, "Timestamp in ISO format", "^\\d{4}-\\d\\d-\\d\\dT\\d\\d:\\d\\d:\\d\\d(\\.\\d+)?(([+-]\\d\\d:\\d\\d)|Z)?$", "com/mikeleitz/sidekick/bash/validation/bash-validation-timestamp-iso-regex.stg"),
    TIMESTAMP_ONE_TRUE(99, "Timestamp in the one true format", "Will match the date and time down to either the seconds level of detail or the milliseconds level of detail. For example it matches both 2020-02-08 12:23:60,123 and 2020-02-08 12:23:60", "com/mikeleitz/sidekick/bash/validation/bash-validation-timestamp-one-true-regex.stg"),
    URL(9, "URL", "Validates http or https urls.", "com/mikeleitz/sidekick/bash/validation/bash-validation-url-regex.stg"),
    IPV4(11, "URL", "Validates an ipv4 address", "com/mikeleitz/sidekick/bash/validation/bash-validation-ipv4-regex.stg"),
    IPV6(12, "Ipv6 address", "Validates an ipv6 address", "com/mikeleitz/sidekick/bash/validation/bash-validation-ipv6-regex.stg"),
    VALUE_REQUIRED(20, "Value required", "Value must be not null and not empty. Checks to make sure there is at least one non-whitespace anywhere in the input.", "com/mikeleitz/sidekick/bash/validation/bash-validation-required-regex.stg"),
    ALPHANUMERIC(21, "Alpha-numeric", "Alpha-numeric value. Any letter or number is accepted.", "com/mikeleitz/sidekick/bash/validation/bash-validation-alphanumeric-regex.stg"),
    GREATER_THAN(14, "Greater than", "", ""),
    GREATER_THAN_EQUAL(15, "Greater than or equal", "", ""),
    LESS_THAN(16, "Less than", "", ""),
    LESS_THAN_EQUAL(17, "Less than or equal", "", ""),
    CURRENCY(5, "Currency", "", ""),
    ENUMERATED(8, "Enumerated type", "", ""),
    ;

    private @NonNull Integer id;
    private @NonNull String validationName;
    private @NonNull String validationDescription;
    private @NonNull String stringTemplate;

    public static Optional<ValidationEnum> getById(Integer id) {
        Optional<ValidationEnum> returnValue = null;

        returnValue = Arrays.stream(ValidationEnum.values()).filter(r -> r.getId() == id).findFirst();

        return returnValue;
    }
}
