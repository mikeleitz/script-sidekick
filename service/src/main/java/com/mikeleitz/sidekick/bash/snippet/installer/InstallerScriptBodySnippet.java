package com.mikeleitz.sidekick.bash.snippet.installer;

import com.mikeleitz.sidekick.base.Snippet;
import com.mikeleitz.sidekick.base.SnippetContext;
import lombok.SneakyThrows;

/**
 * @author leitz@mikeleitz.com
 */
public class InstallerScriptBodySnippet extends Snippet {
  private static final String TEMPLATE_LOCATION = "com/mikeleitz/sidekick/bash/installer-script/installer-script-body.stg";

  @SneakyThrows
  public InstallerScriptBodySnippet(SnippetContext context) {
    super(context);

    setSnippetTemplate(TEMPLATE_LOCATION);
  }
}
