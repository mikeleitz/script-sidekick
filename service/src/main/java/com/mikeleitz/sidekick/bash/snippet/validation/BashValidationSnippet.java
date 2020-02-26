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
import com.mikeleitz.sidekick.bash.domain.BashOption;
import com.mikeleitz.sidekick.bash.domain.BashValidation;
import com.mikeleitz.sidekick.bash.domain.ValidationEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class creates all the common validations used in the bash script. It loops through all the validation enums and
 * creates a super-snippet of all of the validations.
 *
 * @author leitz@mikeleitz.com
 */
@Slf4j
public class BashValidationSnippet extends CompositeSnippet {
    public BashValidationSnippet(SnippetContext context) throws IOException {
        super(context);

        // Handle generic regex validations. These are most of our framework's validations.
        Arrays.stream(ValidationEnum.values())
                .filter(t -> StringUtils.isNotBlank(t.getStringTemplate()))
                .filter(t -> t.isRegexType())
                .forEach(t -> this
                        .addSnippet(new GenericSnippet(context, t.getStringTemplate(), t.getValidationName())));

        Set<BashOption> allBashOptions = (Set<BashOption>) context.getAllValues().get("bashOptions");

        // Handle the custom regex validation. It has special handling.
        if (CollectionUtils.isNotEmpty(allBashOptions)) {
            List<BashOption> bashOptionsWithCustomRegex = allBashOptions.stream()
                    .filter(o -> o.optionUsesValidation(ValidationEnum.CUSTOM_REGEX))
                    .collect(Collectors.toList());

            GenericSnippet customRegexenSnippet = createCustomBashRegexenSnippet(context, bashOptionsWithCustomRegex);
            this.addSnippet(customRegexenSnippet);
        }

        // Handle number range.
        List<ValidationEnum> integerRangeComparisons = List
                .of(ValidationEnum.GREATER_THAN, ValidationEnum.GREATER_THAN_EQUAL, ValidationEnum.LESS_THAN,
                        ValidationEnum.LESS_THAN_EQUAL);

        if (CollectionUtils.isNotEmpty(allBashOptions)) {
            for (BashOption bashOption : allBashOptions) {
                NumberRangeValidationData numberRangeValidationData = createNumberRangeValidation(bashOption);
            }

            // TODO handle range validations: GREATER_THAN, GREATER_THAN_EQUAL, LESS_THAN, and LESS_THAN_EQUAL.
            // GenericSnippet customRegexenSnippet = createCustomBashRegexenSnippet(context, bashOptionsWithCustomRegex);
            // this.addSnippet(customRegexenSnippet);
        }

        log.info("Have a total of [{}] validation snippets.", this.totalSnippets());
    }

    /**
     * See if this option has any of the range options. It can contain at most
     * one of [GREATER_THAN, GREATER_THAN_EQUAL] and also at most one of [LESS_THAN, LESS_THAN_EQUAL].
     *
     * @param bashOption
     * @return
     */
    protected NumberRangeValidationData createNumberRangeValidation(BashOption bashOption) {

        NumberRangeValidationData numberRangeValidationData = new NumberRangeValidationData();
        numberRangeValidationData.setOptionName(bashOption.getLongNameBashFriendly());

        // See if this option has either the greater than or the greater than equal validation.
        Optional<BashValidation> lowerBoundOption = bashOption.getValidation(ValidationEnum.GREATER_THAN);
        if (lowerBoundOption.isEmpty()) {
            lowerBoundOption = bashOption.getValidation(ValidationEnum.GREATER_THAN_EQUAL);
        }

        if (lowerBoundOption.isPresent()) {
            // We have one of the lower bound validations.
            BashValidation lowerBoundValidation = lowerBoundOption.get();
            if (lowerBoundValidation.getValidationEnum() == ValidationEnum.GREATER_THAN_EQUAL) {
                numberRangeValidationData.isLowerBoundInclusive(true);
            }

            Pair<String, String> value = lowerBoundValidation.getPairForKey("value").orElseThrow(IllegalArgumentException::new);
            if (value != null) {
                numberRangeValidationData.setLowerBound(Integer.parseInt(value.getValue()));
            } else {
                throw new IllegalArgumentException(String.format("Missing required value for validation %s.", lowerBoundValidation.getValidationEnum().name()));
            }
        }

        // See if we have an upper limit specified.
        Optional<BashValidation> upperBoundOption = bashOption.getValidation(ValidationEnum.LESS_THAN);
        if (upperBoundOption.isEmpty()) {
            upperBoundOption = bashOption.getValidation(ValidationEnum.LESS_THAN_EQUAL);
        }

        if (upperBoundOption.isPresent()) {
            // We have an upper bound.
            BashValidation upperBoundValidation = upperBoundOption.get();
            if (upperBoundValidation.getValidationEnum() == ValidationEnum.GREATER_THAN_EQUAL) {
                numberRangeValidationData.isUpperBoundInclusive(true);
            }

            Pair<String, String> value = upperBoundValidation.getPairForKey("value").orElseThrow(IllegalArgumentException::new);

            if (value != null) {
                numberRangeValidationData.setUpperBound(Integer.parseInt(value.getValue()));
            } else {
                throw new IllegalArgumentException(String.format("Missing required value for validation %s.", upperBoundValidation.getValidationEnum().name()));
            }
        }



        // The command to execute is
        //  resul=0
        //  .validateIntegerInRange resul 4 2 0 5 0
        //  .validateIntegerInRange
        //      <the-variable-for-the-result>
        //      <the value to check>
        //      <greater than this number>
        //      <also equals?>
        //      <less than this number>
        //      <also equals?>
        //  echo 'Result: ' "$resul"

        return numberRangeValidationData;
    }

    private GenericSnippet createCustomBashRegexenSnippet(SnippetContext context,
            List<BashOption> allCustomRegexenSpecified) {
        GenericSnippet returnValue;

        List<CustomRegex> allCustomRegex = allCustomRegexenSpecified.stream()
                .filter(o -> o.optionUsesValidation(ValidationEnum.CUSTOM_REGEX))
                .filter(o -> o.getValidation(ValidationEnum.CUSTOM_REGEX).get().getPairForKey("value").isPresent())
                .map(o -> new CustomRegex(o.getLongNameBashFriendly(),
                        o.getValidation(ValidationEnum.CUSTOM_REGEX).get().getPairForKey("value").get().getRight()))
                .collect(Collectors.toList());

        context.addValue("bashCustomRegexValidations", allCustomRegex);
        returnValue = new GenericSnippet(context, ValidationEnum.CUSTOM_REGEX.getStringTemplate(),
                ValidationEnum.CUSTOM_REGEX.name());

        return returnValue;
    }

    @Data
    @AllArgsConstructor
    public class CustomRegex {
        private String optionName;
        private String regex;
    }

    @Data
    public class NumberRangeValidationData {
        private String optionName;
        private Number lowerBound;
        private @Accessors(fluent = true)
        Boolean isLowerBoundInclusive;
        private Number upperBound;
        private @Accessors(fluent = true)
        Boolean isUpperBoundInclusive;
    }
}
