package de.dseelp.kommon.command;

import de.dseelp.kommon.command.arguments.*;

/**
 * @author DSeeLP
 */
public class JavaBuilderConstants {
    public static <S> JavaCommandBuilder<S> literal(String name, String[] aliases, Class<S> type) {
        return new JavaCommandBuilder<>(name, aliases);
    }

    public static <S> JavaCommandBuilder<S> literal(String name, Class<S> type) {
        return literal(name, new String[]{}, type);
    }

    public static <S> JavaCommandBuilder<S> argument(Argument<S, ?> argument, Class<S> type) {
        return new JavaCommandBuilder<>(argument);
    }


    public static BooleanArgument bool(String name) {
        return new BooleanArgument(name);
    }

    public static BooleanArgument bool(String name, String trueString, String falseString) {
        return new BooleanArgument(name, trueString, falseString);
    }


    public static IntArgument integerArgument(String name) {
        return new IntArgument(name);
    }


    public static UUIDArgument uniqueIdArgument(String name) {
        return new UUIDArgument(name);
    }


    public static StringArgument stringArgument(String name) {
        return new StringArgument(name);
    }
}
