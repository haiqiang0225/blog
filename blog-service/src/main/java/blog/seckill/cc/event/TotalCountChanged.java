package blog.seckill.cc.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

import java.lang.reflect.Field;

/**
 * description: TotalCountChanged <br>
 * date: 2022/7/7 20:35 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
public class TotalCountChanged extends ApplicationEvent {
    private final TotalCountChangedType type;

    Field targetField;

    Object targetObject;

    public TotalCountChanged(Object source, TotalCountChangedType type, Field targetField, Object targetObject) {
        super(source);
        // 只能对Long/Integer代理起作用
        Class<?> targetFieldType = targetField.getType();
        if (!(Long.class.isAssignableFrom(targetFieldType) || Integer.class.isAssignableFrom(targetFieldType))) {
            throw new IllegalArgumentException("targetField's type must be Long/Integer or their sub class!");
        }
        this.type = type;
        this.targetField = targetField;
        this.targetObject = targetObject;

    }

    /**
     * description: apply <br>
     * version: 1.0 <br>
     * date: 2022/7/7 21:02 <br>
     * author: hq <br>
     * <p>
     * 对于发生的导致TotalCount变化的事件进行处理
     */
    public void apply() throws IllegalAccessException {
        targetField.setAccessible(true);
        Class<?> targetFieldType = targetField.getType();
        Number number = (Number) targetField.get(targetObject);
        if (Long.class.isAssignableFrom(targetFieldType)) {
            targetField.set(targetObject, number.longValue() + type.getValue());
        } else {
            targetField.set(targetObject, number.intValue() + type.getValue());
        }
    }


    public enum TotalCountChangedType {
        ADD(1),
        MINUS(-1);

        private final int value;

        private TotalCountChangedType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return this.name() + ": " + value;
        }
    }
}
