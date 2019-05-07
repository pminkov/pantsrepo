package com.pminkov.search;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntField;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.util.IOUtils;
//import org.apache.lucene.document.


public class Search {
  private static String[] documents = {
      "This is one document",
      "This is another document",
      "Let's not forget the third one",
      "And this is something else",
      "Last document"
  };

  // From 1 to 5. 1 most important.
  private static int[] importance = {
      5, 1, 3, 2, 5
  };

  private static void search(String queryString, IndexSearcher isearcher, StandardAnalyzer analyzer) throws IOException, ParseException {
    System.out.println("Query: " + queryString);
    QueryParser parser = new QueryParser("fieldname", analyzer);
    Query query = parser.parse(queryString);
    ScoreDoc[] hits = isearcher.search(query, 10).scoreDocs;
    System.out.println("Hits # = " + hits.length);
    for (int i = 0; i < hits.length; i++) {
      Document hitDoc = isearcher.doc(hits[i].doc);
      System.out.println(hits[i].doc + " " + hitDoc.get("fieldname"));
    }
  }


  private static Directory indexDocs(Path indexPath, StandardAnalyzer analyzer) throws IOException {
    Directory directory = FSDirectory.open(indexPath);
    IndexWriterConfig config = new IndexWriterConfig(analyzer);
    IndexWriter iwriter = new IndexWriter(directory, config);

    for (int di = 0; di < Search.documents.length; di++) {
      Document doc = new Document();
      String text = Search.documents[di];
      doc.add(new Field("fieldname", text, TextField.TYPE_STORED));
      //doc.add(new IntField("importance", Search.importance[di]));
      iwriter.addDocument(doc);
    }
    iwriter.close();

    return directory;
  }

  public static void main(String[] args) throws IOException, ParseException {
    Path indexPath = Files.createTempDirectory("tempIndex");
    StandardAnalyzer analyzer = new StandardAnalyzer();
    Directory directory = indexDocs(indexPath, analyzer);

    // Now search the index:
    DirectoryReader ireader = DirectoryReader.open(directory);
    IndexSearcher isearcher = new IndexSearcher(ireader);

    search("fieldname: \"document\" OR \"something\"", isearcher, analyzer);

    ireader.close();
    directory.close();
    IOUtils.rm(indexPath);
  }
}


