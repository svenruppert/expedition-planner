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

import java.nio.file.Path;
import java.nio.file.Paths;

public class PersistenceService
    implements HasLogger {

  protected static final String DEFAULT_STORAGE = "storage";
  private StorageManager storageManager;
  private DataRoot dataRoot;

  public void init() {
    init(DEFAULT_STORAGE);
  }

  public void init(String path) {
    Path storagePath = Paths.get(path);
    logger().info("Loading storage from " + storagePath.toAbsolutePath());
    initStorage(storagePath);
  }

  public void init(Path storagePath) {
    initStorage(storagePath);
  }

  private void initStorage(Path storagePath) {
    if (storagePath.toFile().exists()) {
      logger().info("The storage path already exists.");
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

      this.storageManager = EmbeddedStorage
          .start(configuration);

      Object root = this.storageManager.root();
      if (root == null) {
        logger().info("The storage root object is not set... creating a new one");
        DataRoot newDataRoot = new DataRoot();
        this.storageManager.setRoot(newDataRoot);
        this.storageManager.storeRoot();
      } else {
        logger().info("The storage root object is already set...");
      }
      this.dataRoot = (DataRoot) this.storageManager.root();
    } else {
      logger().info("The storage path does not exist.");
      DataRoot newDataRoot = new DataRoot();
      this.storageManager = EmbeddedStorage
          .start(newDataRoot, storagePath);
      this.dataRoot = (DataRoot) this.storageManager.root();
    }

  }

  public void close() {
    storageManager.close();
  }

  public void shutdown() {
    close();
    storageManager.shutdown();
  }

  public DataRoot getDataRoot() {
    return dataRoot;
  }

  public void store(Object object) {
    logger().info("Storing " + object.getClass().getSimpleName());
    storageManager.store(object);
  }

  public void storeRoot() {
    logger().info("Storing root object...");
    storageManager.storeRoot();
  }
}
