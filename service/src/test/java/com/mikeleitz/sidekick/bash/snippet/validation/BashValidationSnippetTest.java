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

import com.mikeleitz.sidekick.base.SnippetContext;
import com.mikeleitz.sidekick.bash.domain.BashOption;
import com.mikeleitz.sidekick.bash.domain.BashValidation;
import com.mikeleitz.sidekick.bash.domain.ValidationEnum;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author leitz@mikeleitz.com
 */
class BashValidationSnippetTest {
    @SneakyThrows
    @Test
    @DisplayName("No validation specified")
    void testCreateNumberRangeValidation_noValidation() {
        BashValidationSnippet bashValidationSnippet = new BashValidationSnippet(new SnippetContext());

        BashOption optionWithoutRangeValidation = BashOption.builder()
                .longName("test-validation")
                .build();

        BashValidationSnippet.NumberRangeValidationData numberRangeValidation = bashValidationSnippet.createNumberRangeValidation(optionWithoutRangeValidation);

        Assertions.assertNull(numberRangeValidation, "When option doesn't have range validation, expect null.");
    }

    @SneakyThrows
    @Test
    @DisplayName("Greater than but missing value arg")
    void testCreateNumberRangeValidation_greaterThanNoArg() {
        BashValidationSnippet bashValidationSnippet = new BashValidationSnippet(new SnippetContext());

        ValidationEnum validationEnum = ValidationEnum.GREATER_THAN;
        BashValidation validation = BashValidation.builder()
                .id(validationEnum.getId())
                .name(validationEnum.name())
                .args(List.of())
                .validationEnum(validationEnum)
                .build();

        BashOption optionWithoutRangeValidation = BashOption.builder()
                .longName("test-validation")
                .bashValidation(validation)
                .build();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    BashValidationSnippet.NumberRangeValidationData numberRangeValidation = bashValidationSnippet
                            .createNumberRangeValidation(optionWithoutRangeValidation);
                }
        );
    }

    @SneakyThrows
    @Test
    @DisplayName("Greater than with incorrect arg")
    void testCreateNumberRangeValidation_greaterThanIllegalArg() {
        BashValidationSnippet bashValidationSnippet = new BashValidationSnippet(new SnippetContext());

        ValidationEnum validationEnum = ValidationEnum.GREATER_THAN;
        BashValidation validation = BashValidation.builder()
                .id(validationEnum.getId())
                .name(validationEnum.name())
                .args(List.of(new ImmutablePair<>("value", "invalid-value")))
                .validationEnum(validationEnum)
                .build();

        BashOption optionWithoutRangeValidation = BashOption.builder()
                .longName("test-validation")
                .bashValidation(validation)
                .build();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    BashValidationSnippet.NumberRangeValidationData numberRangeValidation = bashValidationSnippet
                            .createNumberRangeValidation(optionWithoutRangeValidation);
                }
        );
    }

    @SneakyThrows
    @Test
    @DisplayName("Greater than with valid arg")
    void testCreateNumberRangeValidation_greaterThanWithArg() {
        BashValidationSnippet bashValidationSnippet = new BashValidationSnippet(new SnippetContext());

        ValidationEnum validationEnum = ValidationEnum.GREATER_THAN;
        BashValidation validation = BashValidation.builder()
                .id(validationEnum.getId())
                .name(validationEnum.name())
                .args(List.of(new ImmutablePair<>("value", "6")))
                .validationEnum(validationEnum)
                .build();

        BashOption optionWithoutRangeValidation = BashOption.builder()
                .longName("test-validation")
                .bashValidation(validation)
                .build();

        BashValidationSnippet.NumberRangeValidationData numberRangeValidation = bashValidationSnippet
                .createNumberRangeValidation(optionWithoutRangeValidation);

        Assertions.assertNotNull(numberRangeValidation,
                "When option has at least one range validation, expect not null result.");

        Assertions.assertEquals(6, numberRangeValidation.getLowerBound());
        Assertions.assertFalse(numberRangeValidation.isLowerBoundInclusive());
        Assertions.assertNull(numberRangeValidation.getUpperBound());
        Assertions.assertNull(numberRangeValidation.isUpperBoundInclusive());
    }

    @SneakyThrows
    @Test
    @DisplayName("Greater than equal with valid arg")
    void testCreateNumberRangeValidation_greaterThanEqualWithArg() {
        BashValidationSnippet bashValidationSnippet = new BashValidationSnippet(new SnippetContext());

        ValidationEnum validationEnum = ValidationEnum.GREATER_THAN_EQUAL;
        BashValidation validation = BashValidation.builder()
                .id(validationEnum.getId())
                .name(validationEnum.name())
                .args(List.of(new ImmutablePair<>("value", "6")))
                .validationEnum(validationEnum)
                .build();

        BashOption optionWithoutRangeValidation = BashOption.builder()
                .longName("test-validation")
                .bashValidation(validation)
                .build();

        BashValidationSnippet.NumberRangeValidationData numberRangeValidation = bashValidationSnippet
                .createNumberRangeValidation(optionWithoutRangeValidation);

        Assertions.assertNotNull(numberRangeValidation,
                "When option has at least one range validation, expect not null result.");

        Assertions.assertEquals(6, numberRangeValidation.getLowerBound());
        Assertions.assertTrue(numberRangeValidation.isLowerBoundInclusive());
        Assertions.assertNull(numberRangeValidation.getUpperBound());
        Assertions.assertNull(numberRangeValidation.isUpperBoundInclusive());
    }

    @SneakyThrows
    @Test
    @DisplayName("Less than but missing value arg")
    void testCreateNumberRangeValidation_lessThanNoArg() {
        BashValidationSnippet bashValidationSnippet = new BashValidationSnippet(new SnippetContext());

        ValidationEnum validationEnum = ValidationEnum.LESS_THAN;
        BashValidation validation = BashValidation.builder()
                .id(validationEnum.getId())
                .name(validationEnum.name())
                .args(List.of())
                .validationEnum(validationEnum)
                .build();

        BashOption optionWithoutRangeValidation = BashOption.builder()
                .longName("test-validation")
                .bashValidation(validation)
                .build();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    BashValidationSnippet.NumberRangeValidationData numberRangeValidation = bashValidationSnippet
                            .createNumberRangeValidation(optionWithoutRangeValidation);
                }
        );
    }

    @SneakyThrows
    @Test
    @DisplayName("Less than with incorrect arg")
    void testCreateNumberRangeValidation_lessThanIllegalArg() {
        BashValidationSnippet bashValidationSnippet = new BashValidationSnippet(new SnippetContext());

        ValidationEnum validationEnum = ValidationEnum.LESS_THAN;
        BashValidation validation = BashValidation.builder()
                .id(validationEnum.getId())
                .name(validationEnum.name())
                .args(List.of(new ImmutablePair<>("value", "invalid-value")))
                .validationEnum(validationEnum)
                .build();

        BashOption optionWithoutRangeValidation = BashOption.builder()
                .longName("test-validation")
                .bashValidation(validation)
                .build();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    BashValidationSnippet.NumberRangeValidationData numberRangeValidation = bashValidationSnippet
                            .createNumberRangeValidation(optionWithoutRangeValidation);
                }
        );
    }

    @SneakyThrows
    @Test
    @DisplayName("Less than with valid arg")
    void testCreateNumberRangeValidation_lessThanWithArg() {
        BashValidationSnippet bashValidationSnippet = new BashValidationSnippet(new SnippetContext());

        ValidationEnum validationEnum = ValidationEnum.LESS_THAN;
        BashValidation validation = BashValidation.builder()
                .id(validationEnum.getId())
                .name(validationEnum.name())
                .args(List.of(new ImmutablePair<>("value", "6")))
                .validationEnum(validationEnum)
                .build();

        BashOption optionWithoutRangeValidation = BashOption.builder()
                .longName("test-validation")
                .bashValidation(validation)
                .build();

        BashValidationSnippet.NumberRangeValidationData numberRangeValidation = bashValidationSnippet
                .createNumberRangeValidation(optionWithoutRangeValidation);

        Assertions.assertNotNull(numberRangeValidation,
                "When option has at least one range validation, expect not null result.");

        Assertions.assertNull(numberRangeValidation.getLowerBound());
        Assertions.assertNull(numberRangeValidation.isLowerBoundInclusive());
        Assertions.assertEquals(6, numberRangeValidation.getUpperBound());
        Assertions.assertFalse(numberRangeValidation.isUpperBoundInclusive());
    }

    @SneakyThrows
    @Test
    @DisplayName("Less than equal with valid arg")
    void testCreateNumberRangeValidation_lessThanEqualWithArg() {
        BashValidationSnippet bashValidationSnippet = new BashValidationSnippet(new SnippetContext());

        ValidationEnum validationEnum = ValidationEnum.LESS_THAN_EQUAL;
        BashValidation validation = BashValidation.builder()
                .id(validationEnum.getId())
                .name(validationEnum.name())
                .args(List.of(new ImmutablePair<>("value", "6")))
                .validationEnum(validationEnum)
                .build();

        BashOption optionWithoutRangeValidation = BashOption.builder()
                .longName("test-validation")
                .bashValidation(validation)
                .build();

        BashValidationSnippet.NumberRangeValidationData numberRangeValidation = bashValidationSnippet
                .createNumberRangeValidation(optionWithoutRangeValidation);

        Assertions.assertNotNull(numberRangeValidation,
                "When option has at least one range validation, expect not null result.");

        Assertions.assertNull(numberRangeValidation.getLowerBound());
        Assertions.assertNull(numberRangeValidation.isLowerBoundInclusive());
        Assertions.assertEquals(6, numberRangeValidation.getUpperBound());
        Assertions.assertTrue(numberRangeValidation.isUpperBoundInclusive());
    }
}
