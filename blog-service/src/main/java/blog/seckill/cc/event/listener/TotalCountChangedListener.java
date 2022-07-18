package blog.seckill.cc.event.listener;

import blog.seckill.cc.event.TotalCountChanged;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * description: TotalCountChangedListener <br>
 * date: 2022/7/7 21:36 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Component
public class TotalCountChangedListener implements ApplicationListener<TotalCountChanged> {

    @Override
    public void onApplicationEvent(TotalCountChanged event) {
        try {
            event.apply();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
