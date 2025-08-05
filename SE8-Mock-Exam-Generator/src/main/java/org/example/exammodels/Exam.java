package org.example.exammodels;

import org.example.topicmodels.Topic;
import org.example.topicmodels.TopicRepository;
import org.example.utils.LaTeXProcessor;
import org.example.utils.Printable;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.example.utils.LaTeXProcessor.fixVerbatimString;

public class Exam implements Printable {




    private static TopicRepository topicRepository = new TopicRepository();
    private static QuestionRepository questionRepository = new QuestionRepository();
    private static SolutionRepository solutionRepository = new SolutionRepository();
    private static ExamRepository examRepository = new ExamRepository();


    private static int examGenerated = ExamRepository.getExams().size();

    private final int examId;
    public int questionCount;
    private List<QuestionEntry> examQuestions = new ArrayList<>();
    private String difficulty;

    {
        examId = 100 + examGenerated++;
    }

    public Exam(){

    }

    public Exam(int questionCount, String difficulty) throws IOException {
        this.questionCount = questionCount;
        this.difficulty = difficulty;
//        TopicRepository topicRepository = new TopicRepository();
        int i = 0, j=0;
        while (true){
            if (questionCount < i || j == questionCount*2) break;
            for (Topic topic : TopicRepository.getTopics()){
//            System.out.println(topic);
                Collections.shuffle(topic.getTopicQuestions());
                for (QuestionEntry questionEntry : topic.getTopicQuestions()){
                    if (!questionEntry.isAttempted()
                            && questionEntry
                            .getDifficulty()
                            .equalsIgnoreCase(this.difficulty)){
                        if (questionCount < ++i || j == questionCount*2) break;
                        questionEntry.attempted();
                        examQuestions.add(questionEntry);
//                        i++;
//                        System.out.println(i);
                        break;
                    }
                }
                j++;
            }
        }
        Collections.shuffle(examQuestions);
        examGenerated += 1;
        ExamRepository.addExam(this);
        examRepository.saveProgress();
        questionRepository.saveProgress();
    };

    @Override
    public void generatePDF(){
        String texFileName = "mock-exam-" + examId + ".tex";
        String outputDir = "output/exams";
        StringBuilder content = new StringBuilder();

        for (QuestionEntry questionEntry : examQuestions){
            content.append("\\item (")
                    .append("questionId: ")
                    .append(questionEntry.getQuestionId())
                    .append(") ")
                    .append(fixVerbatimString(questionEntry.getQuestion()))
                    .append("\n");
            if (questionEntry.getType().equalsIgnoreCase("single")){
                content.append("Choose the most correct answer. \n");
            } else {
                content.append("Choose all the correct answer.");
            }
            content.append("\\begin{itemize}\n");
            for (String choice : questionEntry.getChoices().keySet()){
                String choiceString = questionEntry.getChoices().get(choice);

//                if (!choiceString.trim().startsWith("\\begin") && choiceString.indexOf('_') != -1)
//                content.append("\\item ")
//                        .append(choice)
//                        .append(") ")
//                        .append("\\begin{verbatim}")
//                        .append(choiceString)
//                        .append("\\end{verbatim}")
//                        .append("\n");
//                else content.append("\\item ")
//                            .append(choice)
//                            .append(") ")
//                            .append(choiceString)
//                            .append("\n");
                content.append("\\item ")
                        .append(choice)
                        .append(") ")
                        .append(LaTeXProcessor.fixUnderscoreFormat(choiceString))
                        .append("\n");


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
                        "\\lhead{1Z0-808 Mock Exam}\n" +
                        "\\rfoot{\\thepage}\n" +
                        "\n" +
                        "\\titleformat{\\section}{\\normalfont\\Large\\bfseries}{\\thesection}{1em}{}\n" +
                        "\\titleformat{\\subsection}{\\normalfont\\large\\bfseries}{\\thesubsection}{1em}{}\n" +
                        "\n" +
                        "\\title{\\textbf{1Z0-808 Mock Exam}}\n" +
                        "\\author{ExamId: " +
                        examId +
                        " \\\\ Items: " +
                        questionCount +
                        " \\\\ Dificulty: " +
                        difficulty.toUpperCase() +
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

    public List<QuestionEntry> getExamQuestions() {
        return examQuestions;
    }

    public int getExamId(){
        return examId;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setExamQuestions(List<QuestionEntry> examQuestions) {
        this.examQuestions = examQuestions;
    }

}
