package io.vanja.ninja.repository;

import io.vanja.ninja.domain.Service
import org.springframework.data.jpa.repository.JpaRepository

interface ServiceRepository : JpaRepository<Service, Long> {
}