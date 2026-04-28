package eci.edu.byteProgramming.ejercicio.paper.videoclub;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Result object with the financial summary of a rental operation.
 */
public class RentalReceipt {
    private final MembershipType membershipType;
    private final List<RentalItem> items;
    private final BigDecimal subtotal;
    private final BigDecimal discount;
    private final BigDecimal total;

    public RentalReceipt(
        MembershipType membershipType,
        List<RentalItem> items,
        BigDecimal subtotal,
        BigDecimal discount,
        BigDecimal total
    ) {
        this.membershipType = membershipType;
        this.items = List.copyOf(items);
        this.subtotal = subtotal;
        this.discount = discount;
        this.total = total;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }

    public List<RentalItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
