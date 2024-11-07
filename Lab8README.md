Nekha Bose Lab 8 README file

1. Include screenshots and narrative to prove:
   Your authentication is working.
   You know who the authenticated user is
   You know what role(s) the authenticated user has
   **Documentation Provided**

2.
mysql> select * from sec_user;
+----------+----------+
| USERNAME | PASSWORD |
+----------+----------+
| admin    | admin    |
| owner1   | owner1   |
| owner2   | owner2   |
| vet1     | vet1     |
| vet2     | vet2     |
| vet3     | vet3     |
+----------+----------+

mysql> select * from sec_user_groups;
+----------+-------------+
| USERNAME | GROUPNAME   |
+----------+-------------+
| admin    | ADMIN_GROUP |
| vet1     | ADMIN_GROUP |
| owner1   | OWNER_GROUP |
| owner2   | OWNER_GROUP |
| vet2     | OWNER_GROUP |
| vet1     | VET_GROUP   |
| vet2     | VET_GROUP   |
| vet3     | VET_GROUP   |
+----------+-------------+

3.
**Discuss your experiences, including any difficulties you had or changes you made**

Working with Jakarta EE security and JSF was both challenging and rewarding. Setting up secure login, managing different
 roles (like admin, vet, and owner), and ensuring users could only access their allowed sections gave me a hands-on feel 
for building secure applications. Integrating the backend security setup with JSF for login and logout also underscored how
 important it is to have everything smoothly connected between the frontend and backend.

Challenges
Role-Based Access Control: Setting up access for different roles (admin, vet, owner) was tricky at first,especially in 
matching roles in web.xml and payara-web.xml with database groups. Testing and troubleshooting permissions took some time to 
ensure each role had the right access.