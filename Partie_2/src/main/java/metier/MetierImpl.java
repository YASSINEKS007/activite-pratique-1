package metier;

import dao.IDao;
import framework.version_annotation.annotations.Autowired;
import framework.version_annotation.annotations.Component;

@Component
public class MetierImpl implements IMetier{
    @Autowired
    private IDao dao;
    @Override
    public double calcul() {
        double res = dao.getData();
        return res * 100;
    }
    @Autowired
    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
