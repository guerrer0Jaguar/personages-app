
# personages-app

Aplicación para gestionar los personajes de una serie o
película (sólo backend)



## Installation

Requisitos:
- Java versión 21 (o más reciente)
- Mysql versión 9 

1. Crear la base con el script 'personages-db.sql' incluído en el folder docs, ejemplo:
```bash
  mysql -h 127.0.0.1 -P 3306 -u root -p < personages-db.sql 
```
El script crea la base con el nombre "personages_db" y el usuario "personages_db_user" para su conexión en modo local, sí la base se ejecuta en otro host, se tendría que agregar otro usuario con el host del cliente correspondiente.

2. Modificar el archivo de configuración "application.properties" en la ruta personages-backend/src/main/resources/ las propiedades son "spring.datasource.url", "spring.datasource.username" y "spring.datasource.password" , ejemplo:
```bash
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/personages_db
spring.datasource.username=personages_db_user
spring.datasource.password=somePassword
```


## Documentation

La documentación relacionada esta en el folder "docs".
## Features

- Alta y consulta de películas o series, los datos de nombre, imagen, y tipo (serie, película).
- Alta, edición, borrado, consulta (por ID, nombre o fecha de creación) de personajes de películas o series. El alta/edición maneja los datos de nombre, rol, descripción, imagen y serie o película asociada.
- Los endpoints son servicios REST que usan el formato de JSON para entrada/salida.
- Los datos son guardados en Base de Datos tipo MySQL.


## Usage/Examples
Una vez configurado el proyecto y navegando al folder de "personages-backend", se puede ejecutar el proyecto con el siguiente comando:
```shell
./mvnw spring-boot:run
```
El arranque es más o menos el siguiente (por default arranca en el puerto 8080):
```shell
[INFO] Attaching agents: []

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

 :: Spring Boot ::                (v3.5.7)

2025-11-09T17:35:03.991-06:00  INFO 20914 --- [personages-backend] [  restartedMain] o.g.p.b.PersonagesBackendApplication     : Starting PersonagesBackendApplication using Java 25 with PID 
2025-11-09T18:06:05.103-06:00  INFO 21765 --- [personages-backend] [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 8080 (http)
2025-11-09T18:06:05.118-06:00  INFO 21765 --- [personages-backend] [  restartedMain] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
...multiple log lines........
PersonagesBackendApplication     : Started PersonagesBackendApplication in 4.313 seconds (process running for 4.619)
```

Una vez iniciado el proyecto, y en otra ventana de shell, hay que posicionarse en el folder de "docs/examples". 

ALTA DE PELÍCULAS O SERIES.

El primer endpoint que se tiene que ejecutar es el de "/media" que sirve para dar de alta las películas y series que serán asociadas a cada uno de los personajes.

El ejemplo del payload es el siguiente ("content", esta recortado):
```javaScript
{
  "name": "Black widow",
  "mediaType": {
    "id": 1
  },
  "image": {
    "name" : "file1.jpg",	
    "content" : "/9j/4..."	
  }
}
```
El dato de "mediaType" se indica en forma de objeto con el id 1 ó 2 (donde 1 indica que es película y 2 serie). En el objeto "image", el dato "content" tiene que ser una imagen exportada en formato Base64 (veáse el archivo media1-POST.json). Y para dar de alta la película (o serie) ejecutamos el siguiente comando:

 ```shell
curl -X POST http://localhost:8080/media -H "Content-Type:application/json" -d @media1-POST.json
```
La salida del servicio es la siguiente:
```javaScript
{
  "id": 1,
  "name": "Black widow",
  "image": {
    "id": 1,
    "name": "file1.jpg",
    "url": "/home/julio/04_dev/00_projects/pers/personages-app/personages-backend/tmp/file1.jpg",
    "content": ""
  },
  "mediaType": {
    "id": 1,
    "name": null
  }
}
```
El dato del "id" se usará para el alta de personajes, con éste dato asociamos la película al personaje.

ALTA DE PERSONAJES.

