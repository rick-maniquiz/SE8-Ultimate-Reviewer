package org.example.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LaTeXProcessor {
    public static String fixVerbatimString(String string){
        Pattern pattern = Pattern.compile(
                "\\\\begin\\{verbatim\\}(.*?)\\\\end\\{verbatim\\}",
                Pattern.DOTALL
        );

        Matcher matcher = pattern.matcher(string);
        StringBuffer result = new StringBuffer();

        while (matcher.find()){
            String verbatimContent = matcher.group(1);
            String fixedContent = verbatimContent.replace("\\n", "\n");
//            System.out.println("\nFIXED" + fixedContent);
//            System.out.println("\nNOT FIXED" + verbatimContent);


            String replacement = "\\begin{verbatim}" + fixedContent + "\\end{verbatim}";
            matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(result);
        return result.toString();
//        return string;
    }

    public static String fixUnderscoreFormat(String string){
        StringBuilder content = new StringBuilder();
        if (!string.trim().startsWith("\\begin")
                && string.indexOf('_') != -1
                && !string.contains("\\verb"))
            content.append("\\begin{verbatim}")
                    .append(string)
                    .append("\\end{verbatim}")
                    .append("\n");
        else content.append(string)
                .append("\n");

        return content.toString();
    }
}
