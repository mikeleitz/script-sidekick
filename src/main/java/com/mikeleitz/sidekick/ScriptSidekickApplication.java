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

import com.mikeleitz.sidekick.bash.BashInput;
import com.mikeleitz.sidekick.bash.BashOption;
import com.mikeleitz.sidekick.bash.BashPreamble;
import com.mikeleitz.sidekick.bash.BashScript;
import com.mikeleitz.sidekick.bash.ShellOptionEnum;
import com.mikeleitz.sidekick.bash.validation.NotNullBashValidation;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author leitz@mikeleitz.com
 */
@SpringBootApplication
public class ScriptSidekickApplication {

	public static void main(String[] args) throws IOException {
//		SpringApplication.run(ScriptSidekickApplication.class, args);

		test();
	}

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
	}
}
