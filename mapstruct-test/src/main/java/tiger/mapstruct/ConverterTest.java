package tiger.mapstruct;

/**
 * @Author Zenghu
 * @Date 2022年12月08日 23:21
 * @Description
 * @Version: 1.0
 **/
public class ConverterTest {
    public static void main(String[] args) {
        Order order = new Order();
        order.setId(1L);
        Product product = new Product();
        product.setId(1L);
        product.setName("apple");
        product.setPrice(12.3);
        order.setProduct(product);
        User user = new User();
        user.setId(1L);
        user.setName("test");
        order.setUser(user);

        OrderDto orderDto = OrderMapper.ORDER_MAPPER.orderToOrderDto(order);
        System.out.println(orderDto);


    }
}
