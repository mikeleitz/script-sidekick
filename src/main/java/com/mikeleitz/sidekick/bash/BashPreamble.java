/*
 * Copyright (c) 2019, Michael Leitz
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mikeleitz.sidekick.bash;

import com.mikeleitz.sidekick.base.BuilderResult;
import com.mikeleitz.sidekick.base.SectionBuilder;
import com.mikeleitz.sidekick.base.Snippet;
import lombok.Builder;

/**
 * @author leitz@mikeleitz.com
 */
@Builder
public class BashPreamble implements SectionBuilder {
    private ShellOptionEnum shell;

    private final static String LOGGING_CODE = "export __VERBOSE=4\n\n" +
            "" +
            "# Specify all log levels\n"
            + "declare -A LOG_LEVELS\n"
            + "LOG_LEVELS=([0]=\"emerg\" [1]=\"alert\" [2]=\"crit\" [3]=\"err\" [4]=\"warning\" [5]=\"notice\" [6]=\"info\" [7]=\"debug\")\n"
            + "\n"
            + "# Handle basic logging.\n"
            + "function .log() {\n"
            + "  local LEVEL=${1}\n"
            + "  shift\n"
            + "  if [ \"${__VERBOSE}\" -ge \"${LEVEL}\" ]; then\n"
            + "    echo \"[${LOG_LEVELS[$LEVEL]}]\" \"$@\"\n"
            + "  fi\n"
            + "}";

    @Override
    public BuilderResult buildResult() {
        BuilderResult returnValue = null;

        StringBuilder stringBuilder = new StringBuilder(shell.getShebang())
                .append("\n")
                .append("\n")
                .append(_createLogging());

        returnValue = new Snippet(stringBuilder.toString());

        return returnValue;
    }

    private String _createLogging() {
        String returnValue = null;

        returnValue = LOGGING_CODE;

        return returnValue;
    }
}
