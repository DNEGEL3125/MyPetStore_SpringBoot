package cn.csu.mypetstore_springboot.Repositories;

import cn.csu.mypetstore_springboot.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> getCartItemsByUsername(String username);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO shopping_cart (userid, item_id, quantity, in_stock, is_purchased) " +
            "VALUES (:#{#cartItem.userId}, :#{#cartItem.item.itemId}, :#{#cartItem.quantity}, :#{#cartItem.inStock}, 0) " +
            "ON DUPLICATE KEY UPDATE quantity = quantity + 1", nativeQuery = true)
    void addItemToShoppingCart(@Param("cartItem") CartItem cartItem);

    @Transactional
    @Modifying
    @Query(value = "UPDATE shopping_cart SET in_stock = :inStock WHERE id = :id", nativeQuery = true)
    int updateInStockById(@Param("id") Long id, @Param("inStock") boolean inStock);

    @Transactional
    @Modifying
    @Query(value = "UPDATE shopping_cart SET quantity = :quantity WHERE id = :id", nativeQuery = true)
    void updateQuantityById(@Param("id") Long id, @Param("quantity") int quantity);


    @Transactional
    @Modifying
    @Query(value = """
            UPDATE cart_item SET
             quantity = :#{#cartItem.quantity},
              in_stock = :#{#cartItem.inStock} 
              WHERE username = :username"""
            , nativeQuery = true)
    int updateCartItemByUsername(String username, CartItem cartItem);
}
