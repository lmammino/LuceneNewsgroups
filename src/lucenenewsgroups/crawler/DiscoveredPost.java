package lucenenewsgroups.crawler;

import java.io.File;
import lucenenewsgroups.Post;
import lucenenewsgroups.parser.PostParser;

/**
 * Class used to represent post discovered by the crawler
 * @author Luciano Mammino <lmammino[at]oryzone[dot]com>
 * @version 1.0
 */
public class DiscoveredPost
{

    /**
     * The instance of the file representing the discovered file
     */
    protected File file;


    /**
     * Constructor. Creates a new DiscoveredPost
     * @param file the referred file
     */
    public DiscoveredPost(File file)
    {
        this.file = file;
    }


    /**
     * Gets the id of the file
     * @return The id of the file
     */
    public int getId()
    {
        return Integer.getInteger(this.file.getName());
    }


    /**
     * Gets the name of the category
     * @return The category of the post
     */
    public String getCategory()
    {
        return this.file.getParentFile().getName();
    }


    /**
     * Gets a File instance for the discovered file
     * @return
     */
    public File getFile()
    {
        return this.file;
    }


    /**
     * Creates a parser and automatically instanciate it on the discovered post
     * @return a Post resulting from the parsing of the file
     */
    public Post parse()
    {
        return this.parse(new PostParser(this.file, this.getCategory()));
    }


    /**
     * Creates a parser and automatically instanciate it on the discovered post
     * @return a Post resulting from the parsing of the file
     */
    public Post parse(PostParser parser)
    {
        parser.setFile(this.file);
        parser.setCategory(this.getCategory());
        return parser.parse();
    }



}
