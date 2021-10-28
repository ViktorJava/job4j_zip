package ru.job4j;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * Это специальный класс, в котором инкапсулируется вся логика обхода
 * дерева файлов. Поместил сюда всю логику поиска файлов по определённому
 * предикату.
 *
 * @author ViktorJava (gipsyscrew@gmail.com)
 * @version 0.1
 * @since 18.03.2021
 */
public class PrintFiles implements FileVisitor<Path> {
    /**
     * Список директорий.
     */
    private final List<Path> paths;
    /**
     * Хранилище лямбды.
     */
    private final Predicate<Path> condition;

    /**
     * Конструктор.
     *
     * @param cond Предикат разыскиваемых файлов.
     */
    public PrintFiles(final Predicate<Path> cond) {
        this.paths = new ArrayList<>();
        this.condition = cond;
    }

    /**
     * Гетер.
     *
     * @return Список файлов.
     */
    public List<Path> getPaths() {
        return paths;
    }

    /**
     * Здесь мы и описываем что нужно делать с каждым файлом
     * в каждой директории.
     *
     * @param file  Ссылка на файл.
     * @param attrs Основные атрибуты просматриваемого файла.
     * @return Продолжаем обход дерева CONTINUE.
     */
    @Override
    public FileVisitResult visitFile(final Path file,
                                     final BasicFileAttributes attrs) {
        if (condition.test(file)) {
            paths.add(file);
        }
        return CONTINUE;
    }

    /**
     * Вызывается после посещения всех записей в каталоге.
     *
     * @param dir   Ссылка на каталог.
     * @param attrs Основные атрибуты файла.
     * @return Результат посещения.
     */
    @Override
    public FileVisitResult preVisitDirectory(final Path dir,
                                             final BasicFileAttributes attrs) {
        return CONTINUE;
    }

    /**
     * Вызывается, когда к файлу невозможно получить доступ.
     *
     * @param file Ссылка на файл.
     * @param exc  Исключение ввода-вывода, препятствовавшее посещению файла.
     * @return Результат посещения.
     */
    @Override
    public FileVisitResult visitFileFailed(final Path file,
                                           final IOException exc) {
        return CONTINUE;
    }

    /**
     * Вызывается для каталога после посещения записей в каталоге
     * и всех их потомках. Этот метод также вызывается,
     * когда итерация каталога завершается преждевременно.
     *
     * @param dir Ссылка на каталог.
     * @param exc null если итерация каталога завершилась без ошибок;
     *            в противном случае исключение ввода-вывода,
     *            которое привело к преждевременному завершению
     *            итерации каталога.
     * @return Результат посещения.
     */
    @Override
    public FileVisitResult postVisitDirectory(final Path dir,
                                              final IOException exc) {
        return CONTINUE;
    }
}
