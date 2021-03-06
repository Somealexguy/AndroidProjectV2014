package com.nicklase.bilteori.logic;

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
	/// <summary>
	///   Parses the xml file.
	/// </summary>
	public List<Question> parse(InputStream in) throws XmlPullParserException, IOException {
		XmlPullParser parser = Xml.newPullParser();
		List<Question> questionList= new ArrayList<Question>();
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
	/// <summary>
	///   Reads the xml feed.
	/// </summary>
	private List<Question> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
		List<Question> questions = new ArrayList<Question>();
		// noe feil her , m� fikses
		parser.require(XmlPullParser.START_TAG, ns, "traffic");
		while (parser.nextTag() != XmlPullParser.END_TAG) {

			//String name = parser.getName();
			//  Log.w("myApp","navnet til parser:"+name);
			// Starts by looking for the entry tag
			questions.addAll(readQuestion(parser));
		}  
		return questions;
	}
	/// <summary>
	///   Reads each question.
	/// </summary>
	private List<Question> readQuestion(XmlPullParser parser) throws XmlPullParserException, IOException {
		List<Question> questions = new ArrayList<Question>();
		parser.require(XmlPullParser.START_TAG, ns, "category");


		while (parser.nextTag() != XmlPullParser.END_TAG) {
			// String name = parser.getName();

			questions.add(readQuestionInfo(parser));
		}  
		return questions;
	}



	/// <summary>
	///   Reads each line inside the question tag.
	/// </summary>
	private Question readQuestionInfo(XmlPullParser parser) throws XmlPullParserException, IOException {
		//Log.w("myApp","Starting readQ");
		parser.require(XmlPullParser.START_TAG, ns, "question");

		String catName = null;
		String formulation = null;
		String rightAnswer = null;
		String image = null;
		//Log.w("myApp","Leser sp�rsm�l");
		List<String> alternatives = new ArrayList<String>();

		while (parser.nextTag() != XmlPullParser.END_TAG) {

			String name = parser.getName();

			if(name.equals("formulation")){
				if(parser.getAttributeValue(null, "isImage") != null){
					image = parser.getAttributeValue(null, "isImage").trim().toString();
				}
				formulation = parser.nextText().trim();
				// Added this so it would work on android phone with api level lower than 15
				if (parser.getEventType() != XmlPullParser.END_TAG) {
					parser.next();
				}

			}
			else if(name.equals("alternative")){
				if(parser.getAttributeCount()>0){

					if(parser.getAttributeValue(null, "isCorrect").equals("true")){
						rightAnswer=parser.nextText().trim();
						// Added this so it would work on android phone with api level lower than 15
						if (parser.getEventType() != XmlPullParser.END_TAG) {
							parser.next();
						}
						alternatives.add(rightAnswer);
					}

				}else{
					String alt=parser.nextText().trim();
					// Added this so it would work on android phone with api level lower than 15
					if (parser.getEventType() != XmlPullParser.END_TAG) {
						parser.next();
					}
					alternatives.add(alt);
				}

			}
		}

		return new Question(catName, formulation, alternatives,rightAnswer,image);
	}


}
