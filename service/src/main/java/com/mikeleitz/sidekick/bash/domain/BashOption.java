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
import java.util.Optional;

/**
 * @author leitz@mikeleitz.com
 */
@Data
@Builder
@Setter(AccessLevel.NONE)
public class BashOption implements ApplicationInput {
    public static BashOption VERBOSE = BashOption.builder().shortName('v').longName("verbose").helpText("verbose operation").build();
    public static BashOption HELP = BashOption.builder().shortName('h').longName("help").helpText("give this help list").build();

    private Long id;
    private Character shortName;
    @NonNull private String longName;
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

    public Boolean getDecree() {
        // if the validation has required, return true;
        Optional<BashValidation> requiredValidation = validations.stream().filter(v -> v.getId() == 1L).findFirst();

        if (requiredValidation.isPresent()) {
            return true;
        } else {
            return false;
        }
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
