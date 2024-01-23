package me.pesekjak.crypt4offline;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.Exchanger;

public final class ClassDefiner {

    private ClassDefiner() {
        throw new UnsupportedOperationException();
    }

    public static Class<?> defineClassPrivatelyIn(Class<?> clazz, byte[] bytes) throws Exception {
        Exchanger<Class<?>> exchanger = new Exchanger<>();
        Thread.ofVirtual().start(() -> {
            Thread.currentThread().setContextClassLoader(clazz.getClassLoader());
            Class<?> compiled;
            try {
                compiled = MethodHandles
                        .privateLookupIn(clazz, MethodHandles.lookup())
                        .defineClass(bytes);
            } catch (Exception exception) {
                compiled = null;
            }
            try {
                exchanger.exchange(compiled);
            } catch (InterruptedException exception) {
                throw new RuntimeException(exception);
            }
        });
        Class<?> compiled = exchanger.exchange(null);
        if (compiled == null) throw new NullPointerException();
        return compiled;
    }

}
