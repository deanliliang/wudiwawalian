package com.leyou.cart.controller;

import com.leyou.cart.bean.Cart;
import com.leyou.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.cart.controller
 * @ClassName: CartController
 * @Author: Dean
 * @Description:
 * @Date: 2019/7/4 20:18
 * @Version: 1.0
 */
@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 添加购物车
     *
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> addCart(@RequestBody Cart cart) {
        cartService.addCart(cart);
        return ResponseEntity.ok().build();
    }

    /**
     * 查询购物车
     *
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Cart>> queryCartList() {
        return ResponseEntity.ok(cartService.queryCartList());
    }

    /**
     * 修改购物车商品数量
     *
     * @return
     */
    @PutMapping()
    public ResponseEntity<Void> changeCartNum(@RequestParam("id")Long id,
                                              @RequestParam("num")Integer num) {
        cartService.changeCartNum(id,num);
        return ResponseEntity.ok().build();
    }

    /**
     * 删除购物车商品
     *
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCartGoods(@PathVariable("id")Long id) {
        cartService.deleteCartGoods(id);
        return ResponseEntity.ok().build();
    }

}
