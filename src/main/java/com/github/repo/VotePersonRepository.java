package com.github.repo;

import com.github.model.VotePerson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VotePersonRepository extends JpaRepository<VotePerson, String> {

    @Query("select v from VotePerson v")
    Page<VotePerson> findAll(Pageable pageable);
}
