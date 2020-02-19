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

import com.mikeleitz.sidekick.base.Snippet;
import com.mikeleitz.sidekick.base.SnippetContext;
import com.mikeleitz.sidekick.base.application.ApplicationFile;
import com.mikeleitz.sidekick.bash.snippet.HelpBashSnippet;
import com.mikeleitz.sidekick.bash.snippet.InputBashSnippet;
import com.mikeleitz.sidekick.bash.snippet.LoggingBashSnippet;
import com.mikeleitz.sidekick.bash.snippet.ProcessingBashSnippet;
import com.mikeleitz.sidekick.bash.snippet.ShebangBashSnippet;
import com.mikeleitz.sidekick.bash.snippet.common.RegexValidationBashSnippet;
import com.mikeleitz.sidekick.bash.snippet.validation.BashOptionsValidationSnippet;
import com.mikeleitz.sidekick.bash.snippet.validation.BashValidationSnippet;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author leitz@mikeleitz.com
 */
public class BashFile extends ApplicationFile {
    protected SnippetContext snippetContext = new SnippetContext();
    protected BashScriptConfiguration bashScriptConfiguration;

    @SneakyThrows
    public BashFile(BashScriptConfiguration bashScriptConfiguration) {
        this.bashScriptConfiguration = bashScriptConfiguration;

        populateSnippets();
    }

    protected void populateSnippets() throws IOException {
        ShebangBashSnippet shebangBashSnippet = new ShebangBashSnippet(snippetContext, bashScriptConfiguration.getShellType());
        LoggingBashSnippet loggingBashSnippet = new LoggingBashSnippet(snippetContext);

        InputBashSnippet inputBashSnippet = new InputBashSnippet(snippetContext, bashScriptConfiguration.getBashOptions());
        ProcessingBashSnippet processingBashSnippet = new ProcessingBashSnippet(snippetContext);

        RegexValidationBashSnippet regexValidationBashSnippet = new RegexValidationBashSnippet(snippetContext);
        BashValidationSnippet bashValidationSnippet = new BashValidationSnippet(snippetContext);
        BashOptionsValidationSnippet bashOptionsValidationSnippet = new BashOptionsValidationSnippet(snippetContext);

        HelpBashSnippet helpBashSnippet = new HelpBashSnippet(snippetContext,
                bashScriptConfiguration.getBashOptions(),
                bashScriptConfiguration.getAuthor(),
                bashScriptConfiguration.getShellType(),
                bashScriptConfiguration.getScriptName(),
                bashScriptConfiguration.getVersion(),
                bashScriptConfiguration.getPurpose());

        fileName = bashScriptConfiguration.getScriptName();

        preambleList.add(shebangBashSnippet);

        functionsAndSupplementList.add(loggingBashSnippet);
        functionsAndSupplementList.add(helpBashSnippet);
        functionsAndSupplementList.add(bashValidationSnippet);
        functionsAndSupplementList.add(regexValidationBashSnippet);
        functionsAndSupplementList.add(bashOptionsValidationSnippet);

        applicationInputList.add(inputBashSnippet);

        processingList.add(processingBashSnippet);
    }

    @Override
    public String getFileContents() {
        String returnValue = null;

        StringBuilder fileContents = new StringBuilder();
        for (Snippet snippet : preambleList) {
            fileContents.append(snippet.getSnippet());
            fileContents.append("\n");
        }

        for (Snippet snippet : functionsAndSupplementList) {
            fileContents.append(snippet.getSnippet());
            fileContents.append("\n");
        }

        for (Snippet snippet : applicationInputList) {
            fileContents.append(snippet.getSnippet());
            fileContents.append("\n");
        }

        for (Snippet snippet : processingList) {
            fileContents.append(snippet.getSnippet());
            fileContents.append("\n");
        }

        returnValue = fileContents.toString();

        return returnValue;
    }

    @Override
    public String toJson() {
        return null;
    }

    @Override
    public File toFile(String fullPath) throws IOException {
        File returnValue = new File(fullPath + fileName + bashScriptConfiguration.getShellType().extension);

        FileUtils.writeStringToFile(returnValue, getFileContents(), Charset.defaultCharset());

        returnValue.setWritable(true);
        returnValue.setReadable(true);
        returnValue.setExecutable(true);

        return returnValue;
    }
}
