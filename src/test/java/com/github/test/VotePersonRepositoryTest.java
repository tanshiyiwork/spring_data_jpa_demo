package com.github.test;

import com.github.model.VotePerson;
import com.github.repo.VotePersonRepository;
import com.github.service.VotePersonService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class VotePersonRepositoryTest {

    private static ApplicationContext applicationContext = null;

    static{
        applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    }

    @Test
    public void testDataSource() throws SQLException {
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        System.out.println(dataSource.getConnection());
    }

    @Test
    public void testInsert() {
        VotePersonRepository votePersonRepository = (VotePersonRepository)applicationContext.getBean("votePersonRepository");
        /*VotePerson votePerson = new VotePerson();
        votePerson.setStId("100001");
        votePerson.setStPersonName("jack");
        votePerson.setStCountry("china");
        votePerson.setStSex("1");*/
        votePersonRepository.deleteById("100001");
        //assertThat(votePersonRepository.findAll()).isNotEmpty();
        //votePersonRepository.save(votePerson);
    }

    @Test
    public void testFind() {
        VotePersonRepository votePersonRepository = (VotePersonRepository)applicationContext.getBean("votePersonRepository");
        Pageable pageable = PageRequest.of(1,2);
        Page<VotePerson> page = votePersonRepository.findAll(pageable);
        List<VotePerson> list = page.getContent();
        for (VotePerson votePerson:list) {
            System.out.println(votePerson);
        }
        assertThat(page.getContent()).isNotEmpty();
    }

    @Test
    public void testQuery1() {
        VotePersonRepository votePersonRepository = (VotePersonRepository)applicationContext.getBean("votePersonRepository");
        Pageable pageable = PageRequest.of(0,2);
        Page<VotePerson> page = votePersonRepository.findVotePeopleByStPersonName2("风华",pageable);
        List<VotePerson> list = page.getContent();
        for (VotePerson votePerson:list) {
            System.out.println(votePerson);
        }
    }
}
