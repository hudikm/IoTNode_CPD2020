## Základy používania Apache Maven

Maven je komplexný nástroj na správu projektov, ktorého najčastejšou úlohov je kompilácia Java kódu. Maven zabepečuje komplexnú starostlivosť počas celého vývojového cyklu aplikácie počnúc: správou závislostí (rôzne knižnice),  kompiláciou, testovaním, zpúzdrenie aplikácie do požadovaného kontajnera, reportovani a v neposlednom rade nasadením aplikácie do produkcie. 

Pre prístup k Maven s príkazového riadku sa používa príkaz `mvn`. Je samozrejmé, že Maven musí byť nainštalovaný v systéme. V našom prípade, keď využívame vývojové prostredie InteliJ Idea, sa vývojové prostredie postára o nainštalovnie Maven a všetky úkony týkajúce sa Maven budeme riešiť cez dané IDE. 
> Maven integrovaný do vývojového prostredia
	![](images/Screenshot_20200501_134018.png#center)

### Objektový model projektu

Konfigurácia projektu prebieha prostredníctvom objektového modelu (angl. *Project Object Model* - POM), ktorý je reprezentovaný súborom `pom.xml`. Tento súbor je umiestnený v koreňovom adresári projektu. POM definuje všetky nevyhnutné veci o projekte ako napr. názov, verzia, autor, Java verzie, všetky závislosti  a konfigurácie pluginov. 

Ak projekt na ktorom pracujeme je viac modulový tak POM taktiež definuje vzťahy medzi nimi.     

```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>sk.fri.uniza</groupId>
    <artifactId>WeatherStation</artifactId>
    <packaging>jar</packaging>
    <version>1.0-SNAPSHOT</version>
    <name>org.baeldung</name>
    <url>http://maven.apache.org</url>
    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
            //...
            </plugin>
        </plugins>
    </build>
</project>
```

#### Definovanie projektu

Maven používa skupinu identifikátorov,  ktoré musia jedinečne identifikovať projekt a taktiež  definovať finálne zapúzdrenie aplikácie. 

- *groupId* - jedinečný základný názov spoločnosti alebo skupiny, ktorá vytvorila projekt
- *artifactId* - jedinečný názov projektu
- *version* - verzia projektu
- *balenie* - spôsob zapúzdrenia (napr. *WAR* / *JAR* / *ZIP* )

Prvé tri z nich ( *groupId: artifactId: version* ) sa spoja do jedinečného identifikátora a sú mechanizmom, ktorým sa určuje, ktoré verzie externých knižníc (napr. JAR) projekt použije.

#### Pojemy Artefakt a Archetype

**Artefakt** [artifact] je hmatateľný výstup projektu, ktorý vznikol kompiláciu kódu a následným zabalením do balík obvykle JAR / WAR. Tento artefakt musí byť jednoznačne identifikovateľný pomocou:  *groupId: artifactId: version*. 

**Archetype** je kostra/štablóna ako má nový projekt vyzerať, pred-pripravená štruktúra adresárov. Používa sa na rýchle vytvorenie nového projektu  určitého typu (napr. java aplikácia "hello world"). Odbremeňuje vývojára od manuálnej práce, ktorú by musel vykonať pri vytváraní nového projektu. Počas vývoja aplikácie sa používa iba pri vytváraní projektu. 

#### Závislosti

Tieto externé knižnice, ktoré projekt používa, sa nazývajú závislosti. Funkcia správy závislostí v Maven zaisťuje automatické stiahnutie týchto knižníc z centrálneho úložiska(maven repositar), tým pádom odpadá manuálne sťahovanie a vkladanie týchto knižníc do projektu.

Ak chceme deklarovať závislosť od externej knižnice, musíme poznať a poskytnúť *groupId, artifactId* a *verziu* knižnice. 

```xml
<dependency>
      <groupId>com.squareup.retrofit2</groupId>
      <artifactId>converter-jackson</artifactId>
      <version>2.8.1</version>
</dependency>
```

#### Centrálne a lokálne úložiská

Úložisko v Mavene sa používa na uchovávanie artefaktov a závislostí rôznych typov. Predvolený lokálny archív sa nachádza v priečinku `.m2/repository` v domovskom adresári užívateľa.

Ak je artefakt alebo doplnok k dispozícii v miestnom úložisku, Maven ho použije. V opačnom prípade sa stiahne z centrálneho úložiska a uloží do lokálneho úložiska. Predvolené centrálne úložisko je [Maven Central](https://search.maven.org/classic/#search|ga|1|centra) .

Niektoré knižnice, napríklad server JBoss, nie sú dostupné v centrálnom úložisku, ale sú k dispozícii v alternatívnom úložisku. V prípade týchto knižníc musíme zadať adresu URL alternatívneho úložiska v súbore *pom.xml* :

```xml
<repositories>
    <repository>
        <id>JBoss repository</id>
        <url>http://repository.jboss.org/nexus/content/groups/public/</url>
    </repository>
</repositories>
```

!!! note "**Upozorňujeme, že vo svojich projektoch môžete použiť viacero externých úložísk.**"

#### Premenné

Definované premenené môžu pomôcť pri *ľahšom* čítaní a údržbe súboru *pom.xml*.  Napríklad v prípade klasického použitia by sa na definovanie verzií závislostí projektu použili premenné.

!!! note
	Definované premené sú dostupné kdekoľvek v rámci *pom.xml* pomocou notácie `${názov.premennej}` .

```xml
<properties>
    <retrofit.version>2.8.1</spring.version>
</properties>
<dependency>
      <groupId>com.squareup.retrofit2</groupId>
      <artifactId>converter-jackson</artifactId>
      <version>${retrofit.version}</version>
</dependency>
<dependency>
      <groupId>com.squareup.retrofit2</groupId>
      <artifactId>retrofit</artifactId>
      <version>${retrofit.version}</version>
</dependency>
```

#### Build sekcia

*Build* sekcia je veľmi dôležitá časť Maven *POM.* Poskytuje informácie o predvolenom cieli Maven, definuje adresár pre výsledok kompilácie projektu a definuje aj konečný názve aplikácie. Príklad tejto sekcie:

```xml
<build>
    <defaultGoal>install</defaultGoal>
    <directory>${basedir}/target</directory>
    <finalName>${artifactId}-${version}</finalName>
    <filters>
      <filter>filters/filter1.properties</filter>
    </filters>
    //...
</build>
```

#### Používanie profilov

Ďalšou dôležitou vlastnosťou Maven je jej podpora *profilov.* *Profil* je v podstate sada konfiguračných hodnôt. Použitím *profilov* si môžeme prispôsobiť správanie Maven pre rôzne situácie, ako napr. keď je hotová aplikácia do produkcie  / testovacia fáza / vývoj:

```xml
<profiles>
    <profile>
        <id>production</id>
        <build>
            <plugins>
                <plugin>
                //...
                </plugin>
            </plugins>
        </build>
    </profile>
    <profile>
        <id>development</id>
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
        <build>
            <plugins>
                <plugin>
                //...
                </plugin>
            </plugins>
        </build>
     </profile>
 </profiles>
```

Ako je vidno v príklade vyššie, predvolený profil je nastavený na `development`. Ak chceme spustiť *produkčný* *profil* , môžeme použiť nasledujúci príkaz Maven:

```bash
mvn clean install -Pproduction
```

### Maven Build Lifecycles

Každé zostavovanie aplikácie pomocou Maven sa riadi stanoveným *životným cyklom*. Je možné vykonať niekoľko *cieľov* *životného build cyklu* vrátane cieľov na *zostavenie* kódu projektu, vytvorenie *balíka* alebo *inštaláciu* artefaktu do lokálneho úložiska Maven.

#### Fázy životného cyklu

Nasledujúci zoznam zobrazuje najdôležitejšie fázy *životného cyklu* Maven :

- *validate* - overí správnosť projektu
- *compile* - kompiluje poskytnutý zdrojový kód do binárnych artefaktov
- *test* - vykoná unit testy
- *package* - zabalí skompilovaný kód do archívneho súboru
- *integration-test* - vykoná ďalšie testy, ktoré vyžadujú sa vyžadujú pri package cieli
- *verify* - skontroluje, či je balík ok
- *install* - nainštaluje artefakt balíka do lokálneho úložiska Maven
- *deploy* - nasadí artefakt balíka na externý server alebo úložisko

#### Doplnky (plugins) a ciele (goals)

Vo všeobecnosti celý Maven systém je založený na pluginoch.  Každý plugin má zacieľ doplniť nejakú funkcionalitu, ktorá priamo nie je implementovaná v Maven.  Príklad: rozšírené spustenie testov, spustenie aplikácie, generovanie kódu atď. Bohatý zoznam doplnkov, ktoré Maven oficiálne podporuje, je k dispozícii [tu](http://maven.apache.org/plugins/index.html)

Každý maven plug-in predstavuje kolekciu viacero goalov (min. jeden musí mat definovaný). Tieto góly sa vykonávajú vo fázach životného cyklu, čo pomáha určiť poradie, v ktorom sa vykonajú.  

!!! Note
	Maven podporuje aj pluginy od tretích strán pomocou , ktorých môžeme doplniť chýbajúcu funkcionalitu.
    ```xml
    <!-- https://mvnrepository.com/artifact/org.eclipse.jetty/jetty-maven-plugin -->
    <plugin>
        <groupId>org.eclipse.jetty</groupId>
        <artifactId>jetty-maven-plugin</artifactId>
        <version>9.4.28.v20200408</version>
    </plugin>/
    ```

