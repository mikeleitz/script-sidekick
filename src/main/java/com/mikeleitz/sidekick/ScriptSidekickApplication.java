/**
 * Copyright 2019 Michael Leitz
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
package com.mikeleitz.sidekick;

import com.mikeleitz.sidekick.base.SnippetContext;
import com.mikeleitz.sidekick.bash.domain.BashOption;
import com.mikeleitz.sidekick.bash.domain.ShellOptionEnum;
import com.mikeleitz.sidekick.bash.snippet.InputBashSnippet;
import com.mikeleitz.sidekick.bash.snippet.LoggingBashSnippet;
import com.mikeleitz.sidekick.bash.snippet.ProcessingBashSnippet;
import com.mikeleitz.sidekick.bash.snippet.ShebangBashSnippet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;
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

		SnippetContext context = new SnippetContext();

		BashOption bashOption1 = BashOption.builder().shortName('w').longName("work").argNeeded(true).build();
		BashOption bashOption2 = BashOption.builder().shortName('x').longName("extract").argNeeded(false).build();

		Set<BashOption> bashOptions = Stream.of(bashOption1, bashOption2).collect(Collectors.toCollection(HashSet::new));

		ShebangBashSnippet shebangBashSnippet = new ShebangBashSnippet(context, ShellOptionEnum.BASH);
		LoggingBashSnippet loggingBashSnippet = new LoggingBashSnippet(context);

		InputBashSnippet inputBashSnippet = new InputBashSnippet(context, bashOptions);

		ProcessingBashSnippet processingBashSnippet = new ProcessingBashSnippet(context);

		String finalScript = shebangBashSnippet.getSnippet() + "\n" + loggingBashSnippet.getSnippet() + "\n" + inputBashSnippet.getSnippet() + "\n" + processingBashSnippet.getSnippet() + "\n";

		File outputScript = new File("/tmp/myscript.sh");

		// beautysh file1.sh file2.sh file3.sh
		// Run python app inside Docker container to format.
		FileUtils.writeStringToFile(outputScript, finalScript, Charset.defaultCharset());

		outputScript.setWritable(true);
		outputScript.setReadable(true);
		outputScript.setExecutable(true);
	}
}
