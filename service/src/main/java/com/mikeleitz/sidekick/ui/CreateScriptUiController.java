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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import com.github.marschall.memoryfilesystem.MemoryFileSystemBuilder;
import com.mikeleitz.sidekick.base.application.ApplicationFile;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

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

    @RequestMapping(path = "/", method = RequestMethod.GET)
    @CrossOrigin(origins = "https://ui.licketyscript.app")
    public @ResponseBody String base() {
        JSONObject jo = new JSONObject();
        jo.put("status", "Ready");

        return jo.toString();
    }

    @RequestMapping(path = "status", method = RequestMethod.GET)
    @CrossOrigin(origins = "https://ui.licketyscript.app")
    public @ResponseBody String getGeneratorStatus() {
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
    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/zip", method = RequestMethod.POST)
    @CrossOrigin(origins = "https://ui.licketyscript.app")
    public @ResponseBody ResponseEntity<StreamingResponseBody> createScript(@RequestBody BashScriptConfiguration configuration)
            throws IOException {
        log.info("Received create script request for data [{}].", configuration);

        String userBashFileName = configuration.getScriptName();
        String extension = configuration.getShellType().getExtension();

        configuration.addScriptInput(BashOption.HELP);

        FileSystem fileSystem = MemoryFileSystemBuilder.newLinux().build();

        ApplicationFile bashScriptFile = bashService.createDelegateBashScriptContents(configuration);
        Path bashScriptFilePath = createPathForContent(fileSystem, bashScriptFile.getFileName(), bashScriptFile.getFileContents());

        ApplicationFile installerScriptFile = bashService.createInstallerContents(configuration);
        Path installerScriptFilePath = createPathForContent(fileSystem, installerScriptFile.getFileName(), installerScriptFile.getFileContents());

        ApplicationFile readmeFile = bashService.createReadmeContents(configuration);
        Path readmeFilePath = createPathForContent(fileSystem, readmeFile.getFileName(), readmeFile.getFileContents());

        ApplicationFile userBashScriptFile = bashService.createUserBashScriptContents(configuration);
        Path userBashScriptFilePath = createPathForContent(fileSystem, userBashScriptFile.getFileName(), userBashScriptFile.getFileContents());

        ApplicationFile manifestFile = bashService.createManifestContents(configuration, List.of(bashScriptFile, installerScriptFile, readmeFile, userBashScriptFile));
        Path manifestFilePath = createPathForContent(fileSystem, manifestFile.getFileName(), manifestFile.getFileContents());

        List<Path> paths = List.of(bashScriptFilePath, installerScriptFilePath, readmeFilePath, userBashScriptFilePath, manifestFilePath);

        return ResponseEntity
                .ok()
                .header("Content-Disposition", String.format("attachment; filename=\"%s.zip\"", configuration.getScriptName()))
                .body(out -> {
                    createScriptReturnStream(out, configuration.getScriptName(), paths);
                });
    }

    private void createScriptReturnStream(OutputStream response, String scriptName, List<Path> paths)
            throws IOException {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(response)) {
            zipOutputStream.putNextEntry(new ZipEntry(scriptName + "/"));
            zipOutputStream.closeEntry();

            for (Path path : paths) {
                zipOutputStream.putNextEntry(new ZipEntry(scriptName + "/" + path.getFileName().toString()));

                try (InputStream pathInputStream = Files.newInputStream(path);) {
                    IOUtils.copy(pathInputStream, zipOutputStream);
                    zipOutputStream.closeEntry();
                }
            }
        }
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
