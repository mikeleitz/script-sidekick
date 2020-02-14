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
import com.mikeleitz.sidekick.bash.domain.BashOption;
import com.mikeleitz.sidekick.bash.snippet.validation.BashValidationSnippetFactory;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author leitz@mikeleitz.com
 */
public class InputBashSnippet extends Snippet {
    private static final String TEMPLATE_LOCATION = "com/mikeleitz/sidekick/bash/bash-enhanced-getopts-input-template.stg";
    private static final String VERBOSE_SWITCH_STATEMENT = "  -v | --verbose)\n"
            + "    __VERBOSE=7 # Verbose indicates logging at debug.\n"
            + "    shift\n"
            + "    ;;";

    private static final String HELP_SWITCH_STATEMENT = "  -h | --help)\n"
            + "    .printHelp # Show help\n"
            + "    ;;";

    private Set<BashOption> allInputOptions;
    private List<Snippet> allValidationSnippets = new ArrayList<>();

    private BashValidationSnippetFactory bashValidationSnippetFactory = new BashValidationSnippetFactory();

    public InputBashSnippet(SnippetContext context, @NonNull Set<BashOption> allInputOptions) throws IOException {
        super(TEMPLATE_LOCATION, context);
        this.allInputOptions = allInputOptions;

        context.addValue("bashOptions", allInputOptions);
        addDomainValuesToSnippetContext(context);

        for (BashOption bashOption : allInputOptions) {
            List<Snippet> validationSnippets = createValidationSnippets(bashOption);
            allValidationSnippets.addAll(validationSnippets);
        }
    }

    protected List<Snippet> createValidationSnippets(BashOption bashOption) throws IOException {
        List<Snippet> returnValue = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(bashOption.getValidations())) {
          /*
          TODO: update after refactor.
          for (BashValidationSnippet validationEnum : bashOption.getValidations()) {
                Snippet validationSnippet = bashValidationFactory.createValidationSnippet(validationEnum, context, bashOption);
                returnValue.add(validationSnippet);
            }

            */
        }

        return returnValue;
    }

    protected void addDomainValuesToSnippetContext(SnippetContext context) {
        List<String> allShortOpts = new ArrayList<>();
        List<String> allLongOpts = new ArrayList<>();
        List<String> allSwitchStatements = new ArrayList<>();
        List<String> allVariables = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(allInputOptions)) {
            for (BashOption bashOption : allInputOptions) {
                if (bashOption.getShortName() != null) {
                    String shortOptArgsValue = bashOption.getDecree() ?
                            bashOption.getShortName() + ":" : bashOption.getShortName() + "";
                    allShortOpts.add(shortOptArgsValue);
                }

                if (StringUtils.isNotBlank(bashOption.getLongName())) {
                    String longOptArgsValue = bashOption.getDecree() ?
                            bashOption.getLongName() + ":" : bashOption.getLongName() + "";
                    allLongOpts.add(longOptArgsValue);

                    allVariables.add(bashOption.getIsSetVariableName());

                    if (bashOption.getDecree()) {
                        allVariables.add(bashOption.getVariableName());
                    }
                }

                String switchStatement;

                if (bashOption.equals(BashOption.HELP)) {
                    switchStatement = HELP_SWITCH_STATEMENT;
                } else if (bashOption.equals(BashOption.VERBOSE)) {
                    switchStatement = VERBOSE_SWITCH_STATEMENT;
                } else {
                    switchStatement = createSwitchStatement(bashOption);
                }

                if (StringUtils.isNotBlank(switchStatement)) {
                    allSwitchStatements.add(switchStatement);
                }
            }
        }

        context.addValue("allShortOpts", allShortOpts);
        context.addValue("allLongOpts", allLongOpts);
        context.addValue("allSwitchStatements", allSwitchStatements);
        context.addValue("allVariables", allVariables);
    }

    protected String createSwitchStatement(BashOption bashOption) {
        String returnValue = null;
        returnValue = "";

        returnValue += bashOption.getShortName() != null ?
                "-" + bashOption.getShortName() + " | " + "--" + bashOption.getLongName() + ")" :
                "-" + bashOption.getLongName() + ")";
        returnValue += "\n";

        returnValue += bashOption.getIsSetVariableName() + "=1";
        returnValue += "\n";

        if (bashOption.getDecree()) {
            returnValue += bashOption.getVariableName() + "=\"$2\"";
            returnValue += "\n";
            returnValue += "shift 2";
            returnValue += "\n";
        }
        else {
            returnValue += "shift";
            returnValue += "\n";
        }

        returnValue += ";;";

        return returnValue;
    }

    @Override
    protected String buildTemplate() {
        String returnValue = null;

        returnValue = super.buildTemplate();

        // Create all validation snippets.
        for (Snippet validationSnippet : allValidationSnippets) {
            returnValue += "\n";
            returnValue += validationSnippet.getSnippet();
        }

        return returnValue;
    }
}
