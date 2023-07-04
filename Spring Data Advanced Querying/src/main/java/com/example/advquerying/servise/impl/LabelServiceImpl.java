package com.example.advquerying.servise.impl;

import com.example.advquerying.repository.LabelRepository;
import com.example.advquerying.servise.LabelService;
import org.springframework.stereotype.Service;

@Service
public class LabelServiceImpl implements LabelService {

    private final LabelRepository labelRepository;

    public LabelServiceImpl(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

}
