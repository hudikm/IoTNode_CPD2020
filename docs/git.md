## Základy verzovacieho systému

Git je nástroj na spravu verzií. Čo je správa verzií: Správa verzií je systém, ktorý zaznamenáva zmeny v súbore alebo vo viacerých súboroch v čase tak, aby sme sa mohli neskôr k určitej verzii vrátiť. Súčasťou správy verzii je aj história jednotlivých krokov pričom každý krok je sprevádzaný krátkou informáciou o vykonanej zmene.

 S principiálneho hľadiska existujú tri základné prístupy správy verzií:

- **Lokálne systémy správy verzií**
    Jednoduché riešenie by bolo vytvoriť adresárovú štruktúru kde každý priečinok by predstavoval novú verziu súborov. 
	![](images/vcs_loc.png#center#75size)
	
- **Centralizované systémy správy verzií**
	
	Ďalším veľkým problémom, s ktorým sa užívatelia stretávajú, je potreba spolupráce s vývojármi pracujúcimi na iných počítačoch. Pre vyriešenie tohto problému boli vyvinuté tzv. Centralizované systémy pre správu verzií (CVCS z anglického Centralized Version Control System). Tieto systémy, ako je CVS, Subversion a PERFORCE, používajú jeden server, ktorý uchováva všetky verzované súbory, a viac klientov, ktorí vedia súbory z centrálneho miesta získať. Tento koncept správy verzií bol po dlhé roky považovaný za štandard.
	![](images/vcs_ext.png#center#75size)
	
- **Distribuované systémy správy verzií**
	
	V tomto mieste prichádzajú k slovu distribuované systémy riadenia verzií (DVCS z anglického Distributed Version Control System). V distribuovaných systémoch pre správu verzií (ako sú Git, Mercurial, Bazaar alebo Darcs) klient nesťahuje iba najnovšiu verziu súborov (tzv. Snímku, anglicky snapshot), ale zrkadlí celý repozitár. Ak v takejto situácii dôjde ku kolapsu servera a pokiaľ ho tieto systémy využívali, môžeme obsah serveru obnoviť skopírovaním repozitára od ľubovoľného užívateľa. Každý klon je v skutočnosti úplnú zálohou všetkých dát.
	![](images/vcs_git.png#center#75size)

## GIT

Git o spravovaných dátach uvažuje ako o kolekcii obrazov súborového systému. Zakaždým keď Git systém zapíše stav projektu (spraví tzv. commit), vytvorí obraz súborového systému v danom momente a uloží odkaz na tento obraz do svojej databázy.  Pokiaľ sa niektorý súbor nezmenil od poslednej verzie tak z dôvodu efektívnosti daný súbor neuloží ale iba odkáže na prechádzajúci identický súbor. 

> ![Git ukládá data jako snímky projektu v daném čase.](images/snapshots.png)
> Ukladanie dát v podobe snímkov projektu v danom čase. [zdroj](https://git-scm.com/book/cs/v2/%C3%9Avod-Z%C3%A1klady-syst%C3%A9mu-Git)



**Operácie sú v prevažnej miere lokálne**

Systém Git vyžaduje aby sme mali všetky súbory lokálne k dispozícii.  To znamená, že ak pracujeme na projekte tak prvý krok bude klonovanie Git repozitáru zo servera (v prípade, že pracujeme už na existujúcom projekte).  Väčšina operácii, ktoré sa vykonávajú budú vykonávané na lokálnej databáze čo má viacero výhod: operácie sú rýchle, nezávislosť na internetovom pripojení pri práci a v neposlednom rade ak sa niečo pokazí, tak bude ovplyvnený iba lokálny repozitár, chyba sa neprenesie k iným vývojárom, ktorý pracujú na rovnakom projekte.   

**Kontrolná suma**

Pred uložením aktuálneho obrazu Git vypočíta kontrolnú sumu, ktorá sa následne používa na jeho adresovanie. Z toho vyplýva, že pri zmene ľubovolného súboru sa kontrolná suma zmení a Git to zistí. Týmto spôsobom Git udržuje integritu repozitára.  

Algoritmus na výpočet sumy sa používa SHA-1. 
```
Príklad kontrolnej sumy: 24b9da6552252987aa493b52f8696cd6d3b00373
```

**Tri stavy**

Git používa pre spravované súbory tri základné stavy: **zapísané (*committed*)**, **zmenené (*modified*)** a **pripravené na zapísanie (*staged*)**. 

- *Zapísané* znamená, že sú dáta bezpečne uložené v lokálnej databáze. 
- *Zmenené* znamená, že v súbore boli vykonané zmeny, avšak súbor ešte nebol zapísaný do databázy. 
- *Pripravené* na zapísanie znamená, že zmenený súbor v jeho aktuálnej verzii bol určený na to, aby bol zapísaný do ďalšieho obrazu (commit snapshot).

Z toho vyplýva, že projekt je v systéme Git rozdelený do troch hlavných častí: adresár systému .git (Git directory), pracovný adresár (working directory) a oblasť pripravených zmien (staging area).

**Pracovný adresár je adresár v ktorom sa vykonáva samotný vývoj a je jediný adresár kde sa môže manuálne zasahovať.**

**Základný pracovný postup vyzerá v GITU nasledovne:**

- Zmeníte súbory vo svojom pracovnom adresári.

- Súbory pripravíte na zapísanie tak, že ich označíte ako pripravené na zápis (*stage*). 
- Vykonáte zápis (commit), ktorý vezme súbory nachádzajúce sa v oblasti pripravených zmien, a tento snímok trvalo uloží do adresára gitu.

### Inštalacia

#### Windows

Stiahnite si inštalátor zo stránky [git-scm.com](https://git-scm.com/).

Pri inštalácii sa uistite, že ste si zvolili:

- Checkout Windows-style, commit Unix-style line endings

Po inštalácii si otvorte command line(PowerShell, Bash, …) a nastavte si editor, ktorý bude slúžiť na editáciu commit správ, nasledujúcimi príkazmi:

```
git config --global core.editor notepad
# alebo 
git config --global core.editor {editor podľa vášho výberu}
```

Nastavenie zarovnania commit správy:

```
git config --global format.commitMessageColumns 80
```

#### Linux

**Minimum (iba CLI)**

balíček `git`

```
#Fedora, RHEL:
sudo yum install git
#Ubuntu, Debian:
sudo apt-get install git
```

**GUI**

balíček `gitk` `git-gui`

```
#Fedora, RHEL:
sudo yum install gitk git-gui
#Ubuntu, Debian:
sudo apt-get install gitk git-gui
```

### Základné príkazy
- **git init** vytvorí nové lokálne úložisko GIT. Nasledujúci príkaz Git vytvorí archív v aktuálnom adresári:

```
git init
```

- Prípadne môžete vytvoriť úložisko v novom adresári zadaním názvu projektu:

```
git init [názov projektu]
```

- **git clone **sa používa na kopírovanie úložiska. Ak úložisko je na vzdialenom serveri, použite:

```
git clone username@host:/path/to/repository
```

- Naopak, spustite nasledujúci príkaz na skopírovanie lokálneho archívu:

```
git clone /cesta/k/repozitáru
```

- **git add** sa používa na pridávanie súborov do pracovnej oblasti (stage). Napríklad nasledujúci príkaz Git nasledujúci bude indexovať súbor temp.txt:

```
git add <temp.txt>
```

- **git commit** vytvorí obraz zmien a uloží ich do adresára git.

```
git commit –m “Správa ktorá popisuje uložené zmeny”
```

!!! warning "Upozorňujeme, že žiadne prijaté zmeny sa nedostanú na vzdialené úložisko. Odoslanie zmien sa vykoná pomocou príkazu `git push`"

- **git config**  je možné použiť na nastavenie konfiguračných hodnôt špecifických pre používateľa, ako sú e-mail, používateľské meno, formát súboru atď. Na ilustráciu príkaz na nastavenie e-mailu vyzerá takto:

```
git config --global user.email youremail@example.com
```

- **git status**  zobrazuje zoznam zmenených súborov spolu so súbormi, ktoré sa ešte len majú označiť na uloženie alebo commitovať.

```
git status
```

- **git push** sa používa na odoslanie lokálnej git databázy do hlavnej vetvy vzdialeného úložiska. Tu je základná štruktúra príkazu:

```
git push origin <master>
```

- **git checkout** vytvára nové vetvy a pomáha medzi nimi prechádzať. Napríklad nasledujúci základný príkaz vytvorí novú vetvu a automaticky na ňu prepne:

```
command git checkout -b <názvo vetvy/ angl. branch>
```

- Ak chcete prepnúť z jednej vetvy na druhú:

```
git checkout <branch-name>
```

- **git remote** vám umožní prezerať všetky vzdialené archívy. Nasledujúci príkaz zobrazí zoznam všetkých pripojení spolu s ich adresami URL:

```
git remote –v
```

- Ak chcete pripojiť lokálny archív k vzdialenému serveru, použite nasledujúci príkaz:

```
git remote add origin <host-or-remoteURL>
```

- Medzitým nasledujúci príkaz vymaže pripojenie k zadanému vzdialenému úložisku:

```
git remote rm <name-of-the-repository>
```

- **git branch **vypíše, vytvorí alebo odstráni vetvy. Napríklad, ak chcete zobraziť všetky vetvy prítomné v úložisku, použijete príkaz:

```
git branch
```

- Ak chcete pobočku odstrániť, použite:

```
git branch –d <branch-name>
```

- **git pull** zlúči všetky zmeny, ktoré sa nachádzajú vo vzdialenom úložisku, do lokálneho pracovného adresára.

```
git pull
```

- **git merge** sa používa na zlúčenie vetvy do aktívnej vetvy.

```
git merge <branch-name>
```

- **git tag** označuje konkrétne jeden bod v histórií.  Vývojári ju zvyčajne používajú na označenie bodov kedy vydali stabilnú verziu ako napr. v1.0 a v2.0.

```
git tag <insert-commitID-here>
```

- **git log** sa používa na zobrazenie histórie archívu uvedením podrobností o určitých potvrdeniach. Spustením príkazu získate výstup, ktorý vyzerá takto:

```
commit 15f4b6c44b2c834gcdasdac9e4be13246e21sadw
Author: Janko Hraško  <janko.hrasko@gmail.com>
Date:   Mon Oct 1 12:56:29 2020 -0600
```

- **git reset** vynuluje index a pracovný adresár do stavu posledného commitu v git.

```
git reset --hard HEAD
```

- **git rm** sa dá použiť na odstránenie súborov z indexu a pracovného adresára.

```
git rm filename.txt
```

- **git stash** dočasne uloží zmeny, ktoré nie sú pripravené na trvalé uloženie. Týmto spôsobom sa môžete neskôr vrátiť k tomuto projektu.

```
git stash
```

- **git show**  je príkaz používaný na zobrazenie informácií o akomkoľvek objekte git.

```
git show
```

- **git fetch** umožňuje používateľom načítať všetky objekty zo vzdialeného úložiska, ktoré sa momentálne nenachádzajú v miestnom pracovnom adresári.

```
git fetch origin
```

- **git rebase** sa používa na aplikovanie určitých zmien z jednej vetvy do druhej. Napríklad:

```
git rebase master
```

###Odkazy

Podrobnejší návod v angličtine [git (archlinux fórum)](https://wiki.archlinux.org/index.php/git#Usage).

Kniha CZ: [https://git-scm.com/book/cs/v2/%C3%9Avod-Spr%C3%A1va-verz%C3%AD](https://git-scm.com/book/cs/v2/Úvod-Správa-verzí)

