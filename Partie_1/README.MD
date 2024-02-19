<h1>Compte Rendu</h1>
    <ol>
        <li>
            <p>Créer l'interface IDao avec une méthode getData() :</p>
            <pre><code>package dao;

public interface IDao {
double getData();
}</code></pre>
</li>
<li>
<p>Créer une implémentation de cette interface :</p>
<pre><code>package dao;

import org.springframework.stereotype.Component;

@Component("v1")
public class DaoImpl implements IDao {
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
@Autowired
@Qualifier("v1")
private IDao dao;

    @Override
    public double calcul() {
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
Scanner scanner = new Scanner(new File("src/main/resources/config.txt"));
String daoClassName = scanner.nextLine();
Class<?> cDao = Class.forName(daoClassName);
IDao dao = (IDao) cDao.getConstructor().newInstance();

        String metierClassName = scanner.nextLine();
        Class<?> cMetier = Class.forName(metierClassName);
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