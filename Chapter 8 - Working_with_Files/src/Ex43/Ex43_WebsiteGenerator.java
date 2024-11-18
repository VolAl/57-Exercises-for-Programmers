package Ex43;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.in;

public class Ex43_WebsiteGenerator {

    private final static String directoryPath = System.getProperty("user.home") +
                                                "\\IdeaProjects\\57-Exercises-for-Programmers\\Chapter 8 - Working_with_Files\\src\\Ex43";

    public static void main(String[] args) {

        Scanner sc = new Scanner(in);

        System.out.print("Create your website!\nSite name: ");
        String siteName = sc.nextLine();
        System.out.print("Author: ");
        String author = sc.nextLine();
        System.out.print("Do you want a folder for Javascript? ");
        String jsFolder = sc.nextLine();
        System.out.print("Do you want a folder for CSS? ");
        String cssFolder = sc.nextLine();

        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

        File directoryFile = new File(directoryPath);
        List<ProcessBuilder> builders = getProcessBuilders(isWindows, directoryFile, siteName, author, jsFolder, cssFolder);

        try {
            List<Process> processes = ProcessBuilder.startPipeline(builders);
            List<String> possibleOutput = List.of("", "/.index.html", "/js/", "/css/");
            int index = 0;
            while(index < processes.size()-1) {
                String possibleAnswer = index == 2 ?
                            jsFolder.equalsIgnoreCase("y") ? possibleOutput.get(2) : possibleOutput.get(3)
                        : possibleOutput.get(index);
                System.out.println("Created ./awesomeco" + possibleAnswer);
                index++;
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static List<ProcessBuilder> getProcessBuilders(boolean isWindows, File directoryFile, String siteName, String author, String jsFolder, String cssFolder) {
        List<ProcessBuilder> builders = new ArrayList<>();
        if (isWindows) {

            ProcessBuilder builder = new ProcessBuilder();
            builder.directory(directoryFile);
            builder.command("cmd.exe", "/c", "mkdir awesomeco");

            builders.add(builder);

            String cmdCreateFile = "type nul > awesomeco\\index.html";
            builder = new ProcessBuilder();
            builder.directory(directoryFile);
            builder.command("cmd.exe", "/c", cmdCreateFile);

            builders.add(builder);

            String catFile = "echo \"" +
            "<!DOCTYPE html>" +
            "<html>" +
            "<head>" +
            "<meta>" + author + "</meta><title>" + siteName  +"</title>" +
            "</head><body></body></html>\" >> awesomeco\\index.html";
            builder = new ProcessBuilder();
            builder.directory(directoryFile);
            builder.command("cmd.exe", "/c", catFile);

            builders.add(builder);

            if(jsFolder.equalsIgnoreCase("y")) {
                builder = new ProcessBuilder();
                builder.directory(directoryFile);
                builder.command("cmd.exe", "/c", "mkdir awesomeco\\js");

                builders.add(builder);
            }

            if(cssFolder.equalsIgnoreCase("y")) {
                builder = new ProcessBuilder();
                builder.directory(directoryFile);
                builder.command("cmd.exe", "/c", "mkdir awesomeco\\css");

                builders.add(builder);
            }

        } else {
            ProcessBuilder builder = new ProcessBuilder();
            builder.directory(directoryFile);
            builder.command("sh", "-c", "mkdir -p ./awesomeco");

            builders.add(builder);

            String catFile = "\"cat > ./awesomeco/index.html << EOF" +
                             "<!DOCTYPE html>" +
                             "<html>" +
                             "<head>" +
                             "<meta>" + author + "</meta><title>" + siteName  +"</title>" +
                             "</head><body></body></html> EOF";
            builder = new ProcessBuilder();
            builder.directory(directoryFile);
            builder.command("sh", "-c", catFile);

            builders.add(builder);

            if(jsFolder.equalsIgnoreCase("y")) {
                builder = new ProcessBuilder();
                builder.directory(directoryFile);
                builder.command("sh", "-c", "mkdir -p ./awesomeco/js");

                builders.add(builder);
            }

            if(cssFolder.equalsIgnoreCase("y")) {
                builder = new ProcessBuilder();
                builder.directory(directoryFile);
                builder.command("sh", "-c", "mkdir -p ./awesomeco/css");

                builders.add(builder);
            }
        }
        return builders;
    }
}
