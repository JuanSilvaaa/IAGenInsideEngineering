# Solucion Problema 2 - Tienda Virtual

# Promnt 1
copilot: ahora haz el punto 2 que tambien esta en el readme y que quede en otro readme el tema de la solcion pero que sea punto 2
## Prompt utilizado

Actua como un ingeniero de software senior con experiencia en analisis de repositorios, diseno orientado a objetos y generacion de soluciones completas. Tu tarea es revisar TODO el repositorio actual, comprender su estructura, proposito y contexto, y luego enfocarte especificamente en el problema propuesto numero 2 titulado "Tienda Virtual".

Primero, realiza un analisis exhaustivo del repositorio:

- Identifica la arquitectura general del proyecto (capas, paquetes, modulos).
- Describe las clases existentes, sus responsabilidades y relaciones.
- Detecta si hay codigo incompleto, errores de diseno o partes no implementadas.
- Revisa si existen pruebas, documentacion o diagramas que ayuden a entender el problema.

Luego, enfocate en el problema "Tienda Virtual" del README:

- Interpreta claramente los requerimientos funcionales y no funcionales.
- Si los requerimientos no estan completamente explicitos, infierelos con base en el contexto del repositorio y buenas practicas.
- Define el modelo de dominio necesario (clases, atributos, metodos, relaciones).
- Asegurate de aplicar principios de diseno como SOLID, encapsulamiento y cohesion.

A continuacion, genera una solucion completa y funcional, que incluya:

- Todas las clases necesarias correctamente estructuradas.
- Implementacion detallada de metodos con logica clara.
- Uso de nombres descriptivos y buenas practicas de programacion.
- Manejo de errores y validaciones cuando sea necesario.
- Comentarios y documentacion en el codigo (JavaDoc o equivalente).

Tambien debes:

- Crear pruebas unitarias para validar la solucion (por ejemplo, usando JUnit).
- Asegurarte de que los casos principales del problema esten cubiertos.
- Explicar brevemente que valida cada prueba.

Finalmente, genera un nuevo archivo en el repositorio llamado README_solucion_2.md con:

- Explicacion clara del problema.
- Enfoque de la solucion.
- Descripcion del diseno (clases y relaciones).
- Instrucciones para ejecutar el codigo.
- Ejemplos de uso.
- Explicacion de las pruebas.

## 1. Problema

La tienda virtual necesita:

- Soportar pagos por Tarjeta de Credito, PayPal y Criptomonedas.
- Crear cada metodo de pago junto con su validador.
- Ocultar detalles internos a la logica principal.
- Notificar automaticamente a Inventario, Facturacion y Notificaciones cuando un pago es exitoso.
- Permitir extension de metodos de pago y de modulos observadores sin tocar el core.

## 2. Analisis de la implementacion existente

En el paquete util del repositorio ya existian clases para el problema 2, pero con varios sintomas de implementacion parcial y acoplamientos:

- Faltaba contrato de fabrica para el tipo PaymentFactory.
- Habia una referencia a Notification del paquete javax.management en lugar de la clase de dominio local.
- Las clases de pago estaban nombradas como Factory aunque realmente actuaban como metodos de pago concretos.

Se conservaron y corrigieron esos puntos minimos para no romper el trabajo previo, y se implemento una solucion limpia y completa del punto 2 en paquete separado.

## 3. Patrones identificados

Se utilizaron los dos patrones solicitados por el README:

- Abstract Factory:
  - Crea familias relacionadas metodo + validador para cada forma de pago.
  - Contrato: StorePaymentFactory.
- Observer:
  - Notifica automaticamente a multiples modulos cuando hay exito o fallo.
  - Contrato: PaymentEventSubscriber.

Estos patrones son adecuados para el escenario. No requieren cambio de patron, solo una implementacion consistente.

## 4. Solucion implementada

Se implemento en:

- src/main/java/eci/edu/byteProgramming/ejercicio/paper/tiendavirtual

### Core

- StorePaymentFactory: fabrica abstracta (metodo + validador)
- StorePaymentMethod: ejecucion del pago
- StorePaymentValidator: validacion del pago
- VirtualStorePaymentService: nucleo desacoplado que procesa y publica eventos
- ProcessedPayment y PaymentRequest: objetos de datos del caso de uso

### Familias de pago (Abstract Factory)

- Tarjeta:
  - CreditCardPaymentFactory
  - CreditCardPaymentValidator
  - CreditCardPaymentMethod
- PayPal:
  - PaypalPaymentFactory
  - PaypalPaymentValidator
  - PaypalPaymentMethod
- Cripto:
  - CryptoPaymentFactory
  - CryptoPaymentValidator
  - CryptoPaymentMethod

### Observadores (Observer)

- InventorySubscriber: descuenta stock cuando el pago es exitoso
- BillingSubscriber: genera y guarda factura cuando el pago es exitoso
- NotificationSubscriber: registra mensajes de exito o fallo

### Demo de consola

- VirtualStoreConsoleDemo

## 5. Principios SOLID aplicados

- SRP: validacion, ejecucion, orquestacion y reacciones separadas por clase.
- OCP: nuevos metodos de pago o nuevos observadores se agregan sin modificar el core.
- DIP: el servicio depende de interfaces (StorePaymentFactory, StorePaymentValidator, StorePaymentMethod, PaymentEventSubscriber).
- ISP: contratos pequenos y especificos.
- Encapsulamiento: estados internos en modulos y DTOs inmutables para eventos.

## 6. Validacion del diagrama de contexto

El diagrama de contexto es suficiente para identificar actores y flujo de notificacion general.

Ajustes recomendados para mayor precision:

- Separar explicitamente evento de exito y evento de fallo.
- Mostrar que el modulo de facturacion se dispara solo en exito.
- Aclarar que inventario no debe actualizarse en pagos fallidos.

No fue necesario cambiar archivos UML para cumplir el objetivo tecnico.

## 7. Pruebas unitarias

Archivo de pruebas:

- src/test/java/eci/edu/byteProgramming/ejercicio/paper/tiendavirtual/VirtualStorePaymentServiceTest.java

Casos cubiertos:

1. shouldProcessCreditCardAndNotifyAllSubscribers
- Valida flujo exitoso completo:
  - estado SUCCESS
  - descuento de stock
  - factura generada
  - notificacion enviada

2. shouldRejectInvalidPaypalAndKeepModulesConsistent
- Valida fallo por datos invalidos en PayPal:
  - estado FAILED
  - stock sin cambios
  - sin factura
  - notificacion de fallo

3. shouldRejectCryptoWithInsufficientBalance
- Valida rechazo por saldo insuficiente en cripto.

4. shouldAllowAddingNewObserverWithoutChangingCore
- Valida extensibilidad del patron Observer sin tocar VirtualStorePaymentService.

## 8. Instrucciones de ejecucion

### Ejecutar pruebas

Windows:

```powershell
.\\mvnw.cmd test
```

Linux/Mac:

```bash
./mvnw test
```

### Ejecutar demo del problema 2

Ejecutar la clase:

- eci.edu.byteProgramming.ejercicio.paper.tiendavirtual.VirtualStoreConsoleDemo

## 9. Nota de entorno

En este entorno no se pudo ejecutar Maven porque JAVA_HOME no esta configurado. Sin embargo, el analizador de errores del editor no reporta errores de compilacion en el codigo entregado.
