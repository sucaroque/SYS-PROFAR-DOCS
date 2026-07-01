# ADR 001: Uso de Arquitectura Hegaxonal en Microservicios para el Sistema

## Estado
Aceptado

## Fecha
01-07-2026

## Contexto
El sistema actual requiere una alta escalabilidad, autonomía de despliegue y un mantenimiento ágil de sus reglas de negocio. Para cumplir con estos atributos de calidad, el sistema se ha dividido en un ecosistema de **Microservicios** independientes.

Sin embargo, existía el riesgo de que el código interno de cada microservicio se volviera altamente dependiente de la infraestructura tecnológica elegida (como el framework Spring Boot, bases de datos relacionales/NoSQL o protocolos de comunicación como REST/gRPC). Si la tecnología de un microservicio cambia o necesita ser probada de forma aislada, el acoplamiento directo haría que la evolución del sistema fuera costosa y compleja.

Se evaluó la estructura interna de los microservicios bajo dos enfoques:
1. **Diseño tradicional en capas dentro del microservicio:** Desarrollo rápido, pero con riesgo de acoplar la lógica del negocio a las librerías del framework.
2. **Diseño de Arquitectura Hexagonal (Puertos y Adaptadores) por microservicio:** Desacoplamiento total del núcleo del negocio, permitiendo que la lógica interna cambie de forma independiente a la infraestructura de comunicación de la red o persistencia.

## Decisión
Hemos decidido adoptar la **Arquitectura Hexagonal** de manera estricta para el diseño interno de **cada microservicio**.

La estructura interna de cada componente se dividirá en:
* **Dominio / Core:** Lógica pura del negocio (entidades y reglas comerciales) aislada de cualquier framework o cliente externo.
* **Puertos (Ports):** Interfaces que definen los contratos de entrada (casos de uso) y de salida (interfaces de persistencia o clientes de otros microservicios).
* **Adaptadores (Adapters):** Implementaciones técnicas. Los adaptadores de entrada (*Driving*) expondrán las API REST o controladores de eventos, mientras que los adaptadores de salida (*Driven*) gestionarán la base de datos propia del microservicio o las llamadas HTTP/mensajería hacia otros microservicios.



## Consecuencias

### Positivas (Beneficios)
* **Aislamiento Tecnológico Máximo:** Cada microservicio puede cambiar su base de datos o su protocolo de comunicación (por ejemplo, migrar de REST a mensajería orientada a eventos) sin alterar una sola línea de su lógica de negocio central.
* **Facilidad de Pruebas Automatizadas (Mocks):** Permite realizar pruebas unitarias y de integración rápidas del núcleo del negocio simulando el comportamiento de las bases de datos o de los microservicios externos mediante los puertos.
* **Trazabilidad de Requerimientos:** Facilita el mapeo directo de los diagramas de secuencia y casos de uso con los puertos de entrada del microservicio.

### Negativas (Compensaciones / Trade-offs)
* **Duplicidad de Modelos (Overhead de Mapeo):** Al estar en un entorno de microservicios con arquitectura hexagonal, se requiere transformar constantemente los objetos (de JSON de entrada a Objetos de Dominio, y de Objetos de Dominio a Entidades de Persistencia).
* **Curva de Aprendizaje Elevada:** El equipo de desarrollo debe comprender tanto la lógica de comunicación distribuida entre microservicios como las restricciones de aislamiento de paquetes dentro de cada proyecto.