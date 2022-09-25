package com.ws.masterserver.service.impl;

import com.ws.masterserver.service.TypeService;
import com.ws.masterserver.utils.base.WsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {

    private final WsRepository repository;

    @Override
    public Object noPage() {
        return repository.typeRepository.findAll();
    }
}
