package com.dao;

import com.model.Entity;
import org.hibernate.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AbstractCrudDAO<E extends Entity> extends BaseDAO implements CrudDAO<E> {

    private Class<E> metaclass;

    public AbstractCrudDAO(Class<E> metaclass) {
        this.metaclass = metaclass;
    }

    @Override
    public E get(int id) {
        Session session = getSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();
                E entity = (E) session.get(this.metaclass, id);
                tx.commit();

                return entity;
            } catch (HibernateException e) {
                Transaction tx = session.getTransaction();
                if (tx != null) {
                    tx.rollback();
                }

                e.printStackTrace();
            } finally {
                closeSession();
            }
        }

        return null;
    }

    @Override
    public boolean add(E entity) {
        Session session = getSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();
                session.save(entity);
                tx.commit();

                return true;
            } catch (HibernateException e) {
                Transaction tx = session.getTransaction();
                if (tx != null) {
                    tx.rollback();
                }

                e.printStackTrace();
            } finally {
                closeSession();
            }
        }

        return false;
    }

    @Override
    public boolean delete(E entity) {
        Session session = getSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();
                session.delete(entity);
                tx.commit();

                return true;
            } catch (HibernateException e) {
                Transaction tx = session.getTransaction();
                if (tx != null) {
                    tx.rollback();
                }

                e.printStackTrace();
            } finally {
                closeSession();
            }
        }

        return false;
    }

    @Override
    public boolean update(E entity) {
        Session session = getSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();
                session.update(entity);
                tx.commit();

                return true;
            } catch (HibernateException e) {
                Transaction tx = session.getTransaction();
                if (tx != null) {
                    tx.rollback();
                }

                e.printStackTrace();
            } finally {
                closeSession();
            }
        }

        return false;
    }

    @Override
    public List<E> list() {
        Session session = getSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();
                List<E> result = session.createCriteria(this.metaclass).list();
                tx.commit();

                return result;
            } catch (HibernateException e) {
                Transaction tx = session.getTransaction();
                if (tx != null) {
                    tx.rollback();
                }

                e.printStackTrace();
            } finally {
                closeSession();
            }
        }

        return null;
    }

    @Override
    public List<E> filter(Criteria criteria) {
        Session session = getSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();
                List<E> result = criteria.list();
                tx.commit();

                return result;
            } catch (HibernateException e) {
                Transaction tx = session.getTransaction();
                if (tx != null) {
                    tx.rollback();
                }

                e.printStackTrace();
            } finally {
                closeSession();
            }
        }

        return null;
    }

    @Override
    public List<E> filter(SQLQuery query) {
        Session session = getSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();

                List<E> result = new ArrayList<>();
                List rows = query.list();
                for (Object row : rows) {
                    Object[] joinedRow = (Object[])row;

                    for (Object element : joinedRow) {
                        if (element.getClass().equals(this.metaclass)) {
                            result.add((E)element);
                        }
                    }
                }

                tx.commit();

                return result;
            } catch (HibernateException e) {
                Transaction tx = session.getTransaction();
                if (tx != null) {
                    tx.rollback();
                }

                e.printStackTrace();
            } finally {
                closeSession();
            }
        }

        return null;
    }

    @Override
    public List<E> filter(SQLQuery query, Map<String, Object> params, Map<String, Class<? extends Entity>> entities, Map<String, String> joins) {
        Session session = getSession();
        if (session != null) {
            entities.forEach( (name, entityClass) -> { query.addEntity(name, entityClass); });
            params.forEach( (name, value) -> { query.setParameter(name, value); } );

            if (joins != null) {
                joins.forEach( (entityName, property) -> { query.addJoin(entityName, property); } );
            }

            return filter(query);
        }

        return null;
    }
}
