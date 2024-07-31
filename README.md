# test_springboot_webflux


Construyo el proyecto
-------------------------------
mvnw clean package -DskipTest 

Construyo la imagen y levanto los contenedores con la opcion 1 ó 2
-------------------------------------------------------------------------------------------
1) If you add the --build option, it is forced to build the images even when not needed:
docker-compose up --build

2) The following only builds the images, does not start the containers:
docker-compose build

The following builds the images if the images do not exist and starts the containers:
docker-compose up


Liberar puertos ocupados en windows Ej: el 8080
--------------------------------------------------------------
1) netstat -aon | findstr :8080
2) taskkill /PID 13212 /F

Correcto: se terminó el proceso con PID 13212.


Reviso la documentacion del api con swagger en localhost
--------------------------------------------------------------
http://localhost:8080/swagger-ui.html