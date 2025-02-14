package model.dao;

import db.DB;
import model.dao.impl.PersonDaoJDBC;

public class DaoFactory {

    public static PersonDao createPersonDao() {
        return new PersonDaoJDBC(DB.getConnection());
    }
}
