package junit.com.svenruppert.expedition.planner.services.persistence;

import com.svenruppert.expedition.planner.services.persistence.DataRoot;
import com.svenruppert.expedition.planner.services.persistence.PersistenceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersistenceServiceTest {


  @Test
  void test001(TestReporter reporter , @TempDir Path tempDir) {

    PersistenceService service = new PersistenceService();
    service.init(tempDir);

    DataRoot dataRoot = service.getDataRoot();
    dataRoot.helloWorld = "Hello World";
    service.store(dataRoot);

    service.shutdown();
    service.close();

    service.init(tempDir);
    assertEquals("Hello World", service.getDataRoot().helloWorld);

    reporter.publishEntry(service.getDataRoot().toString());


  }
}
