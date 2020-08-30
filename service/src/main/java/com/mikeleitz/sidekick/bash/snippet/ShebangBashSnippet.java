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
import com.mikeleitz.sidekick.bash.domain.ShellOptionEnum;

import java.io.IOException;

/**
 * @author leitz@mikeleitz.com
 */
public class ShebangBashSnippet extends Snippet {
    private static final String BASH_TEMPLATE_LOCATION = "com/mikeleitz/sidekick/bash/lickety-script/bash-shebang-template.stg";

    public ShebangBashSnippet(SnippetContext context, ShellOptionEnum shellOptionEnum) throws IOException {
        super(context);
        setSnippetTemplate(getLocation(shellOptionEnum));

        context.addValue("shell", shellOptionEnum);
    }

    private static String getLocation(ShellOptionEnum shellOptionEnum) {
        String returnValue = null;

        switch (shellOptionEnum) {
            case BASH :
                returnValue = BASH_TEMPLATE_LOCATION;
                break;
            default :
                throw new IllegalArgumentException(String.format("ShellOptionEnum [%s] not supported.  Can't create Shebang snippet.", shellOptionEnum));
        }

        return returnValue;
    }
}
