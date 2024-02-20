package dao;

import org.springframework.stereotype.Component;

@Component("v1")
public class DaoImpl implements IDao{
    @Override
    public double getData() {
        System.out.println("version 1: ");
        double temp = 1;
        return temp;
    }
}
