package metier;

import dao.IDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MetierImpl implements IMetier{
    @Autowired
    @Qualifier("v1")
    private IDao dao;
    @Override
    public double calcul() {
        double res = dao.getData();
        return res * 100;
    }

    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
