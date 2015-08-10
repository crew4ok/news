package ru.uruydas.service.impl;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.uruydas.service.HealthcheckService;

@Service
@Transactional(readOnly = true)
public class HealthcheckServiceImpl implements HealthcheckService {

    @Autowired
    private DSLContext ctx;

    @Override
    public void doHealthcheck() {
        ctx.selectOne().execute();
    }
}
