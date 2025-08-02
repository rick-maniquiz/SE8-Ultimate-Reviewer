package org.example;

import org.example.exammodels.QuestionRepository;

import java.io.*;
import java.nio.file.*;

public class Main {
    public static void main(String[] args) {
        String texFileName = "example.tex";
        String outputDir = "output";
        String sample = "";

        for (int i = 0; i < 20; i++){
            sample += QuestionRepository.getQuestions().get(i).getQuestion() + "\n";
        }

        String texContent =
                "\\documentclass[12pt]{article}\n" +
                        "\\usepackage[a4paper, margin=1in]{geometry}\n" +
                        "\\usepackage{titlesec}\n" +
                        "\\usepackage{hyperref}\n" +
                        "\\usepackage{parskip}\n" +
                        "\\usepackage{fancyhdr}\n" +
                        "\\usepackage{booktabs}\n" +
                        "\\usepackage{tikz}\n" +
                        "\\pagestyle{fancy}\n" +
                        "\\fancyhf{}\n" +
                        "\\rhead{FECP5 45/45}\n" +
                        "\\lhead{1Z0-808 Mock Exam}\n" +
                        "\\rfoot{\\thepage}\n" +
                        "\n" +
                        "\\titleformat{\\section}{\\normalfont\\Large\\bfseries}{\\thesection}{1em}{}\n" +
                        "\\titleformat{\\subsection}{\\normalfont\\large\\bfseries}{\\thesubsection}{1em}{}\n" +
                        "\n" +
                        "\\title{\\textbf{1Z0-808 Mock Exam}}\n" +
                        "\\author{ExamId: *insert id* \\\\ Items: *insert item count* \\\\ Duration: 2 Hours}\n" +
                        "\\date{\\today}\n" +
                        "\n" +
                        "\\begin{document}\n" +
                        "\n" +
                        "\\maketitle\n" +
                        "\n" +
                        sample +
                        "\n" +
                        "\n" +
                        "\\end{document}";

        try {
            // Create output directory if it doesn't exist
            File dir = new File(outputDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Write LaTeX file
            File texFile = new File(dir, texFileName);
            FileWriter writer = new FileWriter(texFile);
            writer.write(texContent);
            writer.close();
            System.out.println("✅ LaTeX file written to: " + texFile.getAbsolutePath());

            // Run pdflatex
            ProcessBuilder pb = new ProcessBuilder(
                    "pdflatex",
                    "-interaction=nonstopmode",
                    texFileName
            );
            pb.directory(dir);
            pb.redirectErrorStream(true);

            Process process = pb.start();

            // Read compiler output
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("✅ PDF generated successfully.");
            } else {
                System.out.println("❌ PDF generation failed. Exit code: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
