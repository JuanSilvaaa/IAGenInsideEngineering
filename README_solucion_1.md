# Solucion Problema 1 - El Videoclub de Don Mario

## 1. Problema

Don Mario necesita un sistema de alquiler de peliculas que permita:

- Registrar peliculas fisicas y digitales con estado de disponibilidad.
- Permitir que un cliente seleccione varias peliculas para alquilar.
- Calcular total segun membresia:
  - Basica: sin descuento.
  - Premium: 20% de descuento.
- Generar recibo final con detalle de peliculas, precio unitario, subtotal, descuento y total.

## 2. Analisis del repositorio actual

### Arquitectura general

El repositorio es un proyecto Maven con Spring Boot y esta organizado en:

- `src/main/java/.../paper`: punto de entrada de aplicacion.
- `src/main/java/.../paper/util`: implementacion del problema 2 (pagos, inventario, facturacion, observadores).
- `src/test/java/...`: pruebas base del proyecto.
- `docs/uml` y `docs/imagenes`: diagramas de contexto/casos de uso/clases del problema 2.

No existia un modulo del problema 1 en codigo fuente; se implemento uno nuevo en paquete dedicado.

### Clases existentes y responsabilidades (problema 2)

- `Application`: bootstrap de Spring.
- `PaymentMethod` + `ValidatePayment`: contrato base para metodos de pago.
- `CreditCardFactory`, `PaypalFactory`, `CryptoFactory`: implementaciones concretas de pago.
- `PaymentStatus`: estados del ciclo de pago.
- `ECIPayment`: coordinador del flujo de pago.
- `PaymentObserver`, `PaymentEventObserver`: observador de eventos de exito/falla.
- `Inventory`, `Facturation`, `Notification`, `Product`: modulos que reaccionan al pago.

### Hallazgos detectados

Durante el analisis se detectaron problemas de compilacion/diseno en el modulo existente:

- Referencia a `PaymentFactory` sin definicion.
- `PaymentEventObserver` importaba `javax.management.Notification` en lugar de la clase local `Notification`.
- Constructor de `PaymentMethod` con parametro mal nombrado, provocando asignacion incorrecta del cliente.

Se realizaron ajustes minimos para evitar que estos defectos bloquearan compilacion y para no alterar el comportamiento esperado del problema 2.

## 3. Enfoque de solucion para Problema 1

Se implemento un modulo independiente y cohesivo en:

- `src/main/java/eci/edu/byteProgramming/ejercicio/paper/videoclub`

### Patrones y principios aplicados

- Strategy: calculo de descuento por tipo de membresia (`PricingStrategy`, `BasicPricingStrategy`, `PremiumPricingStrategy`).
- Factory simple: seleccion de estrategia (`PricingStrategyFactory`).
- Encapsulamiento: entidad `Movie` controla su propio estado de disponibilidad.
- SRP (SOLID):
  - `VideoClubRentalService` coordina el caso de uso.
  - `RentalReceiptFormatter` formatea salida.
  - `MoneyFormatter` centraliza formato monetario.
- OCP (SOLID): nuevas membresias/descuentos se agregan creando nueva estrategia.
- DIP (SOLID): el servicio depende de `PricingStrategy` (abstraccion) a traves de una fabrica.

## 4. Modelo de dominio implementado

- `Movie`: pelicula con id, titulo, formato, precio y disponibilidad.
- `MovieFormat`: enum (`FISICA`, `DIGITAL`).
- `MembershipType`: enum (`BASICA`, `PREMIUM`) con tasa de descuento.
- `RentalItem`: item inmutable del recibo.
- `RentalReceipt`: resultado inmutable del alquiler (subtotal, descuento, total).
- `VideoClubRentalService`: seleccion, validacion y calculo del alquiler.
- `RentalReceiptFormatter`: representacion textual tipo recibo.
- `VideoClubConsoleDemo`: evidencia de ejecucion por consola con caso ejemplo.

## 5. Validaciones y manejo de errores

La solucion valida:

- Membresia obligatoria.
- Seleccion de al menos una pelicula.
- IDs de peliculas existentes.
- Peliculas no disponibles.
- IDs duplicados en una misma solicitud.

Si alguna regla falla, se lanzan excepciones con mensajes explicitos (`IllegalArgumentException` o `IllegalStateException`).

## 6. Pruebas unitarias agregadas

Se agregaron pruebas en:

- `src/test/java/eci/edu/byteProgramming/ejercicio/paper/videoclub/VideoClubRentalServiceTest.java`
- `src/test/java/eci/edu/byteProgramming/ejercicio/paper/videoclub/RentalReceiptFormatterTest.java`

### Cobertura funcional de pruebas

- Calculo premium con caso de ejemplo del enunciado (subtotal, descuento, total).
- Calculo para membresia basica sin descuento.
- Error por pelicula no disponible.
- Error por seleccion vacia.
- Error por seleccion duplicada.
- Actualizacion de disponibilidad tras alquiler exitoso.
- Formato de recibo con valores esperados.

## 7. Instrucciones de ejecucion

### Ejecutar pruebas

```bash
./mvnw test
```

En Windows:

```powershell
.\mvnw.cmd test
```

### Ejecutar demo de consola (caso del enunciado)

Desde un IDE, ejecutar la clase:

- `eci.edu.byteProgramming.ejercicio.paper.videoclub.VideoClubConsoleDemo`

## 8. Ejemplo de salida esperada

```text
--- RECIBO DE ALQUILER ---
Cliente: Premium
Peliculas:
 - Interestellar (Fisica) - $8.000
 - Inception (Digital) - $5.000
Subtotal: $13.000
Descuento (20%): $2.600
Total a pagar: $10.400
--------------------------
!Disfrute su pelicula!
```

## 9. Nota de entorno

En este entorno puntual no fue posible ejecutar Maven porque `JAVA_HOME` no esta configurado. Aun asi, la implementacion y las pruebas quedaron integradas en la estructura del proyecto y listas para ejecutarse al configurar JDK.
