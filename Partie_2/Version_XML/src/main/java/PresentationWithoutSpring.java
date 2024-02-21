public class PresentationWithoutSpring {
    public static void main(String[] args) {
        try {
            DependencyInjector injector = new DependencyInjector("src/main/resources/config.xml");
            IMetier metier = (IMetier) injector.getBean("metier");
            System.out.println(metier.calcul());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
