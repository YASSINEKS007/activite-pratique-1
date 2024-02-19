package presentation;

import dao.DaoImpl;
import metier.MetierImpl;

public class Presentation_statique {
    public static void main(String[] args) {
      DaoImpl dao = new DaoImpl();
        MetierImpl metier = new MetierImpl();
        metier.setDao(dao);
        System.out.println(metier.calcul());
    }
}
