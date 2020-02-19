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

import com.mikeleitz.sidekick.base.CompositeSnippet;
import com.mikeleitz.sidekick.base.GenericSnippet;
import com.mikeleitz.sidekick.base.SnippetContext;
import com.mikeleitz.sidekick.bash.domain.ValidationEnum;
import io.micrometer.core.instrument.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;

/**
 * This class creates all the common validations used in the bash script.
 * It loops through all the validation enums and creates a super-snippet
 * of all of the validations.
 *
 * @author leitz@mikeleitz.com
 */
public class BashValidationSnippet extends CompositeSnippet {
    public BashValidationSnippet(SnippetContext context) throws IOException {
        super(context);

        Arrays.stream(ValidationEnum.values())
                .filter(t -> StringUtils
                .isNotBlank(t.getStringTemplate()))
                .forEach(t -> this.addSnippet(new GenericSnippet(context, t.getStringTemplate(), t.getValidationName())));
    }
}
