public class MetierImpl implements IMetier {
    private IDao dao;

    // Injection de dépendance via le constructeur
    public MetierImpl(IDao dao) {
        this.dao = dao;
    }

    public MetierImpl() {
    }

    public double calcul() {
        double res = dao.save();
        return res * 100;
    }

    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
