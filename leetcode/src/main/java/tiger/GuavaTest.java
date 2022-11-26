package tiger;



import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import com.google.common.util.concurrent.RateLimiter;
import org.apache.logging.log4j.util.Strings;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


/**
 * @ClassName GuavaTest
 * @Description TODO
 * @Author zeng.h
 * @Date 2020/5/4 10:55
 * @Version 1.0
 **/
public class GuavaTest {
    public static void main(String[] args) {
        Optional<String> optional = Optional.of("");
        Optional<Integer> optional1 = Optional.fromNullable(null);
        System.out.println(optional1.isPresent());
        // System.out.println(optional1.get());
        System.out.println(optional1.or(1));

        // 前置条件
        boolean flag = false;
        //Preconditions.checkArgument(flag);

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(-2);
        list.add(45);
        String join = Strings.join(list.iterator(), ',');
        System.out.println(join);

        System.out.println(Objects.hashCode("123123", "hello"));

        String str = MoreObjects.toStringHelper("object").add("x", 1).toString();
        System.out.println(str);

        Ordering<Integer> valueOrdering = new Ordering<Integer>() {
            @Override
            public int compare(@Nullable Integer left, @Nullable Integer right) {
                return left - right;
            }
        };

        List<Integer> list1 = valueOrdering.greatestOf(list.iterator(), 2);
        System.out.println(Strings.join(list1.iterator(), ','));

        ImmutableList<Integer> list2 = ImmutableList.of(1, 2, 3, 4, 5);
        ImmutableList<Integer> build = ImmutableList.<Integer>builder().add(1).build();

        String str1 = "avs,,,dfd,ddf,ggg";
        List<String> list3 = Splitter.on(',').omitEmptyStrings().splitToList(str1);
        System.out.println(list3);


        RateLimiter rateLimiter = RateLimiter.create(100);
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                rateLimiter.acquire();
                System.out.println(Instant.now());
            }).start();
        }


    }

}
