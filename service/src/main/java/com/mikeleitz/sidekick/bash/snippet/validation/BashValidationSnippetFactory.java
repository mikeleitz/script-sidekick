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
package com.mikeleitz.sidekick.bash.snippet.validation;

import com.mikeleitz.sidekick.base.Snippet;
import com.mikeleitz.sidekick.base.SnippetContext;
import com.mikeleitz.sidekick.base.ValidationFactory;
import com.mikeleitz.sidekick.base.application.ApplicationInput;
import com.mikeleitz.sidekick.bash.domain.BashValidation;

import java.io.IOException;
import java.util.Set;

/**
 * @author leitz@mikeleitz.com
 */
public class BashValidationSnippetFactory extends ValidationFactory {

    private static final Set<Integer> BASH_LOGIC_VALIDATIONS = Set.of(14, 15, 16, 17, 5, 8);
    private static final Set<Integer> BASH_REGEX_VALIDATIONS = Set.of(10, 6, 1, 19, 3, 18, 2, 7, 9, 11, 20, 21);

    @Override
    public Snippet createValidationSnippet(BashValidation validationType, SnippetContext snippetContext, ApplicationInput applicationInput) throws IOException {
        Snippet returnValue = null;
//
//
//        if (BASH_LOGIC_VALIDATIONS.contains(id)) {
//            returnValue =
//        } else if (BASH_REGEX_VALIDATIONS.contains(id)) {
//
//        }


/*
        switch (validationType.getId()) {
            case "notnull":
                returnValue = createNewNotNullBashValidation(snippetContext, applicationInput);
                break;
            case "trim":
                returnValue = createNewTrimBashValidation(snippetContext, applicationInput);
                break;
            default:
                throw new IllegalArgumentException(String.format("BashValidationType %s not supported.", validationType));
        }*/

        return returnValue;
    }
/*

    private String validationTypeForId(Long id) {
        String returnValue = null;

        if (BASH_LOGIC_VALIDATIONS.contains(id)) {
            returnValue =
        } else if (BASH_REGEX_VALIDATIONS.contains(id)) {

        }

        return returnValue;
    }
*/

    private Snippet createNewNotNullBashValidation(SnippetContext snippetContext, ApplicationInput applicationInput)
            throws IOException {
        Snippet returnValue = null;

        String variableName = applicationInput.getVariableName();
        String variableSetName = applicationInput.getIsSetVariableName();

        returnValue = new NotNullBashSnippet(snippetContext, variableName, variableSetName);

        return returnValue;
    }

    private Snippet createNewTrimBashValidation(SnippetContext snippetContext, ApplicationInput applicationInput)
            throws IOException {
        Snippet returnValue = null;

        String variableName = applicationInput.getVariableName();
        String variableSetName = applicationInput.getIsSetVariableName();

        returnValue = new TrimBashSnippet(snippetContext, variableName, variableSetName);

        return returnValue;
    }
}
