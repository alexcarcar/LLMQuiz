# 🤖 LLM Quiz Master — A Modular Java Quiz App for LLM Concepts

**LLM Quiz Master** is a Java-based application designed to test and reinforce understanding of key terms and concepts related to Large Language Models (LLMs). Built with clean architecture and modular design principles, this project serves as both a learning tool and a showcase of good software engineering practices.

---

## 🎯 Features

- 🧠 **LLM-Focused Questions**  
  Covers terminology like tokenization, attention mechanisms, embeddings, transformers, fine-tuning, and more.

- 🧩 **Modular Architecture**  
  Core components are decoupled for clarity and reusability.

- 🔄 **Reusable Question Types**  
  Supports multiple-choice, true/false, fill-in-the-blank, and matching formats.

- 🧪 **Extensible Design**  
  Easily add new question types or topics without touching core logic.

- 📊 **Scoring & Feedback**  
  Tracks performance and provides instant feedback for learning reinforcement.

---

## 🛠️ Design Highlights

- **Separation of Concerns**  
  Logic for question generation, user interaction, and scoring are split into distinct modules.

- **Strategy Pattern**  
  Each question type implements a common interface, allowing dynamic selection and rendering.

- **Factory Pattern**  
  Used to instantiate question objects based on type, promoting scalability.

- **Main Class as Orchestrator**  
  The `QuizApp.java` file delegates responsibilities to specialized components, avoiding monolithic code.

## 🚀 Getting Started

Clone the repo and run the app:

```bash
git clone https://github.com/yourusername/LLMQuizMaster.git
cd LLMQuizMaster
javac src/main/*.java
java src/main/QuizApp
