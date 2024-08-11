package metier;

import framework.annotations.Autowired;
import framework.annotations.Component;
import framework.annotations.Qualified;
import dao.IDao;

@Component
public class MetierImpl implements IMetier {

    @Autowired
    @Qualified("daoImplV1")
    private IDao dao;

    public MetierImpl() {
    }

    @Override
    public double calcul() {

        // Some business logic that uses the dao
        return dao.save();
    }

}
