package com.pminkov.search;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.lucene.document.Document;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.LeafReader;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.PostingsEnum;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.DocIdSetIterator;
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
      "This is one document about a cat and a dog",
      "This is another document about a cat",
      "Let's not forget the third one, dog and a cat",
      "And this is something else",
      "Last document. Dog. cat."
  };

  private static void search(String queryString, IndexSearcher isearcher, StandardAnalyzer analyzer) throws IOException, ParseException {
    System.out.println("Query: " + queryString);
    QueryParser parser = new QueryParser("text", analyzer);
    Query query = parser.parse(queryString);
    ScoreDoc[] hits = isearcher.search(query, 10).scoreDocs;

    System.out.println("Hits # = " + hits.length);

    for (int i = 0; i < hits.length; i++) {
      Document hitDoc = isearcher.doc(hits[i].doc);

      // Print all fields in this document.
      for (IndexableField field : hitDoc.getFields()) {
        System.out.println(field);
      }
      System.out.println(hits[i].doc + " " + hitDoc.get("text"));
    }
  }



  private static Directory indexDocs(Path indexPath, StandardAnalyzer analyzer) throws IOException {
    Directory directory = FSDirectory.open(indexPath);
    IndexWriterConfig config = new IndexWriterConfig(analyzer);
    IndexWriter iwriter = new IndexWriter(directory, config);

    for (int di = 0; di < Search.documents.length; di++) {
      Document doc = new Document();
      String text = Search.documents[di];
      doc.add(new Field("text", text, TextField.TYPE_STORED));
      iwriter.addDocument(doc);
    }
    iwriter.close();

    return directory;
  }

  public static void main(String[] args) throws IOException, ParseException {
    Path indexPath = Files.createTempDirectory("tempIndex");
    System.out.println("Location: " + indexPath.toString());

    StandardAnalyzer analyzer = new StandardAnalyzer();
    Directory directory = indexDocs(indexPath, analyzer);

    // Load the index.
    DirectoryReader ireader = DirectoryReader.open(directory);

    // Find the posting list for "dog".
    for (LeafReaderContext leaf : ireader.leaves()) {
      LeafReader leafReader = leaf.reader();
      Term t = new Term("text", "dog");
      PostingsEnum postings = leafReader.postings(t);
      System.out.println("Postings for dog:");
      int docId;
      while ((docId = postings.nextDoc()) != DocIdSetIterator.NO_MORE_DOCS) {
        System.out.print(docId + ", ");
      }
      System.out.println();
    }

    IndexSearcher isearcher = new IndexSearcher(ireader);

    search("text: \"cat\" AND \"dog\"", isearcher, analyzer);

    ireader.close();
    directory.close();
    IOUtils.rm(indexPath);
  }
}


