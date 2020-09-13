package com.mikeleitz.sidekick.bash.domain;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import com.mikeleitz.sidekick.base.Snippet;
import com.mikeleitz.sidekick.base.SnippetContext;
import com.mikeleitz.sidekick.base.application.ApplicationFile;
import com.mikeleitz.sidekick.bash.snippet.manifest.ManifestBody;
import com.mikeleitz.sidekick.bash.snippet.manifest.ManifestInstructions;
import org.apache.commons.io.FileUtils;

/**
 * @author leitz@mikeleitz.com
 */
public class ManifestFile extends ApplicationFile {
    protected ShellOptionEnum shellOptionEnum = ShellOptionEnum.BASH;

    protected SnippetContext snippetContext = new SnippetContext();
    protected List<ApplicationFile> allApplicationFiles;

    public ManifestFile(BashScriptConfiguration bashScriptConfiguration, List<ApplicationFile> allApplicationFiles) {
        this.fileRole = "manifest file";

        this.allApplicationFiles = allApplicationFiles;

        ManifestInstructions manifestInstructions = new ManifestInstructions(snippetContext);
        preambleList.add(manifestInstructions);

        ManifestBody manifestBody = new ManifestBody(snippetContext);
        processingList.add(manifestBody);
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
