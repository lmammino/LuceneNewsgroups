package lucenenewsgroups.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lucenenewsgroups.FileUtils;
import lucenenewsgroups.Post;

/**
 * Class used to parse a post file and create the structured Post object related
 * to the given file
 * @author Luciano Mammino <lmammino[at]oryzone[dot]com>
 */
public class PostParser
{

    protected File file;
    protected int id;
    protected String content;
    protected String category;

    /**
     * Constructor. Creates a blank parser.
     * Should set the category and the file before calling the parse() method
     * @see setCategory(String)
     * @see setFile(File)
     */
    public PostParser()
    {
        this.file = null;
        this.id = 0;
        this.content = null;
        this.category = null;
    }

    /**
     * Constructor. Creates a new PostParser
     * @param file the file to parse
     * @param category the category of the file
     */
    public PostParser(File file, String category)
    {
        this.file = file;
        this.id = Integer.parseInt(file.getName());
        this.content = FileUtils.readFileContent(file);
        this.category = category;
    }

    /**
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(File file) {
        this.file = file;
        this.id = Integer.parseInt(file.getName());
        this.content = FileUtils.readFileContent(file);
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }


    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }


    /**
     * Parses the file and produces a structured Post object
     * @return a structured Post object representing the parsed file
     */
    public Post parse()
    {
        Post post = new Post(this.getFile(), this.getId(), this.getCategory());
        String[] parts = this.getContent().split("\n\n", 2);
        
        String rawHeader = parts[0];
        String text = parts[0];

        if(parts.length > 1)
            text = parts[1];

        HashMap<String,String> header = new HashMap<String,String>();
        String[] headerParts = rawHeader.split("\n");

        for(String headerLine : headerParts)
        {
            String[] headerLineParts = headerLine.split(": ", 2);
            if(headerLineParts.length == 2)
                header.put(headerLineParts[0], headerLineParts[1]);
        }

        if(header.containsKey("Xref"))
            post.setXref(this.parseXref(header.get("Xref")));

        if(header.containsKey("Newsgroups"))
            post.setNewsgroups(this.parseNewsGroups(header.get("Newsgroups")));

        if(header.containsKey("Path"))
            post.setPaths(this.parsePaths(header.get("Path")));

        if(header.containsKey("From"))
            post.setFrom(header.get("From"));

        if(header.containsKey("Subject"))
            post.setSubject(header.get("Subject"));

        if(header.containsKey("Sender"))
            post.setSender(header.get("Sender"));

        if(header.containsKey("Message-ID"))
            post.setMessageId(header.get("Message-ID"));

        if(header.containsKey("Date"))
            post.setDate(header.get("Date"));

        if(header.containsKey("Distribution"))
            post.setDistribution(header.get("Distribution"));

        if(header.containsKey("References"))
            post.setReferences(this.parseReferences(header.get("References")));

        if(header.containsKey("Organization"))
            post.setOrganization(header.get("Organization"));

        post.setText(text);

        return post;
    }


    protected List<String> parseXref(String raw)
    {
        return this.splitString(raw, " ");
    }

    protected List<String> parseNewsGroups(String raw)
    {
        return this.splitString(raw, ",");
    }

    protected List<String> parsePaths(String raw)
    {
        return this.splitString(raw, "!");
    }

    protected List<String> parseReferences(String raw)
    {
        List<String> mails = Collections.synchronizedList(new ArrayList<String>());
        
        Pattern pattern = Pattern.compile("(\\w+)(\\.\\w+)*@(\\w+\\.)(\\w+)(\\.\\w+)*");
        Matcher matcher = pattern.matcher(raw);
        while(matcher.find())
            mails.add(matcher.group());
        
        return mails;
    }

    protected List<String> splitString(String raw, String separator)
    {
        List<String> words = Collections.synchronizedList(new ArrayList<String>());
        words.addAll(Arrays.asList(raw.split(separator)));
        return words;
    }

}
