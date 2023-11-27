package tiger.mapstruct;

import lombok.Data;

/**
 * @Author Zenghu
 * @Date 2022年12月08日 23:14
 * @Description
 * @Version: 1.0
 **/
@Data
public class OrderDto {
    private Long id;
    private String userId;
    private String userName;
    private Long productId;
    private String productName;
    private Double price;
    private Double amount;
}
