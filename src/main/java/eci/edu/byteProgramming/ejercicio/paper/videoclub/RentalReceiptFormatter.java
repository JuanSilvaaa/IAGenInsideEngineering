package eci.edu.byteProgramming.ejercicio.paper.videoclub;

/**
 * Converts a rental result into a plain-text receipt for console output.
 */
public class RentalReceiptFormatter {

    public String format(RentalReceipt receipt) {
        StringBuilder sb = new StringBuilder();
        sb.append("--- RECIBO DE ALQUILER ---\n");
        sb.append("Cliente: ").append(formatMembership(receipt.getMembershipType())).append("\n");
        sb.append("Peliculas:\n");

        for (RentalItem item : receipt.getItems()) {
            sb.append(" - ")
                .append(item.getTitle())
                .append(" (")
                .append(item.getFormat() == MovieFormat.FISICA ? "Fisica" : "Digital")
                .append(") - ")
                .append(MoneyFormatter.format(item.getUnitPrice()))
                .append("\n");
        }

        sb.append("Subtotal: ").append(MoneyFormatter.format(receipt.getSubtotal())).append("\n");
        if (receipt.getDiscount().signum() > 0) {
            sb.append("Descuento (20%): ").append(MoneyFormatter.format(receipt.getDiscount())).append("\n");
        } else {
            sb.append("Descuento (0%): ").append(MoneyFormatter.format(receipt.getDiscount())).append("\n");
        }
        sb.append("Total a pagar: ").append(MoneyFormatter.format(receipt.getTotal())).append("\n");
        sb.append("--------------------------\n");
        sb.append("!Disfrute su pelicula!");
        return sb.toString();
    }

    private String formatMembership(MembershipType membershipType) {
        return membershipType == MembershipType.PREMIUM ? "Premium" : "Basica";
    }
}
