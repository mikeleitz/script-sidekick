package com.mikeleitz.sidekick.bash.snippet.installer;

import com.mikeleitz.sidekick.base.Snippet;
import com.mikeleitz.sidekick.base.SnippetContext;
import lombok.SneakyThrows;

/**
 * @author leitz@mikeleitz.com
 */
public class InstallerScriptInstructionsSnippet extends Snippet {
  private static final String TEMPLATE_LOCATION = "com/mikeleitz/sidekick/bash/installer-script/installer-script-instructions.stg";

  @SneakyThrows
  public InstallerScriptInstructionsSnippet(SnippetContext context) {
    super(context);

    setSnippetTemplate(TEMPLATE_LOCATION);
  }
}
