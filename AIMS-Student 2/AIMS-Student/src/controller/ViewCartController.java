package controller;

import java.sql.SQLException;
import java.util.List;

import entity.media.Media;
import entity.cart.Cart;
import entity.cart.CartMedia;

/**
 * Manages actions related to the Cart view.
 * Controls the flow of events when users interact with the Cart.
 * 
 * Violation:
 * This class tightly couples with the Cart entity by directly invoking its methods,
 * leading to Control Coupling.
 * 
 * Solution:
 * Introduce the CartService acting as an intermediary layer that encapsulates interactions with the Cart entity.
 * This abstraction layer reduces the tight coupling between ViewCartController and Cart, improving maintainability.
 */
public class ViewCartController extends BaseController{
    
    /**
     * Checks the availability of products in the Cart.
     * @throws SQLException if there's an issue with database operations
     * Coupling: Control Coupling - directly depends on Cart's availability check method.
     */
    public void checkAvailabilityOfProduct() throws SQLException{
        Cart.getCart().checkAvailabilityOfProduct();
    }

    /**
     * Calculates the cart subtotal.
     * @return subtotal of the Cart
     * Coupling: Control Coupling - directly depends on Cart's subtotal calculation method.
     */
    public int getCartSubtotal(){
        int subtotal = Cart.getCart().calSubtotal();
        return subtotal;
    }

    /**
     * Comments on Cohesion:
     * These methods have low cohesion since they are performing distinct tasks
     * (checking availability and calculating subtotal) not directly related.
     */

    /**
     * Comments on Coupling:
     * The class exhibits Control Coupling by directly invoking methods from the Cart entity.
     * This tight dependency could lead to maintenance issues if Cart's internals change.
     */
}
