package presentation;

import dao.IDao;
import framework.version_xml.DependencyInjector;
import metier.MetierImpl;

public class TestVersionXml {
    public static void main(String[] args) {
        DependencyInjector injector = new DependencyInjector();
        injector.loadConfig("src/main/resources/config.xml");
        MetierImpl metier = injector.getComponent("metier", MetierImpl.class);

        IDao dao = injector.getComponent("dao", IDao.class);
        metier.setDao(dao);

        System.out.println(metier.calcul());
    }
}

