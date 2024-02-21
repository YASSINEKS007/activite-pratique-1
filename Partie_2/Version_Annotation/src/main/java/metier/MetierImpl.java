package metier;

import framework.annotations.Autowired;
import framework.annotations.Component;
import framework.annotations.Qualified;
import dao.IDao;

@Component
public class MetierImpl implements IMetier {

    @Autowired
    @Qualified("daoImplV2")
    private IDao dao;

    @Override
    public double calcul() {
        // Some business logic that uses the dao
        return dao.save();
    }
}
