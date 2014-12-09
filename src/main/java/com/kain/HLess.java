package com.kain;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.FileWriter;
import java.util.Properties;

/**
 * Created by Kain on 2014/12/9.
 */
public class HLess {

    public static final String TEMPLATE_FILE_SUFFIX = ".vm";

    public static final String PAGE_FILE_SUFFIX = ".html";

    public static void main(String[] args) {

        if (args.length == 0) {
            printHelp();
            return;
        }

        String pwd = System.getProperty("user.dir");

        String vmFileName = args[0] + TEMPLATE_FILE_SUFFIX;
        String templatePath = pwd + File.separator + "template";
        String distPath = pwd + File.separator + "dist";

        try  {
            String file = templatePath + File.separator + vmFileName;
            File vmFile = new File(file);
            if (!vmFile.exists()) {
                printVmFileNotExist(file);
                return;
            }

            System.out.println(templatePath);
            Properties p = new Properties();
            p.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
            p.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
//            p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, templatePath);


            Velocity.init(p);
            VelocityContext context = new VelocityContext();
            Template template = Velocity.getTemplate(vmFileName);


            File fDistPath = new File(distPath);
            if (!fDistPath.exists()) {
                fDistPath.mkdirs();
            }
            String distFileName = fDistPath + File.separator + args[0] + PAGE_FILE_SUFFIX;

            File distFile = new File(distFileName);
            if (distFile.exists()) {
                distFile.delete();
            }
            distFile.createNewFile();
            distFile.setWritable(true);

            FileWriter fileWriter = new FileWriter(distFileName);
            template.merge( context, fileWriter );

            fileWriter.flush();
            fileWriter.close();

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static void printHelp() {
        System.out.println("./hless file");
    }

    private static void printVmFileNotExist(String filePath) {
        System.out.println("File '" + filePath + "' is not exist.");
    }
}
