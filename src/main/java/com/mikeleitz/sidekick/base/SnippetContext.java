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
import org.apache.commons.collections4.ListValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author leitz@mikeleitz.com
 */
public class SnippetContext {
    @NonNull private ListValuedMap<String, Object> values = new ArrayListValuedHashMap();

    public ListValuedMap<String, Object> getAllValues() {
        return values;
    }

    public void addValue(String key, Object value) {
        values.put(key, value);
    }

    public List getList(String key) {
        List returnValue = null;

        returnValue = values.get(key);

        return returnValue;
    }

    public <T> T getValue(Class<T> type, String key) {
        return type.cast(values.get(key).get(0));
    }

    public <T> T getRequiredValue(Class<T> type, String key) {
        List resultList = values.get(key);

        if (resultList == null && CollectionUtils.isEmpty(resultList)) {
            throw new IllegalArgumentException(String.format("Expecting to find value for key %s.", key));
        }

        Object resultEntry = resultList.get(0);

        return type.cast(resultEntry);
    }
}
