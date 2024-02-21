package presentation;

import framework.CustomApplicationContext;
import metier.IMetier;

public class Presentation_CustomFramework {
    public static void main(String[] args) {
        CustomApplicationContext context = new CustomApplicationContext("dao", "metier");
        IMetier metier = (IMetier) context.getBean("metier");
        System.out.println("Result: " + metier.calcul());
    }
}
