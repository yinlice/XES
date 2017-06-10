package Wxzt.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
* Created by Pengxi on 2015/8/26.
*/
public class IniReader{
    protected HashMap sections = new HashMap();
    protected transient String currentSecion;
    private transient Properties current;

    public IniReader(String filename) throws IOException {
        File f = new File(JDBC.class.getClassLoader().getResource("/").getPath());
        String confURL = f+getFileJoin()+"conf"+getFileJoin()+"sysconf.ini";
        BufferedReader reader = new BufferedReader(new FileReader(confURL));
        read(reader);
        reader.close();
    }

    public IniReader() throws IOException {
        File f = new File(JDBC.class.getClassLoader().getResource("/").getPath());
        String confURL = f+getFileJoin()+"conf"+getFileJoin()+"sysconf.ini";
        BufferedReader reader = new BufferedReader(new FileReader(confURL));
        read(reader);
        reader.close();
    }

    protected void read(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            parseLine(line);
        }
    }

    protected void parseLine(String line) {
        line = line.trim();
        if (line.matches("\\[.*\\]")) {
            currentSecion = line.replaceFirst("\\[(.*)\\]", "$1");
            current = new Properties();
            sections.put(currentSecion, current);
        } else if (line.matches(".*=.*")) {
            if (current != null) {
                int i = line.indexOf('=');
                String name = line.substring(0, i);
                String value = line.substring(i + 1);
                current.setProperty(name, value);
            }
        }
    }

    public String getValue(String section, String name) {
        Properties p = (Properties) sections.get(section);
        if (p == null) {
            return null;
        }
        return p.getProperty(name);
    }

    /*判断文件路径间隔符号*/
    public String getFileJoin(){
        return System.getProperty("file.separator");
    }
}
