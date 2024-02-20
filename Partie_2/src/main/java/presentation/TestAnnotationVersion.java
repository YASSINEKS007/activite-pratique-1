package presentation;

import framework.version_annotation.dependencyInjector.DependencyInjector;
import dao.DaoImpl;
import metier.MetierImpl;

public class TestAnnotationVersion {
    public static void main(String[] args) {
        // Via setters
        DependencyInjector injector = new DependencyInjector("dao", "metier");

        // Attempt to retrieve instances of DaoImpl and MetierImpl directly
        DaoImpl daoImpl = injector.getComponent(DaoImpl.class);
        MetierImpl metierImpl = injector.getComponent(MetierImpl.class);
        metierImpl.setDao(daoImpl);

        System.out.println(metierImpl.calcul());


        // Via constructor
        DependencyInjector injector_constructor = new DependencyInjector("dao", "metier");
        MetierImpl metierImpl_construtor = injector.getComponent(MetierImpl.class);
        System.out.println(metierImpl.calcul());

    }
}
