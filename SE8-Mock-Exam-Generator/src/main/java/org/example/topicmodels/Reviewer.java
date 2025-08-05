package org.example.topicmodels;

import org.example.exammodels.QuestionEntry;
import org.example.utils.LaTeXProcessor;
import org.example.utils.Printable;

import java.io.*;

import static org.example.utils.LaTeXProcessor.fixVerbatimString;

public class Reviewer implements Printable {
    private int topicId;
    private String topic;
    private String explanation;

    public void generatePDF(){
        String texFileName = "reviewer-topic-" + topicId + ".tex";
        String outputDir = "output/reviewers";
        String content = explanation;

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
                        "\\lhead{" +
                        topic + //topic title
                        "}\n" +
                        "\\rfoot{\\thepage}\n" +
                        "\n" +
                        "\\titleformat{\\section}{\\normalfont\\Large\\bfseries}{\\thesection}{1em}{}\n" +
                        "\\titleformat{\\subsection}{\\normalfont\\large\\bfseries}{\\thesubsection}{1em}{}\n" +
                        "\n" +
                        "\\title{\\textbf{1Z0-808 Exam Topic Reviewer}}\n" +
                        "\\author{TopicId: " +
                        topicId +
                        " \\\\ Topic: " +
                        topic +
                        "}\n" +
                        "\\date{\\today}\n" +
                        "\n" +
                        "\\begin{document}\n" +
                        "\n" +
                        "\\maketitle\n" +
                        "\\newpage" +
                        "\\begin{enumerate}[label=(\\arabic*)]\n" +
                        content +
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
            if (texFile.exists()){
                return;
            }
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

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
