package ru.job4j;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <h2>Архивировать проект</h2>
 * Класс, представляет собой, утилиту для архивации папки.
 * Входные параметры: -d=<PATH> -e=<EXCLUDE_FILE> -o=<ARCHIVE_NAME>
 *
 * @author ViktorJava (gipsyscrew@gmail.com)
 * @version 0.1
 * @since 27.03.2021
 */
public class Zip {
    /**
     * Обязательное число аргументов.
     */
    public static final int ARG_COUNT = 3;

    /**
     * Метод архивирует файлы, путь и имя которых получаем через аргумент
     * в виде списка.
     *
     * @param sources Список архивируемых файлов.
     * @param target  Имя и расширение архивного файла.
     */
    public void packFiles(final List<File> sources, final File target) {
        try (ZipOutputStream zip = new ZipOutputStream(
                new BufferedOutputStream(new FileOutputStream(target)))) {
            for (File file: sources) {
                zip.putNextEntry(new ZipEntry(file.getPath()));
                try (BufferedInputStream out = new BufferedInputStream(
                        new FileInputStream(file))) {
                    zip.write(out.readAllBytes());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод формирует список всех файлов в заданном каталоге
     * включая подкаталоги учитывая исключенные файлы.
     *
     * @param filePath Корневой каталог.
     * @param exclude  Расширение исключенных файлов.
     * @return Список файлов.
     */
    private static List<File> searchFile(final Path filePath,
                                         final String exclude) {
        List<Path> listPaths = new ArrayList<>();
        try {
            listPaths = Search.search(filePath,
                    p -> !p.toFile()
                           .getName()
                           .endsWith(exclude));
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<File> listFiles = new ArrayList<>();
        for (Path path: listPaths) {
            listFiles.add(path.toFile());
        }
        return listFiles;
    }

    /**
     * Точка входа.
     *
     * @param args аргументы.
     */
    public static void main(final String[] args) {
        if (args.length != ARG_COUNT) {
            throw new IllegalArgumentException(
                    "Example: java -jar zip.jar -d=<PATH> "
                            + "-e=<EXCLUDE_FILE> -o=<ARCHIVE_NAME>"
                            + "\n java -jar pack.jar "
                            + "-d=c:\\projects -e=txt -o=zip");
        }
        ArgsName argsName = ArgsName.of(args);
        Path root = Paths.get(argsName.get("d"));
        new Zip().packFiles(searchFile(root, argsName.get("e")),
                new File(argsName.get("o")));
    }
}
