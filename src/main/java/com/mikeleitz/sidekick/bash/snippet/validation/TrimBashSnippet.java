/**
 * Copyright 2019 Michael Leitz
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

import com.mikeleitz.sidekick.base.Snippet;
import com.mikeleitz.sidekick.base.SnippetContext;
import org.stringtemplate.v4.ST;

import java.io.IOException;

/**
 * @author leitz@mikeleitz.com
 */
public class TrimBashSnippet extends Snippet {
    private static final String TEMPLATE_LOCATION = "com/mikeleitz/sidekick/bash/bash-validation-trim-template.stg";

    private String variableName;        // The name of the variable.
    private String isVariableSpecified; // Each variable has a sister variable that indicates if it was passed in or not.  If the variable was name there's a second variable called name_option_chosen=1 or 0.

    public TrimBashSnippet(SnippetContext context, String variableName, String isVariableSpecified) throws
            IOException {
        super(TEMPLATE_LOCATION, context);

        this.variableName = variableName;
        this.isVariableSpecified = isVariableSpecified;
    }

    @Override
    public String buildTemplate() {
        String returnValue = null;

        ST snippetTemplate = new ST(template);
        snippetTemplate.add("variable", variableName);
        snippetTemplate.add("isVariableSpecified", isVariableSpecified);

        returnValue = snippetTemplate.render();

        return returnValue;
    }
}
