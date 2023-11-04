package io.vanja.ninja.exception

class AlreadyExistsException(message: String) : RuntimeException(message)

class DoesNotExistException(message: String) : RuntimeException(message)