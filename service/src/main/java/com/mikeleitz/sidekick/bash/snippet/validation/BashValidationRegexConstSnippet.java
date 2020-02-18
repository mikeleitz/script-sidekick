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
import com.mikeleitz.sidekick.bash.domain.ValidationRegexEnum;
import io.micrometer.core.instrument.util.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author leitz@mikeleitz.com
 */
public class BashValidationRegexConstSnippet extends Snippet {
    private static final String TEMPLATE_LOCATION = "com/mikeleitz/sidekick/bash/validation/bash-validation-regex-const.stg";

    public BashValidationRegexConstSnippet(SnippetContext context) throws IOException {
        super(TEMPLATE_LOCATION, context);

        context.addValue("allRegexValidations", _getAllRegexValidations());
    }

    /**
     *
     * @return all the regex validation names and their corresponding regex values.
     */
    protected List<ImmutablePair> _getAllRegexValidations() {
        List<ImmutablePair> returnValue;

        returnValue = Arrays.stream(ValidationRegexEnum.values())
                .filter(v -> StringUtils.isNotBlank(v.getRegexValue()))
                .map(v -> new ImmutablePair(v.name(), v.getRegexValue()))
                .collect(Collectors.toList());

        return returnValue;
    }
}
