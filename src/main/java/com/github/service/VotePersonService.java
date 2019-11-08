package com.github.service;

import com.github.model.VotePerson;

import java.util.List;

public interface VotePersonService {
    public List<VotePerson> findPeople(VotePerson probe);
}
