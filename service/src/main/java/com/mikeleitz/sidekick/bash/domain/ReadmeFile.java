package com.mikeleitz.sidekick.bash.domain;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import com.mikeleitz.sidekick.base.Snippet;
import com.mikeleitz.sidekick.base.SnippetContext;
import com.mikeleitz.sidekick.base.application.ApplicationFile;
import com.mikeleitz.sidekick.bash.snippet.readme.ReadmeSnippet;
import org.apache.commons.io.FileUtils;

/**
 * @author leitz@mikeleitz.com
 */
public class ReadmeFile extends ApplicationFile {
  public static final String FILE_NAME = "readme.md";
  protected SnippetContext snippetContext = new SnippetContext();

  public ReadmeFile(BashScriptConfiguration bashScriptConfiguration) {
    this.fileRole = "readme markdown";
    this.fileName = bashScriptConfiguration.getScriptName() + ".md";

    ReadmeSnippet readmeSnippet = new ReadmeSnippet(snippetContext);
    preambleList.add(readmeSnippet);
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
    File returnValue = new File(fullPath + FILE_NAME);

    FileUtils.writeStringToFile(returnValue, getFileContents(), Charset.defaultCharset());

    returnValue.setWritable(true);
    returnValue.setReadable(true);
    returnValue.setExecutable(true);

    return returnValue;
  }
}
