package com.mikeleitz.sidekick.bash.domain;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import com.mikeleitz.sidekick.base.Snippet;
import com.mikeleitz.sidekick.base.SnippetContext;
import com.mikeleitz.sidekick.base.application.ApplicationFile;
import com.mikeleitz.sidekick.bash.snippet.ShebangBashSnippet;
import com.mikeleitz.sidekick.bash.snippet.installer.InstallerScriptBodySnippet;
import com.mikeleitz.sidekick.bash.snippet.installer.InstallerScriptInstructionsSnippet;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

/**
 * @author leitz@mikeleitz.com
 */
public class InstallerScript extends ApplicationFile {
    protected ShellOptionEnum shellOptionEnum = ShellOptionEnum.BASH;

    protected SnippetContext snippetContext = new SnippetContext();

    @SneakyThrows
    public InstallerScript(BashScriptConfiguration bashScriptConfiguration) {
        this.fileRole = "installer script";

        snippetContext.addValue("scriptName", bashScriptConfiguration.getScriptName());

        ShebangBashSnippet shebangBashSnippet = new ShebangBashSnippet(snippetContext, shellOptionEnum);
        preambleList.add(shebangBashSnippet);

        InstallerScriptInstructionsSnippet installerScriptInstructionsSnippet = new InstallerScriptInstructionsSnippet(snippetContext);
        preambleList.add(installerScriptInstructionsSnippet);

        InstallerScriptBodySnippet installerScriptBodySnippet = new InstallerScriptBodySnippet(snippetContext);
        processingList.add(installerScriptBodySnippet);
    }

    @Override
    public String getFileContents() {
        String returnValue;

        StringBuilder fileContents = new StringBuilder();
        for (Snippet snippet : preambleList) {
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
        File returnValue = new File(fullPath + fileName + shellOptionEnum.extension);

        FileUtils.writeStringToFile(returnValue, getFileContents(), Charset.defaultCharset());

        returnValue.setWritable(true);
        returnValue.setReadable(true);
        returnValue.setExecutable(true);

        return returnValue;
    }
}
