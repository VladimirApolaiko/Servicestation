package org.servicestation.dao.impl;

import org.servicestation.dao.IMechanicDao;
import org.servicestation.model.Mechanic;
import org.servicestation.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Map;

public class MechanicDaoImpl implements IMechanicDao {

    private static final String INSERT_MECHANIC = "insert into mechanic (name, surname, patronymic, profile, station_id) " +
            "values(:name, :surname, :patronymic, ROW(:mobile_phone_number), :station_id)";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Mechanic createMechanic(String name, String surname, String patronymic, Profile profile, int stationId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);
        params.addValue("surname", surname);
        params.addValue("patronymic" , patronymic);
        params.addValue("mobile_phone_number", profile.phoneNumber);
        params.addValue("station_id", stationId);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(INSERT_MECHANIC,
                params,
                keyHolder,
                new String[]{"id", "name", "surname", "patronymic", "profile.mobile_phone_number", "station_id"} );

        return getMechanic(keyHolder.getKeys());
    }

    @Override
    public Profile changeProfile(int mechanicId, Profile newProfile) {
        return null;
    }

    @Override
    public Mechanic getMechanicById(int mechanicId) {
        return null;
    }

    @Override
    public Mechanic deleteMechanic(int deleteMechanic) {
        return null;
    }

    private Mechanic getMechanic(Map<String, Object> keys) {
        Mechanic mechanic = new Mechanic();
        mechanic.id = (int)keys.get("id");
        mechanic.name = (String)keys.get("name");
        mechanic.surname = (String) keys.get("surname");
        mechanic.patronymic = (String) keys.get("patronymic");
        mechanic.profile = new Profile(){{phoneNumber =(String) keys.get("mobile_phone_number");}};
        mechanic.stationId = (int)keys.get("station_Id");
        return mechanic;
    }
}
