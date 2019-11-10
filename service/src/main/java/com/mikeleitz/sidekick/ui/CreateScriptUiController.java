/*
 *  Copyright (c) 2019, Michael Leitz
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

import com.mikeleitz.sidekick.bash.domain.BashFile;
import com.mikeleitz.sidekick.bash.domain.BashScriptConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author leitz@mikeleitz.com
 */
@Slf4j
@RestController("/")
public class CreateScriptUiController {
    private List<String> tasks = Arrays.asList("a", "b", "c", "d", "e", "f", "g");

    @GetMapping
    public @ResponseBody String getScript() {
        JSONObject jo = new JSONObject();
        jo.put("message", "Hello There!");

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
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] createScript(@RequestBody BashScriptConfiguration configuration) {
        log.info("Received create script request for data [{}].", configuration);

        BashFile bashFile = new BashFile(configuration);
        String scriptContents = bashFile.getFileContents();

        byte[] returnValue = StringUtils.getBytesUtf8(scriptContents);

        return returnValue;
    }
}
