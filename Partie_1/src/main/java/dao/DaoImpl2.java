package dao;

import dao.IDao;
import org.springframework.stereotype.Component;

@Component("v2")
public class DaoImpl2 implements IDao {
    @Override
    public double getData() {
        System.out.println("Version 2: ");
        double temp = 2;
        return temp;
    }
}
