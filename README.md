# Far-Away-back

| **URI** | **Method** | **Auth?** | **Action** |
| --- | --- | --- | --- |
| suits | GET | Non | READ ALL : retourne tous les scaphandres de la db. |
| suits/{id} | GET | non | READ ONE : retourne un scaphandre |
| suits?sort=value&amp;type=value&amp;min=value&amp;max=value | GET | Non | READ ALL : retourne tous les scaphandres triés par prix croissant ou décroissant ou par type ou par prix minimum, maximum ou encore plusieurs en même temps. |
| suits | POST | Oui : admin | CREATE ONE : ajoute un scaphandre dans la db avec les données passées dans la requête. |
| suits/{id} | DELETE | Oui : admin | DELETE ONE : supprime un scaphandre donné dans la db. |
| suits/{id} | UPDATE | Oui : admin | UPDATE ONE : modifie un scaphandre donné dans la db. |
| me | GET | oui : utilisateur | READ ONE : retourne les informations de la session utilisateur |
| authentification/login | POST | non | LOGIN : connecte un utilisateur. |
| authentification/register | POST | non | CREATE ONE : enregistre un nouvel utilisateur |
| users/{id} | DELETE | oui : utilisateur ou admin | DELETE ONE : supprime un utilisateur |
| users/{id} | UPDATE | oui : utilisateur | UPDATE ONE : modifie un utilisateur |
| users/{mail} | GET | Oui : admin | READ ONE : retourne un utilisateur en fonction de son mail. |
| baskets/{id}
 | GET | Oui : utilisateur | READ SEVERAL : retourne le contenu du panier d&#39;un utilisateur |
| baskets/{idArticle}/{idUser} | DELETE | Oui : utilisateur | DELETE ONE : permet de supprimer un article du panier choisi |
| baskets/{idArticle}/{idUser} | POST | Oui : utilisateur | CREATE ONE : permet de rajouter un article dans le panier choisi |
| baskets/{idArticle}/{idUser} | UPDATE | Oui : utilisateur | UPDATE ONE : permet de modifier la quantité d&#39;un article donné dans le panier choisi |
| comments/{id\_user}/{id\_article} | POST | Oui : utilisateur et admin | CREATE ONE : permet de rajouter un commentaire à un article |
| comments:onlyuser/{id\_user}/{id\_article} | GET | Oui : admin et utilisateur | READ ONE : retourne tous les commentaires d&#39;un utilisateur sur un article |
| comments/exceptUser/{id\_user}/{id\_article} | GET | Oui : admin et utilisateur | READ ONE : retourne tous les commentaires d&#39;un article sauf pour l&#39;utilisateur donné |
| comments/{id\_article} | GET | Oui : admin et utilisateur | READ ONE : affiche le nombre de commentaire d&#39;un article et sa moyenne d&#39;évaluation |
| comments/{idUser}/{idArticle} | DELETE | Oui : admin et utilisateur | DELETE ONE : mets à jour le champ validité (supprime ou valide) |
| comments/putBack/{idUser}/{idArticle} |
 |
 |
 |
| comments{idUser}/{idArticle} | UPDATE | Oui : admin et utilisateur | UPDATE ONE : permet de modifier son commentaire sur un article |