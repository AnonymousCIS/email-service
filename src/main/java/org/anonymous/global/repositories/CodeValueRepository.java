package org.anonymous.global.repositories;

import org.anonymous.global.entities.CodeValue;
import org.springframework.data.repository.CrudRepository;

public interface CodeValueRepository extends CrudRepository {
    CodeValue findByCode(String code);
}
