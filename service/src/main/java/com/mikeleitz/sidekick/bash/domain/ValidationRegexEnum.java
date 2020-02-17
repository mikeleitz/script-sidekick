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

@AllArgsConstructor
@Getter
public enum ValidationRegexEnum {
//    EMAIL(10, "Email validation", "", "^(?:(?:[\\w`~!#$%^&*\\-=+;:{}'|,?\\/]+(?:(?:\\.(?:\"(?:\\\\?[\\w`~!#$%^&*\\-=+;:{}'|,?\\/\\.()<>\\[\\] @]|\\\\\"|\\\\\\\\)*\"|[\\w`~!#$%^&*\\-=+;:{}'|,?\\/]+))*\\.[\\w`~!#$%^&*\\-=+;:{}'|,?\\/]+)?)|(?:\"(?:\\\\?[\\w`~!#$%^&*\\-=+;:{}'|,?\\/\\.()<>\\[\\] @]|\\\\\"|\\\\\\\\)+\"))@(?:[a-zA-Z\\d\\-]+(?:\\.[a-zA-Z\\d\\-]+)*|\\[\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\])$"),
    DATE(6, "Date validation dd/mm/yyy", "", "^(?:(?:19[0-9]{2}|200[0-9]|2010)([-/.]?)(?:(?:0?[1-9]|1[0-2])\\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\\1(?:29|30)|(?:0?[13578]|1[02])\\1(?:31))|(?:19(?:0[48]|[2648][048]|[13579][26])|2000|200[48])([-/.]?)0?2\\2(?:29))$"),
    SIGNED_INTEGER(1, "Signed integer", "", "^-?[0-9]{1,10}$"),
    UNSIGNED_INTEGER(19, "Unsigned integer", "", "^[0-9]{1,10}$"),
    SIGNED_REAL(3, "Signed real number", "", "^-?[0-9]{1,10}.?[0-9]{0,10}$"),
    UNSIGNED_REAL(18, "Unsigned real number", "", "^[0-9]{1,10}.?[0-9]{0,10}$"),
    BOOLEAN(2, "Boolean", "", "^([01]|true|false|on|off|[y]|[n]|yes|no)$"),
    TIMESTAMP_ISO(7, "Timestamp in ISO format", "Matches the ISO 8601 timestamp format with a 'T' separating the date from the time and the timezone offset. For example, 2020-02-08T23:16:30+00:00", "^\\d{4}-\\d\\d-\\d\\dT\\d\\d:\\d\\d:\\d\\d(\\.\\d+)?(([+-]\\d\\d:\\d\\d)|Z)?$"),
    TIMESTAMP_ONE_TRUE(99, "Timestamp in the one true format", "Will match the date and time down to either the seconds level of detail or the milliseconds level of detail. For example it matches both 2020-02-08 12:23:60,123 and 2020-02-08 12:23:60", "^\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}(?:,\\d{1,3})?$"),
    URL(9, "URL", "Validates http or https urls.", "^(https?:\\/\\/)?([\\da-z\\.-]+\\.[a-z\\.]{2,6}|[\\d\\.]+)([\\/:?=&#]{1}[\\da-z\\.-]+)*[\\/\\?]?$"),
    IPV4(11, "URL", "Validates an ipv4 address", "^\\b(?:(?:2(?:[0-4][0-9]|5[0-5])|[0-1]?[0-9]?[0-9])\\.){3}(?:(?:2([0-4][0-9]|5[0-5])|[0-1]?[0-9]?[0-9]))\\b$"),
    IPV6(12, "Ipv6 address", "Validates an ipv6 address", "^\\b(?:(?:2(?:[0-4][0-9]|5[0-5])|[0-1]?[0-9]?[0-9])\\.){3}(?:(?:2([0-4][0-9]|5[0-5])|[0-1]?[0-9]?[0-9]))\\b$"),
    VALUE_REQUIRED(20, "Value required", "Value must be not null and not empty. Checks to make sure there is at least one non-whitespace anywhere in the input.", "^\\s*\\S+.*$"),
    ALPHANUMERIC(21, "Alpha-numeric", "Alpha-numeric value. Any letter or number is accepted.", "^[a-zA-Z0-9]+$"),
    CUSTOM_REGEX (13, "Custom user specified regex", "User specified via arg named 'regex'.", null),
    STRING (4, "Some string?", "Some misc string value. Not sure we need this.'.", null),
    ;

    //language=RegExp
    private @NonNull Integer id;
    private @NonNull String regexName;
    private @NonNull String regexDescription;
    private String regexValue;

    public static Optional<ValidationRegexEnum> getById(Integer id) {
        Optional<ValidationRegexEnum> returnValue = null;

        returnValue = Arrays.stream(ValidationRegexEnum.values()).filter(r -> r.getId() == id).findFirst();

        return returnValue;
    }
}
