# Test_automatisé projet 9 OC

Testez vos développements Java

## Travail demandé :

### Les tests
Votre travail consiste à réaliser 2 types de tests :

des tests unitaires : leurs objectifs sont de valider les règles de gestion unitaires de chaque "composant" de l'application
des tests d'intégration : leurs objectifs sont de valider la bonne interaction entre les différents composants de l'application
À vous de définir vos tests et la stratégie que vous allez mettre en place pour les tests d'intégration.

Vous implémenterez et automatiserez ces tests à l'aide de JUnit, Mockito, Maven et Travis CI / GitLab CI / Jenkins.

Les tests seront lancés via le build Maven.

Les tests d'intégration font l'objet de profils Maven spécifiques (cf. le fichier pom.xml du projet parent fourni).

Cet environnement sera construit (à partir des éléments disponibles dans le dépôt Git du projet) et monté à la volée par votre système d'intégration continue.


### Complément d'implémentation
Vous compléterez également l'implémentation de l'application en vous appuyant sur les commentaires TODO insérés dans le code source. Vous devez tous les réaliser, il ne devra rester aucun TODO dans votre livrable.


## Livrables attendus
Le code doit être géré avec Git.

Vous livrerez, sur GitHub ou GitLab , dans un seul dépôt privé et dédié :

Le code source de l’application avec vos ajouts, modifications, corrections
Les éléments d'automatisation des tests unitaires
Les éléments d'automatisation des tests d'intégration
La configuration du serveur d’intégration


# MyERP

## Organisation du répertoire

*   `doc` : documentation
*   `docker` : répertoire relatifs aux conteneurs _docker_ utiles pour le projet
    *   `dev` : environnement de développement
*   `src` : code source de l'application


## Environnement de développement

Les composants nécessaires lors du développement sont disponibles via des conteneurs _docker_.
L'environnement de développement est assemblé grâce à _docker-compose_
(cf docker/dev/docker-compose.yml).

Il comporte :

*   une base de données _PostgreSQL_ contenant un jeu de données de démo (`postgresql://127.0.0.1:9032/db_myerp`)



### Lancement

    cd docker/dev
    docker-compose up


### Arrêt

    cd docker/dev
    docker-compose stop


### Remise à zero

    cd docker/dev
    docker-compose stop
    docker-compose rm -v
    docker-compose up
