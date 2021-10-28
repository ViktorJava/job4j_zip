package ru.job4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

/**
 * <h2>Сканирование файловой системы.</h2>
 *
 * @author ViktorJava (gipsyscrew@gmail.com)
 * @version 0.1
 * @since 18.03.2021
 */
final class Search {
    /**
     * Утилитный класс поэтому переобъявленный дэфолтный конструктор и
     * приватный.
     */
    private Search() {
    }

    /**
     * Метод поиска файлов в заданной директории, согласно предикату.
     *
     * @param root      Корневая директория.
     * @param condition Предикат поиска файлов.
     * @return Список найденных файлов.
     * @throws IOException При возникновении IO исключений.
     */
    public static List<Path> search(final Path root,
                                    final Predicate<Path> condition)
            throws IOException {
        PrintFiles searcher = new PrintFiles(condition);
        Files.walkFileTree(root, searcher);
        return searcher.getPaths();
    }
}
