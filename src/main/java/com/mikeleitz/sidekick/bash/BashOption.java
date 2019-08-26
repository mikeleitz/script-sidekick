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
package com.mikeleitz.sidekick.bash;

import com.mikeleitz.sidekick.base.BuilderResult;
import com.mikeleitz.sidekick.base.Snippet;
import com.mikeleitz.sidekick.base.Validation;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Singular;

import java.util.List;

/**
 * @author leitz@mikeleitz.com
 */
@Data
@Builder
public class BashOption implements BuilderResult {
    private Character shortName;
    @NonNull private String longName;
    private String helpDescription;
    @Singular private List<Validation> validations;

    @Override
    public Object getResult() {
        Object returnValue = null;

        String variableName = makeVariableNameAcceptableToBash(longName);
        Snippet resultSnippet = new Snippet("\n" + variableName + "=\n");

        for (Validation validation : validations) {
            Snippet validationSnippet = validation.createValidation(variableName, null);
            resultSnippet.merge(validationSnippet);
        }

        returnValue = resultSnippet.getResult();
        return returnValue;
    }

    protected String makeVariableNameAcceptableToBash(String variableName) {
        String returnValue = null;

        returnValue = variableName.toUpperCase();

        return returnValue;
    }
}
