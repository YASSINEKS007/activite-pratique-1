package dao;

import framework.annotations.Component;

@Component("daoImplV1")
public class DaoImpl1 implements IDao {
    public double save() {
        System.out.println("Version framework.dao.DaoImpl1: ");
        double temp = 1;
        return temp;
    }
}
