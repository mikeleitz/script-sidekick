package com.mikeleitz.sidekick.bash.snippet.readme;

import com.mikeleitz.sidekick.base.Snippet;
import com.mikeleitz.sidekick.base.SnippetContext;
import lombok.SneakyThrows;

/**
 * @author leitz@mikeleitz.com
 */
public class ReadmeSnippet extends Snippet {
  private static final String TEMPLATE_LOCATION = "com/mikeleitz/sidekick/bash/readme-file/readme-instructions.stg";
  
  @SneakyThrows
  public ReadmeSnippet(SnippetContext context) {
    super(context);

    setSnippetTemplate(TEMPLATE_LOCATION);
  }
}
