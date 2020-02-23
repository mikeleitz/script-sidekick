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

package com.mikeleitz.sidekick.ui;

import com.github.marschall.memoryfilesystem.MemoryFileSystemBuilder;
import com.mikeleitz.sidekick.bash.domain.BashOption;
import com.mikeleitz.sidekick.bash.domain.BashScriptConfiguration;
import com.mikeleitz.sidekick.bash.service.BashService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author leitz@mikeleitz.com
 */
@Slf4j
@RestController("/")
public class CreateScriptUiController {
    private BashService bashService;

    @Autowired
    public CreateScriptUiController(BashService bashService) {
        this.bashService = bashService;
    }

    @GetMapping(path = "status")
    public @ResponseBody
    String getGeneratorStatus() {
        JSONObject jo = new JSONObject();
        jo.put("status", "Ready");

        return jo.toString();
    }

    /**
     * Creates a file that's streamed back to the client.
     *
     * @param configuration
     * @return
     */
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = "application/zip")
    public ResponseEntity<StreamingResponseBody> createScript(@RequestBody BashScriptConfiguration configuration)
            throws IOException {
        log.info("Received create script request for data [{}].", configuration);

        configuration.addScriptInput(BashOption.HELP);

        FileSystem fileSystem = MemoryFileSystemBuilder.newLinux().build();

        String scriptContents = bashService.createDelegateBashScriptContents(configuration);
        Path scriptContentsPath = createPathForContent(fileSystem, "script-contents.sh", scriptContents);

        String installerContents = bashService.createInstallerContents(configuration);
        Path installerContentsPath = createPathForContent(fileSystem, "installer-contents.sh", installerContents);

        String readmeContents = bashService.createReadmeContents(configuration);
        Path readmeContentsPath = createPathForContent(fileSystem, "readme-contents.md", readmeContents);

        String userBashScriptContents = bashService.createUserBashScriptContents(configuration);
        Path userBashScriptContentsPath = createPathForContent(fileSystem, "user-script-contents.md", userBashScriptContents);

        ArrayList<Path> paths = new ArrayList<>(4);
        paths.add(scriptContentsPath);
        paths.add(installerContentsPath);
        paths.add(readmeContentsPath);
        paths.add(userBashScriptContentsPath);

        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment; filename=\"test.zip\"")
                .body(out -> {
                    var zipOutputStream = new ZipOutputStream(out);

                    // create the root-directory
                    zipOutputStream.putNextEntry(new ZipEntry("base-dir/"));
                    zipOutputStream.closeEntry();

                    // package files
                    for (Path path : paths) {
                        //new zip entry and copying inputstream with file to zipOutputStream, after all closing streams
                        zipOutputStream.putNextEntry(new ZipEntry("base-dir/" + path.getFileName().toString()));

                        InputStream pathInputStream = Files.newInputStream(path);
                        IOUtils.copy(pathInputStream, zipOutputStream);

                        pathInputStream.close();
                        zipOutputStream.closeEntry();
                    }

                    zipOutputStream.close();
                });
    }

    private Path createPathForContent(FileSystem fileSystem, String fileName, String contents) throws IOException {
        Path returnValue = null;

        returnValue = fileSystem.getPath(fileName);
        log.debug("Creating path for file name [{}]", returnValue.getFileName());

        Files.createFile(returnValue);
        Files.write(returnValue, StringUtils.getBytesUtf8(contents));

        return returnValue;
    }
}
