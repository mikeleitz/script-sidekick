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

package com.mikeleitz.sidekick.bash.service;

import java.util.List;
import com.mikeleitz.sidekick.base.application.ApplicationFile;
import com.mikeleitz.sidekick.bash.domain.BashScriptConfiguration;

/**
 * @author leitz@mikeleitz.com
 */
public interface BashService {
    String createDelegateBashScriptContents(BashScriptConfiguration bashScriptConfiguration);

    String createReadmeContents(BashScriptConfiguration bashScriptConfiguration);

    String createInstallerContents(BashScriptConfiguration bashScriptConfiguration);

    String createUserBashScriptContents(BashScriptConfiguration bashScriptConfiguration);

    String createManifestContents(BashScriptConfiguration bashScriptConfiguration, List<ApplicationFile> applicationFiles);
}
