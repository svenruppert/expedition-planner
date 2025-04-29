package com.svenruppert.expedition.planner.services.persistence;

import com.svenruppert.dependencies.core.logger.HasLogger;
import org.eclipse.serializer.afs.types.ADirectory;
import org.eclipse.store.afs.nio.types.NioFileSystem;
import org.eclipse.store.storage.embedded.types.EmbeddedStorage;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageFoundation;
import org.eclipse.store.storage.types.Storage;
import org.eclipse.store.storage.types.StorageConfiguration;
import org.eclipse.store.storage.types.StorageLiveFileProvider;
import org.eclipse.store.storage.types.StorageManager;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PersistenceService
    implements HasLogger {

  protected static final String DEFAULT_STORAGE = "storage";
  private StorageManager storageManager;
  private DataRoot dataRoot;

  public boolean isUpAndRunning() {
    if (storageManager == null) {
      logger().warn("Storage Manager is null");
      return false;
    }
    if (!storageManager.isRunning()) {
      logger().warn("Storage Manager is not running");
      return false;
    }

    if (!storageManager.isActive()) {
      logger().warn("Storage Manager is not active");
      return false;
    }
    if (dataRoot == null) {
      logger().warn("DataRoot is null");
      return false;
    }
    return true;
  }


  public void init() {
    init(DEFAULT_STORAGE);
  }

  public void init(String path) {
    init(Paths.get(path));
  }

  public void init(Path storagePath) {
    try {
      logger().info("Initializing storage from {}", storagePath.toAbsolutePath());
      initStorage(storagePath);
    } catch (Exception e) {
      logger().error("Failed to initialize storage at {}: {}", storagePath.toAbsolutePath(), e.getMessage(), e);
      throw new RuntimeException("Failed to initialize storage", e);
    }
  }

  private void initStorage(Path storagePath)
      throws Exception {
    if (Files.exists(storagePath)) {
      logger().info("The storage path exists.");

      NioFileSystem fileSystem = NioFileSystem.New();
      ADirectory aDirectory = fileSystem.ensureDirectoryPath(storagePath.toString());

      StorageLiveFileProvider fileProvider = Storage
          .FileProviderBuilder(fileSystem)
          .setDirectory(aDirectory)
          .createFileProvider();

      StorageConfiguration configuration = StorageConfiguration
          .Builder()
          .setStorageFileProvider(fileProvider)
          .createConfiguration();

      EmbeddedStorageFoundation.New()
          .setConfiguration(configuration);

      this.storageManager = EmbeddedStorage.start(configuration);

      Object root = storageManager.root();
      if (root == null) {
        logger().info("The storage root object is not set. Creating new one...");
        DataRoot newDataRoot = new DataRoot();
        storageManager.setRoot(newDataRoot);
        storageManager.storeRoot();
      } else {
        logger().info("The storage root object is already set.");
      }
      this.dataRoot = (DataRoot) storageManager.root();
    } else {
      logger().info("The storage path does not exist. Creating new storage...");
      DataRoot newDataRoot = new DataRoot();
      this.storageManager = EmbeddedStorage.start(newDataRoot, storagePath);
      this.dataRoot = (DataRoot) storageManager.root();
    }
  }

  public synchronized void close() {
    if (storageManager != null) {
      try {
        logger().info("Closing storage manager...");
        storageManager.close();
      } catch (Exception e) {
        logger().error("Error while closing storage manager: {}", e.getMessage(), e);
      }
    }
  }

  public synchronized void shutdown() {
    if (storageManager != null) {
      try {
        logger().info("Shutting down storage manager...");
        storageManager.shutdown();
      } catch (Exception e) {
        logger().error("Error while shutting down storage manager: {}", e.getMessage(), e);
      }
    }
  }

  public DataRoot getDataRoot() {
    if (dataRoot == null) {
      throw new IllegalStateException("The data root object is not set.");
    }
    return dataRoot;
  }

  public synchronized void store(Object object) {
    if (storageManager != null) {
      try {
        logger().info("Storing {}", object.getClass().getSimpleName());
        storageManager.store(object);
      } catch (Exception e) {
        logger().error("Error while storing {}: {}", object.getClass().getSimpleName(), e.getMessage(), e);
      }
    } else {
      logger().warn("Storage manager not initialized. Cannot store object.");
    }
  }

  public synchronized void storeRoot() {
    if (storageManager != null) {
      try {
        logger().info("Storing root object...");
        storageManager.storeRoot();
      } catch (Exception e) {
        logger().error("Error while storing root object: {}", e.getMessage(), e);
      }
    } else {
      logger().warn("Storage manager not initialized. Cannot store root.");
    }
  }
}
