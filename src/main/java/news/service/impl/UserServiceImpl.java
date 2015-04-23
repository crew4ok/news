package news.service.impl;

import news.service.UserService;
import news.tables.pojos.User;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static news.tables.User.USER;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private DSLContext dslContext;

    @Override
    public User getById(Long id) {
        news.tables.User u = USER.as("u");

        Result<Record> records = dslContext.select(u.fields())
                .from(u)
                .fetch();
    }
}
