package org.servicestation.dao.impl;

import org.servicestation.dao.IMechanicOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class MechanicOrderImpl implements IMechanicOrder {

    private static String ASSIGN_ORDER = "insert into mechanic_order " +
            "(order_id, mechanic_id) values(:order_id, :mechanic_id);";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void assignOrder(final Integer mechanicId, final Long orderId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("mechanic_id", mechanicId);
        params.addValue("order_id", orderId);

        namedParameterJdbcTemplate.update(ASSIGN_ORDER, params);
    }
}
