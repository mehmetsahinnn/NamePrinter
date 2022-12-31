package com.mycompany.app;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;




public class App
{
    public static void main(String[] args) throws IOException {


        String link  = "https://en.wikipedia.org/wiki/Main_Page";
        Document doc = Jsoup.connect(link).get();
        String words = Objects.requireNonNull(doc.select("body").first()).text();


        //SENTENCE PART
        SentenceDetectorME sentenceDetector = new SentenceDetectorME(new SentenceModel(new FileInputStream("src/main/resources/opennlp-en-ud-ewt-sentence-1.0-1.9.3.bin")));

        //TOKENİZER PART
        TokenizerME tokenizer = new TokenizerME(new TokenizerModel(new FileInputStream("src/main/resources/opennlp-en-ud-ewt-tokens-1.0-1.9.3.bin")));

        //NAME FINDER PART
        NameFinderME nameFinder = new NameFinderME(new TokenNameFinderModel(new FileInputStream("src/main/resources/en-ner-person.bin")));

        String[] sentences = sentenceDetector.sentDetect(words);

        for (String sentence : sentences) {
          String[] tokens = tokenizer.tokenize(sentence);

          Span[] nameSpans = nameFinder.find(tokens);
          String[] names = Span.spansToStrings(nameSpans, tokens);


            for (String name: names) {
                System.out.println(name);
            }

        }


    }
}


