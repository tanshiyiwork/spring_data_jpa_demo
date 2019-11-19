package com.github.service.impl;

import com.github.model.CardLoseInfo;
import com.github.repo.CardLoseInfoRepository;
import com.github.service.CardLoseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("cardLoseInfoService")
@Transactional
public class CardLoseInfoServiceImpl implements CardLoseInfoService {

    @Autowired
    CardLoseInfoRepository cardLoseInfoRepository;

    @Override
    public void saveInfo(CardLoseInfo cardLoseInfo) {
        cardLoseInfoRepository.save(cardLoseInfo);
    }
}
