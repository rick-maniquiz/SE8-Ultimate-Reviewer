# Java SE8 Mock Exam & Solution Sheet Generator

### Author: Rick

> ⚠️ *Note: Reviewer generation and content are still under development. Stay tuned for upcoming improvements.*

This tool generates a **printable PDF mock exam** and its corresponding **solution sheet** using LaTeX. It's developed in **Java SE 8** and uses **Maven** for dependency and build management.

---

## ✅ Features

* Generates randomized mock exams
* Automatically creates a matching solution sheet
* Outputs both as high-quality LaTeX-generated PDFs
* Lightweight and easy to use

---

## 📦 Requirements

* Java 8 (Java SE)
* Maven
* `pdflatex` (LaTeX compiler, typically provided by TeX Live)

---

## ⚙️ Setup Instructions

### 🖥 macOS (with Homebrew)

Install a lightweight LaTeX distribution:

```bash
brew install --cask basictex
```

> After installation, you may need to run `sudo tlmgr update --self && sudo tlmgr install <package>` to install missing LaTeX packages.

---

### 🔐 macOS (Work Laptop with Admin Restrictions)

If you're unable to install LaTeX using Homebrew due to admin privileges, use a **Linux virtual machine** (such as Ubuntu) for running this project.

Install LaTeX on Ubuntu/Debian-based systems:

```bash
sudo apt update
sudo apt install texlive-latex-recommended texlive-latex-extra texlive-fonts-recommended
```

---

## 🚀 Usage Example

```java
ReviewerRepository reviewerRepository = new ReviewerRepository();

Exam exam = new Exam(56, "hard");
SolutionSheet solutionSheet = new SolutionSheet(exam);

exam.generatePDF();
solutionSheet.generatePDF();

reviewerRepository.printAllReviewers();
```
### Difficulty Levels

When creating an `Exam`, you can choose from the following difficulty levels:

* `"easy"`
* `"medium"`
* `"hard"`

---

### What This Does:

* Creates an exam with ID `56` and difficulty `"hard"`
* Generates a corresponding solution sheet
* Compiles both into PDF format using `pdflatex`
* Prints all reviewers from the `ReviewerRepository`

---

## 📌 Notes

* Ensure that `pdflatex` is accessible from your system's terminal.
* Output files will be saved in the working directory.
* This project is intended for **offline generation** of mock exams and is ideal for educators, trainers, or self-review.

