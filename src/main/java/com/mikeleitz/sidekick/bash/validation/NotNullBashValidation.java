/*
 * Copyright (c) 2019, Michael Leitz
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mikeleitz.sidekick.bash.validation;

import com.mikeleitz.sidekick.base.Snippet;
import com.mikeleitz.sidekick.base.Validation;

import java.util.Map;

/**
 * @author leitz@mikeleitz.com
 */
public class NotNullBashValidation implements Validation {
    private static final String NOT_NULL_VALIDATION = "if [ -z \"${%s}\" ]\n"
            + "then\n"
            + "      .log 1   \"%s can not be NULL.\"\n"
            + " exit 1\n"
            + "else\n"
            + "      .log 7  \"%s is NOT NULL and passes the validation.\"\n"
            + "fi";

    @Override
    public Snippet createValidation(String variableName, Map<String, Object> validationParams) {
        Snippet returnValue = null;

/*
        String snippetValue = String.format(NOT_NULL_VALIDATION, variableName, variableName, variableName);
        returnValue = new Snippet(snippetValue);
*/

        return returnValue;
    }
}
