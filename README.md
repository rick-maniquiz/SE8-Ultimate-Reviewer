# Java SE8 Mock Exam & Solution Sheet Generator
### Created by: Rafael Nico T. Maniquiz

This tool generates a printable PDF mock exam and its corresponding solution sheet using LaTeX. It is built with **Java 8 (SE)** and uses **Maven**.

---

## Requirements

- Java 8
- Maven
- `pdflatex` (from TeX Live)

---

## Setup Instructions

### Install a LaTeX for macOS

```bash
brew install --cask basictex
```

### If you're on a restricted macOS work laptop

Since admin permissions may be required to install LaTeX tools using Homebrew, it's recommended to use a **Linux virtual machine (e.g., Ubuntu)**.

```bash
sudo apt update
sudo apt install texlive-latex-recommended texlive-latex-extra texlive-fonts-recommended
```
