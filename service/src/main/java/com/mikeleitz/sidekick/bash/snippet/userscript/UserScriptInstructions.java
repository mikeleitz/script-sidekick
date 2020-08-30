package com.mikeleitz.sidekick.bash.snippet.userscript;

import java.util.List;
import com.mikeleitz.sidekick.base.Snippet;
import com.mikeleitz.sidekick.base.SnippetContext;
import lombok.SneakyThrows;

/**
 * @author leitz@mikeleitz.com
 */
public class UserScriptInstructions extends Snippet {
    private static final String TEMPLATE_LOCATION = "com/mikeleitz/sidekick/bash/user-script/user-script-instructions.stg";

    @SneakyThrows
    public UserScriptInstructions(SnippetContext context, List<String> optionList) {
        super(context);

        setSnippetTemplate(TEMPLATE_LOCATION);

        context.addValue("allOptionsAndType", optionList);
    }

}
