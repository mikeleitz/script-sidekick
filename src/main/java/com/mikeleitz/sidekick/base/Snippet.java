/**
 * Copyright 2019 Michael Leitz
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

import lombok.Getter;

/**
 * @author leitz@mikeleitz.com
 */
public class Snippet implements BuilderResult {
    @Getter private String value;

    public Snippet(String value) {
        this.value = value;
    }

    public void append(String value) {
        this.value += value;
    }

    public void merge(Snippet snippet) {
        this.value += snippet.getValue();
    }

    @Override
    public Object getResult() {
        Object returnValue = null;

        returnValue = value;

        return returnValue;
    }
}
