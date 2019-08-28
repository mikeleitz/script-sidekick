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
package com.mikeleitz.sidekick.bash.snippet.validation;

import com.mikeleitz.sidekick.base.ApplicationInputValue;
import com.mikeleitz.sidekick.base.Snippet;
import com.mikeleitz.sidekick.base.SnippetContext;
import com.mikeleitz.sidekick.base.ValidationFactory;

import java.io.IOException;

/**
 * @author leitz@mikeleitz.com
 */
public class BashValidationFactory extends ValidationFactory<BashValidationEnum> {

    @Override
    public Snippet createValidationSnippet(BashValidationEnum validationType, SnippetContext snippetContext, ApplicationInputValue applicationInputValue) throws IOException {
        Snippet returnValue = null;

        switch (validationType) {
            case NOT_NULL:
                returnValue = createNewNotNullBashValidation(snippetContext, applicationInputValue);
                break;
            default:
                throw new IllegalArgumentException(String.format("BashValidationType %s not supported.", validationType));
        }

        return returnValue;
    }

    private Snippet createNewNotNullBashValidation(SnippetContext snippetContext, ApplicationInputValue applicationInputValue)
            throws IOException {
        Snippet returnValue = null;

        String variableName = applicationInputValue.getVariableName();
        String variableSetName = applicationInputValue.getIsSetVariableName();

        returnValue = new NotNullBashSnippet(snippetContext, variableName, variableSetName);

        return returnValue;
    }
}
