package me.pesekjak.crypt4offline;

import com.google.common.collect.Iterators;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassExtractor {

    public static byte[] getBytes(byte[] fileData) {
        ClassWriter writer = new ClassWriter(new ClassReader(fileData), ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        return writer.toByteArray();
    }

    public static List<byte[]> getClasses(File file, String basePackage) throws IOException {
        basePackage = basePackage.replace('.', '/') + "/";
        List<byte[]> classes = new ArrayList<>();
        List<JarEntry> entries = new ArrayList<>();
        try(JarFile jar = new JarFile(file)) {
            Iterators.addAll(entries, jar.entries().asIterator());
            for (JarEntry entry : entries) {
                String name = entry.getName();
                if (!name.startsWith(basePackage)) continue;
                if (!name.endsWith(".class")) continue;
                if (name.endsWith("package-info.class")) continue;
                name = name.replaceFirst(basePackage, "");
                name = name.substring(0, name.length() - ".class".length());
                if (name.indexOf('.') != -1) continue;
                classes.add(jar.getInputStream(entry).readAllBytes());
            }
        }

        return classes;
    }


}