Para dar de alta personajes se usa el endpoint de "/personage". El ejemplo del payload es el siguiente ( el dato de "content" esta recortado):
```javaScript
{
  "name": "Black widow",
  "rol": "secundary actor",
  "description": "heroe acted by ",
  "media": {
    "id": 1
  },
  "image": {
    "name": "blackWidow.jpeg",
    "content": "/9j/4AAQSkZJRgABAQAAAQABAAD..."
  }
}
```
El dato de "media" se indica como objeto, con el id de una película o serie existente ( en nuestro caso es el 1). En el objeto "image", el dato "content" tiene que ser una imagen exportada en formato Base64 (veáse el archivo personage1-POST.json). Los datos de "rol" y "description" no son los correctos, pero habrá oportunidad de corregirlos más adelante . Y para dar de alta al personaje ejecutamos el siguiente comando:
```shell
curl -X POST http://localhost:8080/personage -H "Content-Type:application/json" -d @personage1-POST.json
```
La salida del servicio es la siguiente:
```javaScript
{
  "id": 1,
  "name": "Black widow",
  "rol": "secundary actor",
  "description": "heroe acted by ",
  "creationDate": "2025-11-09T18:27:17.905691819",
  "media": {
    "id": 1,
    "name": null,
    "image": null,
    "mediaType": null
  },
  "image": {
    "id": 2,
    "name": "blackWidow.jpeg",
    "url": "/home/julio/04_dev/00_projects/pers/personages-app/personages-backend/tmp/blackWidow.jpeg",
    "content": ""
  }
}
```
Nos devuelve el "id" del personaje, con el que podemos hacer la búsqueda específica por éste dato como se verá más adelante. Añadimos otros 2 personajes de la misma película con los siguientes comandos:
```shell
curl -X POST http://localhost:8080/personage -H "Content-Type:application/json" -d @personage2-POST.json
curl -X POST http://localhost:8080/personage -H "Content-Type:application/json" -d @personage3-POST.json
```
CONSULTA DE PERSONAJE POR ID

Para consultar un personaje en específico, se puede hacer por el id, pasando éste dato como parte de la llamada al endpoint de "/personage/{id}":
```shell
curl http://localhost:8080/personage/1
```
Salida:
```javaScript
{
  "id": 1,
  "name": "Black widow",
  "rol": "secundary actor",
  "description": "heroe acted by ",
  "creationDate": "2025-11-09T18:27:17.905692",
  "media": {
    "id": 1,
    "name": "Black widow",
    "image": {
      "id": 1,
      "name": "file1.jpg",
      "url": "/home/julio/04_dev/00_projects/pers/personages-app/personages-backend/tmp/file1.jpg",
      "content": null
    },
    "mediaType": {
      "id": 1,
      "name": "película"
    }
  },
  "image": {
    "id": 2,
    "name": "blackWidow.jpeg",
    "url": "/home/julio/04_dev/00_projects/pers/personages-app/personages-backend/tmp/blackWidow.jpeg",
    "content": null
  }
}
```
EDICION DE PERSONAJE.

Para editar los datos de un personaje, se invoca la ruta "/personaje/{id}" con un POST y pasando las correcciones dentro del cuerpo de la petición, ejemplo del payload, donde se corrigen el "rol" y "description" del personaje ( el dato de "content" esta recortado):
```javaScript
{
  "name": "Black widow",
  "rol": "protagonist",
  "description": "heroe acted by Scarlet Johansson",
  "media": {
    "id": 1
  },
  "image": {
    "name": "blackWidow.jpeg",
    "content": "/9j/4AAQSkZJRgABAQAAAQABAAD..."
  }
}
```
Comando a ejecutar:
```shell
curl -X PUT http://localhost:8080/personage/1 -H "Content-Type:application/json" -d @personage1-PUT.json
```
Salida
```javaScript
{
  "id": 1,
  "name": "Black widow",
  "rol": "protagonist",
  "description": "heroe acted by Scarlet Johansson",
  "creationDate": "2025-11-09T18:51:02.881030041",
  "media": {
    "id": 1,
    "name": "Black widow",
    "image": {
      "id": 1,
      "name": "file1.jpg",
      "url": "/home/julio/04_dev/00_projects/pers/personages-app/personages-backend/tmp/file1.jpg",
      "content": null
    },
    "mediaType": {
      "id": 1,
      "name": "película"
    }
  },
  "image": {
    "id": 4,
    "name": "blackWidow.jpeg",
    "url": "/home/julio/04_dev/00_projects/pers/personages-app/personages-backend/tmp/blackWidow.jpeg",
    "content": null
  }
}
```

CONSULTA DE PERSONAJES POR NOMBRE

Se invoca el endpoint de "/personage" con el parámetro de "name=algunPersonaje". El ejemplo es el siguiente, notése que el espacio entre palabras tiene que codificarse:
```shell
curl http://localhost:8080/personage?name=Black%20widow
```
Salida del servicio:
```javaScript
[
  {
    "id": 1,
    "name": "Black widow",
    "rol": "protagonist",
    "description": "heroe acted by Scarlet Johansson",
    "creationDate": "2025-11-09T18:51:02.88103",
    "media": {
      "id": 1,
      "name": "Black widow",
      "image": {
        "id": 1,
        "name": "file1.jpg",
        "url": "/home/julio/04_dev/00_projects/pers/personages-app/personages-backend/tmp/file1.jpg",
        "content": null
      },
      "mediaType": {
        "id": 1,
        "name": "película"
      }
    },
    "image": {
      "id": 4,
      "name": "blackWidow.jpeg",
      "url": "/home/julio/04_dev/00_projects/pers/personages-app/personages-backend/tmp/blackWidow.jpeg",
      "content": null
    }
  }
]
```
También se podría buscar con "name=black", o "name=widow" y devolvería el mismo resultado.

