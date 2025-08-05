package org.example.exammodels;

import org.example.utils.LaTeXProcessor;
import org.example.utils.Printable;

import java.io.*;
import java.util.Optional;

public class SolutionSheet implements Printable {
    private final int solutionSheetId;
    private final Exam exam;

    public SolutionSheet(Exam exam) {
        this.exam = exam;
        this.solutionSheetId = exam.getExamId();
    }

    @Override
    public void generatePDF(){
        String texFileName = "mock-exam-solutions-" + solutionSheetId + ".tex";
        String outputDir = "output/solutions";
        StringBuilder content = new StringBuilder();

        for (QuestionEntry questionEntry : exam.getExamQuestions()){
            int questionId = questionEntry.getQuestionId();
            content.append("\\item (")
                    .append("questionId: ")
                    .append(questionId)
                    .append(", topic: ")
                    .append(questionEntry.getTopic())
                    .append(") \\\\ \n")
                    .append(LaTeXProcessor.fixVerbatimString(questionEntry.getQuestion()))
                    .append("\n");
            if (questionEntry.getType().equalsIgnoreCase("single")){
                content.append("\\\\ \\noindent Only one correct choice. \n");
            } else {
                content.append("\\\\ \\noindent Multiple correct choices. \n");
            }
            content.append("\\begin{itemize}\n");

            Optional<SolutionEntry> solutionEntryResult = SolutionRepository.getSolutions().stream()
                                                                                            .filter(solutionEntry -> solutionEntry.getSolutionId() == questionId)
                                                                                            .findFirst();
            if (solutionEntryResult.isPresent()){
                SolutionEntry solutionEntry = solutionEntryResult.get();
                for (String choice : solutionEntry.getExplanation().keySet()){
                    String choiceString = solutionEntry.getExplanation().get(choice);

                    content.append("\\item ")
                            .append(choice)
                            .append(") ")
                            .append(LaTeXProcessor.fixUnderscoreFormat(questionEntry.getChoices().get(choice)))
                            .append(" \\\\ \n")
                            .append(LaTeXProcessor.fixUnderscoreFormat(choiceString))
                            .append("\n");

                }
            } else {
                content.append("\\item No available solution in the SolutionRepository is found for this question. idk why tho");
            }


            content.append("\\end{itemize}\n");
        }

        String texContent =
                "\\documentclass[12pt]{article}\n" +
                        "\\usepackage[a4paper, margin=1in]{geometry}\n" +
                        "\\usepackage{titlesec}\n" +
                        "\\usepackage{hyperref}\n" +
                        "\\usepackage{parskip}\n" +
                        "\\usepackage{fancyhdr}\n" +
                        "\\usepackage{booktabs}\n" +
                        "\\usepackage{enumitem}\n" +
                        "\\usepackage{tikz}\n" +
                        "\\pagestyle{fancy}\n" +
                        "\\fancyhf{}\n" +
                        "\\rhead{FECP5 45/45}\n" +
                        "\\lhead{1Z0-808 Mock Exam Solution}\n" +
                        "\\rfoot{\\thepage}\n" +
                        "\n" +
                        "\\titleformat{\\section}{\\normalfont\\Large\\bfseries}{\\thesection}{1em}{}\n" +
                        "\\titleformat{\\subsection}{\\normalfont\\large\\bfseries}{\\thesubsection}{1em}{}\n" +
                        "\n" +
                        "\\title{\\textbf{1Z0-808 Mock Exam Solutions}}\n" +
                        "\\author{ExamId: " +
                        solutionSheetId +
                        "}\n" +
                        "\\date{\\today}\n" +
                        "\n" +
                        "\\begin{document}\n" +
                        "\n" +
                        "\\maketitle\n" +
                        "\\newpage" +
                        "\\begin{enumerate}[label=(\\arabic*)]\n" +
                        content.toString() +
                        "\n" +
                        "\\end{enumerate}\n" +
                        "\n" +
                        "\\end{document}";

//        System.out.println(texContent);
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
//            if (exitCode == 0) {
//                System.out.println("✅ PDF generated successfully.");
//            } else {
//                System.out.println("❌ PDF generation failed. Exit code: " + exitCode);
//            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
