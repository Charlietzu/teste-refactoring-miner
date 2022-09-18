package org.example;

import org.eclipse.jgit.lib.Repository;
import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.api.GitService;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringHandler;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.util.GitServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        GitService gitService = new GitServiceImpl();
        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();

        try {

            Repository repo = gitService.cloneIfNotExists(
                    "tmp/refactoring-toy-example",
                    "https://github.com/danilofes/refactoring-toy-example.git");

            System.out.println("------- INÍCIO REFACTORING MINER -------");

            System.out.println("------- INÍCIO DETECT ALL -------");
            miner.detectAll(repo, "master", new RefactoringHandler() {
                @Override
                public void handle(String commitId, List<Refactoring> refactorings) {
                    System.out.println("Refactorings at " + commitId);
                    for (Refactoring ref : refactorings) {
                        System.out.println(ref.toString());
                    }
                }
            });
            System.out.println("------- FIM DETECT ALL -------");

            System.out.println("------- INÍCIO DETECT BETWEEN COMMITS -------");
            miner.detectBetweenCommits(repo,
                    "819b202bfb09d4142dece04d4039f1708735019b", "d4bce13a443cf12da40a77c16c1e591f4f985b47",
                    new RefactoringHandler() {
                        @Override
                        public void handle(String commitId, List<Refactoring> refactorings) {
                            System.out.println("Refactorings at " + commitId);
                            for (Refactoring ref : refactorings) {
                                System.out.println(ref.toString());
                            }
                        }
                    });
            System.out.println("------- FIM DETECT BETWEEN COMMITS -------");

            System.out.println("------- INÍCIO DETECT AT COMMIT -------");
            miner.detectAtCommit(repo, "05c1e773878bbacae64112f70964f4f2f7944398", new RefactoringHandler() {
                @Override
                public void handle(String commitId, List<Refactoring> refactorings) {
                    System.out.println("Refactorings at " + commitId);
                    for (Refactoring ref : refactorings) {
                        System.out.println(ref.toString());
                    }
                }
            });
            System.out.println("------- FIM DETECT AT COMMIT -------");

            System.out.println("------- FIM REFACTORING MINER -------");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}