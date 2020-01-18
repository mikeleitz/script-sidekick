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
package com.mikeleitz.sidekick.bash.snippet;

import com.mikeleitz.sidekick.base.Snippet;
import com.mikeleitz.sidekick.base.SnippetContext;

import java.io.IOException;

/**
 * @author leitz@mikeleitz.com
 */
public class ProcessingBashSnippet extends Snippet {
    private static final String TEMPLATE_LOCATION = "com/mikeleitz/sidekick/bash/bash-processing-template.stg";

    public ProcessingBashSnippet(SnippetContext context) throws IOException {
        super(TEMPLATE_LOCATION, context);
    }
}
