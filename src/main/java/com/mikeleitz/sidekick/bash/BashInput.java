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
import io.micrometer.core.instrument.util.StringUtils;
import lombok.Builder;
import lombok.Singular;

import java.util.Set;

/**
 * @author leitz@mikeleitz.com
 */
@Builder
public class BashInput implements SectionBuilder {

    /*
     * String format vargs:
     *  1. short names : e.g. vqdlihr:
     *  2. long name : e.g. verbose,quiet,development,list,info,help,version:
     */
    private static final String INPUT_PARSING =
            "##################################################################################################\n"
                    + "# Setup enhanced getopts to handle input\n"
                    + "# From: https://stackoverflow.com/questions/192249/how-do-i-parse-command-line-arguments-in-bash\n"
                    + "##################################################################################################\n"
                    + "\n"
                    + "# saner programming env: these switches turn some bugs into errors\n"
                    + "set -o errexit -o pipefail -o noclobber -o nounset\n"
                    + "\n"
                    + "# Check to make sure the correct version of getopt is installed.\n"
                    + "# This script uses a more sophisticated version to make things easier to program.\n"
                    + "! getopt --test >/dev/null\n"
                    + "if [[ ${PIPESTATUS[0]} -ne 4 ]]; then\n"
                    + "  echo \"This script requires enhanced getopt to work.  Enhanced getopt is included on any modern *nix system.\"\n"
                    + "  echo \"If you're running OSX install via\"\n"
                    + "  echo \"$ brew install gnu-getopt\"\n"
                    + "  echo \"The add it to your path and restart your shell.\"\n"
                    + "  echo \"$ echo 'export PATH=\\\"/usr/local/opt/gnu-getopt/bin:\\$PATH\\\"' >> ~/.bash_profile\"\n"
                    + "  echo \"\"\n"
                    + "  echo \"If you're on another distro, install enhanced getopt and make it available in the path.\"\n"
                    + "  exit 1\n"
                    + "fi\n"
                    + "\n"
                    + "# These are all the switches for the script.\n"
                    + "#\n"
                    + "# The OPTIONS letters correspond to the LONGOPTS names.  e.g. v is synonomous with verbose, r: means version:\n"
                    + "#\n"
                    + "# The colon means the switch has an arg associated with it.  n: and name: means you specify a name long with the switch.\n"
                    + "# Without a colon means the switch is standalone and doesn't require additional information. v and verbose mean put it in verbose mode.\n"
                    + "OPTIONS=%s\n"
                    + "LONGOPTS=%s\n"
                    + "\n"
                    + "# -regarding ! and PIPESTATUS see above\n"
                    + "# -temporarily store output to be able to check for errors\n"
                    + "# -activate quoting/enhanced mode (e.g. by writing out “--options”)\n"
                    + "# -pass arguments only via   -- \"$@\"   to separate them correctly\n"
                    + "! PARSED=$(getopt --options=$OPTIONS --longoptions=$LONGOPTS --name \"$0\" -- \"$@\")\n"
                    + "if [[ ${PIPESTATUS[0]} -ne 0 ]]; then\n"
                    + "  # e.g. return value is 1\n"
                    + "  #  then getopt has complained about wrong arguments to stdout\n"
                    + "  exit 2\n"
                    + "fi\n"
                    + "\n"
                    + "# read getopt’s output this way to handle the quoting right:\n"
                    + "eval set -- \"$PARSED\"\n"
                    + "\n"
                    + "# Handle all possible script inputs.\n"
                    + "while true; do\n"
                    + "  case \"$1\" in\n";

    /*
     * String vargs
     *  1. short command char
     *  2. long command string
     */
    private static final String CASE_ENTRY_SHORT_AND_LONG = ""
            + "  -%c | --%s)\n"
            + "    __VERBOSE=7 # Verbose indicates logging at debug.\n"
            + "    shift\n"
            + "    ;;\n";

    /*
     * String vargs
     *  1. short command char
     */
    private static final String CASE_ENTRY_SHORT = ""
            + "  -%c)\n"
            + "    __VERBOSE=7 # Verbose indicates logging at debug.\n"
            + "    shift\n"
            + "    ;;\n";

    private static final String END_CASE = ""
            + "  *)\n"
            + "    echo \"Programming error\"\n"
            + "    exit 3\n"
            + "    ;;\n"
            + "  esac\n"
            + "done\n";

    private Boolean enableLogging;
    private Boolean enableQuiet;
    private Boolean enableHelp;
    @Singular
    private Set<BashOption> bashOptions;

    @Override
    public BuilderResult buildResult() {
        BuilderResult returnValue = null;

        String snippetValue = null;

        String allShortNames = "";
        String allLongNames = "";
        String allCommandParsing = "";

        for (BashOption bashOption : bashOptions) {
            allShortNames += bashOption.getShortName();
            allLongNames += bashOption.getLongName() + ",";

            if (StringUtils.isNotBlank(bashOption.getLongName())) {
                allCommandParsing += String.format(CASE_ENTRY_SHORT_AND_LONG, bashOption.getShortName(), bashOption.getLongName());
            } else {
                allCommandParsing += String.format(CASE_ENTRY_SHORT, bashOption.getShortName());
            }

//            snippetValue = bashOption.getResult().toString();
        }

        String parsingString = String.format(INPUT_PARSING, allShortNames, allLongNames);
        parsingString += allCommandParsing;
        parsingString += END_CASE;

        returnValue = new Snippet(parsingString);

        return returnValue;
    }

    protected String createInputParsing(BashOption bashOption) {
        String returnValue = null;

        returnValue = String.format(INPUT_PARSING, "");

        return returnValue;
    }
}
