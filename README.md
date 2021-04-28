# tweet
prueba de servicio tweet

Trabaja con java 8 y desarrollado con spring-boot 2.4.5

La aplicación trabaja en el puerto 8080

Por defecto:
Tiene 5 usuarios. Los usuarios 2 y 3 tienen más de 1500 seguidores.
Tiene 5 tweets de los usuarios anteriores. 

Con la aplicacion arrancada puede consultar las tablas con http://localhost:8080/h2-console
Se crean dos tablas TWEET Y USER

En application.properties podemos encontrar las propiedades:
* application.properties.umbral.followers ==> es el numero minimo de seguidores para poder perse
* application.properties.hastags.top ==> el número de hashtag más usados que muestra en el endpoint de estadisticas de hashtags
* application.properties.idiomas.permitidos ==> idiomas permitidos para persistir tweets. Ahora es spanish, french, italian

Endpoint implementados

/tweet POST ==> crea un tweet
json entrada:
{
    "user": {
        "userId": 2
    },
    "idioma": "french",
    "texto": "texto de prueba validation",
    "localizacion": "Bilbao",
    "validation": false,
    "hashtags": "deportes"
}
devuelve el nuevo tweet creado
{
    "tweetId": 7,
    "user": {
        "userId": 2,
        "name": "Javier",
        "lastName": "Garcia Gómez",
        "followers": 2000
    },
    "idioma": "french",
    "texto": "texto de prueba validation",
    "localizacion": "Bilbao",
    "validation": false,
    "hashtags": "deportes"
}
si no persiste devuelve error

/tweet/user/{userid}?page=0&size=10 GET ==> consulta tweets por usuario. page y size son para paginación no son requeridos (default page=0 y size=10)
/tweet/{id}  PATCH ==> valida un tweet
/tweet/hashtag-statistics GET ==> consulta las estadisticas de hashtag
/tweet?page=0&size=10 GET ==> consulta todos los tweets. page y size son para paginación no son requeridos (default page=0 y size=10)
/tweet/{id} GET ==> consulta un tweet en concreto

En la carpeta resources/postman hay un export de la collection de postman usada para las pruebas.

Falta implementar:
* Documentar con swagger
* Test unitarios
* Test de integración



  
