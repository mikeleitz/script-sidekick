/**
 * Copyright 2019 Michael Leitz
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.mikeleitz.sidekick;

import com.mikeleitz.sidekick.bash.domain.BashFile;
import com.mikeleitz.sidekick.bash.domain.BashOption;
import com.mikeleitz.sidekick.bash.domain.BashScriptConfiguration;
import com.mikeleitz.sidekick.bash.domain.ShellOptionEnum;
import com.mikeleitz.sidekick.bash.snippet.validation.BashValidationEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author leitz@mikeleitz.com
 */
@SpringBootApplication
@Slf4j
public class ScriptSidekickApplication implements CommandLineRunner {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(ScriptSidekickApplication.class, args);
    }
/*
	public static void test() throws IOException {
		BashPreamble bashPreamble = BashPreamble.builder().shell(ShellOptionEnum.BASH).build();

		BashOption bashOption = BashOption.builder()
				.shortName('o')
				.longName("open")
				.helpDescription("Open stream along with other work.")
				.validation(new NotNullBashValidation()).build();

		BashInput input = BashInput.builder().bashOption(bashOption).build();

		BashScript bashScript = BashScript.builder().bashPreamble(bashPreamble).bashInput(input).build();

		File scriptFile = new File("/tmp/myscript.sh");
		FileUtils.writeStringToFile(scriptFile, bashScript.buildScript(), Charset.defaultCharset());
		scriptFile.setExecutable(true);
		scriptFile.setReadable(true);
		scriptFile.setWritable(true);
	}*/

    @Override
    public void run(String... args) throws Exception {
        log.info("Running command line!!");

        BashOption bashOption1 = BashOption.builder().shortName('w').longName("work").argNeeded(true)
                .validation(BashValidationEnum.NOT_NULL).validation(BashValidationEnum.TRIM_WHITESPACE).build();
        BashOption bashOption2 = BashOption.builder().shortName('x').longName("extract").argNeeded(false).build();
        BashOption bashOption3 = BashOption.HELP;
        BashOption bashOption4 = BashOption.VERBOSE;

        BashScriptConfiguration configuration = new BashScriptConfiguration();
        configuration.setBashScriptName("my-new-script");
        configuration.setScriptShell(ShellOptionEnum.BASH);
        configuration.setScriptInputs(
                Stream.of(bashOption1, bashOption2, bashOption3, bashOption4)
                        .collect(Collectors.toCollection(HashSet::new)));

        BashFile bashFile = new BashFile(configuration);
        bashFile.toFile("./");
    }
}
