package com.nicklase.bilteori;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

public class PullParser {

	private static final String ns = null;
	   
    public List<Question> parse(InputStream in) throws XmlPullParserException, IOException {
    	XmlPullParser parser = Xml.newPullParser();
    	List<Question> questionList= new ArrayList();
    	try {		
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
           // Log.w("myApp", "next tagg:" + parser.nextText());
            questionList= readFeed(parser);
            Log.w("myApp","test"+questionList.get(0).toString());
            
        }catch(IOException e){
        	
        	Log.w("myApp",e.toString());
        	parser=null; 
        }
        finally {
            in.close();
        }
    	return questionList;
    }
    private List<Question> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Question> questions = new ArrayList();
        // noe feil her , må fikses
        parser.require(XmlPullParser.START_TAG, ns, "traffic");
        while (parser.nextTag() != XmlPullParser.END_TAG) {
           
            String name = parser.getName();
          //  Log.w("myApp","navnet til parser:"+name);
            // Starts by looking for the entry tag
            questions = readQuestion(parser);
        }  
        return questions;
    }
    private List<Question> readQuestion(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Question> questions = new ArrayList();
        // noe feil her , må fikses
        parser.require(XmlPullParser.START_TAG, ns, "category");
       
      
        while (parser.nextTag() != XmlPullParser.END_TAG) {
            String name = parser.getName();
            
            questions.add(readQuestionInfo(parser));
        }  
        return questions;
    }
 
     
    
    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
    // to their respective "read" methods for processing. Otherwise, skips the tag.
    private Question readQuestionInfo(XmlPullParser parser) throws XmlPullParserException, IOException {
    	//Log.w("myApp","Starting readQ");
    	parser.require(XmlPullParser.START_TAG, ns, "question");
    	
    	String catName = null;
        String formulation = null;
        String rightAnswer = null;
        //Log.w("myApp","Leser spørsmål");
        List<String> alternatives = new ArrayList<String>();
       
        while (parser.nextTag() != XmlPullParser.END_TAG) {
           
            String name = parser.getName();
            
            if(name.equals("formulation")){
                 formulation = parser.nextText().trim();
            }
            else if(name.equals("alternative")){
            	if(parser.getAttributeCount()>0){
            	if(parser.getAttributeValue(null, "isCorrect").equals("true")){
            		rightAnswer=parser.nextText().trim();
            		alternatives.add(rightAnswer);
            	}}else
            	alternatives.add(parser.nextText().trim());
            	//}
            }
        }
        
        return new Question(catName, formulation, alternatives,rightAnswer);
    }

    // Processes title tags in the feed.
    private String readAlternative(XmlPullParser parser) throws IOException, XmlPullParserException {
       // parser.require(XmlPullParser.START_TAG, ns, "alternative");
        String alternative = readText(parser);
       
            
        parser.nextTag();
        return alternative;
    }
      
    // Processes link tags in the feed.
    private String readFormulation(XmlPullParser parser) throws IOException, XmlPullParserException {
        String formulation = "";
        //parser.require(XmlPullParser.START_TAG, ns, "formulation");
        String tag = parser.getName();
        if (tag.equals("formulation")) {
                formulation = readText(parser);
               // parser.nextTag();
        }
     //   parser.require(XmlPullParser.END_TAG, ns, "formulation");
        return formulation;
    }


    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText().trim();
           // parser.nextTag();
        }
        return result;
    }
    
    //hopper over de taggene vi ikke trenger.

        
     

}
