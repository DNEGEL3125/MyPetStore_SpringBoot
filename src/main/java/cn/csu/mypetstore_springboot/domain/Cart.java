package cn.csu.mypetstore_springboot.domain;

import cn.csu.mypetstore_springboot.Repositories.CartItemRepository;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Cart implements Serializable {
    @Serial
    private static final long serialVersionUID = 8329559983943337176L;
    private final CartItemRepository cartItemDAO;
    private List<CartItem> itemList = new ArrayList<>();
    private String userId;

    public Cart(CartItemRepository cartItemDAO) {
        this.cartItemDAO = cartItemDAO;
    }

    public void updateItemList() {
        if (userId == null) {
            return;
        }
        itemList = cartItemDAO.getCartItemsByUsername(userId);
    }

    public Iterator<CartItem> getCartItems() {
        updateItemList();
        return itemList.iterator();
    }

    public List<CartItem> getCartItemList() {
        updateItemList();
        return itemList;
    }

    public int getNumberOfItems() {
        updateItemList();
        return itemList.size();
    }

    public Iterator<CartItem> getAllCartItems() {
        this.updateItemList();
        return itemList.iterator();
    }


    public void addItem(Product product, boolean isInStock) {
        CartItem cartItem = new CartItem();
        cartItem.setItem(product);
        cartItem.setQuantity(0);
        cartItem.setInStock(isInStock);
        cartItem.setUsername(this.userId);
        cartItem.incrementQuantity();
        itemList.add(cartItem);

        cartItemDAO.addItemToShoppingCart(cartItem);
    }

    public Product removeItemById(Long productId) {
        Product product = new Product();
        product.setProductId(productId);
        CartItem cartItem = new CartItem();
        cartItem.setUsername(userId);
        cartItem.setItem(product);
        cartItem.setQuantity(0);
        int result = cartItemDAO.updateInStockById(cartItem.getId(), false);
        if (result > 0) {
            return product;
        } else {
            return null;
        }
    }

    public void setQuantityByProductId(Long productId, int quantity) {
        Product product = new Product();
        product.setProductId(productId);
        CartItem cartItem = new CartItem();
        cartItem.setUsername(userId);
        cartItem.setItem(product);
        cartItem.setQuantity(quantity);
        cartItemDAO.updateQuantityById(cartItem.getId(), quantity);
    }

    public BigDecimal getSubTotal() {
        BigDecimal subTotal = new BigDecimal("0");
        Iterator<CartItem> items = getAllCartItems();
        while (items.hasNext()) {
            CartItem cartItem = items.next();
            Product product = cartItem.getProduct();
            BigDecimal listPrice = product.getListPrice();
            BigDecimal quantity = new BigDecimal(String.valueOf(cartItem.getQuantity()));
            subTotal = subTotal.add(listPrice.multiply(quantity));
        }
        return subTotal;
    }

    public CartItem getCartItemByProductId(Long productId) {
        for (CartItem cartItem : this.getCartItemList()) {
            if (Objects.equals(cartItem.getProduct().getProductId(), productId)) {
                return cartItem;
            }
        }
        return null;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
