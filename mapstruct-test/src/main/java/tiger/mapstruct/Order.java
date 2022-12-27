package tiger.mapstruct;

import lombok.Data;

/**
 * @Author Zenghu
 * @Date 2022年12月08日 23:13
 * @Description
 * @Version: 1.0
 **/
@Data
public class Order {
    private Long id;
    private User user;
    private Product product;
    private Double amount;
}
