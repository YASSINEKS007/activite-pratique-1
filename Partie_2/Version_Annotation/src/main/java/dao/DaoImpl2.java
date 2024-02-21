package dao;

import framework.annotations.Component;

@Component("daoImplV2")
public class DaoImpl2 implements IDao {
    public double save() {
        System.out.println("Version DaoImpl2: ");
        double temp = 2;
        return temp;
    }
}
