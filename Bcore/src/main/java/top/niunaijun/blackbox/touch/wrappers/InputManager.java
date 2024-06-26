package top.niunaijun.blackbox.touch.wrappers;

import android.view.InputEvent;

import top.niunaijun.blackbox.touch.Ln;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class InputManager {

    public static final int INJECT_MODE_ASYNC = 0;
    public static final int INJECT_MODE_WAIT_FOR_RESULT = 1;
    public static final int INJECT_MODE_WAIT_FOR_FINISH = 2;

    private final Object manager;
    private Method injectInputEventMethod = null;

    public InputManager(Object manager) {
        this.manager = manager;
    }

    private Method getInjectInputEventMethod() throws NoSuchMethodException {
        if (injectInputEventMethod == null) {
            injectInputEventMethod = manager.getClass().getMethod("injectInputEvent", InputEvent.class, int.class);
        }
        return injectInputEventMethod;
    }

    public boolean injectInputEvent(InputEvent inputEvent, int mode) {
        try {
            Method method = getInjectInputEventMethod();
            return (boolean) method.invoke(manager, inputEvent, mode);
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            Ln.e("Could not invoke method", e);
            return false;
        }
    }
}
