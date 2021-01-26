# Psy-Calender

## Configuration  base de données

Pour cause d'un problème lors de la création de compte sur l'une de nos VM, nous avons utilisés le compte System pour réaliser la base de données.
### Import
Un script ```export.sql```contient toutes les tables,view,fonctions et procedures utilisés.
### Changer les idenfitians de connections
Dans le fichier Main.java, 3 lignes sont à changer pour ajuster la base données.
Veuillez changer celles-ci pour adapter à votre base de données.
```bash
DatabaseConnection.ip = "jdbc:oracle:thin:@192.168.56.102:1521:orclcdb";
DatabaseConnection.user = "system";
DatabaseConnection.passwd = "oracle";
```

## Commande pour lancer l'application

### Dependance 1
le fichier ```dependencies.jar``` contient toutes les classes créees pour le projet.
### Dependance 2
le fichier ```objdc8.jar``` contient le driver java.

### Commande
```bash
java -classpath dependencies.jar:ojdbc8.jar:. ./Main.java
```


