package com.dao;

import com.model.Role;
import com.model.RoleId;
import org.hibernate.Session;

/**
 * Created by MakerS MS on 02.05.2017.
 */
public class RoleDAOImpl extends BaseDAO implements RoleDAO {

    @Override
    public Role getById(int id){
        Session session = getSessionFactory().openSession();
        session.beginTransaction();

        Role role = (Role) session.get(Role.class, id);

        session.getTransaction().commit();
        session.close();

        return role;
    }
}
