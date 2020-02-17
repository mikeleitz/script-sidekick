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
package com.mikeleitz.sidekick.bash.domain;

import com.mikeleitz.sidekick.base.application.ApplicationInput;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import lombok.Singular;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leitz@mikeleitz.com
 */
@Data
@Builder
@Setter(AccessLevel.NONE)
public class BashOption implements ApplicationInput {
    public static BashOption VERBOSE = BashOption.builder().shortName('v').longName("verbose").optionHasValue(false).helpText("verbose operation").build();
    public static BashOption HELP = BashOption.builder().shortName('h').longName("help").optionHasValue(false).helpText("give this help list").build();

    private Long id;
    private Character shortName;
    @NonNull private String longName;

    @Builder.Default private Boolean optionHasValue = true;
    private String defaultValue;
    @Builder.Default @NonNull private String helpText = "";
    @Singular private List<BashValidation> validations;

    @Override
    public String getVariableName() {
        return createVariableName(this);
    }

    @Override
    public String getIsSetVariableName() {
        return createVariableSetName(this);
    }

    /**
     * true if this bash input needs a value to be useful. This doesn't mean the the bash option
     * is required, it just means the option has a user input associated with it.
     *
     * Returns true if for example,
     *   -f ./my-file.txt
     *
     * This return value doesn't specify if the option is required for the script to run however.
     * That's done via a required validation.
     *
     * @return
     */
    public Boolean optionHasValue() {
        return optionHasValue;
    }

    public void addBashValidation(BashValidation bashValidation) {
        if (this.validations == null) {
            this.validations = new ArrayList<>();
        }

        this.validations.add(bashValidation);
    }

    private String createVariableName(BashOption bashOption) {
        return makeVariableNameAcceptableToBash(bashOption.getLongName()) + "_ARG";
    }

    private String createVariableSetName(BashOption bashOption) {
        return makeVariableNameAcceptableToBash(bashOption.getLongName()) + "_OPTION_CHOSEN";
    }

    private String makeVariableNameAcceptableToBash(String variableName) {
        String returnValue = null;

        returnValue = variableName.toUpperCase();

        return returnValue;
    }
}
