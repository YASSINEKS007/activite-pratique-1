package dao;


public class DaoImpl2 implements IDao {
    @Override
    public double getData() {
        System.out.println("Version 2: ");
        double temp = 2;
        return temp;
    }
}
