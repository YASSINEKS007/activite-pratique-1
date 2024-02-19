<h1>Compte Rendu</h1>
<p>Le code fourni comprend la définition d'interfaces et de leurs implémentations, ainsi que des méthodes pour injecter les dépendances dans une application Java.

Premièrement, deux interfaces sont définies : IDao et IMetier. L'interface IDao spécifie une méthode getData() qui retourne un double. Ensuite, une implémentation de cette interface, DaoImpl, est fournie. Cette implémentation retourne une valeur constante dans la méthode getData().

De même, l'interface IMetier déclare une méthode calcul() qui retourne un double, et son implémentation, MetierImpl, utilise l'injection de dépendances pour obtenir un objet IDao et effectuer un calcul sur les données obtenues.

La section suivante du code concerne l'injection des dépendances. Trois méthodes d'injection sont présentées : l'instanciation statique, l'instanciation dynamique et l'utilisation du Framework Spring.

Dans l'instanciation statique, les objets sont créés explicitement dans le code. Par exemple, dans Presentation_statique, une instance de DaoImpl est créée et injectée dans une instance de MetierImpl.

Dans l'instanciation dynamique, les classes sont chargées dynamiquement à partir d'un fichier de configuration("config.txt"). La classe Presentation_dynamique charge les noms de classe à partir d'un fichier et crée les instances appropriées à l'aide de la réflexion.

Enfin, l'utilisation du Framework Spring simplifie considérablement l'injection des dépendances. Deux exemples sont donnés : l'un utilisant une configuration XML (Presentation_Spring_XML) et l'autre utilisant des annotations (Presentation_Spring_Annotation). Dans les deux cas, Spring gère l'injection des dépendances en configurant les beans et leurs relations.

En résumé, le code illustre différentes approches pour gérer les dépendances dans une application Java, allant des méthodes traditionnelles à l'utilisation de frameworks comme Spring pour simplifier le processus d'injection de dépendances. Chaque méthode a ses propres avantages et inconvénients en termes de flexibilité, maintenabilité et facilité d'utilisation.



</p>
<ol>
    <li>
        <p>Créer l'interface IDao avec une méthode getData() :</p>
        <pre><code>package dao;

public interface IDao {
// Interface définissant une méthode pour obtenir des données
double getData();
}</code></pre>
</li>
<li>
<p>Créer une implémentation de cette interface :</p>
<pre><code>package dao;

import org.springframework.stereotype.Component;

@Component("v1")
public class DaoImpl implements IDao {
// Implémentation de l'interface IDao
@Override
public double getData() {
System.out.println("version 1: ");
double temp = 1;
return temp;
}
}</code></pre>
</li>
<li>
<p>Créer l'interface IMetier avec une méthode calcul :</p>
<pre><code>package metier;

public interface IMetier {
// Interface définissant une méthode de calcul
double calcul();
}</code></pre>
</li>
<li>
<p>Créer une implémentation de cette interface en utilisant le couplage faible :</p>
<pre><code>package metier;

import dao.IDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MetierImpl implements IMetier {
// Implémentation de l'interface IMetier avec injection de dépendance
@Autowired
@Qualifier("v1")
private IDao dao;

    @Override
    public double calcul() {
        // Méthode de calcul utilisant les données obtenues via l'interface IDao
        double res = dao.getData();
        return res * 100;
    }

    public void setDao(IDao dao) {
        this.dao = dao;
    }
}</code></pre>
</li>
<li>
<p>Faire l'injection des dépendances :</p>
<ol type="a">
<li>
<p>Par instanciation statique :</p>
<pre><code>package presentation;

import dao.DaoImpl;
import metier.MetierImpl;

public class Presentation_statique {
public static void main(String[] args) {
// Création des objets et injection des dépendances de manière statique
DaoImpl dao = new DaoImpl();
MetierImpl metier = new MetierImpl();
metier.setDao(dao);
System.out.println(metier.calcul());
}
}</code></pre>
</li>
<li>
<p>Par instanciation dynamique :</p>
<pre><code>package presentation;

import dao.IDao;
import metier.IMetier;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Presentation_dynamique {
public static void main(String[] args) throws Exception {
// Chargement dynamique des classes à partir d'un fichier de configuration
Scanner scanner = new Scanner(new File("src/main/resources/config.txt"));
String daoClassName = scanner.nextLine();
Class cDao = Class.forName(daoClassName);
IDao dao = (IDao) cDao.getConstructor().newInstance();
String metierClassName = scanner.nextLine();
Class cMetier = Class.forName(metierClassName);
IMetier metier = (IMetier) cMetier.getConstructor().newInstance();

        Method setDao = cMetier.getDeclaredMethod("setDao", IDao.class);
        setDao.invoke(metier, dao);
        System.out.println(metier.calcul());
    }
}</code></pre>
</li>
<li>
<p>En utilisant le Framework Spring :</p>
<ul>
<li>
<p>Version XML :</p>
<pre><code>package presentation;

import metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Presentation_Spring_XML {
public static void main(String[] args) {
// Utilisation de Spring avec configuration XML
ApplicationContext springContext = new ClassPathXmlApplicationContext("config.xml");
IMetier metier = springContext.getBean(IMetier.class);
System.out.println(metier.calcul());
}
}</code></pre>
</li>
<li>
<p>Version annotations :</p>
<pre><code>package presentation;

import metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Presentation_Spring_Annotation {
public static void main(String[] args) {
// Utilisation de Spring avec annotations
ApplicationContext context = new AnnotationConfigApplicationContext("dao", "metier");
IMetier metier = context.getBean(IMetier.class);
System.out.println(metier.calcul());
}
}</code></pre>
</li>
</ul>
</li>
</ol>
</li>
</ol>
