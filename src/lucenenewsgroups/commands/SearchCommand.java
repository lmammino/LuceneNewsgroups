package lucenenewsgroups.commands;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import lucenenewsgroups.AppInfo;
import lucenenewsgroups.Command;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * Command used to perform a search query on the indexed documents
 * @author Luciano Mammino <lmammino[at]oryzone[dot]com>
 * @version 1.0
 */
public class SearchCommand extends Command {

    /**
     * The query to perform
     */
    protected String query;

    /**
     * Constructor. Creates a new search command with a given query
     * @param query the query to perform
     */
    public SearchCommand(String query)
    {
        this.query = query;
    }

    
    @Override
    public void execute()
    {

        File indexFile = new File(AppInfo.indexFile);
        if(!indexFile.exists())
        {
            System.out.println("The directory '" + indexFile.getAbsolutePath() + "' has not been indexed yet");
        }
        else
        {
            try {
                FSDirectory index = FSDirectory.open(indexFile);
                Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_31);
                Query q = new QueryParser(Version.LUCENE_31, "text", analyzer).parse(this.query);

                int hitsPerPage = 10;
                IndexSearcher searcher = new IndexSearcher(index, true);
                TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
                searcher.search(q, collector);
                ScoreDoc[] hits = collector.topDocs().scoreDocs;

                System.out.println("Found " + hits.length + " hits.");
                for(int i=0;i<hits.length;++i) {
                    int docId = hits[i].doc;
                    Document d = searcher.doc(docId);
                    System.out.println((i + 1) + ". " + "(" + d.get("id") + ") " + d.get("subject") );
                }

            } catch (Exception ex) {
                Logger.getLogger(SearchCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
