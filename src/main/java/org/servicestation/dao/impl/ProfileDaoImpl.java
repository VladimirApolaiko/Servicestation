package org.servicestation.dao.impl;

import org.servicestation.dao.IProfileDao;
import org.servicestation.dao.exceptions.NullPropertiesException;
import org.servicestation.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileDaoImpl implements IProfileDao {

    private static final String DELIMITER = ", ";
    private static final String TYPE_NAME = "profile.";

    private static final String CREATE_PROFILE = "update mechanic set profile = ROW(:name, :surname, :patronymic, :passport_number, :passport_id, :address, :phone_number) where mechanic.id=:id;";

    private static final String SELECT_PROFILE = "select " +
            "(mechanic.profile).name, " +
            "(mechanic.profile).surname, " +
            "(mechanic.profile).patronymic, " +
            "(mechanic.profile).passport_number, " +
            "(mechanic.profile).passport_id, " +
            "(mechanic.profile).address," +
            "(mechanic.profile).phone_number " +
            "from mechanic where mechanic.id=:id;";

    private static final String UPDATE_PROFILE = "update mechanic set ";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public Profile createProfile(final Integer mechanicId,
                                 final String name,
                                 final String surname,
                                 final String patronymic,
                                 final String passportNumber,
                                 final String passportId,
                                 final String address,
                                 final String phoneNumber) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);
        params.addValue("surname", surname);
        params.addValue("patronymic", patronymic);
        params.addValue("passport_number", passportNumber);
        params.addValue("passport_id", passportId);
        params.addValue("address", address);
        params.addValue("phone_number", phoneNumber);

        params.addValue("id", mechanicId);
        namedParameterJdbcTemplate.update(CREATE_PROFILE, params);

        return getProfileById(mechanicId);
    }

    @Override
    public Profile changeProfile(final Integer mechanicId, final Profile newProfile) throws Exception {
        MapSqlParameterSource params = new MapSqlParameterSource();
        StringBuilder sql = new StringBuilder(UPDATE_PROFILE);
        boolean notNull = false;

        for (Field field : newProfile.getClass().getFields()) {
            field.setAccessible(true);
            Object value = field.get(newProfile);

            if (value != null) {
                params.addValue(field.getName(), value);
                sql.append(getColumnMapping(field.getName()));
                notNull = true;
            }
        }

        if (!notNull) throw new NullPropertiesException("At least one property should be not null");

        sql.deleteCharAt(sql.length() - 2);//delete last delimiter
        params.addValue("id", mechanicId);
        sql.append("where id=:id");

        namedParameterJdbcTemplate.update(sql.toString(), params);

        return getProfileById(mechanicId);

    }

    @Override
    public Profile getProfileById(final Integer mechanicId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", mechanicId);
        return namedParameterJdbcTemplate.queryForObject(SELECT_PROFILE, params, (rs, rowNum) -> {
            return getProfile(rs);
        });
    }

    private Profile getProfile(final ResultSet rs) throws SQLException {
        Profile profile = new Profile();
        profile.name = rs.getString("name");
        profile.surname = rs.getString("surname");
        profile.patronymic = rs.getString("patronymic");
        profile.passport_number = rs.getString("passport_number");
        profile.passport_id = rs.getString("passport_id");
        profile.address = rs.getString("address");
        profile.phone_number = rs.getString("phone_number");

        return profile;
    }

    private String getColumnMapping(final String columnName) {
        return TYPE_NAME + columnName + "=:" + columnName + DELIMITER;
    }


}
