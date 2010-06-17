package com.bc.ceres.site.util;

import com.bc.ceres.site.SiteCreator;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.DOMBuilder;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Basic helper class which allows to generate and extend a file (exclusion_list.csv). That file contains a number of
 * modules which are obtained by one or more given POMs, and are written in single row, comma separated.
 *
 * @author Thomas Storm
 * @version 1.0
 */
public class ExclusionListBuilder {

    private static final String MODULE_NAME = "module";
    private static final String MODULES_NODE = "modules";

    public static final String EXCLUSION_LIST_FILENAME = "exclusion_list.csv";
    public static final char CSV_SEPARATOR = ',';
    public static final char[] CSV_SEPARATOR_ARRAY = new char[]{CSV_SEPARATOR};
    public static final String POM_LIST_FILENAME = "pom_list";

    public static void main(String[] args) {
        File exclusionList;
        String pomListFileName = POM_LIST_FILENAME;
        if (args.length < 1) {
            exclusionList = new File(EXCLUSION_LIST_FILENAME);
        } else {
            exclusionList = new File(args[0] + File.pathSeparator + EXCLUSION_LIST_FILENAME);
        }
        if (args.length > 1) {
            pomListFileName = args[1];
        }
        try {
            generateExclusionList(exclusionList, ExclusionListBuilder.retrievePoms(pomListFileName));
            System.out.println("Written exclusion list to " + exclusionList.getAbsolutePath() + ".");
        } catch (Exception ignored) {
        }
    }

    static void generateExclusionList(File exclusionList, List<URL> poms) throws ParserConfigurationException,
                                                                                 IOException,
                                                                                 SAXException {
        for (URL pom : poms) {
            addPomToExclusionList(exclusionList, pom);
        }
    }

    static void addPomToExclusionList(File exclusionList, URL pom) throws ParserConfigurationException,
                                                                          IOException, SAXException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(exclusionList, true));
        try {
            final DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            final Document w3cDoc = builder.parse(pom.openStream());
            final DOMBuilder domBuilder = new DOMBuilder();
            final org.jdom.Document doc = domBuilder.build(w3cDoc);
            final Element root = doc.getRootElement();
            final Namespace namespace = root.getNamespace();
            final List<Element> modules = root.getChildren(MODULES_NODE, namespace);
            if (modules != null) {
                // hard-coded index 0 is ok because xml-schema allows only one <modules>-node
                final Element modulesNode = modules.get(0);
                final List<Element> modulesList = (List<Element>) modulesNode.getChildren(MODULE_NAME, namespace);
                for (Element module : modulesList) {
                    addModuleToExclusionList(exclusionList, writer, module.getText());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            writer.close();
        }

    }

    static void addModuleToExclusionList(File exclusionList, Writer writer, String moduleName) throws IOException {
        CsvReader reader = new CsvReader(new FileReader(exclusionList), CSV_SEPARATOR_ARRAY);
        final String[] records = reader.readRecord();
        List<String> recordList = new ArrayList<String>();
        if (records != null) {
            recordList.addAll(Arrays.asList(records));
        }

        if (!recordList.contains(moduleName)) {
            writer.write(moduleName);
            writer.write(CSV_SEPARATOR);
        }
    }

    static List<URL> retrievePoms(String fileName) {
        List<URL> pomList = new ArrayList<URL>();
        try {
            final String pomListFile = SiteCreator.class.getResource(fileName).getFile();
            final BufferedReader reader = new BufferedReader(new FileReader(pomListFile));
            String line;
            while ((line = reader.readLine()) != null) {
                pomList.add(new URL(line));
            }
        } catch (IOException e) {
            return pomList;
        }

        return pomList;
    }
}