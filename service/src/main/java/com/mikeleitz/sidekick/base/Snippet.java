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
package com.mikeleitz.sidekick.base;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.stringtemplate.v4.ST;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author leitz@mikeleitz.com
 */
@Slf4j
public abstract class Snippet {
    @NonNull private Resource templateResource;
    protected String template;
    protected SnippetContext context;

    public Snippet(String templateLocation, SnippetContext context) throws IOException {
        this.context = context;

        templateResource = new ClassPathResource(templateLocation);
        template = FileUtils.readFileToString(templateResource.getFile(), Charset.defaultCharset());
    }

    public String getSnippet() {
        String returnValue = null;

        returnValue = buildTemplate();

        return returnValue;
    }

    protected String buildTemplate() {
        String returnValue = null;

        ST snippetTemplate = new ST(template);
        context.getAllValues().forEach((e,v) -> snippetTemplate.add(e, v));

        returnValue = snippetTemplate.render();

        log.debug("Rendered template for [{}]", this.getClass());

        return returnValue;
    }
}
