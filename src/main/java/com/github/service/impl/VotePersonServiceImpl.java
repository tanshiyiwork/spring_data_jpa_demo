package com.github.service.impl;

import com.github.model.VotePerson;
import com.github.repo.VotePersonRepository;
import com.github.service.VotePersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("votePersonService")
@Transactional
public class VotePersonServiceImpl implements VotePersonService {

    @Autowired
    VotePersonRepository votePersonRepository;

    @Override
    public List<VotePerson> findPeople(VotePerson probe) {
        return votePersonRepository.findAll(Example.of(probe));
    }
}
