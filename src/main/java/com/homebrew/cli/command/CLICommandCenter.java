package com.homebrew.cli.command;

import picocli.CommandLine;

@CommandLine.Command(name = "custombrew", mixinStandardHelpOptions = true, version = "custombrew 1.0",
        description = "Homebrew-style package manager")
public class CLICommandCenter implements Runnable {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new CLICommandCenter())
                .addSubcommand(new InstallCommand())
                .execute(args);
        System.exit(exitCode);
    }
    @Override
    public void run() {
        System.out.println("Welcome to CustomBrew CLI! Use --help to see available commands.");
    }
}
