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
package com.mikeleitz.sidekick.base;

import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author leitz@mikeleitz.com
 */
public class SnippetContext {
    @NonNull private Map<String, Object> values = new HashMap<>();

    public Map<String, Object> getAllValues() {
        return values;
    }

    public void addValue(String key, Object value) {
        values.put(key, value);
    }

}
