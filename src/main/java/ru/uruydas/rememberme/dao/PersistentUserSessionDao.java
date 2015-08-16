package ru.uruydas.rememberme.dao;

import ru.uruydas.rememberme.model.PersistentUserSession;

public interface PersistentUserSessionDao {

    PersistentUserSession findByToken(String token);

    PersistentUserSession save(PersistentUserSession session);

    void deleteByToken(String token);
}
