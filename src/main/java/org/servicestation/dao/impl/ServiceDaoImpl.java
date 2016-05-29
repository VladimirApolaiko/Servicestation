package org.servicestation.dao.impl;

import org.servicestation.dao.IServiceDao;
import org.servicestation.dao.exceptions.NullPropertiesException;
import org.servicestation.model.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceDaoImpl implements IServiceDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceDaoImpl.class);

    private static final String DELIMITER = ", ";

    private static final String CREATE_NEW_SERVICE = "insert into service (service_name, price) values (:service_name, :price)";

    private static final String DELETE_SERVICE_BY_ID = "delete from service where id = :id";

    private static final String GET_SERVICE_BY_ID = "select * from service where id = :id";

    private static final String GET_ALL_SERVICES = "select * from service";

    private static final String UPDATE_SERVIE = "update service set ";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Service createNewService(Service newService) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("service_name", newService.service_name);
        params.addValue("price", newService.price);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(CREATE_NEW_SERVICE, params, keyHolder);

        return getService(keyHolder.getKeys());
    }

    @Override
    public void deleteService(Integer serviceId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", serviceId);

        namedParameterJdbcTemplate.update(DELETE_SERVICE_BY_ID, params);
    }

    @Override
    public List<Service> getAllServices() {
        List<Service> services = new ArrayList<>();

        namedParameterJdbcTemplate.query(GET_ALL_SERVICES, rs -> {
            services.add(getService(rs));
        });

        return services;
    }


    @Override
    public Service getServiceById(Integer serviceId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", serviceId);

        return namedParameterJdbcTemplate.queryForObject(GET_SERVICE_BY_ID, params, (rs, rowNum) -> {
            return getService(rs);
        });
    }

    @Override
    public Service updateService(Integer serviceId, Service newService) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        StringBuilder sql = new StringBuilder(UPDATE_SERVIE);
        boolean notNull = false;

        for (Field field : newService.getClass().getFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(newService);

                if (field.get(newService) != null) {
                    params.addValue(field.getName(), value);
                    sql.append(getColumnMapping(field.getName()));
                    notNull = true;
                }
            } catch (IllegalAccessException e) {
                LOGGER.debug("Can't get value of field " + field.getName(), e);
            }
        }

        if (!notNull) throw new NullPropertiesException("At least one property should be not null");

        sql.deleteCharAt(sql.length() - 2);
        params.addValue("id", serviceId);
        sql.append("where id=:id");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql.toString(), params, keyHolder);

        return getService(keyHolder.getKeys());
    }

    private Service getService(Map<String, Object> keys) {
        Service service = new Service();

        service.id = (Integer) keys.get("id");
        service.service_name = (String) keys.get("service_name");
        service.price = (Double) keys.get("price");

        return service;
    }

    private Service getService(ResultSet rs) throws SQLException {
        Service service = new Service();

        service.id = rs.getInt("id");
        service.service_name = rs.getString("service_name");
        service.price = rs.getDouble("price");

        return service;
    }

    private String getColumnMapping(final String columnName) {
        return columnName + "=:" + columnName + DELIMITER;
    }
}
