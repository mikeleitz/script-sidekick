package com.mikeleitz.sidekick.bash.domain;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import com.mikeleitz.sidekick.base.Snippet;
import com.mikeleitz.sidekick.base.SnippetContext;
import com.mikeleitz.sidekick.base.application.ApplicationFile;
import com.mikeleitz.sidekick.bash.snippet.ShebangBashSnippet;
import com.mikeleitz.sidekick.bash.snippet.userscript.UserScriptInstructions;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

/**
 * @author leitz@mikeleitz.com
 */
public class UserScript extends ApplicationFile {
    ShellOptionEnum shellOptionEnum = ShellOptionEnum.BASH;
    protected SnippetContext snippetContext = new SnippetContext();
    protected BashScript bashScriptConfiguration;

    @SneakyThrows
    public UserScript(BashScriptConfiguration bashScriptConfiguration) {
        ShebangBashSnippet shebangBashSnippet = new ShebangBashSnippet(snippetContext, shellOptionEnum);
        preambleList.add(shebangBashSnippet);

        String scriptName = bashScriptConfiguration.getScriptName();
        String extension = bashScriptConfiguration.getShellType().getExtension();
        snippetContext.addValue("scriptFile", scriptName + extension);

        List<String> optionsAndType = createOptionsAndType(bashScriptConfiguration);

        UserScriptInstructions userScriptInstructions = new UserScriptInstructions(snippetContext, optionsAndType);
        preambleList.add(userScriptInstructions);
    }

    @Override
    public String getFileContents() {
        String returnValue;

        StringBuilder fileContents = new StringBuilder();
        for (Snippet snippet : preambleList) {
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
        File returnValue = new File(fullPath + fileName + shellOptionEnum.extension);

        FileUtils.writeStringToFile(returnValue, getFileContents(), Charset.defaultCharset());

        returnValue.setWritable(true);
        returnValue.setReadable(true);
        returnValue.setExecutable(true);

        return returnValue;
    }

    protected List<String> createOptionsAndType(BashScriptConfiguration bashScriptConfiguration) {
        List<String> returnValue = new ArrayList<>();

        for (BashOption bashOption : bashScriptConfiguration.getBashOptions()) {
            String variableName = bashOption.getVariableName();
            String displayLine = String.format("%s", variableName);
            returnValue.add(displayLine);
        }

        return returnValue;
    }
}
