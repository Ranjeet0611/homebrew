🛠️ Custom Homebrew CLI – A Java-based Homebrew-style Package Manager

Custom homebrew is a custom package manager written in Java. It connects to a Spring Boot backend that manages package metadata and stores actual files on AWS S3. The CLI fetches and installs packages directly to your machine — just like Homebrew, but made in Java!
🚀 Features

    📦 Install any package from your own S3-backed registry

    ⬇️ Downloads and extracts .zip packages

    💾 Keeps track of installed packages locally

    ⚙️ Fully customizable backend using Spring Boot

    🧪 Lightweight and blazing fast

🧩 Architecture

[CLI App] ──> [Spring Boot Backend] ──> [AWS S3 Bucket]
| returns package metadata
| returns signed S3 download URL

🖥️ CLI Commands

custombrew install <package-name>
custombrew list
custombrew uninstall <package-name>  # (coming soon)
