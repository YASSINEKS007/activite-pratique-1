<h1 style="text-align: center">Compte Rendu</h1>

<h2>Introduction</h2>
<p>Ce compte rendu se concentre sur deux parties essentielles liées à la gestion des dépendances dans le développement logiciel. 
Dans la première partie, nous explorons diverses techniques de gestion des dépendances, de la création d'interfaces et d'implémentations à l'injection de dépendances, en passant par l'utilisation du Framework Spring. 
Dans la deuxième partie, nous présentons un mini-projet axé sur le développement d'un Framework d'injection de dépendances, similaire à Spring IOC, offrant une solution personnalisée pour simplifier la gestion des dépendances dans les applications. 
Dans la première partie, nous commençons par la définition de deux interfaces, IDao et IMetier, représentant respectivement les opérations de récupération de données et de calcul métier. En développant des implémentations pour ces interfaces, nous mettons l'accent sur le couplage faible pour favoriser la modularité et la flexibilité du système. Nous explorons ensuite différentes méthodes d'injection de dépendances, de l'instanciation statique à l'utilisation du Framework Spring, en passant par l'instanciation dynamique. 
Dans la deuxième partie, nous concevons et créons un mini Framework d'injection de dépendances qui permet aux programmeurs de gérer les dépendances entre les différents composants de leur application. Ce Framework offre deux approches de configuration : via un fichier XML et via l'utilisation d'annotations directement dans le code source. Il propose également trois méthodes d'injection : par le constructeur, par le Setter, ou directement via l'attribut (Field), offrant ainsi une flexibilité maximale dans la gestion des dépendances. Ce compte rendu détaillera chaque étape de manière à offrir une compréhension approfondie des techniques utilisées pour gérer les dépendances dans un système logiciel, ainsi que la conception et la mise en œuvre pratique d'un Framework d'injection de dépendances personnalisé. Des exemples concrets d'utilisation seront fournis pour illustrer la mise en œuvre pratique de ces concepts dans des applications réelles.

<h2>Partie 1</h2>

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

<h2>Partie 2</h2>

<h3>Structure du projet</h4>
<img src="partie_2_project_structure.png">
<h3>Injection des dependances a travers un fichier XML</h3>
