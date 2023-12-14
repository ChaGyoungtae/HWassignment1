package com.smutify_cgt.smutify.music;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SongTableRepository extends JpaRepository<SongTable,Long> {

    @Override
    List<SongTable> findAll();
}
