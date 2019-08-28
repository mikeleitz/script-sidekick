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
package com.mikeleitz.sidekick.bash.domain;

import com.mikeleitz.sidekick.base.Validation;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import lombok.Singular;

import java.util.List;

/**
 * @author leitz@mikeleitz.com
 */
@Data
@Builder
@Setter(AccessLevel.NONE)
public class BashOption {
    private Character shortName;
    private boolean argNeeded = false;
    @NonNull private String longName;
    private String helpDescription;
    @Singular private List<Validation> validations;

    // Fields to help render the template.
    // These are populated when the object is built via builder().
    protected String shortOptArgsValue;
    protected String longOptArgsValue;
    protected String switchStatementSection;
    protected String declareVariablesSection;

    public static BashOptionBuilder builder() {
        return new BashOptionBuilder(){
            @Override
            public BashOption build() {
                BashOption buildResult = super.build();


                return buildResult;
            }
        };
    }


}
