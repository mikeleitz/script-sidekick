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
    public static BashOption VERBOSE = BashOption.builder().argNeeded(false).shortName('v').longName("verbose").helpDescription("verbose operation").build();
    public static BashOption HELP = BashOption.builder().argNeeded(false).shortName('h').longName("help").helpDescription("give this help list").build();

    private Character shortName;
    private boolean argNeeded = false;
    @NonNull private String longName;
    private String helpDescription = "";
    @Singular private List<Validation> validations;

}
