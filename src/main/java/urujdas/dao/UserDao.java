package urujdas.dao;

import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import urujdas.model.Gender;
import urujdas.model.User;
import urujdas.tables.records.UsersRecord;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static urujdas.Tables.USERS;

public interface UserDao {

    User getById(Long id);

    User getByUsername(String username);

    void create(User user);
}
