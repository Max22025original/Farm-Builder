FarmBuilder - Paper/Spigot-Plugin
=================================
Befehl:  /farm        -> Menue (10 Kategorien -> Farm -> Groesse)
         /farm cancel -> Vorschau abbrechen
Nutzung: Nach der Groessenwahl erscheint eine Partikel-Vorschau. Bewege dich und schau auf die
         Stelle. Rechtsklick = platzieren (sofort, ohne Materialien), Linksklick = abbrechen.
Rechte:  nur OPs bzw. Permission farmbuilder.use

JAR bauen (Java 21 + Maven noetig):
    mvn clean package
    -> target/FarmBuilder.jar  in den Ordner /plugins deines Servers hochladen, Server neu starten.

Version anpassen: in pom.xml <paper.version> (und ggf. maven.compiler.release) auf deine
Server-Version setzen, und in src/main/resources/plugin.yml api-version.
