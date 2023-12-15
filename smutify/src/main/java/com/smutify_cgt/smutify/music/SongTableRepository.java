package com.smutify_cgt.smutify.music;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SongTableRepository extends JpaRepository<SongTable,Long> {

    @Override
    List<SongTable> findAll();

    List<SongTable> findBySingerLike(String singer);

    List<SongTable> findByTitleLike(String title);

    List<SongTable> findByGenreLike(String genre);

    @Modifying
    @Query("UPDATE SongTable s SET s.genre = :newGenre WHERE s.title = :songTitle")
    void updateGenreByTitle(@Param("songTitle") String songTitle, @Param("newGenre") String newGenre);
}
