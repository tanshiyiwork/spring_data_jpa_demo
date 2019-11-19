package com.github.repo;

import com.github.model.VotePerson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VotePersonRepository extends JpaRepository<VotePerson, String> {

    @Query("select v from VotePerson v")
    Page<VotePerson> findAll(Pageable pageable);

    @Query(value = "select * from VOTE_PERSON v where v.ST_PERSON_NAME = ?1",nativeQuery = true)
    Page<VotePerson> findVotePeopleByStPersonName(String stPersonName, Pageable pageable);

    @Query(value = "select * from VOTE_PERSON v where v.ST_PERSON_NAME = :stPersonName",nativeQuery = true)
    Page<VotePerson> findVotePeopleByStPersonName2(@Param("stPersonName")String stPersonName, Pageable pageable);

}
