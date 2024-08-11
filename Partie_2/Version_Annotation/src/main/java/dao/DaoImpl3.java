package dao;

import framework.annotations.Component;

@Component("daoImplV3")
public class DaoImpl3 implements IDao {
    public double save() {
        System.out.println("Version DaoImpl3: ");
        double temp = 3;
        return temp;
    }
}
