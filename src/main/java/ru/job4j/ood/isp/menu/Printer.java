package ru.job4j.ood.isp.menu;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

public class Printer implements MenuPrinter {

    private final static String INDENT_CHARACTER = "-";
    private final static int INDENT_SIZE = 2;
    private final PrintStream output;

    public Printer(PrintStream output) {
        this.output = output;
    }

    @Override
    public void print(Menu menu) {
        var printed = new HashSet<Menu.MenuItemInfo>();
        menu.forEach(item -> printMenuItem(menu, item, 0, printed));
    }

    private void printMenuItem(Menu menu, Menu.MenuItemInfo item, int indentCount, Set<Menu.MenuItemInfo> printed) {
        if (!printed.contains(item)) {
            output.print(INDENT_CHARACTER.repeat(indentCount));
            output.printf("%s %s%n", item.number(), item.name());
            printed.add(item);
            item.children().forEach(child -> {
                var optionalItem = menu.select(child);
                if (optionalItem.isPresent()) {
                    var childItemInfo = optionalItem.get();
                    printMenuItem(menu, childItemInfo, indentCount + INDENT_SIZE, printed);
                }
            });
        }
    }
}
