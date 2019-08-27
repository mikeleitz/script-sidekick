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

import com.mikeleitz.sidekick.base.Snippet;
import com.mikeleitz.sidekick.base.SnippetContext;
import org.stringtemplate.v4.ST;

import java.io.IOException;
import java.util.List;

/**
 * @author leitz@mikeleitz.com
 */
public class InputBashSnippet extends Snippet {
    private static final String templateLocation = "com/mikeleitz/sidekick/bash/bash-input-template.stg";

    public InputBashSnippet(SnippetContext context) throws IOException {
        super(templateLocation, context);
    }

    @Override
    public String getSnippet() {
        String returnValue = null;

        returnValue = buildTemplate();

        return returnValue;
    }

    @Override
    protected String buildTemplate() {
        String returnValue = null;

        ST snippetTemplate = new ST(template);

        List<Object> inputOptions = context.getAllValues().get("inputOptions");
        snippetTemplate.add("inputOptions", inputOptions);

        returnValue = snippetTemplate.render();

        return returnValue;
    }
}
