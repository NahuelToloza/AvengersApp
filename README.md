# AvengersApp

AvengersApp es una app Android realizada con el fin de ejercicio técnico

## Requerimientos

La aplicación a que desarrollar debe constar de las siguientes secciones:<br />
**Autenticación:**<br />
Se debe integrar Firebase para realizar el registro/ingreso a través de Facebook o Email (sólo esas 2 vías). La sesión, una vez que el usuario entra, debe ser guardada para que una próxima vez no sea necesario autenticarse. Un usuario con credenciales debe ser re-dirigido a Pantalla Principal. Aclaración: se puede usar la interfaz que provee Firebase para la autenticación, no es necesario proveer una custom UI.<br />
**Pantalla Principal:**<br />
Esta pantalla consta de un TabBar en la parte inferior, de la que se puede seleccionar entre:<br />
**Personajes:** <br />
Listado de personajes de Marvel, con un paginado (estilo scroll infinito) de 15 elementos (es decir, se cargan 15, y al llegar al fondo del scroll, se cargan 15 más, y así). Al hacer click se abre la pantalla Detalle del Personaje.<br />
**Eventos:** <br />
Listado de eventos futuros, con un límite de 25 elementos y ordenados por fecha de comienzo de manera ascendente. El diseño contiene una flecha arriba/abajo para expandir/colapsar la celda. Cuando se expande se muestra más información de los eventos.<br />
**Detalle del Personaje:** <br />
Info del personaje (foto, nombre, descripción) + listado de cómics en los que figura.

## Componentes usados/bibliotecas usadas

* Arquitectura MVVM + Repository
* Corutinas
* Navigation
* Koin
* ViewBinding
* AndroidX
* Glide
* Unit test con JUnit y Mockito2

## Como usar el proyecto

Se deben agregar los siguientes valores en el gradle.properties:<br />
PrivateKeyAvengersApi=""<br />
PublicKeyAvengersApi=""<br />
Con sus respectivos valores

## Android Studio

El desarrollo se hizo sobre la versión 4.1.2 de Android Studio

## Tiempo de desarrollo

33:30 horas

### Información personal ###

* LinkedIn
https://www.linkedin.com/in/nahuel-toloza-072b54162
