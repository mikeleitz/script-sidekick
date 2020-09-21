package com.mikeleitz.sidekick;

import java.io.IOException;
import java.nio.charset.Charset;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikeleitz.sidekick.bash.domain.BashScriptConfiguration;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author leitz@mikeleitz.com
 */
@JsonTest
@RunWith(SpringRunner.class)
public class JsonInputTest {

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void allValidations() throws IOException {
    // Load the json.
    // Send it through the deserializer

    Resource resource = new ClassPathResource("test-1.json");
    String testJson = IOUtils.toString(resource.getInputStream(), Charset.defaultCharset());
    System.out.println(testJson);

    BashScriptConfiguration bashScriptConfiguration = objectMapper.readValue(testJson, BashScriptConfiguration.class);

    System.out.println(bashScriptConfiguration);
  }

}
