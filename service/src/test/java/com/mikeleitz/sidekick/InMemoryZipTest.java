/*
 *  Copyright (c) 2020, Michael Leitz
 *  <p/>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p/>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p/>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.mikeleitz.sidekick;

import com.github.marschall.memoryfilesystem.MemoryFileSystemBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author leitz@mikeleitz.com
 */
@Slf4j
public class InMemoryZipTest {
    private static String FILE_CONTENTS = "my new file";

    @Test
    public void testCreateFileInMemory() throws IOException {
        log.info("Hello there.");

        try (FileSystem fileSystem = MemoryFileSystemBuilder.newLinux().build()) {
            Path p = fileSystem.getPath("my-new-file.sh");
            log.info("{}", p.getFileName());

            p = Files.createFile(p);
            p = Files.write(p, StringUtils.getBytesUtf8(FILE_CONTENTS));

            log.info("{}", Files.exists(p));
            log.info("{}", StringUtils.newStringUtf8(Files.readAllBytes(p)));
        }

    }
}
