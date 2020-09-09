package com.mikeleitz.sidekick.bash.snippet.manifest;

import com.mikeleitz.sidekick.base.Snippet;
import com.mikeleitz.sidekick.base.SnippetContext;
import lombok.SneakyThrows;

/**
 * @author leitz@mikeleitz.com
 */
public class ManifestBody extends Snippet {
    private static final String TEMPLATE_LOCATION = "com/mikeleitz/sidekick/bash/manifest/manifest-body.stg";

    @SneakyThrows
    public ManifestBody(SnippetContext context) {
        super(context);

        setSnippetTemplate(TEMPLATE_LOCATION);
    }

}
