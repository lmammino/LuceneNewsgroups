package lucenenewsgroups.commands;

import java.io.File;
import java.util.Queue;
import lucenenewsgroups.AppInfo;
import lucenenewsgroups.Command;
import lucenenewsgroups.Post;
import lucenenewsgroups.crawler.DiscoveredPost;
import lucenenewsgroups.crawler.PostCrawler;
import lucenenewsgroups.parser.PostParser;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * Creates the index
 * @author Luciano Mammino <lmammino[at]oryzone[dot]com>
 */
public class CreateIndexCommand extends Command {

    /**
     * The path of the directory to index
     */
    protected String directory;


    /**
     * Constructor. Creates a new instance of the command
     * @param directory the path of the directory to index
     */
    public CreateIndexCommand(String directory)
    {
        this.directory = directory;
    }


    @Override
    public void execute()
    {
        System.out.println("\nCreating index...\n\n");

        try
        {
            PostCrawler crawler = new PostCrawler(this.directory);
            Queue<String> posts = crawler.discoverUsingQueue();
            PostParser parser = new PostParser();

            FSDirectory dir = FSDirectory.open(new File(this.directory + "/" + AppInfo.indexFile));
            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_31, new StandardAnalyzer(Version.LUCENE_31));
            IndexWriter writer = new IndexWriter(dir, config);

            int size = posts.size();
            int i = 1;
            String postFile = null;
            DiscoveredPost discoveredPost = null;
            Post post = null;
            Document document = null;
            while( (postFile = posts.poll() ) != null )
            {
                discoveredPost = new DiscoveredPost(new File(crawler.getDirectory() + postFile));
                Command.showProgress(i, size);
                parser.setCategory(discoveredPost.getCategory());
                parser.setFile(discoveredPost.getFile());
                post = parser.parse();
                document = post.toDocument();
                writer.addDocument(document);

                discoveredPost = null;
                post = null;
                document = null;
                System.gc();

                i++;
            }
            
            writer.optimize();
            writer.close();

            System.out.println();
        }
        catch(Exception e)
        {
            System.err.println("CANNOT CREATE INDEX!\n");
            System.err.println(e.getMessage());
        }
    }

}