CONSULTA DE PERSONAJES POR FECHA DE CREACIÓN.

Se invoca el mismo endpoint de la consulta por nombre, sólo que los parámetros son "initDate" (fecha de inicio) y endDate (fecha fin) con el formato de yyyy-MM-dd. Ejemplo:
```shell
curl "http://localhost:8080/personage?initDate=2025-11-08&endDate=2025-11-09"
```
Salida del servicio:
```javaScript
[
  {
    "id": 2,
    "name": "Yelena Belova",
    "rol": "secundary actor",
    "description": "Black Widow's sister",
    "creationDate": "2025-11-09T18:36:50.508399",
    "media": {
      "id": 1,
      "name": "Black widow",
      "image": {
        "id": 1,
        "name": "file1.jpg",
        "url": "/home/julio/04_dev/00_projects/pers/personages-app/personages-backend/tmp/file1.jpg",
        "content": null
      },
      "mediaType": {
        "id": 1,
        "name": "película"
      }
    },
    "image": {
      "id": 3,
      "name": "florencePugh.jpeg",
      "url": "/home/julio/04_dev/00_projects/pers/personages-app/personages-backend/tmp/florencePugh.jpeg",
      "content": null
    }
  },
  {
    "id": 3,
    "name": "Melina",
    "rol": "secundary actor",
    "description": "antagonist",
    "creationDate": "2025-11-09T18:37:17.303456",
    "media": {
      "id": 1,
      "name": "Black widow",
      "image": {
        "id": 1,
        "name": "file1.jpg",
        "url": "/home/julio/04_dev/00_projects/pers/personages-app/personages-backend/tmp/file1.jpg",
        "content": null
      },
      "mediaType": {
        "id": 1,
        "name": "película"
      }
    },
    "image": null
  },
  {
    "id": 1,
    "name": "Black widow",
    "rol": "protagonist",
    "description": "heroe acted by Scarlet Johansson",
    "creationDate": "2025-11-09T18:51:02.88103",
    "media": {
      "id": 1,
      "name": "Black widow",
      "image": {
        "id": 1,
        "name": "file1.jpg",
        "url": "/home/julio/04_dev/00_projects/pers/personages-app/personages-backend/tmp/file1.jpg",
        "content": null
      },
      "mediaType": {
        "id": 1,
        "name": "película"
      }
    },
    "image": {
      "id": 4,
      "name": "blackWidow.jpeg",
      "url": "/home/julio/04_dev/00_projects/pers/personages-app/personages-backend/tmp/blackWidow.jpeg",
      "content": null
    }
  }
]
```

BORRADO DE PERSONAJES

Se invoca el servicio "/personage/{id}" con el método DELETE y pasando el id del personaje dentro de la ruta. Ejemplo:
```shell
curl -X DELETE http://localhost:8080/personage/3
```
Esta operación no devuelve resultado alguno. Si intentamos consultar el personaje:
```shell
curl http://localhost:8080/personage/3
```
Obtendremos el siguiente mensaje:
```javaScript
{
  "status": 404,
  "message": "The personage with id:3 doesnt' exits",
  "timestamp": "2025-11-09T19:12:30.605795281"
}
```

CONSULTA DE PELÍCULAS O SERIES.

Para obtener un listado de las películas o series guardadas, se invoca el 
siguiente servicio (sin parámetros):
```shell
curl http://localhost:8080/media
```
La salida es la siguiente:
```javaScript
[
  {
    "id": 1,
    "name": "Black widow",
    "image": {
      "id": 1,
      "name": "file1.jpg",
      "url": "/home/julio/04_dev/00_projects/pers/personages-app/personages-backend/tmp/file1.jpg",
      "content": null
    },
    "mediaType": {
      "id": 1,
      "name": "película"
    }
  }
]
```
## Acknowledgements

 - [Awesome Readme Templates](https://awesomeopensource.com/project/elangosundar/awesome-README-templates)
 - [Awesome README](https://github.com/matiassingers/awesome-readme)
 - [How to write a Good readme](https://bulldogjob.com/news/449-how-to-write-a-good-readme-for-your-github-project)

