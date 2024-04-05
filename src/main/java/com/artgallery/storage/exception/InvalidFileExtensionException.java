package com.artgallery.storage.exception;

public class InvalidFileExtensionException  extends StorageException {

    public InvalidFileExtensionException(String message) {
        super(message);
    }

    public InvalidFileExtensionException(String message, Throwable cause) {
        super(message, cause);
    }
}