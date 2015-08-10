package ru.uruydas.dao.impl.jooq.mappers;

import org.jooq.Record;
import org.jooq.RecordMapper;
import ru.uruydas.model.users.*;

import static ru.uruydas.tables.UserFiltersTable.USER_FILTERS;
import static ru.uruydas.util.MapperUtils.fromNullable;

public class UserFilterRecordMapper implements RecordMapper<Record, UserFilter> {
    @Override
    public UserFilter map(Record record) {
        Gender gender = fromNullable(record.getValue(USER_FILTERS.GENDER), Gender::valueOf);
        GenderPreferences genderPreferences = fromNullable(
                record.getValue(USER_FILTERS.GENDER_PREFERENCES),
                GenderPreferences::valueOf
        );
        RelationsPreferences relationsPreferences = fromNullable(
                record.getValue(USER_FILTERS.RELATIONS_PREFERENCES),
                RelationsPreferences::valueOf
        );

        AgeRange ageRange = null;
        Integer lower = record.getValue(USER_FILTERS.AGE_LOWER);
        Integer higher = record.getValue(USER_FILTERS.AGE_HIGHER);
        if (lower != null || higher != null) {
            ageRange = new AgeRange(lower, higher);
        }

        return UserFilter.builder()
                .withUserId(record.getValue(USER_FILTERS.USER_ID))
                .withGender(gender)
                .withGenderPreferences(genderPreferences)
                .withRelationsPreferences(relationsPreferences)
                .withAgeRange(ageRange)
                .build();

    }
}
